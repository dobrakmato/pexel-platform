// @formatter:off
/*
 * Pexel Project - Minecraft minigame server platform. 
 * Copyright (C) 2014 Matej Kormuth <http://www.matejkormuth.eu>
 * 
 * This file is part of Pexel.
 * 
 * Pexel is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Pexel is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 */
// @formatter:on
package eu.matejkormuth.pexel.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLException;

import eu.matejkormuth.pexel.commons.Logger;

public class NettyServerComunicator extends MessageComunicator {
    protected final ChannelGroup                     channels        = new DefaultChannelGroup(
                                                                             GlobalEventExecutor.INSTANCE);
    protected Map<String, ChannelHandlerContext>     ctxByName       = new HashMap<String, ChannelHandlerContext>();
    protected Map<ChannelHandlerContext, ServerInfo> serverInfoByCTX = new HashMap<ChannelHandlerContext, ServerInfo>();
    protected MasterServer                           server;
    protected String                                 authKey;
    
    protected Logger                                 log;
    protected ServerBootstrap                        b;
    protected int                                    port;
    
    static final boolean                             SSL             = System.getProperty("ssl") != null;
    
    public NettyServerComunicator(final PayloadHandler handler, final int port,
            final String authKey, final MasterServer server) {
        super(handler);
        
        this.authKey = authKey;
        this.log = server.getLogger().getChild("Netty");
        this.server = server;
        this.port = port;
    }
    
    @Override
    public void start() {
        // Use separated thread for netty, to not block main thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    NettyServerComunicator.this.init(NettyServerComunicator.this.port);
                } catch (SSLException | CertificateException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Netty-ListenThread").start();;
    }
    
    @Override
    public void stop() {
        this.b.group().shutdownGracefully();
    }
    
    public void init(final int port) throws SSLException, CertificateException,
            InterruptedException {
        this.log.info("Initializing SSL...");
        SelfSignedCertificate ssc = new SelfSignedCertificate("pexel.eu");
        SslContext sslCtx = SslContext.newServerContext(ssc.certificate(),
                ssc.privateKey());
        
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            this.log.info("Starting up server...");
            this.b = new ServerBootstrap();
            this.b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.ERROR))
                    .childHandler(new NettyServerComunicatorInitializer(sslCtx));
            
            this.b.bind(port).sync().channel().closeFuture().sync();
        }
        finally {
            this.log.info("Stopping server...");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    
    @Override
    public void send(final ServerInfo target, final byte[] payload) {
        this.getCTX(target).writeAndFlush(new NettyMessage(payload));
    }
    
    public ServerInfo getServerInfo(final ChannelHandlerContext ctx) {
        return this.serverInfoByCTX.get(ctx);
    }
    
    public ChannelHandlerContext getCTX(final ServerInfo serverinfo) {
        return this.ctxByName.get(serverinfo.getName());
    }
    
    class NettyServerComunicatorInitializer extends ChannelInitializer<SocketChannel> {
        private final SslContext sslCtx;
        
        public NettyServerComunicatorInitializer(final SslContext sslCtx) {
            this.sslCtx = sslCtx;
        }
        
        @Override
        public void initChannel(final SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            
            // Add SSL handler first to encrypt and decrypt everything.
            // In this example, we use a bogus certificate in the server side
            // and accept any invalid certificates in the client side.
            // You will need something more complicated to identify both
            // and server in the real world.
            pipeline.addLast(this.sslCtx.newHandler(ch.alloc()));
            
            // On top of the SSL handler, add messages decoder and encoder.
            pipeline.addLast(new NettyMessageDecoder());
            pipeline.addLast(new NettyMessageEncoder());
            
            // and then business logic.
            pipeline.addLast(new NettyServerComunicatorHandler(
                    NettyServerComunicator.this));
        }
    }
    
    class NettyServerComunicatorHandler extends
            SimpleChannelInboundHandler<NettyMessage> {
        private final NettyServerComunicator i;
        
        public NettyServerComunicatorHandler(
                final NettyServerComunicator nettyServerComunicator) {
            this.i = nettyServerComunicator;
        }
        
        @Override
        public void channelActive(final ChannelHandlerContext ctx) {
            // Once session is secured, send a greeting and register the channel to the global channel
            // list so the channel received the messages from others.
            ctx.pipeline()
                    .get(SslHandler.class)
                    .handshakeFuture()
                    .addListener(new GenericFutureListener<Future<Channel>>() {
                        @Override
                        public void operationComplete(final Future<Channel> future)
                                throws Exception {
                            // Client logged in.
                            
                            // He should send log in packet.
                        }
                    });
        }
        
        @Override
        public void channelRead0(final ChannelHandlerContext ctx, final NettyMessage msg)
                throws Exception {
            // Invoke onReceive if registered server.
            if (this.i.channels.contains(ctx)) {
                this.i.onReceive(this.i.getServerInfo(ctx), msg.payload);
            }
            else {
                // Try to register.
                if (NettyRegisterMesssage.validate(msg.payload, this.i.authKey)) {
                    // Add connected server to pool.
                    NettyServerComunicatorHandler.this.i.channels.add(ctx.channel());
                    String name = NettyRegisterMesssage.getName(msg.payload);
                    SlaveServer server = new SlaveServer(name);
                    NettyServerComunicatorHandler.this.i.ctxByName.put(name, ctx);
                    NettyServerComunicatorHandler.this.i.server.addSlave(server);
                    NettyServerComunicatorHandler.this.i.serverInfoByCTX.put(ctx, server);
                }
                else {
                    // Bad login. Disconnect.
                    ctx.close();
                }
            }
        }
        
        @Override
        public void exceptionCaught(final ChannelHandlerContext ctx,
                final Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
        
    }
}

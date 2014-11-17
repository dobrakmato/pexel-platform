package eu.matejkormuth.pexel.protocol.requests;

import java.nio.ByteBuffer;

import eu.matejkormuth.pexel.commons.TransferPurpose;
import eu.matejkormuth.pexel.network.Request;
import eu.matejkormuth.pexel.protocol.PexelProtocol;

/**
 * Request that carries file.
 */
public class FileTransferRequest extends Request {
    public TransferPurpose tranfserPurpose;
    public String          name;
    public byte[]          data;
    
    public FileTransferRequest(final TransferPurpose tranfserPurpose, final String name,
            final byte[] data) {
        this.tranfserPurpose = tranfserPurpose;
        this.name = name;
        this.data = data;
    }
    
    @Override
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.allocate(1 + this.name.length() + this.data.length)
                .put(this.tranfserPurpose.getByte())
                .put(this.name.getBytes(PexelProtocol.CHARSET))
                .put(this.data);
    }
    
    @Override
    public void fromByteBuffer(final ByteBuffer buffer) {
        // TODO Auto-generated method stub
        
    }
}

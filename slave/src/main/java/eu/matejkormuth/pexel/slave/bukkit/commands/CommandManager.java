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
package eu.matejkormuth.pexel.slave.bukkit.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.matejkormuth.pexel.slave.bukkit.core.Log;

/**
 * Class that is used for dynamic command registration.
 */
public class CommandManager {
    /**
     * Map of subcommands.
     */
    private final Map<String, Map<String, Method>> subcommands = new HashMap<String, Map<String, Method>>();
    /**
     * Map of commands.
     */
    private final Map<String, Object>              commands    = new HashMap<String, Object>();
    /**
     * Map of command aliases.
     */
    private final Map<String, String>              aliases     = new HashMap<String, String>();
    
    public CommandManager() {
        
    }
    
    /**
     * Tries to register specified object as command handler.
     * 
     * @param command
     *            command handler
     */
    public void registerCommands(final Object command) {
        Log.info("Register command on object: " + command.getClass().getSimpleName()
                + "#" + command.hashCode());
        Class<?> clazz = command.getClass();
        if (clazz.isAnnotationPresent(CommandHandler.class)) {
            this.registerCommand(command);
            this.registerAliases(clazz);
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(SubCommand.class)) {
                    this.registerSubcommand(command, method);
                }
            }
        }
        else {
            throw new RuntimeException(
                    "Anootation not present! Annotation: Command; Class: clazz");
        }
    }
    
    /**
     * Parses command from string and tries to execute it as specified player.
     * 
     * @param sender
     *            executor
     * @param command
     *            command
     */
    public boolean parseCommand(final Player sender, final String command) {
        String parts[] = command.trim().split("\\s+");
        String baseCommand = parts[0];
        
        if (this.commands.containsKey(baseCommand.toLowerCase())) {
            if (this.hasPermission(sender, baseCommand)) {
                //If no subcommand
                if (parts.length == 1) {
                    this.invoke(this.commands.get(baseCommand),
                            this.subcommands.get(baseCommand).get("main"), sender);
                    return true;
                }
                else {
                    String subCommand = parts[1];
                    if (this.subcommands.get(baseCommand).containsKey(subCommand)) {
                        if (this.hasPermission(sender, baseCommand + "." + subCommand)) {
                            //Executing subcommand
                            if (parts.length == 2) {
                                this.invoke(this.commands.get(baseCommand),
                                        this.subcommands.get(baseCommand)
                                                .get(subCommand), sender);
                                return true;
                            }
                            else {
                                Object[] args = new String[parts.length - 2];
                                System.arraycopy(parts, 2, args, 0, parts.length - 2);
                                this.invoke(this.commands.get(baseCommand),
                                        this.subcommands.get(baseCommand)
                                                .get(subCommand), sender, args);
                                return true;
                            }
                        }
                        else {
                            sender.sendMessage(ChatColor.RED
                                    + "You don't have permission!");
                            return true;
                        }
                    }
                    else {
                        if (subCommand.equalsIgnoreCase("help")) {
                            sender.sendMessage(ChatColor.YELLOW + "Command help: "
                                    + baseCommand);
                            for (Method m : this.subcommands.get(baseCommand).values()) {
                                SubCommand annotation = m.getAnnotation(SubCommand.class);
                                
                                String scArgs = "";
                                for (Class<?> param : m.getParameterTypes())
                                    scArgs += "<[" + param.getSimpleName() + "]> ";
                                
                                String scName = m.getName();
                                if (!annotation.name().equals(""))
                                    scName = annotation.name();
                                
                                sender.sendMessage(ChatColor.BLUE + "/" + baseCommand
                                        + ChatColor.RED + " " + scName + " "
                                        + ChatColor.GOLD + scArgs + ChatColor.GREEN
                                        + "- " + annotation.description());
                            }
                            return true;
                        }
                        else {
                            if (this.hasPermission(sender, baseCommand + "."
                                    + subCommand)) {
                                Object[] args = new String[parts.length - 1];
                                System.arraycopy(parts, 1, args, 0, parts.length - 1);
                                this.invoke(this.commands.get(baseCommand),
                                        this.subcommands.get(baseCommand).get("main"),
                                        sender, args);
                                return true;
                            }
                            else {
                                sender.sendMessage(ChatColor.RED
                                        + "You don't have permission!");
                                return true;
                            }
                        }
                    }
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "You don't have permission!");
                return true;
            }
        }
        else {
            //sender.sendMessage(ChatManager.error("Unknown command!"));
            return false;
        }
    }
    
    /**
     * Returns whether specified player has permission to specififed command.
     * 
     * @param sender
     *            player
     * @param baseCommand
     *            command
     * @return true or false
     */
    private boolean hasPermission(final Player sender, final String node) {
        return true;
    }
    
    /**
     * Invokes specified subcommand of command on specififed player weith specified arguments.
     * 
     * @param command
     *            command
     * @param subcommand
     *            sub command
     * @param invoker
     *            player
     * @param args
     *            args
     */
    private void invoke(final Object command, final Method subcommand,
            final Player invoker, final Object... args) {
        try {
            String argsString = "[";
            if (args != null)
                for (Object o : args)
                    argsString += o.toString() + ",";
            
            if (argsString.length() > 1)
                argsString = argsString.substring(0, argsString.length() - 1);
            
            String name = command.getClass().getAnnotation(CommandHandler.class).name();
            
            Log.info("Invoking command '" + name + "("
                    + command.getClass().getSimpleName() + ") -> "
                    + subcommand.getAnnotation(SubCommand.class).name() + " ("
                    + subcommand.getName() + ")' on player '" + invoker.getName()
                    + "' with args: " + argsString + "]");
            
            if (!Arrays.asList(command.getClass().getDeclaredMethods()).contains(
                    subcommand))
                Log.warn("Subcommand is not method of command class.");
            
            if (args.length == 0) {
                subcommand.invoke(command, invoker);
            }
            else {
                if (this.validParams(subcommand, args)) {
                    Log.info(" Invoking: Player, " + args.length);
                    Object[] objs = new Object[args.length + 1];
                    objs[0] = invoker;
                    System.arraycopy(args, 0, objs, 1, args.length);
                    subcommand.invoke(command, objs);
                }
                else
                    invoker.sendMessage(ChatColor.RED
                            + "Unknown command: invalid params");
            }
            
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            invoker.sendMessage(ChatColor.RED + "Unknown command: " + e.getMessage());
            if (invoker.isOp())
                invoker.sendMessage(ChatColor.RED + e.toString());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            invoker.sendMessage(ChatColor.RED
                    + "Internal server error occured while attempting to execute this command!");
            throw new RuntimeException(e);
        }
    }
    
    private boolean validParams(final Method subcommand, final Object[] args) {
        Class<?>[] parameterTypes = subcommand.getParameterTypes();
        if (args.length > parameterTypes.length - 1)
            return false;
        for (int i = 1; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            Log.info(" Validating param #" + i + ": " + parameterType.getSimpleName()
                    + " == " + args[i - 1].getClass().getSimpleName());
            if (!args[i - 1].getClass().equals(parameterType))
                return false;
        }
        return true;
    }
    
    private void registerSubcommand(final Object command, final Method method) {
        String baseCommand = command.getClass()
                .getAnnotation(CommandHandler.class)
                .name()
                .toLowerCase();
        String subCommand = method.getName().toLowerCase();
        
        if (!method.getAnnotation(SubCommand.class).name().equalsIgnoreCase(""))
            subCommand = method.getAnnotation(SubCommand.class).name().toLowerCase();
        
        Log.info("  Register subcommand: " + baseCommand + " -> " + subCommand + "#"
                + method.hashCode());
        
        if (!method.isAccessible())
            method.setAccessible(true);
        
        this.subcommands.get(baseCommand).put(subCommand, method);
    }
    
    private void registerCommand(final Object object) {
        String baseCommand = object.getClass()
                .getAnnotation(CommandHandler.class)
                .name()
                .toLowerCase();
        
        Log.info(" Register command: " + baseCommand);
        this.commands.put(baseCommand, object);
        this.subcommands.put(baseCommand, new HashMap<String, Method>());
    }
    
    private void registerAliases(final Class<?> clazz) {
        String baseName = clazz.getAnnotation(CommandHandler.class).name();
        for (String alias : clazz.getAnnotation(CommandHandler.class).aliases()) {
            this.aliases.put(baseName, alias);
        }
    }
}

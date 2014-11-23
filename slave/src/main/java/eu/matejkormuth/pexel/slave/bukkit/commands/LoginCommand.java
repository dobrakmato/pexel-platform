package eu.matejkormuth.pexel.slave.bukkit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.pexel.slave.bukkit.Pexel;
import eu.matejkormuth.pexel.slave.bukkit.chat.ChatManager;

public class LoginCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command,
            final String label, final String[] args) {
        if (command.getName().equalsIgnoreCase("lobbyarena")) {
            if (sender instanceof Player) {
                if (sender.isOp()) {
                    this.processOpCommand((Player) sender, args);
                }
                else {
                    this.processCommand((Player) sender, args);
                }
            }
            else {
                sender.sendMessage(ChatManager.error("This command is only avaiable for players!"));
            }
            return true;
        }
        sender.sendMessage(ChatManager.error("Wrong use!"));
        return true;
    }
    
    private void processCommand(final Player sender, final String[] args) {
        if (args.length != 1)
            sender.sendMessage(ChatManager.error("Wrong use! Syntax: /login <password>"));
        else
            Pexel.getAuth().authenticateCommand(sender, args[0]);
    }
    
    private void processOpCommand(final Player sender, final String[] args) {
        this.processCommand(sender, args);
    }
}

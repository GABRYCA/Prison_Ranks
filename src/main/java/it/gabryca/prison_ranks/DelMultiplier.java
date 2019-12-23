package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

public class DelMultiplier implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Configuration config = Main.getInstance().getConfig();
        Configuration messages = Main.getMessages();

        if (!(sender.hasPermission(config.getString("Permissions.Admin-Management")))){
            sender.sendMessage(messages.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
            return true;
        }

        if (args.length != 1){
            sender.sendMessage(messages.getString("Messages.WrongFormat"));
            return true;
        }

        if (config.getString("Prestiges." + args[0]) == null){
            sender.sendMessage(messages.getString("Messages.PrestigeNotFound"));
            return true;
        }

        if (config.getString("Prestiges." + args[0] + ".Multiplier") == null){
            sender.sendMessage(messages.getString("Messages.NoMultiplierFound"));
            return true;
        }

        config.set("Prestiges." + args[0] + ".Multiplier", null);
        Main.getInstance().saveConfig();
        sender.sendMessage(messages.getString("Messages.PrestigeMultiplierRemoveSuccess"));

        return true;
    }
}

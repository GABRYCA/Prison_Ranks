package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

public class AddMultiplier{

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Configuration config = Main.getInstance().getConfig();
        Configuration messages = Main.getMessages();

        // Check the permission
        if (!(sender.hasPermission(config.getString("Permissions.Admin-Management")))){
            sender.sendMessage(messages.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
            return true;
        }

        // Check parameters
        if (args.length != 2){
            sender.sendMessage(messages.getString("Messages.WrongFormat"));
            return true;
        }

        // Check if element exist
        if (config.getString("Prestiges." + args[0]) == null){
            sender.sendMessage(messages.getString("Messages.PrestigeNotFound"));
            return true;
        }

        // Check if number it's a number or double
        if (!(Main.isDouble(args[1]))) {
            sender.sendMessage(messages.getString("Messages.WrongFormat") + " [" + args[1] + "]");
            return true;
        }

        // Apply the changes
        config.set("Prestiges." + args[0] + ".Multiplier", args[1]);
        Main.getInstance().saveConfig();
        sender.sendMessage(messages.getString("Messages.PrestigeMultiplierAddSuccess"));
        return true;
    }
}

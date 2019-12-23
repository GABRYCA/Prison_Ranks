package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

public class AddMultiplier implements CommandExecutor {

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Configuration config = Main.getInstance().getConfig();
        Configuration messages = Main.getMessages();

        if (!(sender.hasPermission(config.getString("Permissions.Admin-Management")))){
            sender.sendMessage(messages.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
            return true;
        }

        if (args.length != 2){
            sender.sendMessage(messages.getString("Messages.WrongFormat"));
            return true;
        }

        if (config.getString("Prestiges." + args[0]) == null){
            sender.sendMessage(messages.getString("Messages.PrestigeNotFound"));
            return true;
        }

        if (!(isDouble(args[1]))) {
            sender.sendMessage(messages.getString("Messages.WrongFormat") + " [" + args[1] + "]");
            return true;
        }

        config.set("Prestiges." + args[0] + ".Multiplier", args[1]);
        Main.getInstance().saveConfig();
        sender.sendMessage(messages.getString("Messages.PrestigeMultiplierAddSuccess"));

        return true;
    }
}

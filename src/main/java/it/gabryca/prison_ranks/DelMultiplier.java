package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Objects;

public class DelMultiplier{

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Configuration config = Main.getInstance().getConfig();
        Configuration messages = Main.getMessages();

        // Check the permission
        if (!(sender.hasPermission(Objects.requireNonNull(config.getString("Permissions.Admin-Management"))))){
            sender.sendMessage(messages.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
            return true;
        }

        // Check parameters
        if (args.length != 1){
            sender.sendMessage(Objects.requireNonNull(messages.getString("Messages.WrongFormat")));
            return true;
        }

        if (config.getString("Prestiges." + args[0]) == null){
            sender.sendMessage(Objects.requireNonNull(messages.getString("Messages.PrestigeNotFound")));
            return true;
        }

        if (config.getString("Prestiges." + args[0] + ".Multiplier") == null){
            sender.sendMessage(Objects.requireNonNull(messages.getString("Messages.NoMultiplierFound")));
            return true;
        }

        config.set("Prestiges." + args[0] + ".Multiplier", null);
        Main.getInstance().saveConfig();
        sender.sendMessage(Objects.requireNonNull(messages.getString("Messages.PrestigeMultiplierRemoveSuccess")));

        return true;
    }
}

package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Objects;

public class ChangePrestigePrefix{

    public static boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Configuration config = Main.getInstance().getConfig();
        Configuration messages = Main.getMessages();

        // Check the permission
        if (!(commandSender.hasPermission(Objects.requireNonNull(config.getString("Permissions.Admin-Management"))))){
            commandSender.sendMessage(messages.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
            return true;
        }

        // Check parameters
        if (strings.length != 2){
            commandSender.sendMessage(Objects.requireNonNull(messages.getString("Messages.WrongFormat")));
            return true;
        }

        if (config.getString("Prestiges." + strings[0]) == null){
            commandSender.sendMessage(Objects.requireNonNull(messages.getString("Messages.PrestigeNotFound")));
            return true;
        }

        if (config.getString("Prestiges." + strings[0] + ".PrestigeName") == null){
            commandSender.sendMessage(Objects.requireNonNull(messages.getString("Messages.PrestigeNotFound")));
            return true;
        }

        config.set("Prestiges." + strings[0] + ".PrestigePrefix", strings[1] + "&f");
        Main.getInstance().saveConfig();
        commandSender.sendMessage(messages.getString("Messages.Prestige-Edited-Success") + " [ " + strings[0] + ", " + Main.format(config.getString("Prestiges." + strings[0] + ".PrestigePrefix")) + "§a" + ", Price " + config.getString("Prestiges." + strings[0] + ".Price") + " ]");
        return true;
    }
}

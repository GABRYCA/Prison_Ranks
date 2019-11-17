package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

public class DelPrestige implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Configuration config = Main.getInstance().getConfig();
        Configuration message = Main.getMessages();

        if (!(commandSender.hasPermission(config.getString("Permissions.Admin-Management")))){
            commandSender.sendMessage(message.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
            return true;
        }

        if (strings.length != 1){
            commandSender.sendMessage(message.getString("Messages.WrongFormat"));
            return true;
        }

        if (config.getString("Ranks." + strings[0]) == null){
            commandSender.sendMessage(message.getString("Messages.PrestigeNotFound"));
            return true;
        }

        config.set("Prestiges." + strings[0] + ".PrestigeName", null);
        config.set("Prestiges." + strings[0] + ".PrestigePrefix", null);
        config.set("Prestiges." + strings[0] + ".Price", null);
        config.set("Prestiges." + strings[0], null);
        Main.getInstance().saveConfig();
        commandSender.sendMessage(message.getString("Messages.Rank-Remove-Success") + " [ " + strings[0] + " ]");
        return true;
    }

}
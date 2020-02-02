package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Objects;

public class ChangeRankPrice {

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

        if (config.getString("Ranks." + strings[0]) == null){
            commandSender.sendMessage(Objects.requireNonNull(messages.getString("Messages.RankNotFound")));
            return true;
        }

        if (config.getString("Ranks." + strings[0] + ".RankName") == null){
            commandSender.sendMessage(Objects.requireNonNull(messages.getString("Messages.RankNotFound")));
            return true;
        }

        if (!(Main.isInt(strings[1]))) {
            commandSender.sendMessage(messages.getString("Messages.WrongFormat") + " [" + strings[1] + "]");
            return true;
        }

        int num = Integer.parseInt(strings[1]);
        config.set("Ranks." + strings[0] + ".Price", num);
        Main.getInstance().saveConfig();
        commandSender.sendMessage(messages.getString("Messages.Rank-Edited-Success") + " [ " + strings[0] + ", " + Main.format(config.getString("Ranks." + strings[0] + ".RankPrefix")) + "§a" + ", Price "  + config.getString("Settings.Currency-Symbol") + config.getString("Ranks." + strings[0] + ".Price") + " ]");
        return true;
    }
}

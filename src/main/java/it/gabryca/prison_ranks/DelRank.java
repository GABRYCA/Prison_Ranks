package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Objects;

public class DelRank {

    public static boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Configuration config = Main.getInstance().getConfig();
        Configuration message = Main.getMessages();

        // Check the permission
        if (!(commandSender.hasPermission(Objects.requireNonNull(config.getString("Permissions.Admin-Management"))))){
            commandSender.sendMessage(message.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
            return true;
        }

        // Check parameters
        if (strings.length != 1){
            commandSender.sendMessage(Objects.requireNonNull(message.getString("Messages.WrongFormat")));
            return true;
        }

        if (config.getString("Ranks." + strings[0]) == null){
            commandSender.sendMessage(Objects.requireNonNull(message.getString("Messages.RankNotFound")));
            return true;
        }

        config.set("Ranks." + strings[0] + ".RankName", null);
        config.set("Ranks." + strings[0] + ".RankPrefix", null);
        config.set("Ranks." + strings[0] + ".Price", null);
        config.set("Ranks." + strings[0], null);
        Main.getInstance().saveConfig();
        commandSender.sendMessage(message.getString("Messages.Rank-Remove-Success") + " [ " + strings[0] + " ]");
        return true;
    }
}

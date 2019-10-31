package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

public class AddRank implements CommandExecutor {

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Configuration config = Main.getInstance().getConfig();
        Configuration messages = Main.getMessages();

        if (!(commandSender.hasPermission(config.getString("Permissions.Admin-Management")))){
            commandSender.sendMessage(messages.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
            return true;
        }

        if (strings.length != 3){
            commandSender.sendMessage(messages.getString("Messages.WrongFormat"));
            return true;
        }

        if (!(isInt(strings[2]))) {
            commandSender.sendMessage(messages.getString("Messages.WrongFormat") + " [" + strings[2] + "]");
            return true;
        }

        int num = Integer.parseInt(strings[2]);
        config.set("Ranks." + strings[0] + ".RankName", strings[0]);
        config.set("Ranks." + strings[0] + ".RankPrefix", strings[1] + "&f");
        config.set("Ranks." + strings[0] + ".Price", num);
        Main.getInstance().saveConfig();
        commandSender.sendMessage(messages.getString("Messages.Rank-Add-Success") + " [ " + strings[0] + ", " + Main.format(strings[1] + "&a") + ", Price " + strings[2] + " ]");
        return true;
    }
}

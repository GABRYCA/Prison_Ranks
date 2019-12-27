package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

public class ChangeRankPrice implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Configuration config = Main.getInstance().getConfig();
        Configuration messages = Main.getMessages();

        if (!(commandSender.hasPermission(config.getString("Permissions.Admin-Management")))){
            commandSender.sendMessage(messages.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
            return true;
        }

        if (strings.length != 2){
            commandSender.sendMessage(messages.getString("Messages.WrongFormat"));
            return true;
        }

        if (config.getString("Ranks." + strings[0]) == null){
            commandSender.sendMessage(messages.getString("Messages.RankNotFound"));
            return true;
        }

        if (config.getString("Ranks." + strings[0] + ".RankName") == null){
            commandSender.sendMessage(messages.getString("Messages.RankNotFound"));
            return true;
        }

        if (!(Main.isInt(strings[1]))) {
            commandSender.sendMessage(messages.getString("Messages.WrongFormat") + " [" + strings[1] + "]");
            return true;
        }

        int num = Integer.parseInt(strings[1]);
        config.set("Ranks." + strings[0] + ".Price", num);
        Main.getInstance().saveConfig();
        commandSender.sendMessage(messages.getString("Messages.Rank-Edited-Success") + " [ " + strings[0] + ", " + Main.format(config.getString("Ranks." + strings[0] + ".RankPrefix")) + "§a" + ", Price " + config.getString("Ranks." + strings[0] + ".Price") + " ]");
        return true;
    }
}

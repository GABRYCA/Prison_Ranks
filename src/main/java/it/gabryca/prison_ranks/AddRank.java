package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Objects;

public class AddRank{

    public static boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Configuration config = Main.getInstance().getConfig();
        Configuration messages = Main.getMessages();

        // Check the permission
        if (!(commandSender.hasPermission(Objects.requireNonNull(config.getString("Permissions.Admin-Management"))))){
            commandSender.sendMessage(messages.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
            return true;
        }

        // Check parameters
        if (args.length != 3){
            commandSender.sendMessage(Objects.requireNonNull(messages.getString("Messages.WrongFormat")));
            return true;
        }

        // Check if number it's a number or Int
        if (!(Main.isInt(args[2]))) {
            commandSender.sendMessage(messages.getString("Messages.WrongFormat") + " [" + args[2] + "]");
            return true;
        }

        // Apply the changes
        int num = Integer.parseInt(args[2]);
        config.set("Ranks." + args[0] + ".RankName", args[0]);
        config.set("Ranks." + args[0] + ".RankPrefix", args[1] + "&f");
        config.set("Ranks." + args[0] + ".Price", num);
        Main.getInstance().saveConfig();
        commandSender.sendMessage(messages.getString("Messages.Rank-Add-Success") + " [ " + args[0] + ", " + Main.format(args[1] + "&a") + ", Price " + config.getString("Settings.Currency-Symbol") + args[2] + " ]");
        return true;
    }
}

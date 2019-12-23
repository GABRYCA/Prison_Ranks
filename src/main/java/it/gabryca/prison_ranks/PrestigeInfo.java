package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Set;

public class PrestigeInfo implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Configuration config = Main.getInstance().getConfig();
        Configuration message = Main.getMessages();

        if (!(sender.hasPermission(config.getString("Permissions.Admin-Management")))){
            sender.sendMessage(message.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
            return true;
        }

        if (args.length != 1){
            sender.sendMessage(message.getString("Messages.WrongFormat"));
            return true;
        }

        if (config.getString("Prestiges." + args[0]) == null){
            sender.sendMessage(message.getString("Messages.RankNotFound"));
            return true;
        }

        int RankNumber = 0;
        Set<String> ranks = config.getConfigurationSection("Prestiges").getKeys(false);
        for (String key : ranks) {
            if (config.getString("Prestiges." + key + ".PrestigeName").equals(args[0])){
                sender.sendMessage("§7--------------------");
                sender.sendMessage("     §bPrestigeInfo " + Main.format(config.getString("Prestiges." + key + ".PrestigePrefix")));
                sender.sendMessage("§7--------------------");
                sender.sendMessage("§9PrestigeNumber: §7" + RankNumber);
                sender.sendMessage("§9PrestigeName: §7" + config.getString("Prestiges." + key + ".PrestigeName"));
                sender.sendMessage("§9Prefix: §7" + Main.format(config.getString("Prestiges." + key + ".PrestigePrefix")));
                sender.sendMessage("§9Price: §7" + config.getInt("Prestiges." + key + ".Price"));
                if (config.getString("Prestiges." + key + ".Multiplier") != null){
                    sender.sendMessage("§9Multiplier: §7" + config.getDouble("Prestiges." + key + ".Multiplier"));
                } else {
                    sender.sendMessage("§9Multiplier: §7None");
                }
                if (config.getString("Prestiges." + key + ".PrestigeCommand") != null){
                    sender.sendMessage("§9Commands:");
                    Set<String> commands = config.getConfigurationSection("Prestiges." + key + ".PrestigeCommand").getKeys(false);
                    int num = 1;
                    for (String key2 : commands){
                        sender.sendMessage("§9 [" + num + "] §7" + config.getString("Prestiges." + key + ".PrestigeCommand." + key2));
                        num++;
                    }
                }
                else {
                    sender.sendMessage("§9Commands: §7None");
                }
            }
            RankNumber++;
        }

        return true;
    }

}

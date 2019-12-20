package it.gabryca.prison_ranks;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Set;

public class RankInfo implements CommandExecutor {

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

        if (config.getString("Ranks." + args[0]) == null){
            sender.sendMessage(message.getString("Messages.RankNotFound"));
            return true;
        }

        int RankNumber = 0;
        Set<String> ranks = config.getConfigurationSection("Ranks").getKeys(false);
        for (String key : ranks) {
            if (config.getString("Ranks." + key + ".RankName").equals(args[0])){
                sender.sendMessage("§7--------------------");
                sender.sendMessage("     §bRankInfo " + Main.format(config.getString("Ranks." + key + ".RankPrefix")));
                sender.sendMessage("§7--------------------");
                sender.sendMessage("§9RankNumber: §7" + RankNumber);
                sender.sendMessage("§9RankName: §7" + config.getString("Ranks." + key + ".RankName"));
                sender.sendMessage("§9Prefix: §7" + Main.format(config.getString("Ranks." + key + ".RankPrefix")));
                sender.sendMessage("§9Price: §7" + config.getInt("Ranks." + key + ".Price"));
                if (config.getString("Ranks." + key + ".RankupCommand") != null){
                    sender.sendMessage("§9Commands:");
                    Set<String> commands = config.getConfigurationSection("Ranks." + key + ".RankupCommand").getKeys(false);
                    int num = 1;
                    for (String key2 : commands){
                        sender.sendMessage("§9 [" + num + "] §7" + config.getString("Ranks." + key + ".RankupCommand." + key2));
                        num++;
                    }
                }
            }
            RankNumber++;
        }

        return true;
    }
}

package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Set;

public class AddRankupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Configuration config = Main.getInstance().getConfig();
        Configuration message = Main.getMessages();

        if (!(sender.hasPermission(config.getString("Permissions.Admin-Management")))){
            sender.sendMessage(message.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
            return true;
        }

        if (args.length <= 2){
            sender.sendMessage(message.getString("Messages.WrongFormat"));
            return true;
        }

        StringBuilder commandmessage = new StringBuilder(args[1]);
        for (int arg = 2; arg < args.length; arg++) {
            commandmessage.append(" ").append(args[arg]);
        }

        if (config.getConfigurationSection("Ranks") != null) {
            Set<String> ranks = config.getConfigurationSection("Ranks").getKeys(false);
            for (String key : ranks) {
                if (config.getString("Ranks." + key + ".RankName").equalsIgnoreCase(args[0])){
                    int freekey = 1;
                    while (config.getString("Ranks." + key + ".RankupCommand." + freekey) != null){
                        freekey++;
                    }
                    config.set("Ranks." + key + ".RankupCommand." + freekey, commandmessage.toString());
                    Main.getInstance().saveConfig();
                    sender.sendMessage(message.getString("Messages.Rank-Edited-Success") + " [" + commandmessage + "]");
                    return true;
                }
            }
        }

        return true;
    }
}

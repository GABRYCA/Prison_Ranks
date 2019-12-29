package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Set;

public class DelRankupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Configuration config = Main.getInstance().getConfig();
        Configuration messages = Main.getMessages();

        // Check the permission
        if(sender.hasPermission(config.getString("Permissions.Admin-Management"))){

            // Check parameters
            if (args.length == 1){

                if (config.getString("Ranks." + args[0] + ".RankName") == null){
                    sender.sendMessage(messages.getString("Messages.RankNotFound"));
                    return true;
                } else {
                    if (config.getString("Ranks." + args[0] + ".RankupCommand") != null){
                        sender.sendMessage(messages.getString("Messages.RankCommands"));
                        int num = 1;
                        Set<String> commands = config.getConfigurationSection("Ranks." + args[0] + ".RankupCommand").getKeys(false);
                        for (String key2 : commands){
                            sender.sendMessage("§c[§6" + num + "§c]§9: " + config.getString("Ranks." + args[0] + ".RankupCommand." + key2));
                            num++;
                        }
                        sender.sendMessage(messages.getString("Messages.DelRankupCommandFormat"));
                        return true;
                    } else {
                        sender.sendMessage(messages.getString("Messages.NoRankupCommands"));
                        return true;
                    }
                }

            }

            if (args.length == 2){

                if (config.getString("Ranks." + args[0] + ".RankName") == null){
                    sender.sendMessage(messages.getString("Messages.RankNotFound"));
                    return true;
                }

                if (config.getString("Ranks." + args[0] + ".RankupCommand") != null){
                    int num = 1;
                    Set<String> commands = config.getConfigurationSection("Ranks." + args[0] + ".RankupCommand").getKeys(false);
                    for (String key2 : commands){
                        if (args[1].equals(Integer.toString(num))){
                            config.set("Ranks." + args[0] + ".RankupCommand." + key2, null);
                            Main.getInstance().saveConfig();
                            sender.sendMessage(messages.getString("Messages.RankupCommandDelSuccess"));
                            return true;
                        }
                        num++;
                    }
                }

            }

            if (args.length != 2){
                sender.sendMessage(messages.getString("Messages.WrongFormat"));
                return true;
            }

        } else {
            sender.sendMessage(messages.getString("Messages.NoPerm"));
            return true;
        }

        return true;
    }
}

package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Set;

public class DelPrestigeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Configuration config = Main.getInstance().getConfig();
        Configuration messages = Main.getMessages();

        if(sender.hasPermission(config.getString("Permissions.Admin-Management"))){


            if (args.length == 1){

                if (config.getString("Prestiges." + args[0] + ".PrestigeName") == null){
                    sender.sendMessage(messages.getString("Messages.RankNotFound"));
                    return true;
                } else {
                    if (config.getString("Prestiges." + args[0] + ".PrestigeCommand") != null){
                        sender.sendMessage(messages.getString("Messages.PrestigeCommands"));
                        int num = 1;
                        Set<String> commands = config.getConfigurationSection("Prestiges." + args[0] + ".PrestigeCommand").getKeys(false);
                        for (String key2 : commands){
                            sender.sendMessage("§c[§6" + num + "§c]§9: " + config.getString("Prestiges." + args[0] + ".PrestigeCommand." + key2));
                            num++;
                        }
                        sender.sendMessage(messages.getString("Messages.DelPrestigeCommandFormat"));
                        return true;
                    } else {
                        sender.sendMessage(messages.getString("Messages.NoPrestigeCommands"));
                        return true;
                    }
                }

            }

            if (args.length == 2){

                if (config.getString("Prestiges." + args[0] + ".PrestigeName") == null){
                    sender.sendMessage(messages.getString("Messages.RankNotFound"));
                    return true;
                }

                if (config.getString("Prestiges." + args[0] + ".PrestigeCommand") != null){
                    int num = 1;
                    Set<String> commands = config.getConfigurationSection("Prestiges." + args[0] + ".PrestigeCommand").getKeys(false);
                    for (String key2 : commands){
                        if (args[1].equals(Integer.toString(num))){
                            config.set("Prestiges." + args[0] + ".PrestigeCommand." + key2, null);
                            Main.getInstance().saveConfig();
                            sender.sendMessage(messages.getString("Messages.PrestigeCommandDelSuccess"));
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

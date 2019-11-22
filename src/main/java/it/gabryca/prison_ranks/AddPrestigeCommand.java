package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Set;

public class AddPrestigeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Configuration config = Main.getInstance().getConfig();
        Configuration message = Main.getMessages();

        if (!(sender.hasPermission(config.getString("Permissions.Admin-Management")))){
            sender.sendMessage(message.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
            return true;
        }

        if (args.length != 2){
            sender.sendMessage(message.getString("Messages.WrongFormat"));
            return true;
        }

        if (config.getConfigurationSection("Prestiges") != null) {
            Set<String> ranks = config.getConfigurationSection("Prestiges").getKeys(false);
            for (String key : ranks) {
                if (config.getString("Prestiges." + key + ".PrestigeName").equalsIgnoreCase(args[0])){
                    int freekey = 1;
                    while (config.getString("Prestiges." + key + ".PrestigeCommand" + freekey) != null){
                        freekey++;
                    }
                    config.set("Prestiges." + key + ".PrestigeCommand" + freekey, args[1]);
                    Main.getInstance().saveConfig();
                    sender.sendMessage(message.getString("Messages.Prestige-Edited-Success") + " [" + args[1] + "]");
                    return true;
                }
            }
        }

        return true;
    }

}

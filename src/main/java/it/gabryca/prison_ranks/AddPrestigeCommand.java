package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Objects;
import java.util.Set;

public class AddPrestigeCommand{

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Configuration config = Main.getInstance().getConfig();
        Configuration message = Main.getMessages();

        // Check the permission
        if (!(sender.hasPermission(Objects.requireNonNull(config.getString("Permissions.Admin-Management"))))){
            sender.sendMessage(message.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
            return true;
        }

        // Check parameters
        if (args.length <= 2){
            sender.sendMessage(Objects.requireNonNull(message.getString("Messages.WrongFormat")));
            return true;
        }

        StringBuilder commandmessage = new StringBuilder(args[1]);
        for (int arg = 2; arg < args.length; arg++) {
            commandmessage.append(" ").append(args[arg]);
        }

        if (config.getConfigurationSection("Prestiges") != null) {
            Set<String> ranks = config.getConfigurationSection("Prestiges").getKeys(false);
            for (String key : ranks) {
                if (config.getString("Prestiges." + key + ".PrestigeName").equalsIgnoreCase(args[0])){
                    int freekey = 1;
                    while (config.getString("Prestiges." + key + ".PrestigeCommand." + freekey) != null){
                        freekey++;
                    }
                    config.set("Prestiges." + key + ".PrestigeCommand." + freekey, commandmessage.toString());
                    Main.getInstance().saveConfig();
                    sender.sendMessage(message.getString("Messages.Prestige-Edited-Success") + " [" + commandmessage + "]");
                    return true;
                }
            }
        }
        return true;
    }
}

package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

public class AddPrestige implements CommandExecutor {

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

            if (!(commandSender.hasPermission(config.getString("Permissions.Permission-AddRank")))){
                commandSender.sendMessage(messages.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Permission_AddRank") + "]");
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
            config.set("Prestiges." + strings[0] + ".PrestigeName", strings[0]);
            config.set("Prestiges." + strings[0] + ".PrestigePrefix", strings[1]);
            config.set("Prestiges." + strings[0] + ".Price", num);
            Main.getInstance().saveConfig();
            commandSender.sendMessage(messages.getString("Messages.Prestige-Add-Success") + " [" + strings[0] + ", " + strings[1] + ", Price " + strings[2] + "]");

            return true;
        }
}

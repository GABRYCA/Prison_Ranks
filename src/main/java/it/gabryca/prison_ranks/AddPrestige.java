package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

public class AddPrestige implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

            Configuration config = Main.getInstance().getConfig();
            Configuration messages = Main.getMessages();

            if (!(commandSender.hasPermission(config.getString("Permissions.Admin-Management")))){
                commandSender.sendMessage(messages.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Admin-Management") + "]");
                return true;
            }

            if (strings.length != 3){
                commandSender.sendMessage(messages.getString("Messages.WrongFormat"));
                return true;
            }

            if (!(Main.isInt(strings[2]))) {
                commandSender.sendMessage(messages.getString("Messages.WrongFormat") + " [" + strings[2] + "]");
                return true;
            }

            int num = Integer.parseInt(strings[2]);
            config.set("Prestiges." + strings[0] + ".PrestigeName", strings[0]);
            config.set("Prestiges." + strings[0] + ".PrestigePrefix", strings[1]);
            config.set("Prestiges." + strings[0] + ".Price", num);
            Main.getInstance().saveConfig();
            commandSender.sendMessage(messages.getString("Messages.Prestige-Add-Success") + " [" + strings[0] + ", " + Main.format(strings[1]) + "§a, Price " + strings[2] + "]");

            return true;
        }
}

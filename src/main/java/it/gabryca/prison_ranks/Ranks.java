package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Ranks implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Configuration config = Main.getInstance().getConfig();
        Configuration messages = Main.getMessages();

        if (!(commandSender instanceof Player)){
            commandSender.sendMessage(Objects.requireNonNull(messages.getString("Messages.OnlyPlayer")));
            return true;
        }

        if(config.getBoolean("Settings.Ranks-GUI-Permission")) {
            if (commandSender.hasPermission(Objects.requireNonNull(config.getString("Permissions.Ranks-GUI-Permission")))) {
                Player p = (Player) commandSender;
                RanksGUI gui = new RanksGUI(p);
                gui.open();
            } else {
                commandSender.sendMessage(messages.getString("Messages.NoPerm") + " [" + config.getString("Permissions.Ranks-GUI-Permission") + "]");
            }
        }

            Player p = (Player) commandSender;
            RanksGUI gui = new RanksGUI(p);
            gui.open();
        return true;
    }
}

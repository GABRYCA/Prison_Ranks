package it.gabryca.prison_ranks;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ResetPlayerRank implements CommandExecutor {



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

        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
        if (op.hasPlayedBefore()) {
            UUID uuid = op.getUniqueId();
            File dataplayer = new File(Main.getInstance().getDataFolder() + "/data/" + uuid + ".yml");
            FileConfiguration PlayerIn = YamlConfiguration.loadConfiguration(dataplayer);

            try {
            PlayerIn.set("PlayerData.RankNumber", 1);
            PlayerIn.save(dataplayer);
            sender.sendMessage(message.getString("Messages.PlayerRankResetSuccess"));
            return true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            sender.sendMessage(message.getString("Messages.PlayerNotFound"));
            return true;
        }

        return true;

    }
}
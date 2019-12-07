package it.gabryca.prison_ranks;

import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Rankup implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player p = (Player) commandSender;
        File dataplayer = new File(Main.getInstance().getDataFolder() + "/data/" + p.getUniqueId() + ".yml");
        FileConfiguration PlayerIn = YamlConfiguration.loadConfiguration(dataplayer);
        Configuration config = Main.getInstance().getConfig();
        Configuration message = Main.getMessages();
        Economy econ = Main.getInstance().getEconomy();
        int PlayerBalance = (int) econ.getBalance(p);
        int PlayerRank = PlayerIn.getInt("PlayerData.RankNumber");
        int HackyWayToGetARank = 0;

        if (config.getConfigurationSection("Ranks") != null) {
            Set<String> ranks = config.getConfigurationSection("Ranks").getKeys(false);
            int num = ranks.size();
            for (String key : ranks) {
                HackyWayToGetARank++;

                if (PlayerRank + 1 > num) {
                    commandSender.sendMessage(message.getString("Messages.MaxRank"));
                    return true;
                }

                if (PlayerRank + 1 == HackyWayToGetARank) {
                    if (PlayerBalance >= config.getInt("Ranks." + key + ".Price")) {
                        try {
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2F, 1F);
                            econ.withdrawPlayer(p, config.getInt("Ranks." + key + ".Price"));
                            PlayerIn.set("PlayerData.RankNumber", PlayerRank + 1);
                            PlayerIn.save(dataplayer);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        if (config.getString("Ranks." + key + ".RankupCommand") != null){
                            Set<String> commands = config.getConfigurationSection("Ranks." + key + ".RankupCommand").getKeys(false);
                            for (String key2 : commands){
                                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), Main.format(PlaceholderAPI.setPlaceholders(p,config.getString("Ranks." + key + ".RankupCommand." + key2))));
                            }
                        }
                        if (config.getBoolean("Settings.Rankup-Broadcast")) {
                            Bukkit.broadcastMessage(message.getString("Messages.ThePlayer") + p.getName() + message.getString("Messages.DidRankup") + Main.format(config.getString("Ranks." + key + ".RankPrefix")));
                            return true;
                        }
                        p.sendMessage("PlayerData.YouRankup" + " " + Main.format(config.getString("Ranks." + key + ".RankPrefix")));
                        return true;
                    } else {
                        p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_LAND,2F,1F);
                        p.sendMessage(message.getString("Messages.NotEnoughMoney"));
                        return true;
                    }
                }
            }
        }
        return true;
    }
}

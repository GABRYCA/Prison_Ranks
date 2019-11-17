package it.gabryca.prison_ranks;

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

public class Prestige implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player p = (Player) commandSender;
        File dataplayer = new File(Main.getInstance().getDataFolder() + "/data/" + p.getUniqueId() + ".yml");
        FileConfiguration PlayerIn = YamlConfiguration.loadConfiguration(dataplayer);
        Configuration config = Main.getInstance().getConfig();
        Configuration message = Main.getMessages();
        Economy econ = Main.getInstance().getEconomy();
        int PlayerBalance = (int) econ.getBalance(p);
        int PlayerPrestige = PlayerIn.getInt("PlayerData.PrestigeNumber");
        int PlayerRank = PlayerIn.getInt("PlayerData.RankNumber");

        if (config.getConfigurationSection("Ranks") == null) {
            return true;
        }
            Set<String> ranks = config.getConfigurationSection("Ranks").getKeys(false);
            int num = ranks.size();
                if (PlayerRank + 1 > num) {
                    if (config.getConfigurationSection("Prestiges") == null) {
                        return true;
                    }
                    Set<String> prestiges = config.getConfigurationSection("Prestiges").getKeys(false);
                    int num2 = prestiges.size();
                    for (String key2 : prestiges) {
                        if (PlayerPrestige + 1 > num2) {
                            commandSender.sendMessage(message.getString("Messages.MaxPrestige"));
                            return true;
                        }
                        if (PlayerBalance >= config.getInt("Prestiges." + key2 + ".Price")) {
                            try {
                                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2F, 1F);
                                econ.withdrawPlayer(p, config.getInt("Prestiges." + key2 + ".Price"));
                                PlayerIn.set("PlayerData.PrestigeNumber", PlayerPrestige + 1);
                                PlayerIn.save(dataplayer);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            if (config.getBoolean("Settings.Prestige-Broadcast")) {
                                Bukkit.broadcastMessage(message.getString("Messages.ThePlayer") + p.getName() + message.getString("Messages.DidPrestige") + Main.format(config.getString("Prestiges." + key2 + ".PrestigePrefix")));
                                return true;
                            }
                            p.sendMessage("PlayerData.YouPrestiged" + " " + Main.format(config.getString("Prestiges." + key2 + ".PrestigePrefix")));
                            return true;
                        } else {
                            p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_LAND,2F,1F);
                            p.sendMessage(message.getString("Messages.NotEnoughMoneyToPrestige"));
                            return true;
                        }
                    }
                    return true;
                } else {
                    p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_LAND,2F,1F);
                    commandSender.sendMessage(message.getString("Messages.NotMaxRank"));
                    return true;
                }
        }
}
package it.gabryca.prison_ranks;


import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;
import java.io.File;
import java.io.IOException;

public class listeners implements Listener {

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent e){

        String player = e.getPlayer().getName();

        File data = new File(Main.getInstance().getDataFolder() + "/data");
        File dataplayer = new File(Main.getInstance().getDataFolder() + "/data/" + e.getPlayer().getUniqueId() + ".yml");
        FileConfiguration conf;

            if (!data.exists()) {
                data.mkdirs();
            }

        if (!dataplayer.exists()) {
            try {
                int num = 1;
                int nump = 0;
                dataplayer.createNewFile();
                conf = YamlConfiguration.loadConfiguration(dataplayer);
                conf.createSection("PlayerData");
                conf.set("PlayerData.UUID", e.getPlayer().getUniqueId().toString());
                conf.set("PlayerData.PlayerName", player);
                conf.set("PlayerData.RankNumber", num);
                conf.set("PlayerData.PrestigeNumber", nump);
                conf.save(dataplayer);
            } catch (IOException er) {
                er.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){

        Player p = (Player) e.getWhoClicked();
        File dataplayer = new File(Main.getInstance().getDataFolder() + "/data/" + p.getUniqueId() + ".yml");
        FileConfiguration PlayerIn = YamlConfiguration.loadConfiguration(dataplayer);
        Configuration config = Main.getInstance().getConfig();
        Configuration message = Main.getMessages();
        Economy econ = Main.getInstance().getEconomy();
        int PlayerBalance = (int) econ.getBalance(p);
        int PlayerRank = Main.getRankNumber(p);
        int PlayerPrestige = Main.getPrestigeNumber(p);
        int HackyWayToGetARank = 0;

        if (e.getCurrentItem() == null){
            return;
        }

        if (e.getView().getTitle().equals("§7" + "Ranks")){

            if (e.getCurrentItem() == null){
                return;
            }

            if (!(e.getCurrentItem().hasItemMeta())){
                return;
            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().substring(2).equals(message.getString("Messages.ClickToRankup"))){
                int HackyWayToGetAPrestige = 0;
                double Multiplier = 0;
                if (config.getConfigurationSection("Prestiges") != null) {
                    Set<String> prestiges = config.getConfigurationSection("Prestiges").getKeys(false);
                    for (String key : prestiges){
                        if (HackyWayToGetAPrestige == PlayerPrestige){
                            if (config.getString("Prestiges." + key + ".Multiplier") != null) {
                                Multiplier = config.getDouble("Prestiges." + key + ".Multiplier");
                            }
                        }
                        HackyWayToGetAPrestige++;
                    }
                }

                Set<String> ranks = config.getConfigurationSection("Ranks").getKeys(false);
                int num = ranks.size();
                for (String key : ranks){
                    HackyWayToGetARank++;
                    if (PlayerRank + 1 > num){
                        p.sendMessage(message.getString("Messages.MaxRank"));
                        return;
                    }
                    if (PlayerRank + 1 == HackyWayToGetARank){
                        int Price = (int) (config.getInt("Ranks." + key + ".Price") + config.getInt("Ranks." + key + ".Price")*Multiplier);
                        if (PlayerBalance >= Price){
                            try {
                                p.playSound(p.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,2F,1F);
                                econ.withdrawPlayer(p, Price);
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
                            if (config.getBoolean("Settings.Fireworks-rankup")){
                                Main.spawnFireworks(p.getLocation(), 1);
                            }
                            if (config.getBoolean("Settings.Rankup-Broadcast")){
                                Bukkit.broadcastMessage(message.getString("Messages.ThePlayer") + p.getName() + message.getString("Messages.DidRankup") + Main.format(config.getString("Ranks." + key + ".RankPrefix")));
                                p.closeInventory();
                                return;
                            }
                            p.sendMessage("PlayerData.YouRankup" + " " + Main.format(config.getString("Ranks." + key + ".RankPrefix")));
                            p.closeInventory();
                            return;
                        } else {
                            p.closeInventory();
                            p.sendMessage(message.getString("Messages.NotEnoughMoney"));
                            return;
                        }
                    }
                }
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().substring(2).equals(message.getString("Messages.NotEnoughMoney"))){
                p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_LAND,2F,1F);
                p.sendMessage(message.getString("Messages.NotEnoughMoney"));
                e.setCancelled(true);
            } else {
                e.setCancelled(true);
            }
        }

        if (e.getView().getTitle().equals("§c" + "Prestiges")){

            if (e.getCurrentItem() == null){
                return;
            }

            if (!(e.getCurrentItem().hasItemMeta())){
                return;
            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().substring(2).equals(message.getString("Messages.ClickToPrestige"))){
                if (config.getConfigurationSection("Ranks") == null) {
                    return;
                }
                Set<String> ranks = config.getConfigurationSection("Ranks").getKeys(false);
                int num = ranks.size();
                if (PlayerRank + 1 > num) {
                    if (config.getConfigurationSection("Prestiges") == null) {
                        return;
                    }
                    Set<String> prestiges = config.getConfigurationSection("Prestiges").getKeys(false);
                    int num2 = prestiges.size();
                    for (String key : prestiges) {
                        if (PlayerPrestige + 1 > num2) {
                            p.sendMessage(message.getString("Messages.MaxPrestige"));
                            p.closeInventory();
                            return;
                        }
                        if (PlayerBalance >= config.getInt("Prestiges." + key + ".Price")) {
                            try {
                                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2F, 1F);
                                econ.withdrawPlayer(p, config.getInt("Prestiges." + key + ".Price"));
                                PlayerIn.set("PlayerData.PrestigeNumber", PlayerPrestige + 1);
                                PlayerIn.save(dataplayer);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            if (config.getString("Prestiges." + key + ".PrestigeCommand") != null){
                                Set<String> commands = config.getConfigurationSection("Prestiges." + key + ".PrestigeCommand").getKeys(false);
                                for (String key2 : commands){
                                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), Main.format(PlaceholderAPI.setPlaceholders(p,config.getString("Prestiges." + key + ".PrestigeCommand." + key2))));
                                }
                            }
                            if (config.getBoolean("Settings.Fireworks-prestige")){
                                Main.spawnFireworks(p.getLocation(), 1);
                            }
                            if (config.getBoolean("Settings.Prestige-Broadcast")) {
                                Bukkit.broadcastMessage(message.getString("Messages.ThePlayer") + p.getName() + message.getString("Messages.DidPrestige") + Main.format(config.getString("Prestiges." + key + ".PrestigePrefix")));
                                p.closeInventory();
                                return;
                            }
                            p.sendMessage("PlayerData.YouPrestiged" + " " + Main.format(config.getString("Prestiges." + key + ".PrestigePrefix")));
                            p.closeInventory();
                            return;
                        } else {
                            p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_LAND,2F,1F);
                            p.sendMessage(message.getString("Messages.NotEnoughMoneyToPrestige"));
                            p.closeInventory();
                            return;
                        }
                    }
                } else {
                    p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_LAND,2F,1F);
                    p.sendMessage(message.getString("Messages.NotMaxRank"));
                    p.closeInventory();
                }
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().substring(2).equals(message.getString("Messages.NotEnoughMoneyToPrestige"))){
                p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_LAND,2F,1F);
                p.sendMessage(message.getString("Messages.NotEnoughMoneyToPrestige"));
                p.closeInventory();
                e.setCancelled(true);
            } else {
                e.setCancelled(true);
            }

        }


        }
}

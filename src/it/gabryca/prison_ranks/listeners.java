package it.gabryca.prison_ranks;


import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;
import java.util.UUID;
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
        int PlayerRank = PlayerIn.getInt("PlayerData.RankNumber");
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
                Set<String> ranks = config.getConfigurationSection("Ranks").getKeys(false);
                int num = ranks.size();
                for (String key : ranks){
                    HackyWayToGetARank++;
                    if (PlayerRank + 1 > num){
                        p.sendMessage(message.getString("Messages.MaxRank"));
                        return;
                    }
                    if (PlayerRank + 1 == HackyWayToGetARank){
                        if (PlayerBalance >= config.getInt("Ranks." + key + ".Price")){
                            try {
                                p.playSound(p.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,2F,1F);
                                econ.withdrawPlayer(p, config.getInt("Ranks." + key + ".Price"));
                                PlayerIn.set("PlayerData.RankNumber", PlayerRank + 1);
                                PlayerIn.save(dataplayer);
                            } catch (IOException ex) {
                                ex.printStackTrace();
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
    }
}

package it.gabryca.prison_ranks;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
}

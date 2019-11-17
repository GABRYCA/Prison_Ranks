package it.gabryca.prison_ranks;

import me.clip.placeholderapi.PlaceholderHook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Placeholders implements Listener {
    private Main plugin ;
    public Placeholders(Main plugin){
        this.plugin = plugin;
        registerPlaceholders();
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    private void registerPlaceholders() {
        PlaceholderAPI.registerPlaceholderHook("prisonranks", new PlaceholderHook() {
            @Override
            public String onRequest(OfflinePlayer p, String params) {
                if (p!=null && p.isOnline()) {
                    return onPlaceholderRequest(p.getPlayer(), params);
                }
                return null;
            }

            @Override
            public String onPlaceholderRequest(Player p, String params){
                if (p==null){
                    return null;
                }
                // %prisonranks_PARAMS%
                if (params.equalsIgnoreCase("currentrank")){
                    File dataplayer = new File(Main.getInstance().getDataFolder() + "/data/" + p.getUniqueId() + ".yml");
                    Configuration PlayerIn = YamlConfiguration.loadConfiguration(dataplayer);
                    Configuration config = Main.getInstance().getConfig();
                    int PlayerRank = PlayerIn.getInt("PlayerData.RankNumber");
                    int HackyWayToGetARank = 0;

                    if (config.getConfigurationSection("Ranks") != null) {
                        Set<String> ranks = config.getConfigurationSection("Ranks").getKeys(false);
                        for (String key : ranks) {
                            HackyWayToGetARank++;

                            String display = Main.format(config.getString("Ranks." + key + ".RankPrefix"));
                            if (HackyWayToGetARank == PlayerRank){
                                return display;
                            }
                        }
                    } else {
                        return "No Ranks";
                    }
                }

                if (params.equalsIgnoreCase("nextrank")){
                    File dataplayer = new File(Main.getInstance().getDataFolder() + "/data/" + p.getUniqueId() + ".yml");
                    Configuration PlayerIn = YamlConfiguration.loadConfiguration(dataplayer);
                    Configuration config = Main.getInstance().getConfig();
                    int PlayerRank = PlayerIn.getInt("PlayerData.RankNumber");
                    int HackyWayToGetARank = 0;

                    if (config.getConfigurationSection("Ranks") != null) {
                        Set<String> ranks = config.getConfigurationSection("Ranks").getKeys(false);
                        int num = ranks.size();
                        for (String key : ranks) {
                            HackyWayToGetARank++;

                            if (PlayerRank + 1 > num){
                                return "Max Rank";
                            }

                            String display = Main.format(config.getString("Ranks." + key + ".RankPrefix"));
                            if (HackyWayToGetARank == PlayerRank + 1){
                                return display;
                            }
                        }
                    } else {
                        return "No Ranks";
                    }
                }

                if (params.equalsIgnoreCase("currentprestige")){
                    File dataplayer = new File(Main.getInstance().getDataFolder() + "/data/" + p.getUniqueId() + ".yml");
                    Configuration PlayerIn = YamlConfiguration.loadConfiguration(dataplayer);
                    Configuration config = Main.getInstance().getConfig();
                    int PlayerRank = PlayerIn.getInt("PlayerData.PrestigeNumber");
                    int HackyWayToGetAPrestige = 0;

                    if (config.getConfigurationSection("Prestiges") != null || PlayerRank <= 0) {
                        Set<String> Prestiges = config.getConfigurationSection("Prestiges").getKeys(false);
                        for (String key : Prestiges) {
                            HackyWayToGetAPrestige++;

                            String display = Main.format(config.getString("Prestiges." + key + ".PrestigePrefix"));
                            if (HackyWayToGetAPrestige == PlayerRank){
                                return display;
                            }
                        }
                    } else {
                        return Main.format(Main.getMessages().getString("Messages.Default-NoPrestiges-Placeholder"));
                    }
                }

                if (params.equalsIgnoreCase("nextprestige")){
                    File dataplayer = new File(Main.getInstance().getDataFolder() + "/data/" + p.getUniqueId() + ".yml");
                    Configuration PlayerIn = YamlConfiguration.loadConfiguration(dataplayer);
                    Configuration config = Main.getInstance().getConfig();
                    int PlayerPrestige = PlayerIn.getInt("PlayerData.PrestigeNumber");
                    int HackyWayToGetAPrestige = 0;

                    if (config.getConfigurationSection("Prestiges") != null || PlayerPrestige <= 0) {
                        Set<String> Prestiges = config.getConfigurationSection("Prestiges").getKeys(false);
                        int num = Prestiges.size();
                        for (String key : Prestiges) {
                            HackyWayToGetAPrestige++;

                            if (PlayerPrestige + 1 > num){
                                return "Max Prestige";
                            }

                            String display = Main.format(config.getString("Prestiges." + key + ".PrestigePrefix"));
                            if (HackyWayToGetAPrestige == PlayerPrestige + 1){
                                return display;
                            }
                        }
                    } else {
                        return Main.format(Main.getMessages().getString("Messages.Default-NoPrestiges-Placeholder"));
                    }
                }

                return null;
            }
        });
    }

}

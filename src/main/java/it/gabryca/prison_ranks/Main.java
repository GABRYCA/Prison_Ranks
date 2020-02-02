package it.gabryca.prison_ranks;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.*;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.Set;

public class Main extends JavaPlugin {

    private Economy econ;
    private Permission perms;
    private Chat chat;

    private static Main config;

    static Main getInstance(){
        return config;
    }

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            this.getLogger().severe("[PrisonRanks] ERROR: You need to install Vault or the plugin won't work!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        } else {
            System.out.println(ChatColor.GREEN + "[PrisonRanks] Hooked into Vault with SUCCESS");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            System.out.println(ChatColor.GREEN + "[PrisonRanks] Hooked into PlaceholderAPI with SUCCESS");
        } else {
            this.getLogger().severe("[PrisonRanks] ERROR: You need to install PlaceholderAPI or the plugin won't work!");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")){
            System.out.println(ChatColor.GREEN + "[PrisonRanks] Hooked into MVdWPlaceholderAPI with SUCCESS");
        } else {
            System.out.println(ChatColor.YELLOW + "[PrisonRanks] WARN: MVdWPlaceholderAPI isn't installed, some placeholders may not work!");
        }
        this.setupPermissions();
        this.setupChat();
        System.out.println(ChatColor.GREEN + "[PrisonRanks] Plugin enabled with success!");
        Bukkit.getPluginManager().registerEvents(new listeners(),this);
        new Placeholders(this);
        Objects.requireNonNull(getCommand("Rankup")).setExecutor(new Rankup());
        Objects.requireNonNull(getCommand("Ranks")).setExecutor(new Ranks());
        Objects.requireNonNull(getCommand("Prestiges")).setExecutor(new Prestiges());
        Objects.requireNonNull(getCommand("Prestige")).setExecutor(new Prestige());
        Objects.requireNonNull(getCommand("PrisonRanks")).setExecutor(new PrisonRanks());
        this.saveDefaultConfig();
        config = this;
        this.saveConfig();
        new MessagesYML();
    }

    @Override
    public void onDisable() {
        System.out.println(ChatColor.RED + "[PlayerShop] Plugin disabled with success!");
    }

    static FileConfiguration getMessages(){
        MessagesYML messages = new MessagesYML();
        return messages.getFile();
    }

    public static String format(String format){
        return ChatColor.translateAlternateColorCodes('&', format);
    }

    static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    static void spawnFireworks(Location location, int amount){
        Firework fw = (Firework) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.RED).flicker(true).build());

        fw.setFireworkMeta(fwm);
        fw.detonate();

        for(int i = 0;i<amount; i++){
            Firework fw2 = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            fw2.setFireworkMeta(fwm);
        }
    }

    static Integer getRankNumber(Player p){
        File dataplayer = new File(Main.getInstance().getDataFolder() + "/data/" + p.getUniqueId() + ".yml");
        Configuration PlayerIn = YamlConfiguration.loadConfiguration(dataplayer);
        return PlayerIn.getInt("PlayerData.RankNumber");
    }

    static String getRankPrefix(Player p){
        int PlayerRank = getRankNumber(p);
        Configuration config = Main.getInstance().getConfig();
        int HackyWayToGetARank = 0;

        if (config.getConfigurationSection("Ranks") != null) {
            Set<String> ranks = Objects.requireNonNull(config.getConfigurationSection("Ranks")).getKeys(false);
            for (String key : ranks) {
                HackyWayToGetARank++;

                String rankPrefix = Main.format(config.getString("Ranks." + key + ".RankPrefix"));
                if (HackyWayToGetARank == PlayerRank){
                    return rankPrefix;
                }
            }
        }
            return "No Ranks";
    }

    static String getNextRankPrefix(Player p){
        int PlayerRank = getRankNumber(p);
        Configuration config = Main.getInstance().getConfig();
        int HackyWayToGetARank = 0;

        if (config.getConfigurationSection("Ranks") != null) {
            Set<String> ranks = Objects.requireNonNull(config.getConfigurationSection("Ranks")).getKeys(false);
            int num = ranks.size();
            for (String key : ranks) {
                HackyWayToGetARank++;

                if (PlayerRank + 1 > num){
                    return "Max Rank";
                }

                String NextRankPrefix = Main.format(config.getString("Ranks." + key + ".RankPrefix"));
                if (HackyWayToGetARank == PlayerRank + 1){
                    return NextRankPrefix;
                }
            }
        }
            return "No Ranks";
    }

    static Integer getNextRankPrice(Player p){
        Configuration config = Main.getInstance().getConfig();
        int PlayerRank = Main.getRankNumber(p);
        int HackyWayToGetARank = 0;
        double Multiplier = 0.00;
        if (config.getConfigurationSection("Prestiges") != null) {
            Multiplier = getMultiplier(p);
        }
        if (config.getConfigurationSection("Ranks") != null) {
            Set<String> ranks = config.getConfigurationSection("Ranks").getKeys(false);
            for (String key : ranks) {
                HackyWayToGetARank++;
                if (PlayerRank + 1 == HackyWayToGetARank) {
                    return (int) (config.getInt("Ranks." + key + ".Price") + config.getInt("Ranks." + key + ".Price") * Multiplier);
                }
            }
        }
        return Integer.valueOf("0.00");
    }

    static Integer getPrestigeNumber(Player p){
        File dataplayer = new File(Main.getInstance().getDataFolder() + "/data/" + p.getUniqueId() + ".yml");
        Configuration PlayerIn = YamlConfiguration.loadConfiguration(dataplayer);
        return PlayerIn.getInt("PlayerData.PrestigeNumber");
    }

    static String getPrestigePrefix(Player p){
        int PlayerPrestige = getPrestigeNumber(p);
        Configuration config = Main.getInstance().getConfig();
        int HackyWayToGetAPrestige = 0;

        if (config.getConfigurationSection("Prestiges") != null || PlayerPrestige <= 0) {
            Set<String> Prestiges = config.getConfigurationSection("Prestiges").getKeys(false);
            for (String key : Prestiges) {
                HackyWayToGetAPrestige++;

                String prestigePrefix = Main.format(config.getString("Prestiges." + key + ".PrestigePrefix"));
                if (HackyWayToGetAPrestige == PlayerPrestige){
                    return prestigePrefix;
                }
            }
        }
            return Main.format(Main.getMessages().getString("Messages.Default-NoPrestiges-Placeholder"));
    }

    static String getNextPrestigePrefix(Player p){
        int PlayerPrestige = getPrestigeNumber(p);
        Configuration config = Main.getInstance().getConfig();
        int HackyWayToGetAPrestige = 0;

        if (config.getConfigurationSection("Prestiges") != null || PlayerPrestige <= 0) {
            Set<String> Prestiges = config.getConfigurationSection("Prestiges").getKeys(false);
            int num = Prestiges.size();
            for (String key : Prestiges) {
                HackyWayToGetAPrestige++;

                if (PlayerPrestige + 1 > num){
                    return "Max Prestige";
                }

                String nextPrestigePrefix = Main.format(config.getString("Prestiges." + key + ".PrestigePrefix"));
                if (HackyWayToGetAPrestige == PlayerPrestige + 1){
                    return nextPrestigePrefix;
                }
            }
        }
            return Main.format(Main.getMessages().getString("Messages.Default-NoPrestiges-Placeholder"));
    }

    static Integer getNextPrestigePrice(Player p){
        Configuration config = Main.getInstance().getConfig();
        int PlayerRank = Main.getPrestigeNumber(p);
        int HackyWayToGetAPrestige = 0;
        if (config.getConfigurationSection("Prestiges") != null) {
            Set<String> ranks = config.getConfigurationSection("Prestiges").getKeys(false);
            for (String key : ranks) {
                HackyWayToGetAPrestige++;
                if (PlayerRank + 1 == HackyWayToGetAPrestige) {
                    return config.getInt("Prestiges." + key + ".Price");
                }
            }
        }
        return Integer.valueOf("0.00");
    }

    static Double getMultiplier(Player p){
        Configuration config = Main.getInstance().getConfig();
        int PlayerPrestige = Main.getPrestigeNumber(p);

        if (config.getConfigurationSection("Ranks") != null) {
            int HackyWayToGetAPrestige = 0;
            double Multiplier;
            if (config.getConfigurationSection("Prestiges") != null) {
                Set<String> prestiges = config.getConfigurationSection("Prestiges").getKeys(false);
                for (String key : prestiges) {
                    if (HackyWayToGetAPrestige == PlayerPrestige) {
                        if (config.getString("Prestiges." + key + ".Multiplier") != null) {
                            Multiplier = config.getDouble("Prestiges." + key + ".Multiplier");
                            return Multiplier;
                        }
                    }
                    HackyWayToGetAPrestige++;
                }
            }
        }
        return Double.parseDouble("0.00");
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp == null){
            return false;
        }
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);

        perms = rsp.getProvider();
        return perms != null;
    }

    public Economy getEconomy() {
        return econ;
    }

    public Permission getPermissions() {
        return perms;
    }

    public Chat getChat() {
        return chat;
    }

}

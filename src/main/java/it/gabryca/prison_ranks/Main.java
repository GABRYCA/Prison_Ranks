package it.gabryca.prison_ranks;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

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
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")){
            System.out.println(ChatColor.GREEN + "[PrisonRanks] Hooked into MVdWPlaceholderAPI");
        } else {
            System.out.println(ChatColor.YELLOW + "[PrisonRanks] WARN: MVdWPlaceholderAPI isn't installed, some placeholders may not work!");
        }
        this.setupPermissions();
        this.setupChat();
        System.out.println(ChatColor.GREEN + "[PrisonRanks] Plugin enabled with success!");
        Bukkit.getPluginManager().registerEvents(new listeners(),this);
        new Placeholders(this);
        getCommand("Rankup").setExecutor(new Rankup());
        getCommand("AddRank").setExecutor(new AddRank());
        getCommand("AddPrestige").setExecutor(new AddPrestige());
        getCommand("Ranks").setExecutor(new Ranks());
        getCommand("DelRank").setExecutor(new DelRank());
        getCommand("DelPrestige").setExecutor(new DelPrestige());
        getCommand("ChangeRankPrefix").setExecutor(new ChangeRankPrefix());
        getCommand("ChangeRankPrice").setExecutor(new ChangeRankPrice());
        getCommand("ChangePrestigePrefix").setExecutor(new ChangePrestigePrefix());
        getCommand("ChangePrestigePrice").setExecutor(new ChangePrestigePrice());
        getCommand("Prestiges").setExecutor(new Prestiges());
        getCommand("Prestige").setExecutor(new Prestige());
        getCommand("AddRankupCommand").setExecutor(new AddRankupCommand());
        getCommand("AddPrestigeCommand").setExecutor(new AddPrestigeCommand());
        getCommand("DelRankupCommand").setExecutor(new DelRankupCommand());
        getCommand("DelPrestigeCommand").setExecutor(new DelPrestigeCommand());
        getCommand("PrisonRanks").setExecutor(new PrisonRanks());
        getCommand("ResetPlayerPrestige").setExecutor(new ResetPlayerPrestige());
        getCommand("ResetPlayerRank").setExecutor(new ResetPlayerRank());
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

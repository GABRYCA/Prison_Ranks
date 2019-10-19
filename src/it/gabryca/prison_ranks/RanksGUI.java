package it.gabryca.prison_ranks;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RanksGUI {

    int dimension = 54;
    private Player p;

    RanksGUI(Player p){
        this.p = p;
    }

    private ItemStack createButton(Material id, int amount, List<String> lore, String display) {

        ItemStack item = new ItemStack(id, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(display);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    void open() {

        File dataplayer = new File(Main.getInstance().getDataFolder() + "/data/" + p.getUniqueId() + ".yml");
        Configuration PlayerIn = YamlConfiguration.loadConfiguration(dataplayer);
        Configuration config = Main.getInstance().getConfig();
        Configuration message = Main.getMessages();
        Economy econ = Main.getInstance().getEconomy();
        int PlayerBalance = (int) econ.getBalance(p);
        int PlayerRank = PlayerIn.getInt("PlayerData.RankNumber");
        int HackyWayToGetARank = 0;

        if (config.getConfigurationSection("Ranks") != null) {
            Set<String> ranks = config.getConfigurationSection("Ranks").getKeys(false);
            int num = ranks.size();
            List<String> loretest = new ArrayList<String>();
            loretest.add("Something didn't work! ");
            ItemStack item = createButton(Material.EMERALD_BLOCK, 1, loretest, "§6" + PlayerRank);
            while (dimension < num + 8) {
                dimension = dimension + 9;
            }
            Inventory inv = Bukkit.createInventory(null, dimension, "§7Ranks");
            for (String key : ranks) {
                    List<String> lore = new ArrayList<String>();
                    lore.add(message.getString("Messages.Price") + config.getInt("Ranks." + key + ".Price"));
                    String display = config.getString("Ranks." + key + ".RankPrefix");
                    inv.addItem(createButton(Material.valueOf(config.getString("Settings.Default-Rank-Material")), 1, lore, "§6" + display));
                    HackyWayToGetARank++;
                    if (PlayerRank == HackyWayToGetARank){
                        String display2 = null;
                    if (PlayerBalance >= config.getInt("Ranks." + key + ".Price")){
                        display2 = message.getString("Messages.ClickToRankup");
                    } else {
                        display2 = message.getString("Messages.NotEnoughMoney");
                    }
                    List<String> lore2 = new ArrayList<String>();
                    lore2.add(config.getString("§6" + "Ranks." + key + ".RankPrefix"));
                    lore2.add(message.getString("Messages.YourMoney") + PlayerBalance);
                    lore2.add(message.getString("Messages.Price") + config.getInt("Ranks." + key + ".Price"));
                    item = createButton(Material.EMERALD_BLOCK, 1, lore2, "§6" + display2);
                }
            }
            inv.setItem(dimension - 5, item);
            this.p.openInventory(inv);
        } else {
            p.closeInventory();
            p.sendMessage(message.getString("Messages.NoRanksEverCreated"));
        }
    }

}

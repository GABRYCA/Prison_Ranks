package it.gabryca.prison_ranks;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PrestigesGUI {

    int dimension = 54;
    private Player p;

    PrestigesGUI(Player p){
        this.p = p;
    }

    private ItemStack createButton(Material id, int amount, List<String> lore, String display) {

        ItemStack item = new ItemStack(id, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(display);
        meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
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
        int PlayerRank = Main.getRankNumber(p);
        int HackyWayToGetARank = 0;

        if (config.getConfigurationSection("Prestiges") != null) {
            Set<String> ranks = config.getConfigurationSection("Prestiges").getKeys(false);
            int num = ranks.size();
            List<String> loretest = new ArrayList<String>();
            loretest.add("Something didn't work! ");
            ItemStack item = createButton(Material.EMERALD_BLOCK, 1, loretest, "§6" + PlayerRank);
            ItemStack itemrank;
            while (dimension < num + 8) {
                dimension = dimension + 9;
            }
            Inventory inv = Bukkit.createInventory(null, dimension, "§cPrestiges");
            for (String key : ranks) {
                HackyWayToGetARank++;

                List<String> lore = new ArrayList<String>();
                lore.add(message.getString("Messages.Price") + config.getInt("Prestiges." + key + ".Price"));
                if (config.getString("Prestiges." + key + ".Multiplier") != null){
                    lore.add("§9Multiplier: §7" + config.getDouble("Prestiges." + key + ".Multiplier"));
                } else {
                    lore.add("§9Multiplier: §7None");
                }
                String display = Main.format(config.getString("Prestiges." + key + ".PrestigePrefix"));
                if (HackyWayToGetARank <= PlayerRank){
                    Enchantment enchantitem = Enchantment.LUCK;
                    lore.add(message.getString("Messages.AlreadyHave"));
                    itemrank = createButton(Material.valueOf(config.getString("Settings.Default-Prestige-Material")), 1, lore, "§6" + display);
                    itemrank.addUnsafeEnchantment(enchantitem, 1);
                    inv.addItem(itemrank);
                } else {
                    lore.add(message.getString("Messages.DontHave"));
                    itemrank = createButton(Material.valueOf(config.getString("Settings.Default-NotReachedPrestige-Material")), 1, lore, "§6" + display);
                    inv.addItem(itemrank);
                }

                if (PlayerRank + 1 > num){
                    String display2 = (message.getString("Messages.MaxPrestige"));
                    List<String> lore2 = new ArrayList<String>();
                    lore2.add(config.getString(display2));
                    item = createButton(Material.EMERALD_BLOCK, 1, lore2, "§6" + display2);
                }

                if (PlayerRank + 1 == HackyWayToGetARank){
                    String display2;
                    if (PlayerBalance >= config.getInt("Prestiges." + key + ".Price")){
                        display2 = message.getString("Messages.ClickToPrestige");
                        Enchantment enchant = Enchantment.LUCK;
                        List<String> lore2 = new ArrayList<String>();
                        lore2.add(Main.format(config.getString("Prestiges." + key + ".PrestigePrefix")));
                        lore2.add(message.getString("Messages.YourMoney") + PlayerBalance);
                        lore2.add(message.getString("Messages.Price") + config.getInt("Prestiges." + key + ".Price"));
                        item = createButton(Material.EMERALD_BLOCK, 1, lore2, "§6" + display2);
                        item.addUnsafeEnchantment(enchant, 1);
                    } else {
                        display2 = message.getString("Messages.NotEnoughMoneyToPrestige");
                        List<String> lore2 = new ArrayList<String>();
                        lore2.add(Main.format(config.getString("Prestiges." + key + ".PrestigePrefix")));
                        lore2.add(message.getString("Messages.YourMoney") + PlayerBalance);
                        lore2.add(message.getString("Messages.Price") + config.getInt("Prestiges." + key + ".Price"));
                        item = createButton(Material.EMERALD_BLOCK, 1, lore2, "§6" + display2);
                    }
                }
            }
            inv.setItem(dimension - 5, item);
            this.p.openInventory(inv);
        } else {
            p.closeInventory();
            p.sendMessage(message.getString("Messages.NoPrestigesEverCreated"));
        }
    }

}

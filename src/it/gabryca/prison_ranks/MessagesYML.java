package it.gabryca.prison_ranks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

class MessagesYML {

    private File file = new File(Main.getInstance().getDataFolder()+"/messages.yml");
    private FileConfiguration conf;

    MessagesYML() {
        if(!file.exists()){
            try {
                file.createNewFile();
                conf = YamlConfiguration.loadConfiguration(file);
                conf.createSection("Messages");
                conf.set("Messages.WrongFormat", "§cWrong format, please retry or contact the support");
                conf.set("Messages.NoPerm", "§cSorry but you haven't the permissions to do that");
                conf.set("Messages.Rank-Add-Success", "§aRank added with success");
                conf.set("Messages.Prestige-Add-Success", "§aPrestige added with success");
                conf.set("Messages.OnlyPlayer", "§cSorry but only players can execute this command (Hi Console!)");
                conf.set("Messages.Price", "§6Price: §a$");
                conf.set("Messages.ClickToRankup", "§aClick me to Rankup!");
                conf.set("Messages.NotEnoughMoney", "§cYou don't have enough money!");
                conf.set("Messages.YourMoney", "§6Your money: §a$");
                conf.set("Messages.NoRanksEverCreated", "§cThere aren't ranks in this server.");
                conf.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        conf = YamlConfiguration.loadConfiguration(file);
    }

    FileConfiguration getFile(){
        return conf;
    }

}

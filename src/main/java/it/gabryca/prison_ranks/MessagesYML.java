package it.gabryca.prison_ranks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

class MessagesYML {

    private FileConfiguration conf;

    MessagesYML() {
        File file = new File(Main.getInstance().getDataFolder() + "/messages.yml");
        if(!file.exists()){
            try {
                file.createNewFile();
                conf = YamlConfiguration.loadConfiguration(file);
                conf.createSection("Messages");
                conf.set("Messages.WrongFormat", "§cWrong format, please retry or check the wiki");
                conf.set("Messages.NoPerm", "§cSorry but you haven't the permission to do that");
                conf.set("Messages.Rank-Add-Success", "§aRank added with success");
                conf.set("Messages.Rank-Remove-Success", "§aRank deleted with success");
                conf.set("Messages.Prestige-Add-Success", "§aPrestige added with success");
                conf.set("Messages.Prestige-Remove-Sucess", "§aPrestige deleted with success");
                conf.set("Messages.OnlyPlayer", "§cSorry but only players can execute this command (Hi Console!)");
                conf.set("Messages.Price", "§6Price: §a");
                conf.set("Messages.ClickToRankup", "§aClick me to Rankup!");
                conf.set("Messages.ClickToPrestige", "§aClick me to Prestige if you meet the requirements");
                conf.set("Messages.NotEnoughMoney", "§cYou don't have enough money!");
                conf.set("Messages.NotEnoughMoneyToPrestige", "§cYou don't meet the requirements!");
                conf.set("Messages.YourMoney", "§6Your money: §a");
                conf.set("Messages.NoRanksEverCreated", "§cThere aren't ranks in this server.");
                conf.set("Messages.NoPrestigesEverCreated", "§cThere aren't prestiges in this server");
                conf.set("Messages.ThePlayer", "§6The Player §c[§6§l");
                conf.set("Messages.DidRankup", "§c] §6have rankup to ");
                conf.set("Messages.DidPrestige", "§c] §6§lhave prestiged to ");
                conf.set("Messages.YouRankup", "§6You've rankup to ");
                conf.set("Messages.YouPrestiged", "§6You've prestiged to ");
                conf.set("Messages.MaxRank", "§cSorry, but you're already at the §c§dMAX §crank, please before do a prestige with §1/Prestige");
                conf.set("Messages.NotMaxRank", "§cSorry, but you must be at the §c§dMAX §crank before prestige");
                conf.set("Messages.MaxPrestige", "§cSorry, but you're already at the §c§dMAX §cprestige, you've unlocked everything!");
                conf.set("Messages.AlreadyHave", "§aYou already unlocked this rank");
                conf.set("Messages.DontHave", "§cYou haven't unlocked this rank");
                conf.set("Messages.RankNotFound", "§cRank not found, check if the name it's correct");
                conf.set("Messages.PlayerNotFound", "§cPlayer not found");
                conf.set("Messages.PrestigeNotFound", "§cPrestige not found, check if the name it's correct");
                conf.set("Messages.Rank-Edited-Success", "§aRank edited with success");
                conf.set("Messages.PrestigeNotFound", "§cPrestige not found, check if the name it's correct");
                conf.set("Messages.Prestige-Edited-Success", "§aPrestige edited with success");
                conf.set("Messages.Default-NoPrestiges-Placeholder", "No Prestiges");
                conf.set("Messages.NoRankupCommands", "§cThe rank has no command");
                conf.set("Messages.RankCommands", "§cThe rank have these commands:");
                conf.set("Messages.DelRankupCommandFormat", "§cTo delete a RankupCommand do: /delrankupcommand <rank> <§6id/number§c>");
                conf.set("Messages.RankupCommandDelSuccess", "§aThe RankupCommand got deleted with success");
                conf.set("Messages.NoPrestigeCommands", "§cThe prestige has no command");
                conf.set("Messages.PrestigeCommands", "§cThe prestige have these commands:");
                conf.set("Messages.DelPrestigeCommandFormat", "§cTo delete a PrestigeCommand do: /delrankupcommand <rank> <§6id/number§c>");
                conf.set("Messages.PrestigeCommandDelSuccess", "§aThe PrestigeCommand got deleted with success");
                conf.set("Messages.PlayerRankResetSuccess", "§aThe Player Rank got reset with success");
                conf.set("Messages.PlayerPrestigeResetSuccess", "§aThe Player Prestige got reset with success");
                conf.set("Messages.PlayerRankChangeSuccess", "§aThe Player Rank got changed with success");
                conf.set("Messages.PlayerPrestigeChangeSuccess", "§aThe player Prestige got changed with success");
                conf.set("Messages.PrestigeMultiplierAddSuccess", "§aPrestige multiplier added with success");
                conf.set("Messages.PrestigeMultiplierRemoveSuccess", "§aPrestige multiplier removed with success");
                conf.set("Messages.NoMultiplierFound", "§cThis prestige it's already without a multiplier");
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

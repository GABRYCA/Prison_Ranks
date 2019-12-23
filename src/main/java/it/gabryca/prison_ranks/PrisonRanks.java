package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

public class PrisonRanks implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Configuration config = Main.getInstance().getConfig();

        if(sender.hasPermission(config.getString("Permissions.Admin-Management"))){

            sender.sendMessage("§9-------------------------");
            sender.sendMessage("       §7PrisonRanks       ");
            sender.sendMessage("§9-------------------------");
            sender.sendMessage("§9PrisonRanks: §7Show a list of commands");
            sender.sendMessage("§9Rankup: §7Just do a rankup");
            sender.sendMessage("§9Prestige: §7Just do a prestige");
            sender.sendMessage("§9AddRank: §7Add a rank, usage: /addrank <Name> <Prefix> <Price>");
            sender.sendMessage("§9AddRankupCommand: §7Add a command to execute on the rankup, usage: /addrankupcommand <rank> <command>");
            sender.sendMessage("§9AddPrestige: §7Add a prestige, usage: /addprestige <Name> <Prefix> <Price>");
            sender.sendMessage("§9AddPrestigeCommand: §7Add a command to execute on the prestige, usage : /addprestigecommand <prestige> <command>");
            sender.sendMessage("§9DelRank: §7Delete a rank, usage: /delrank <RankName>");
            sender.sendMessage("§9DelRankupCommand: §7Delete a command executed on a rankup, usage: /delrankupcommand <rank> <commandID_or_number>, do /delrankupcommand <rank> to see a list and ID of commands");
            sender.sendMessage("§9DelPrestige: §7Delete a prestige. usage: /delprestige <PrestigeName>");
            sender.sendMessage("§9DelPrestigeCommand: §7Delete a command executed on a prestige, usage: /delprestigecommand <prestige> <commandID_or_number>, do /delprestigecommand <prestige> to see a list and ID of commands");
            sender.sendMessage("§9ChangeRankPrefix: §7Change a rank prefix, usage: /changerankprefix <rank> <NewPrefix>");
            sender.sendMessage("§9ChangeRankPrice: §7Change a rank price, usage: /changerankprice <rank> <NewPrice>");
            sender.sendMessage("§9ChangePrestigePrefix, §7Change a prestige prefix, usage: /changeprestigeprefix <prestige> <NewPrefix>");
            sender.sendMessage("§9ChangePrestigePrice, §7Change a prestige price, usage: /changeprestigeprice <prestige> <price>");
            sender.sendMessage("§9Ranks, §7Open a GUI with a list of ranks");
            sender.sendMessage("§9Prestiges, §7Open a GUI with a list of prestiges");
            sender.sendMessage("§9ResetPlayerRank, §7Reset a player rank, usage: /ResetPlayerRank <PlayerName>");
            sender.sendMessage("§9ResetPlayerPrestige, §7Reset a player prestige, usage: /resetplayerpresige <PlayerName>");
            sender.sendMessage("§9ChangePlayerRank, §7Change the player rank to another, usage: /ChangePlayerRank <PlayerName> <RankNumber>, check the RankNumber using /rankinfo <rank>");
            sender.sendMessage("§9ChangePlayerPrestige, §7Change the player prestige to another, usage: /ChangePlayerPrestige <PlayerName> <PrestigeNumber>, check the PrestigeNumber using /prestigeinfo <prestige>");
            sender.sendMessage("§9RankInfo, §7See many info about a rank, usage: /rankinfo <Rank>");
            sender.sendMessage("§9PrestigeInfo, §7See many info about a prestige, usage: /prestigeinfo <Prestige>");
            sender.sendMessage("§9AddMultiplier, §7Add a multiplier to a prestige: /AddMultiplier <Prestige> <Multiplier>");
            sender.sendMessage("§9DelMultiplier, §7Remove a multiplier from a prestige, usage: /DelMultiplier <Prestige>");

        } else {

            sender.sendMessage("§9-------------------------");
            sender.sendMessage("       §7PrisonRanks       ");
            sender.sendMessage("§9-------------------------");
            sender.sendMessage("§9PrisonRanks: §7Show a list of commands");
            sender.sendMessage("§9Rankup: §7Just rankup");
            sender.sendMessage("§9Prestige: §7Just prestige");
            sender.sendMessage("§9Ranks, §7Open a GUI with a list of ranks");
            sender.sendMessage("§9Prestiges, §7Open a GUI with a list of prestiges");
            sender.sendMessage("§9RankInfo, §7See many info about a rank, usage: /rankinfo <Rank>");
            sender.sendMessage("§9PrestigeInfo, §7See many info about a prestige, usage: /prestigeinfo <Prestige>");

        }
        return true;
    }
}

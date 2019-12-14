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

        } else {

            sender.sendMessage("§9-------------------------");
            sender.sendMessage("       §7PrisonRanks       ");
            sender.sendMessage("§9-------------------------");
            sender.sendMessage("§9PrisonRanks: §7Show a list of commands");
            sender.sendMessage("§9Rankup: §7Just do a rankup");
            sender.sendMessage("§9Prestige: §7Just do a prestige");
            sender.sendMessage("§9Ranks, §7Open a GUI with a list of ranks");
            sender.sendMessage("§9Prestiges, §7Open a GUI with a list of prestiges");

        }
        return true;
    }
}

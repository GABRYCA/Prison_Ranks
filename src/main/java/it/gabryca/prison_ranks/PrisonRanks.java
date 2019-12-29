package it.gabryca.prison_ranks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

public class PrisonRanks implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Configuration config = Main.getInstance().getConfig();

        // prisonranks
        // Check parameters
        if (args.length == 0) {

            if (sender.hasPermission(config.getString("Permissions.Admin-Management"))) {

                sender.sendMessage("§9-------------------------");
                sender.sendMessage("       §7PrisonRanks       ");
                sender.sendMessage("§9-------------------------");
                sender.sendMessage("§3/PrisonRanks §6| §3Show a list of commands");
                sender.sendMessage("§3/Rankup §6| §3Just do a rankup");
                sender.sendMessage("§3/Prestige §6| §3Just do a prestige");
                sender.sendMessage("§3/AddRank <Name> <Prefix> <Price> §6| §3Add a rank");
                sender.sendMessage("§3/AddRankupCommand <Rank> <Command> §6| §3Add a command to execute on the rankup");
                sender.sendMessage("§3/AddPrestige <Name> <Prefix> <Price> §6| §3Add a prestige");
                sender.sendMessage("§3/AddPrestigeCommand <Prestige> <Command> §6| §3Add a command to execute on the prestige");
                sender.sendMessage("§3/DelRank <RankName> §6| §3Delete a rank");
                sender.sendMessage("§3/DelRankupCommand <Rank> <commandID_or_number> §6| §3Delete a command executed on a rankup, do /delrankupcommand <rank> to see a list and ID of commands");
                sender.sendMessage("§3/DelPrestige <PrestigeName> §6| §3Delete a prestige");
                sender.sendMessage("§3/DelPrestigeCommand <Prestige> <commandID_or_number> §6| §3Delete a command executed on a prestige, do /delprestigecommand <prestige> to see a list and ID of commands");
                sender.sendMessage("§3/ChangeRankPrefix <Rank> <NewPrefix> §6| §3Change a rank prefix");
                sender.sendMessage("§3/ChangeRankPrice <Rank> <NewPrice> §6| §3Change a rank price");
                sender.sendMessage("§3/ChangePrestigePrefix <Prestige> <NewPrefix> §6| §3Change a prestige prefix");
                sender.sendMessage("§3/ChangePrestigePrice <Prestige> <Price> §6| §3Change a prestige price");
                sender.sendMessage("§3/Ranks §6| §3Open a GUI with a list of ranks");
                sender.sendMessage("§3/Prestiges §6| §3Open a GUI with a list of prestiges");
                sender.sendMessage("§3/ResetPlayerRank <PlayerName> §6| §3Reset a player rank");
                sender.sendMessage("§3/ResetPlayerPrestige <PlayerName> §6| §3Reset a player prestige");
                sender.sendMessage("§3/ChangePlayerRank <PlayerName> <RankNumber> §6| §3Change the player rank to another, check the RankNumber using /rankinfo <rank>");
                sender.sendMessage("§3/ChangePlayerPrestige <PlayerName> <PrestigeNumber> §6| §3Change the player prestige to another, check the PrestigeNumber using /prestigeinfo <prestige>");
                sender.sendMessage("§3/RankInfo <Rank> §6| §3See many info about a rank");
                sender.sendMessage("§3/PrestigeInfo <Prestige> §6| §3See many info about a prestige");
                sender.sendMessage("§3/AddMultiplier <Prestige> <Multiplier> §6| §3Add a multiplier to a prestige");
                sender.sendMessage("§3/DelMultiplier <Prestige> §6| §3Remove a multiplier from a prestige");

            } else {

                sender.sendMessage("§9-------------------------");
                sender.sendMessage("       §7PrisonRanks       ");
                sender.sendMessage("§9-------------------------");
                sender.sendMessage("§3/PrisonRanks §6| §3Show a list of commands");
                sender.sendMessage("§3/Rankup §6| §3Just rankup");
                sender.sendMessage("§3/Prestige §6| §3Just prestige");
                sender.sendMessage("§3/Ranks §6| §3Open a GUI with a list of ranks");
                sender.sendMessage("§3/Prestiges §6| §3Open a GUI with a list of prestiges");

            }
            return true;
        }

        return true;
    }
}

package it.gabryca.prison_ranks;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Objects;

public class PrisonRanks implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Configuration config = Main.getInstance().getConfig();

        // prisonranks
        // Check parameters
        if (args.length == 0) {

            if (sender.hasPermission(Objects.requireNonNull(config.getString("Permissions.Admin-Management")))) {

                sender.sendMessage("§9---------------------------");
                sender.sendMessage("      §7PrisonRanks 0.8      ");
                sender.sendMessage("§9---------------------------");
                sender.sendMessage("§3/PrisonRanks §6| §3Show a list of commands");
                sender.sendMessage("§3/Rankup §6| §3Just do a rankup");
                sender.sendMessage("§3/Prestige §6| §3Just do a prestige");
                sender.sendMessage("§3/PrisonRanks AddRank <Name> <Prefix> <Price> §6| §3Add a rank");
                sender.sendMessage("§3/PrisonRanks AddRankupCommand <Rank> <Command> §6| §3Add a command to execute on the rankup");
                sender.sendMessage("§3/PrisonRanks AddPrestige <Name> <Prefix> <Price> §6| §3Add a prestige");
                sender.sendMessage("§3/PrisonRanks AddPrestigeCommand <Prestige> <Command> §6| §3Add a command to execute on the prestige");
                sender.sendMessage("§3/PrisonRanks DelRank <RankName> §6| §3Delete a rank");
                sender.sendMessage("§3/PrisonRanks DelRankupCommand <Rank> <commandID_or_number> §6| §3Delete a command executed on a rankup, do /delrankupcommand <rank> to see a list and ID of commands");
                sender.sendMessage("§3/PrisonRanks DelPrestige <PrestigeName> §6| §3Delete a prestige");
                sender.sendMessage("§3/PrisonRanks DelPrestigeCommand <Prestige> <commandID_or_number> §6| §3Delete a command executed on a prestige, do /delprestigecommand <prestige> to see a list and ID of commands");
                sender.sendMessage("§3/PrisonRanks ChangeRankPrefix <Rank> <NewPrefix> §6| §3Change a rank prefix");
                sender.sendMessage("§3/PrisonRanks ChangeRankPrice <Rank> <NewPrice> §6| §3Change a rank price");
                sender.sendMessage("§3/PrisonRanks ChangePrestigePrefix <Prestige> <NewPrefix> §6| §3Change a prestige prefix");
                sender.sendMessage("§3/PrisonRanks ChangePrestigePrice <Prestige> <Price> §6| §3Change a prestige price");
                sender.sendMessage("§3/Ranks §6| §3Open a GUI with a list of ranks");
                sender.sendMessage("§3/Prestiges §6| §3Open a GUI with a list of prestiges");
                sender.sendMessage("§3/PrisonRanks ResetPlayerRank <PlayerName> §6| §3Reset a player rank");
                sender.sendMessage("§3/PrisonRanks ResetPlayerPrestige <PlayerName> §6| §3Reset a player prestige");
                sender.sendMessage("§3/PrisonRanks ChangePlayerRank <PlayerName> <RankNumber> §6| §3Change the player rank to another, check the RankNumber using /rankinfo <rank>");
                sender.sendMessage("§3/PrisonRanks ChangePlayerPrestige <PlayerName> <PrestigeNumber> §6| §3Change the player prestige to another, check the PrestigeNumber using /prestigeinfo <prestige>");
                sender.sendMessage("§3/PrisonRanks RankInfo <Rank> §6| §3See many info about a rank");
                sender.sendMessage("§3/PrisonRanks PrestigeInfo <Prestige> §6| §3See many info about a prestige");
                sender.sendMessage("§3/PrisonRanks AddMultiplier <Prestige> <Multiplier> §6| §3Add a multiplier to a prestige");
                sender.sendMessage("§3/PrisonRanks DelMultiplier <Prestige> §6| §3Remove a multiplier from a prestige");

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

        String param = args[0];
        int NotNull = 0;
        while (NotNull != args.length - 1){
            args[NotNull] = args[NotNull + 1];
            NotNull++;
        }

        args = (String[]) ArrayUtils.removeElement(args, args[NotNull]);


        if (param.equalsIgnoreCase("addmultiplier")){
            return AddMultiplier.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("addprestige")){
            return AddPrestige.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("addprestigecommand")){
            return AddPrestigeCommand.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("addrank")){
            return AddRank.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("addrankupcommand")){
            return AddRankupCommand.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("changeplayerprestige")){
            return ChangePlayerPrestige.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("changeplayerrank")){
            return ChangePlayerRank.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("changeprestigeprefix")){
            return ChangePrestigePrefix.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("changeprestigeprice")){
            return ChangePrestigePrice.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("changerankprefix")){
            return ChangeRankPrefix.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("changerankprice")){
            return ChangeRankPrice.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("delmultiplier")){
            return DelMultiplier.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("delprestige")){
            return DelMultiplier.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("delprestigecommand")){
            return DelPrestigeCommand.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("delrank")){
            return DelRank.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("delrankupcommand")){
            return DelRankupCommand.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("prestigeinfo")){
            return PrestigeInfo.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("rankinfo")){
            return RankInfo.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("resetplayerprestige")){
            return ResetPlayerPrestige.onCommand(sender, command, label, args);
        }

        if (param.equalsIgnoreCase("resetplayerrank")){
            return ResetPlayerRank.onCommand(sender, command, label, args);
        }

        return true;
    }
}

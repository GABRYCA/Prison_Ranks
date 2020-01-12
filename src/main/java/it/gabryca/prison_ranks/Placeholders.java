package it.gabryca.prison_ranks;

import me.clip.placeholderapi.PlaceholderHook;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Placeholders implements Listener {
    private Main plugin ;
    public Placeholders(Main plugin){
        this.plugin = plugin;
        registerPlaceholders();
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    private void registerPlaceholders() {
        PlaceholderAPI.registerPlaceholderHook("prisonranks", new PlaceholderHook() {
            @Override
            public String onRequest(OfflinePlayer p, String params) {
                if (p!=null && p.isOnline()) {
                    return onPlaceholderRequest(p.getPlayer(), params);
                }
                return null;
            }

            @Override
            public String onPlaceholderRequest(Player p, String params){
                if (p==null){
                    return null;
                }
                // %prisonranks_PARAMS%
                if (params.equalsIgnoreCase("currentrank")){
                    return Main.getRankPrefix(p);
                }

                if (params.equalsIgnoreCase("nextrank")){
                    return Main.getNextRankPrefix(p);
                }

                if (params.equalsIgnoreCase("nextrankprice")){
                    return String.valueOf(Main.getNextRankPrice(p));
                }

                if (params.equalsIgnoreCase("currentprestige")){
                    return Main.getPrestigePrefix(p);
                }

                if (params.equalsIgnoreCase("nextprestige")){
                    return Main.getNextPrestigePrefix(p);
                }

                if (params.equalsIgnoreCase("nextprestigeprice")){
                    return String.valueOf(Main.getNextPrestigePrice(p));
                }

                if (params.equalsIgnoreCase("multiplier")){
                    return String.valueOf(Main.getMultiplier(p));
                }



                return null;
            }
        });
    }

}

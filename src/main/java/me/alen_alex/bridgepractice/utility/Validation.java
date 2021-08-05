package me.alen_alex.bridgepractice.utility;

import me.Abhigya.core.version.CoreVersion;
import me.alen_alex.bridgepractice.configurations.Configuration;
import org.bukkit.Bukkit;

public class Validation {

    public static boolean validateDatabase(){
        if(Configuration.getHost().length() != 0 && Configuration.getUsername().length() != 0 && Configuration.getPort().length() != 0 && Configuration.getDatabase().length() != 0)
            return true;
        else
            return false;
    }

    public static boolean ValidateCoreAPI(){
        if(!(CoreVersion.getCoreVersion() == CoreVersion.v1_1_2))
            return false;
        else
            return true;
    }

    public static boolean ValidatePlaceholderAPI(){
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
            return true;
        else
            return false;
    }

    public static boolean validateHolograms(){
        if(Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays"))
            return true;
        else {
            Bukkit.getLogger().severe("Found holograms.enabled to true without having the plugin, Disabling Holographic support!!");
            Configuration.getConfig().set("holograms.enabled",false);
            return false;
        }
    }

    public static void checkLobbyLocation(){
        if(Configuration.getLobbyLocation() == null || Configuration.getLobbyLocation().equalsIgnoreCase("")){
            Configuration.getConfig().set("settings.lobby-location", Location.parseLocation(Bukkit.getWorlds().get(0).getSpawnLocation()));
            Bukkit.getLogger().severe("=================================================================");
            Bukkit.getLogger().severe("settings.lobby-location not found, setting default location of the world "+Bukkit.getServer().getWorlds().get(0).getName());
            Bukkit.getLogger().severe("use /practiceadmin setlobby");
            Bukkit.getLogger().severe("=================================================================");
        }
    }

}

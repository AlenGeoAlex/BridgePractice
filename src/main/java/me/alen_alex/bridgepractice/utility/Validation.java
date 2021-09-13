package me.alen_alex.bridgepractice.utility;

import me.Abhigya.core.version.CoreVersion;
import me.alen_alex.bridgepractice.configurations.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class Validation {

    public static boolean validateDatabase(){
        if(Configuration.getHost().length() != 0 && Configuration.getUsername().length() != 0 && Configuration.getPort().length() != 0 && Configuration.getDatabase().length() != 0)
            return true;
        else
            return false;
    }

    public static boolean ValidateCoreAPI(){
        if(!(CoreVersion.getCoreVersion().isNewerEquals(CoreVersion.v1_2_1)))
            return false;
        else
            return true;
    }

    public static boolean isAdvancedReplayEnabled(){
        return Bukkit.getPluginManager().isPluginEnabled("AdvancedReplay");
    }

    public static boolean ValidatePlaceholderAPI(){
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
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

    public static boolean validateVaultAPI(){
        return Bukkit.getPluginManager().isPluginEnabled("Vault");
    }

    public static boolean validateCitizens(){
        return Bukkit.getPluginManager().isPluginEnabled("Citizens");
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

    public static boolean checkEndPointLocation(org.bukkit.Location checkLocation){
        boolean plate= checkLocation.getBlock().getType() == Material.GOLD_PLATE || checkLocation.getBlock().getType() == Material.STONE_PLATE || checkLocation.getBlock().getType() == Material.IRON_PLATE || checkLocation.getBlock().getType() == Material.WOOD_PLATE;
        return plate;
    }


}

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

}

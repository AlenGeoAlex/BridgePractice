package me.alen_alex.bridgepractice.utility;

import me.alen_alex.bridgepractice.configurations.Configuration;

public class Validation {

    public static boolean validateDatabase(){
        if(Configuration.getHost().length() != 0 && Configuration.getUsername().length() != 0 && Configuration.getPort().length() != 0 && Configuration.getDatabase().length() != 0)
            return true;
        else
            return false;
    }

}

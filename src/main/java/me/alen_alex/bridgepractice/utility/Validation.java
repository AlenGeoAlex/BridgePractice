package me.alen_alex.bridgepractice.utility;

import me.alen_alex.bridgepractice.configurations.Configuration;

public class Validation {

    public static boolean validateDatabase(){
        if(Configuration.getHost() != null && Configuration.getUsername() != null && Configuration.getPort() != null && Configuration.getDatabase() != null)
            return true;
        else
            return false;
    }

}

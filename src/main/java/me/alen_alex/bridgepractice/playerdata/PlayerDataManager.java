package me.alen_alex.bridgepractice.playerdata;

import me.alen_alex.bridgepractice.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    private static HashMap<Player, PlayerData> cachedPlayerData = new HashMap<>();

    public static void loadPlayerData(UUID uuid){
        ResultSet set = DataManager.fetchPlayerData(uuid);
    }

}

package me.alen_alex.bridgepractice.playerdata;

import me.Abhigya.core.particle.ParticleEffect;
import me.alen_alex.bridgepractice.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    private static HashMap<UUID, PlayerData> cachedPlayerData = new HashMap<>();

    public static void loadPlayerData(UUID uuid) throws SQLException {
        ResultSet set = DataManager.fetchPlayerData(uuid);
        while(set.next()){
            cachedPlayerData.put(uuid,new PlayerData(set.getString("name"),uuid, Material.getMaterial(set.getString("material")), ParticleEffect.valueOf(set.getString("particle")),set.getInt("gamesplayed"),set.getLong("currenttime"),set.getLong("bestTime"),set.getInt("blocksPlaced")));
        }
        set.close();
    }

    public static HashMap<UUID, PlayerData> getCachedPlayerData() {
        return cachedPlayerData;
    }
}

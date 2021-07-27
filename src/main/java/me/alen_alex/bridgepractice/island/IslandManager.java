package me.alen_alex.bridgepractice.island;

import me.alen_alex.bridgepractice.configurations.ArenaConfigurations;
import me.alen_alex.bridgepractice.utility.Location;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;

public class IslandManager {

    private static HashMap<String, Island> islandData = new HashMap<String, Island>();
    private static YamlConfiguration arenaConfigurations = ArenaConfigurations.getArenaConfiguration();

    public static void fetchIslandsFromFile(){
        for(String islandName : ArenaConfigurations.getArenaConfiguration().getKeys(false)){
            try {
                islandData.put(islandName,new Island(islandName, Location.getWorldFromData(arenaConfigurations.getString(islandName+".spawn.position")).getName(),arenaConfigurations.getString(islandName+".group"),arenaConfigurations.getString(islandName+".permission"),Location.getLocation(arenaConfigurations.getString(islandName+".spawn.position")),Location.getLocation(arenaConfigurations.getString(islandName+".end.position")),Location.getLocation(arenaConfigurations.getString(islandName+".lobby.position")),Location.getLocation(arenaConfigurations.getString(islandName+".position.pos1")),Location.getLocation(arenaConfigurations.getString(islandName+".position.pos2")), true));
            } catch (IllegalArgumentException e) {
                arenaConfigurations.set(islandName+".enabled", false);
                e.printStackTrace();
                Bukkit.getLogger().severe("Can't load island "+islandName);
                Bukkit.getLogger().severe("Disabling Island.");

                continue;
            }
            Bukkit.getLogger().info("Succesfully Loaded Island "+islandName+" on the world "+islandData.get(islandName).getWorldName());
        }
        ArenaConfigurations.saveArenaConfiguration();
    }

    public static HashMap<String, Island> getIslandData() {
        return islandData;
    }


}

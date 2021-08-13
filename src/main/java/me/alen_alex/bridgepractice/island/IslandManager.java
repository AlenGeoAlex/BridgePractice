package me.alen_alex.bridgepractice.island;

import me.alen_alex.bridgepractice.configurations.ArenaConfigurations;
import me.alen_alex.bridgepractice.group.GroupManager;
import me.alen_alex.bridgepractice.utility.Location;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class IslandManager {

    private static HashMap<String, Island> islandData = new HashMap<String, Island>();
    private static YamlConfiguration arenaConfigurations = ArenaConfigurations.getArenaConfiguration();

    public static void fetchIslandsFromFile(){
        for(String islandName : ArenaConfigurations.getArenaConfiguration().getKeys(false)){
            if(arenaConfigurations.getBoolean(islandName+".enabled")) {
                try {
                    islandData.put(islandName, new Island(islandName, Location.getWorldFromData(arenaConfigurations.getString(islandName + ".spawn.position")).getName(), GroupManager.getGroupByName(arenaConfigurations.getString(islandName + ".group")), arenaConfigurations.getString(islandName + ".permission"), Location.getLocation(arenaConfigurations.getString(islandName + ".spawn.position")), Location.getLocation(arenaConfigurations.getString(islandName + ".end.position")), Material.getMaterial(arenaConfigurations.getString(islandName + ".end.material")) ,Location.getLocation(arenaConfigurations.getString(islandName + ".lobby.position")), Location.getLocation(arenaConfigurations.getString(islandName + ".position.pos1")), Location.getLocation(arenaConfigurations.getString(islandName + ".position.pos2")),arenaConfigurations.getInt(islandName+".mintimeRequired"),arenaConfigurations.getInt(islandName+".minblocksRequired"),true));
                } catch (IllegalArgumentException e){
                    arenaConfigurations.set(islandName + ".enabled", false);
                    e.printStackTrace();
                    Bukkit.getLogger().severe("Can't load island " + islandName);
                    Bukkit.getLogger().severe("Disabling Island.");

                    continue;
                }
                Bukkit.getLogger().info("[ISLAND LOADER] Succesfully Loaded Island " + islandName + " on the world " + islandData.get(islandName).getWorldName());
            }else{
                Bukkit.getLogger().info("Island Disabled By Default "+islandName+". Skipping!!");
            }
        }
        ArenaConfigurations.saveArenaConfiguration();
    }

    public static HashMap<String, Island> getIslandData() {
        return islandData;
    }

    public static Island getAnIsland(Player player){
        Island selectedIsland = null;
        for(Map.Entry<String,Island> entry : islandData.entrySet()){
            Island checkIsland = entry.getValue();
            if(!checkIsland.isIslandOccupied() && checkIsland.isActive() && checkIsland.hasIslandPermission(player)) {
                selectedIsland = checkIsland;
                break;
            }
        }
        return selectedIsland;
    }

    public static Island getAnIsland(Player player,String groupName){
        Island selectedIsland = null;
        for(Map.Entry<String, Island> entry : islandData.entrySet()){
            Island checkIsland = entry.getValue();
            if(!checkIsland.isIslandOccupied() && checkIsland.isActive() && checkIsland.hasIslandPermission(player) &&checkIsland.getIslandGroup().getGroupName().equalsIgnoreCase(groupName)){
                selectedIsland = checkIsland;
                break;
            }
        }
        return selectedIsland;
    }

    public static Island getIslandByName(Player player,String islandName){
        Island islandSelected = null;
         if(islandData.containsKey(islandName)) {
             Island checkIsland = islandData.get(islandName);
             if (!checkIsland.isIslandOccupied() && checkIsland.isActive() && checkIsland.hasIslandPermission(player))
                 islandSelected = checkIsland;


         }
         return islandSelected;
        }

    }

package me.alen_alex.bridgepractice.commands.admin;

import me.alen_alex.bridgepractice.configurations.ArenaConfigurations;
import me.alen_alex.bridgepractice.island.IslandManager;
import me.alen_alex.bridgepractice.utility.Location;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CreationCommand {

    private static String creatingIsland=null;
    private static YamlConfiguration arenaStorage = ArenaConfigurations.getArenaConfiguration();
    public static void createAnIsland(Player player,String islandName){
        if(creatingIsland!=null){
            Messages.sendJSONExecuteCommand(player,"&cThere is already an island creation in progress.","/practiceadmin save","&fClick here to save the current island progress", false);
            return;
        }
        if(arenaStorage.contains(islandName)){
            Messages.sendMessage(player,"&cIsland with this name already exist", true);
            return;
        }
        creatingIsland = islandName;
        arenaStorage.set(creatingIsland+".enabled",false);
        arenaStorage.set(creatingIsland+".minblocksRequired",0);
        arenaStorage.set(creatingIsland+".mintimeRequired",0);
        Messages.sendMessage(player,"&aCreated new arena with the name "+creatingIsland,false);
    }

    public static void setGroup(Player player, String groupName){
        if(creatingIsland == null){
            Messages.sendMessage(player,"&cYou have not yet created an arena to set spawnpoint", true);
            Messages.sendJSONSuggestMessage(player,"&b&lClick here to create a new island","/practiceadmin create island ","&fClick this message", true);
            return;
        }

        arenaStorage.set(creatingIsland+".group",groupName);
        Messages.sendMessage(player,"&aGroup was set for island "+creatingIsland+" as "+groupName,true);

    }

    public static void setSpawnPoint(Player player){
        if(creatingIsland == null){
            Messages.sendMessage(player,"&cYou have not yet created an arena to set spawnpoint", true);
            Messages.sendJSONSuggestMessage(player,"&b&lClick here to create a new island","/practiceadmin create island ","&fClick this message", true);
            return;
        }

        String location = Location.parseLocation(player);
        arenaStorage.set(creatingIsland+".spawn.position",location);
        Messages.sendMessage(player,"&aSpawn point has been set to &6"+location+" &afor the arena "+creatingIsland, true);
    }

    public static void setEndPoint(Player player){
        if(creatingIsland == null){
            Messages.sendMessage(player,"&cYou have not yet created an arena to set endpoint", true);
            Messages.sendJSONSuggestMessage(player,"&b&lClick here to create a new island","/practiceadmin create island ","&fClick this message", true);
            return;
        }

        String location = Location.parseLocation(player);
        arenaStorage.set(creatingIsland+".end.position",location);
        Messages.sendMessage(player,"&aEnd point has been set to &6"+location+" &afor the arena "+creatingIsland, true);
    }

    public static void setLobbyWorld(Player player){
        if(creatingIsland == null){
            Messages.sendMessage(player,"&cYou have not yet created an arena to set endpoint", true);
            Messages.sendJSONSuggestMessage(player,"&b&lClick here to create a new island","/practiceadmin create island ","&fClick this message", true);
            return;
        }

        String location = Location.parseLocation(player);
        arenaStorage.set(creatingIsland+".lobby.position",location);
        Messages.sendMessage(player,"&aLobby point has been set to &6"+location+" &afor the arena "+creatingIsland, true);
    }

    public static void setPosition1(Player player){
        if(creatingIsland == null){
            Messages.sendMessage(player,"&cYou have not yet created an arena to set position1", true);
            Messages.sendJSONSuggestMessage(player,"&b&lClick here to create a new island","/practiceadmin create island ","&fClick this message", true);
            return;
        }

        String location = Location.parseLocation(player);
        arenaStorage.set(creatingIsland+".position.pos1",location);
        Messages.sendMessage(player,"&aPosition 1 has been set to &6"+location+" &afor the arena "+creatingIsland, true);
    }

    public static void setPosition2(Player player){
        if(creatingIsland == null){
            Messages.sendMessage(player,"&cYou have not yet created an arena to set position2", true);
            Messages.sendJSONSuggestMessage(player,"&b&lClick here to create a new island","/practiceadmin create island ","&fClick this message", true);
            return;
        }

        String location = Location.parseLocation(player);
        arenaStorage.set(creatingIsland+".position.pos2",location);
        Messages.sendMessage(player,"&aPosition 2 has been set to &6"+location+" &afor the arena "+creatingIsland, true);
    }

    public static void setPermission(Player player,String permissionNode){
        if(creatingIsland == null){
            Messages.sendMessage(player,"&cYou have not yet created an arena to set position2", true);
            Messages.sendJSONSuggestMessage(player,"&b&lClick here to create a new island","/practiceadmin create island ","&fClick this message", true);
            return;
        }

        arenaStorage.set(creatingIsland+".permission",permissionNode);
        Messages.sendMessage(player,"&aPermission was set for island "+creatingIsland+" as "+permissionNode,true);
    }
    public static void deleteIsland(Player player, String islandName){
        if(!arenaStorage.contains(islandName)){
            Messages.sendMessage(player, "&cArena with the name does not exist", true);
            return;
        }

        arenaStorage.set(islandName,null);
        ArenaConfigurations.saveArenaConfiguration();
        if(IslandManager.getIslandData().containsKey(islandName)){
            IslandManager.getIslandData().remove(islandName);
            Bukkit.getLogger().info("Deleted island Objects.");
        }
        Messages.sendMessage(player,"&aDeleted Island "+islandName, true);

    }



    public static void saveIslandDetails(Player player){
        ArenaConfigurations.saveArenaConfiguration();
        creatingIsland = null;
        Messages.sendMessage(player,"&aSaved arena configuration", true);
    }

}

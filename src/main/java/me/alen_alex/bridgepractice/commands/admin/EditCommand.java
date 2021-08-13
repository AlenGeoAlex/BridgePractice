package me.alen_alex.bridgepractice.commands.admin;

import me.alen_alex.bridgepractice.configurations.ArenaConfigurations;
import me.alen_alex.bridgepractice.utility.Location;
import me.alen_alex.bridgepractice.utility.Messages;
import me.alen_alex.bridgepractice.utility.Validation;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class EditCommand {

    private static String editIslands = null;
    private static YamlConfiguration arenaStorage = ArenaConfigurations.getArenaConfiguration();

    public static void setEditIslands(Player player, String IslandName){
        if(editIslands == null){
            editIslands = IslandName;
            Messages.sendMessage(player,"&eIsland editor has been set to &b&l"+editIslands,true);
        }else{
            Messages.sendMessage(player,"&eYou already have a pending island edition in progress", true);
        }
    }

    public static void saveEditIslands(Player player){
        if(editIslands != null){
            ArenaConfigurations.saveArenaConfiguration();
            Messages.sendMessage(player,"&aIsland configuration has been saved", true);
            editIslands = null;
        }else{
            Messages.sendMessage(player,"&cNo Data has been re-edited", true);
        }
    }

    public static void setSpawnPoint(Player player){
        if(editIslands == null){
            Messages.sendMessage(player,"&cYou have not set an island to edit", true);
            return;
        }
        if(!arenaStorage.contains(editIslands)){
            Messages.sendMessage(player,"&cIsland with this name does not exist", true);
            return;
        }
        String location = Location.parseLocation(player);
        arenaStorage.set(editIslands+".spawn.position",location);
        Messages.sendMessage(player,"&aSpawn point has been set to &6"+location+" &afor the arena "+editIslands, true);
    }

    public static void setEndPoint(Player player){
        if(editIslands == null){
            Messages.sendMessage(player,"&cYou have not set an island to edit", true);
            return;
        }
        if(!arenaStorage.contains(editIslands)){
            Messages.sendMessage(player,"&cIsland with this name does not exist", true);
            return;
        }
        if(!(Validation.checkEndPointLocation(player.getLocation()))){
            Messages.sendMessage(player,"&cThe provided block is not a pressure plate",false);
            return;
        }

        String location = Location.parseLocation(player);
        arenaStorage.set(editIslands+".end.position",location);
        arenaStorage.set(editIslands+".end.material",player.getLocation().getBlock().getType().name());
        Messages.sendMessage(player,"&aEnd point has been set to &6"+location+" &afor the arena "+editIslands, true);
    }

    public static void setLobbyPoint(Player player){
        if(editIslands == null){
            Messages.sendMessage(player,"&cYou have not set an island to edit", true);
            return;
        }
        if(!arenaStorage.contains(editIslands)){
            Messages.sendMessage(player,"&cIsland with this name does not exist", true);
            return;
        }
        String location = Location.parseLocation(player);
        arenaStorage.set(editIslands+".lobby.position",location);
        Messages.sendMessage(player,"&aLobby point has been set to &6"+location+" &afor the arena "+editIslands, true);
    }

    public static void setPos1(Player player){
        if(editIslands == null){
            Messages.sendMessage(player,"&cYou have not set an island to edit", true);
            return;
        }
        if(!arenaStorage.contains(editIslands)){
            Messages.sendMessage(player,"&cIsland with this name does not exist", true);
            return;
        }
        String location = Location.parseLocation(player);
        arenaStorage.set(editIslands+".position.pos1",location);
        Messages.sendMessage(player,"&aPosition 1 has been set to &6"+location+" &afor the arena "+editIslands, true);
    }

    public static void setPos2(Player player){
        if(editIslands == null){
            Messages.sendMessage(player,"&cYou have not set an island to edit", true);
            return;
        }
        if(!arenaStorage.contains(editIslands)){
            Messages.sendMessage(player,"&cIsland with this name does not exist", true);
            return;
        }
        String location = Location.parseLocation(player);
        arenaStorage.set(editIslands+".position.pos2",location);
        Messages.sendMessage(player,"&aPosition 2 has been set to &6"+location+" &afor the arena "+editIslands, true);
    }

}

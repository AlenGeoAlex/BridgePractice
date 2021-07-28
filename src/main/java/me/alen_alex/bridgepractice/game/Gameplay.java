package me.alen_alex.bridgepractice.game;

import me.alen_alex.bridgepractice.api.PlayerIslandJoinEvent;
import me.alen_alex.bridgepractice.api.PlayerIslandLeaveEvent;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.island.Island;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import me.alen_alex.bridgepractice.utility.Blocks;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Gameplay {

    private static HashMap<PlayerData,Island> playerIslands = new HashMap<PlayerData,Island>();


    public static void handleGameJoin(PlayerData playerData, Island islandData){
        PlayerIslandJoinEvent event = new PlayerIslandJoinEvent(playerData,islandData);
        Bukkit.getPluginManager().callEvent(event);
        playerIslands.put(playerData,islandData);
        playerData.setCurrentState(PlayerState.IDLE_ISLAND);
        islandData.setCurrentPlayer(playerData);
        islandData.teleportToIslandSpawn(playerData.getOnlinePlayer());
        ItemStack material;
        if(Blocks.doPlayerHavePreferencePermission(playerData.getOnlinePlayer())){
            material = new ItemStack(playerData.getPlayerMaterial());
        }else {
            material = new ItemStack(Material.WOOD);
        }
        playerData.fillPlayerBlocks(material);
        Messages.sendMessage(playerData.getOnlinePlayer(),"&cYou have been assigned to island &6"+islandData.getName()+".", false);
    }


    public static void handleGameLeave(PlayerData playerData){
        Island playerIsland = playerIslands.get(playerData);
        PlayerIslandLeaveEvent event = new PlayerIslandLeaveEvent(playerIsland,playerData);
        Bukkit.getPluginManager().callEvent(event);
        playerData.setLobbyItems();
        playerIsland.teleportToQuitlobby(playerData.getOnlinePlayer());
        Messages.sendMessage(playerData.getOnlinePlayer(),"&cYou left the island!",false);
        playerData.setCurrentState(null);
        playerIsland.setCurrentPlayer(null);
    }


    public static HashMap<PlayerData, Island> getPlayerIslands() {
        return playerIslands;
    }



}

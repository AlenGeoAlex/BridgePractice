package me.alen_alex.bridgepractice.game;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.api.PlayerIslandJoinEvent;
import me.alen_alex.bridgepractice.api.PlayerIslandLeaveEvent;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.island.Island;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Blocks;
import me.alen_alex.bridgepractice.utility.Countdown;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class Gameplay {

    private static HashMap<PlayerData,Island> playerIslands = new HashMap<PlayerData,Island>();
    private static HashMap<UUID, Integer> playerCountdown = new HashMap<UUID, Integer>();

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
        playerData.resetPlacedBlocks();
        playerIsland.teleportToQuitlobby(playerData.getOnlinePlayer());
        Messages.sendMessage(playerData.getOnlinePlayer(),"&cYou left the island!",false);
        playerData.setCurrentState(null);
        playerIsland.setCurrentPlayer(null);
    }

    public static void onFirstBlockPlace(Player player, Location placedLocation){
        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setCurrentState(PlayerState.PLAYING);
        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setCurrentTime(System.currentTimeMillis());
        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).addPlayerPlacedBlock();
        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).addPlacedBlocks(placedLocation);
        playerCountdown.put(player.getUniqueId(),Bukkit.getScheduler().scheduleAsyncRepeatingTask(BridgePractice.getPlugin(), new Countdown(player), 0, 20));
        Messages.sendMessage(player,"&b&lBridge has begun!", false);
    }

    public static void onBlockPlace(Player player, Location placedLocation){
        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).addPlayerPlacedBlock();
        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).addPlacedBlocks(placedLocation);
    }


    public static HashMap<PlayerData, Island> getPlayerIslands() {
        return playerIslands;
    }

    public static HashMap<UUID, Integer> getPlayerCountdown() {
        return playerCountdown;
    }
}

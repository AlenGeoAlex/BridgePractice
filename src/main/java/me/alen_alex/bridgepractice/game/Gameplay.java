package me.alen_alex.bridgepractice.game;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.api.PlayerIslandJoinEvent;
import me.alen_alex.bridgepractice.api.PlayerIslandLeaveEvent;
import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.data.DataManager;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.group.GroupManager;
import me.alen_alex.bridgepractice.island.Island;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Countdown;
import me.alen_alex.bridgepractice.utility.Messages;
import me.alen_alex.bridgepractice.utility.TimeUtility;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Gameplay {

    private static HashMap<PlayerData,Island> playerIslands = new HashMap<PlayerData,Island>();
    private static HashMap<UUID, Integer> playerCountdown = new HashMap<UUID, Integer>();

    public static void handleGameJoin(PlayerData playerData, Island islandData){
        PlayerIslandJoinEvent event = new PlayerIslandJoinEvent(playerData,islandData);
        Bukkit.getPluginManager().callEvent(event);
        playerIslands.put(playerData,islandData);
        playerData.getOnlinePlayer().setHealthScale(20.0);
        playerData.setCurrentState(PlayerState.IDLE_ISLAND);
        islandData.setCurrentPlayer(playerData);
        islandData.teleportToIslandSpawn(playerData.getOnlinePlayer());
        playerData.fillPlayerBlocks();
        playerData.setStartTime(System.currentTimeMillis());
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

    public static void handleGameEnd(Player player, boolean completed){
        long durationTaken;
        PlayerData playerData = PlayerDataManager.getCachedPlayerData().get(player.getUniqueId());
        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setCurrentState(PlayerState.IDLE_ISLAND);
        Island islandData = Gameplay.getPlayerIslands().get(playerData);
        if(completed){
            PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setEndTime(System.currentTimeMillis());
            durationTaken = (PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getEndTime()  - PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getStartTime());
            PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setCurrentTime(durationTaken);
            if(Configuration.doUseGroups()){
                if(Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).hasGroup()) {
                    long currentBestTime = GroupManager.getHighestOfPlayerInGroup(Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getIslandGroup().getGroupName(), PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getPlayerName());
                    if (durationTaken < currentBestTime) {
                        Messages.sendMessage(player, "&bH&eo&6o&ar&6a&dy&c!&4!&f...&eYou have broke you previous record of &6" + TimeUtility.getDurationFromLongTime(currentBestTime) + "&e with new record of &b&l" + TimeUtility.getDurationFromLongTime(durationTaken), true);
                        GroupManager.setHighestInGroup(Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getIslandGroup().getGroupName(), PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getPlayerName(), durationTaken);
                        if (PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getBestTime() > durationTaken) {
                            PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setBestTime(durationTaken);
                            Messages.sendMessage(player, "&6&lYou also broke your all time best time!!", true);
                        }
                    }
                }
            }else{
                if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getBestTime() > durationTaken){
                    PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setBestTime(durationTaken);
                    Messages.sendMessage(player, "&c6&lYou also broke your all time best time!!", true);
                }
            }
        }
        DataManager.savePlayerData(playerData);
        Gameplay.getPlayerIslands().remove(playerData);
        playerIslands.put(playerData,islandData);
        handleGameJoin(playerData,islandData);
        playerData.resetPlacedBlocks();
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

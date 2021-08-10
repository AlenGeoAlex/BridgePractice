package me.alen_alex.bridgepractice.game;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.api.PlayerGameEndEvent;
import me.alen_alex.bridgepractice.api.PlayerIslandJoinEvent;
import me.alen_alex.bridgepractice.api.PlayerIslandLeaveEvent;
import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.data.DataManager;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.group.GroupManager;
import me.alen_alex.bridgepractice.island.Island;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Countdown;
import me.alen_alex.bridgepractice.utility.Messages;
import me.alen_alex.bridgepractice.utility.TimeUtility;
import me.jumper251.replay.api.ReplayAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;

import org.bukkit.entity.Player;

import java.util.*;

public class Gameplay {

    private static HashMap<PlayerData,Island> playerIslands = new HashMap<PlayerData,Island>();
    private static HashMap<UUID, Integer> playerCountdown = new HashMap<UUID, Integer>();
    private static HashMap<Player,Player> spectators = new HashMap<>();

    public static void handleGameJoin(PlayerData playerData, Island islandData){
        PlayerIslandJoinEvent event = new PlayerIslandJoinEvent(playerData,islandData);
        Bukkit.getPluginManager().callEvent(event);
        playerIslands.put(playerData,islandData);
        if(playerData.isSpectating()){
            Messages.sendMessage(playerData.getOnlinePlayer(), MessageConfiguration.getCannotWhileSpecing(),true);
            return;
        }

        if(PlayerDataManager.getCachedPlayerData().get(playerData.getPlayerUUID()).isWatchingReplay()){
            Messages.sendMessage(playerData.getOnlinePlayer(),MessageConfiguration.getCannotWhileReplay(),false);
            return;
        }

        playerData.getOnlinePlayer().setHealthScale(20.0);
        playerData.setCurrentState(PlayerState.IDLE_ISLAND);
        islandData.setCurrentPlayer(playerData);
        islandData.teleportToIslandSpawn(playerData.getOnlinePlayer());
        islandData.setOccupied(true);
        playerData.fillPlayerBlocks();
        playerData.setStartTime(System.currentTimeMillis());
        Messages.sendMessage(playerData.getOnlinePlayer(),MessageConfiguration.getFoundIslandPL().replaceAll("%island-name%",islandData.getName()), false);
    }


    public static void handleGameLeave(PlayerData playerData){
        Island playerIsland = playerIslands.get(playerData);
        PlayerIslandLeaveEvent event = new PlayerIslandLeaveEvent(playerIsland,playerData);
        Bukkit.getPluginManager().callEvent(event);
        playerData.setLobbyItems();
        playerData.resetPlacedBlocks();
        if(Gameplay.getSpectators().containsValue(playerData.getOnlinePlayer())){
            Gameplay.getCurrentlySpectatingPlayers(playerData.getOnlinePlayer()).forEach(player1 -> {
                Gameplay.handleLeaveSpectating(player1,playerData.getOnlinePlayer());
                Messages.sendMessage(player1,MessageConfiguration.getSpectatorPlayerLeft(),false);
            });
        }
        playerIsland.teleportToQuitlobby(playerData.getOnlinePlayer());
        Messages.sendMessage(playerData.getOnlinePlayer(),MessageConfiguration.getPlayerLeftIsland(),false);
        playerData.setCurrentState(null);
        playerIsland.setCurrentPlayer(null);
        playerIsland.setOccupied(false);
    }

    public static void handleGameEnd(Player player, boolean completed){
        long durationTaken;
        PlayerData playerData = PlayerDataManager.getCachedPlayerData().get(player.getUniqueId());
        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setCurrentState(PlayerState.IDLE_ISLAND);
        Island islandData = Gameplay.getPlayerIslands().get(playerData);
        PlayerGameEndEvent event = new PlayerGameEndEvent(playerData,islandData,completed);
        Bukkit.getPluginManager().callEvent(event);
        if(BridgePractice.isAdvanceReplayEnabled()) {
            if (PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isRecordingEnabled())
                ReplayAPI.getInstance().stopReplay(player.getName() + "-" + Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getName() + "-" + (PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getPlayerReplays().size() + 1), true);
        }
        if(completed){
            PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).addGamesPlayed();
            PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setEndTime(System.currentTimeMillis());
            durationTaken = (PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getEndTime()  - PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getStartTime());
            PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setCurrentTime(durationTaken);
            Messages.sendMessage(player,MessageConfiguration.getPlayerCompletedSessionPL().replaceAll("%currenttime%",TimeUtility.getDurationFromLongTime(durationTaken)),false);
            if(Configuration.doUseGroups()){
                if(Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).hasGroup()) {
                    long currentBestTime = GroupManager.getHighestOfPlayerInGroup(Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getIslandGroup().getGroupName(), PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getPlayerName());
                    if (durationTaken < currentBestTime) {
                        Messages.sendMessage(player, MessageConfiguration.getBrokeRecordPL().replaceAll("%current_besttime%",TimeUtility.getDurationFromLongTime(currentBestTime)).replaceAll("%new_besttime%",TimeUtility.getDurationFromLongTime(durationTaken)),false);

                        GroupManager.setHighestInGroup(Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getIslandGroup().getGroupName(), PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getPlayerName(), durationTaken);
                        if (PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getBestTime() > durationTaken) {
                            PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setBestTime(durationTaken);
                            Messages.sendMessage(player, MessageConfiguration.getBrokeAllTimeHighest(), false);
                        }
                    }
                }
            }else{
                if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getBestTime() > durationTaken){
                    PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setBestTime(durationTaken);
                    Messages.sendMessage(player, MessageConfiguration.getBrokeAllTimeHighest(), false);
                }
            }
        }
        DataManager.savePlayerData(playerData);
        playerIslands.get(playerData).setOccupied(false);
        Gameplay.getPlayerIslands().remove(playerData);
        playerIslands.put(playerData,islandData);
        handleGameJoin(playerData,islandData);
        playerData.resetPlacedBlocks();
        playerData.spawnFirework();
    }

    public static void handleJoinSpectating(Player player, Player toPlayer){
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() != null){
            Messages.sendMessage(player,MessageConfiguration.getCannotWhileSpecing(), true);
            return;
        }
        if(!toPlayer.isOnline()){
            Messages.sendMessage(player,MessageConfiguration.getSpectatorPlayerLeft(),true);
            return;
        }

        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setSpectating(true);
        spectators.put(player,toPlayer);
        player.teleport(toPlayer.getLocation());
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        getCurrentlySpectatingPlayers(player).forEach(player::hidePlayer);
    }

    public static void handleLeaveSpectating(Player player, Player fromPlayer){
        spectators.remove(player);
        player.teleport(me.alen_alex.bridgepractice.utility.Location.getLocation(Configuration.getLobbyLocation()));
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);
        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setSpectating(true);
    }

    public static void onFirstBlockPlace(Player player, Location placedLocation){
        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setCurrentState(PlayerState.PLAYING);
        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setCurrentTime(System.currentTimeMillis());
        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).addPlayerPlacedBlock();
        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).addPlacedBlocks(placedLocation);
        playerCountdown.put(player.getUniqueId(),Bukkit.getScheduler().scheduleAsyncRepeatingTask(BridgePractice.getPlugin(), new Countdown(player,PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getPlayerTimer()), 0, 20));
        Messages.sendMessage(player,MessageConfiguration.getTimerStarted(), false);
        if(BridgePractice.isAdvanceReplayEnabled()) {
            if (PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isRecordingEnabled())
                ReplayAPI.getInstance().recordReplay(player.getName() + "-" + Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getName() + "-" + (PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getPlayerReplays().size() + 1), Bukkit.getConsoleSender(), player);
        }
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

    public static List<Player> getCurrentPlayes(){
        if(playerIslands.size() == 0)
            return null;
        List<Player> currentPlayer = new ArrayList<Player>();
        for(Map.Entry<PlayerData, Island> entry : playerIslands.entrySet()){
            currentPlayer.add(entry.getKey().getOnlinePlayer());
        }
        return currentPlayer;
    }

    public static HashMap<Player, Player> getSpectators() {
        return spectators;
    }

    public static List<Player> getCurrentlySpectatingPlayers(Player player){
        List<Player> specingList = new ArrayList<>();
        if(!spectators.containsValue(player))
            return null;

        for(Map.Entry<Player,Player> playerEntry : spectators.entrySet()){
            if(playerEntry.getValue() == player)
                specingList.add(playerEntry.getKey());
        }
        return specingList;
    }

}

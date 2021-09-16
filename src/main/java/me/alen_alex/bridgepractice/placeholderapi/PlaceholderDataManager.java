package me.alen_alex.bridgepractice.placeholderapi;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.game.GameplayHandler;
import me.alen_alex.bridgepractice.group.GroupManager;
import me.alen_alex.bridgepractice.island.IslandManager;
import me.alen_alex.bridgepractice.leaderboards.LeaderboardManager;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
import me.alen_alex.bridgepractice.utility.TimeUtility;
import org.bukkit.entity.Player;

public class PlaceholderDataManager {

    private static final String NOTAVAILABLE = Messages.parseColor("&c&lN/A");
    private static final String NOTANISLAND = Messages.parseColor("&c&lUnknown Island");
    private static final String NOTAGROUP = Messages.parseColor("&c&lUnknown Group");
    private static final String DISABLED = Messages.parseColor("&e&lDisabled");
    public String getPlayerIslandName(Player player){
        if(BridgePractice.getGameplayHandler().getPlayerIslands().containsKey(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())))
            return BridgePractice.getGameplayHandler().getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getName();
        else
            return NOTAVAILABLE;
    }

    public String getPlayerCurrentTime(Player player){
        return TimeUtility.getDurationFromLongTime(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentTime());
    }

    public String getPlayerCurrentState(Player player){
        String currentState = null;
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING)
            currentState = "PLAYING";
        else if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.IDLE_ISLAND)
            currentState = "WAITING";
        else
            if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isWatchingReplay())
                currentState = "REPLAY SESSION";
            else
                currentState = "LOBBY";

        return currentState;
    }

    public String getAllTimeBestPlayer(Player player){
        return TimeUtility.getDurationFromLongTime(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getBestTime());
    }

    public String getAllTimeBlocksPlaced(Player player){
        return String.valueOf(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getBlocksPlaced());
    }

    public String getGamesPlayed(Player player){
        return String.valueOf(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getGamesPlayed());
    }

    public String isPlayerSpectating(Player player){
        return String.valueOf(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isSpectating());
    }

    public String getPlayerSpectating(Player player){
        if(BridgePractice.getGameplayHandler().getSpectators().containsKey(player))
            return BridgePractice.getGameplayHandler().getSpectators().get(player).getName();
        else
            return NOTAVAILABLE;
    }

    public String getSpectatingIsland(Player player){
        if(BridgePractice.getGameplayHandler().getSpectators().containsKey(player))
            return BridgePractice.getGameplayHandler().getPlayerIslands().get(BridgePractice.getGameplayHandler().getSpectators().get(player.getUniqueId())).getName();
        else
            return NOTAVAILABLE;
    }

    public String getBlocksPlacedOnCurrentGame(Player player){
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getPlacedBlocks().isEmpty())
            return NOTAVAILABLE;
        else
            return String.valueOf(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getBlocksPlacedOnCurrentGame());
    }

    public String getRunningTimer(Player player){
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
            return TimeUtility.getDurationFromLongTime((System.currentTimeMillis() - PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getStartTime()));
        }else
            return NOTAVAILABLE;
    }

    public String getIslandGroup(String islandName){
        if(IslandManager.getIslandData().containsKey(islandName))
            if(IslandManager.getIslandData().get(islandName).hasGroup())
                return IslandManager.getIslandData().get(islandName).getIslandGroup().getGroupName();
            else
                return NOTAVAILABLE;
        else
            return NOTANISLAND;
    }

    public String getIslandPlayer(String islandName){
        if(IslandManager.getIslandData().containsKey(islandName))
            if(IslandManager.getIslandData().get(islandName).getCurrentPlayer() != null)
                return IslandManager.getIslandData().get(islandName).getCurrentPlayer().getPlayerName();
            else
                return NOTAVAILABLE;
        else
            return NOTANISLAND;
    }

    public String getLeaderboardRefreshIn(){
        if(LeaderboardManager.isRefreshing())
            return Messages.parseColor("&b&lCurrently &e&lRefreshing");
        else
            return String.valueOf(TimeUtility.getProperTimeFromSec(LeaderboardManager.getRemainingTime()));
    }

    public String getLeaderboardName(String groupName, int position){
        if(position < 0 && position >= 10)
            return NOTAVAILABLE;
        if(GroupManager.getCachedGroups().containsKey(groupName)){
            if(GroupManager.getCachedGroups().get(groupName).isLeaderboardEnabled()){
                return GroupManager.getGroupByName(groupName).getLeaderboardplayerName().get(position);
            }else{
                return NOTAVAILABLE;
            }
        }else{
            return NOTAGROUP;
        }
    }

    public String getLeaderboardDuration(String groupName, int position){
        if(position < 0 && position >= 10)
            return NOTAVAILABLE;
        if(GroupManager.getCachedGroups().containsKey(groupName)){
            if(GroupManager.getCachedGroups().get(groupName).isLeaderboardEnabled()){
                return TimeUtility.getDurationFromLongTime(GroupManager.getGroupByName(groupName).getLeaderboardScore().get(position));
            }else{
                return NOTAVAILABLE;
            }
        }else{
            return NOTAGROUP;
        }
    }

    public String getIsPlayerRecording(Player player){
        return String.valueOf(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isRecordingEnabled());
    }

    public String getRecordingStatus(Player player){
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
            if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isRecordingEnabled()){
                return MessageConfiguration.getPlaceholderCurrentlyRecording();
            }else{
                return DISABLED;
            }
        }else
            return NOTAVAILABLE;
    }

}

package me.alen_alex.bridgepractice.placeholderapi;

import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.game.Gameplay;
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

    public static String getPlayerIslandName(Player player){
        if(Gameplay.getPlayerIslands().containsKey(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())))
            return Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getName();
        else
            return NOTAVAILABLE;
    }

    public static String getPlayerCurrentTime(Player player){
        return TimeUtility.getDurationFromLongTime(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentTime());
    }

    public static String getPlayerCurrentState(Player player){
        String currentState = null;
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING)
            currentState = "PLAYING";
        else if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.IDLE_ISLAND)
            currentState = "WAITING";
        else
            currentState = "LOBBY";

        return currentState;
    }

    public static String getAllTimeBestPlayer(Player player){
        return TimeUtility.getDurationFromLongTime(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getBestTime());
    }

    public static String getAllTimeBlocksPlaced(Player player){
        return String.valueOf(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getBlocksPlaced());
    }

    public static String getGamesPlayed(Player player){
        return String.valueOf(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getGamesPlayed());
    }

    public static String isPlayerSpectating(Player player){
        return String.valueOf(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isSpectating());
    }

    public static String getPlayerSpectating(Player player){
        if(Gameplay.getSpectators().containsKey(player))
            return Gameplay.getSpectators().get(player).getName();
        else
            return NOTAVAILABLE;
    }

    public static String getSpectatingIsland(Player player){
        if(Gameplay.getSpectators().containsKey(player))
            return Gameplay.getPlayerIslands().get(Gameplay.getSpectators().get(player.getUniqueId())).getName();
        else
            return NOTAVAILABLE;
    }

    public static String getBlocksPlacedOnCurrentGame(Player player){
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getPlacedBlocks().isEmpty())
            return NOTAVAILABLE;
        else
            return String.valueOf(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getBlocksPlacedOnCurrentGame());
    }

    public static String getRunningTimer(Player player){
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
            return TimeUtility.getDurationFromLongTime((System.currentTimeMillis() - PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getStartTime()));
        }else
            return NOTAVAILABLE;
    }

    public static String getIslandGroup(String islandName){
        if(IslandManager.getIslandData().containsKey(islandName))
            if(IslandManager.getIslandData().get(islandName).hasGroup())
                return IslandManager.getIslandData().get(islandName).getIslandGroup().getGroupName();
            else
                return NOTAVAILABLE;
        else
            return NOTANISLAND;
    }

    public static String getIslandPlayer(String islandName){
        if(IslandManager.getIslandData().containsKey(islandName))
            if(IslandManager.getIslandData().get(islandName).getCurrentPlayer() != null)
                return IslandManager.getIslandData().get(islandName).getCurrentPlayer().getPlayerName();
            else
                return NOTAVAILABLE;
        else
            return NOTANISLAND;
    }

    public static String getLeaderboardRefreshIn(){
        if(LeaderboardManager.isRefreshing())
            return Messages.parseColor("&b&lCurrently &e&lRefreshing");
        else
            return String.valueOf(TimeUtility.getProperTimeFromSec(LeaderboardManager.getRemainingTime()));
    }

    public static String getLeaderboardName(String groupName, int position){
        if(position <= 0 && position > 10)
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

    public static String getLeaderboardDuration(String groupName, int position){
        if(position <= 0 && position > 10)
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

}

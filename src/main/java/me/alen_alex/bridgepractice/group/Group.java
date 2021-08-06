package me.alen_alex.bridgepractice.group;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.data.Data;
import me.alen_alex.bridgepractice.data.DataManager;
import me.alen_alex.bridgepractice.enumerators.LeaderboardStatus;
import me.alen_alex.bridgepractice.utility.Messages;
import me.alen_alex.bridgepractice.utility.TimeUtility;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Group {
    private String groupName;
    private boolean leaderboardEnabled;
    private int rewardAmount;
    private LeaderboardStatus leaderboardStatus;
    //private LinkedHashMap<String, String> leaderboardPlayers;
    private LinkedList<String> leaderboardplayerName;
    private LinkedList<Long> leaderboardScore;
    public Group(String groupName, boolean leaderboardEnabled, int rewardAmount) {
        this.groupName = groupName;
        this.leaderboardEnabled = leaderboardEnabled;
        this.rewardAmount = rewardAmount;
        this.leaderboardStatus = LeaderboardStatus.LOADED;
        this.leaderboardplayerName = new LinkedList<String>();
        this.leaderboardScore = new LinkedList<Long>();
    }

    /*public LinkedHashMap<String, String> getLeaderboardPlayers() {
        return leaderboardPlayers;
    }

    public void setLeaderboardPlayers(LinkedHashMap<String, String> leaderboardPlayers) {
        this.leaderboardPlayers = leaderboardPlayers;
    }*/

    public String getGroupName() {
        return groupName;
    }

    public boolean isLeaderboardEnabled() {
        return leaderboardEnabled;
    }

    public int getRewardAmount() {
        return rewardAmount;
    }

    public void buildDB(){
        DataManager.createGroupDatabase(this.groupName);
    }

    public void updateLeaderboard(){
        if(!leaderboardEnabled)
            return;

        this.leaderboardStatus = LeaderboardStatus.REFRESHING;

        Bukkit.getScheduler().runTaskAsynchronously(BridgePractice.getPlugin(), new Runnable() {
            @Override
            public void run() {
                try {
                    ResultSet set = Data.getDatabaseConnection().executeQuery("SELECT `name`,`besttime` FROM `"+groupName+"` ORDER BY besttime ASC;");
                    if(!set.next())
                        return;
                    leaderboardplayerName.clear();
                    leaderboardScore.clear();
                    for(int i =1;i<=10;i++){
                        if(set.absolute(i)) {
                            //leaderboardPlayers.put(set.getString("name"), TimeUtility.getDurationFromLongTime(set.getLong("besttime")));
                            leaderboardplayerName.add(set.getString("name"));
                            leaderboardScore.add(set.getLong("besttime"));
                        }
                        else {
                            //leaderboardPlayers.put(Messages.parseColor("&c&lN/A"),Messages.parseColor("&e0"));
                            leaderboardplayerName.add(Messages.parseColor("&c&lN/A"));
                            leaderboardScore.add(0L);
                        }
                    }
                    set.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Bukkit.getLogger().severe("Unable to refresh leaderboards for "+groupName);
                }
                leaderboardStatus = LeaderboardStatus.LOADED;
            }
        });
    }

    public LeaderboardStatus getLeaderboardStatus() {
        return leaderboardStatus;
    }

    public LinkedList<String> getLeaderboardplayerName() {
        return leaderboardplayerName;
    }

    public LinkedList<Long> getLeaderboardScore() {
        return leaderboardScore;
    }
}

package me.alen_alex.bridgepractice.group;

import me.alen_alex.bridgepractice.data.Data;
import me.alen_alex.bridgepractice.data.DataManager;

import java.util.HashMap;

public class Group {
    private String groupName;
    private boolean leaderboardEnabled;
    private int rewardAmount;
    private HashMap<String, String> leaderboardPlayers;

    public Group(String groupName, boolean leaderboardEnabled, int rewardAmount) {
        this.groupName = groupName;
        this.leaderboardEnabled = leaderboardEnabled;
        this.rewardAmount = rewardAmount;
    }

    public HashMap<String, String> getLeaderboardPlayers() {
        return leaderboardPlayers;
    }

    public void setLeaderboardPlayers(HashMap<String, String> leaderboardPlayers) {
        this.leaderboardPlayers = leaderboardPlayers;
    }

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
}

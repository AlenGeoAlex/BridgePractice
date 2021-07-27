package me.alen_alex.bridgepractice.group;

import java.util.HashMap;

public class Group {
    private String groupName;
    private boolean leaderboardEnabled;
    private int rewardAmount;
    private HashMap<String, String> leaderboardPlayers;

    public Group(String groupName, boolean leaderboardEnabled, int rewardAmount, HashMap<String, String> leaderboardPlayers) {
        this.groupName = groupName;
        this.leaderboardEnabled = leaderboardEnabled;
        this.rewardAmount = rewardAmount;
        this.leaderboardPlayers = leaderboardPlayers;
    }
}

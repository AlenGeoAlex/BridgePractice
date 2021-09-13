package me.alen_alex.bridgepractice.group;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.citizens.CitizenNPC;
import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.data.Data;
import me.alen_alex.bridgepractice.data.DataManager;
import me.alen_alex.bridgepractice.enumerators.LeaderboardStatus;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Group {
    private String groupName;
    private boolean leaderboardEnabled;
    private double rewardAmount;
    private LeaderboardStatus leaderboardStatus;
    private LinkedList<String> leaderboardplayerName;
    private LinkedList<Long> leaderboardScore;
    private List<CitizenNPC> npcList;
    public Group(String groupName, boolean leaderboardEnabled, double rewardAmount) {
        this.groupName = groupName;
        this.leaderboardEnabled = leaderboardEnabled;
        this.rewardAmount = rewardAmount;
        this.leaderboardStatus = LeaderboardStatus.LOADED;
        this.leaderboardplayerName = new LinkedList<String>();
        this.leaderboardScore = new LinkedList<Long>();
    }


    public String getGroupName() {
        return groupName;
    }

    public boolean isLeaderboardEnabled() {
        return leaderboardEnabled;
    }

    public double getRewardAmount() {
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
                    leaderboardplayerName.add("");
                    leaderboardScore.add(0L);
                    for(int i =1;i<=10;i++){
                        if(set.absolute(i)) {
                            leaderboardplayerName.add(set.getString("name"));
                            leaderboardScore.add(set.getLong("besttime"));
                        }
                        else {
                            leaderboardplayerName.add(Messages.parseColor(MessageConfiguration.getPlaceholderNA()));
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

    public List<CitizenNPC> getNpcList() {
        return npcList;
    }

    public void setNpcList(List<CitizenNPC> npcList) {
        this.npcList = npcList;
    }
}

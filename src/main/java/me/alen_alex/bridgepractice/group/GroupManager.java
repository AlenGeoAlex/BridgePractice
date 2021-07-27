package me.alen_alex.bridgepractice.group;

import me.alen_alex.bridgepractice.configurations.GroupConfiguration;
import me.alen_alex.bridgepractice.data.Data;
import me.alen_alex.bridgepractice.data.DataManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class GroupManager {

    private static HashMap<String, Group> cachedGroups = new HashMap<String, Group>();

    public static void fetchGroups(){
        for(String groupName : GroupConfiguration.getGroupConfigurations().getKeys(false)){
            cachedGroups.put(groupName,new Group(groupName,GroupConfiguration.getGroupConfigurations().getBoolean(groupName+".leaderboardEnabled"),GroupConfiguration.getGroupConfigurations().getInt(groupName+".rewardMoney")));
            cachedGroups.get(groupName).buildDB();
        }
    }


    public static void deleteGroup(String groupName){
        DataManager.deleteGroupDatabase(groupName);
    }

    public static boolean doGroupExist(String groupName){
        boolean exist = false;

        try {
            ResultSet rs = Data.getDatabaseConnection().executeQuery("SHOW TABLES LIKE "+groupName+";");
            exist = rs.next();
            rs.getStatement().close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return exist;
    }

    public static HashMap<String, Group> getCachedGroups() {
        return cachedGroups;
    }



}

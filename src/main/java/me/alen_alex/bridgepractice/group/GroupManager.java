package me.alen_alex.bridgepractice.group;

import me.alen_alex.bridgepractice.configurations.GroupConfiguration;
import me.alen_alex.bridgepractice.data.Data;
import me.alen_alex.bridgepractice.data.DataManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupManager {

    public static void fetchGroupsFromFile(){
        for(String groupName : GroupConfiguration.getGroupConfigurations().getKeys(false)){
            DataManager.createGroupDatabase(groupName);
        }
    }

    public static void setupDatabase(){
        for(String groupName : GroupConfiguration.getGroupConfigurations().getKeys(false)){
            DataManager.createGroupDatabase(groupName);
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


    public static void registerUserInGroup(){

    }


}

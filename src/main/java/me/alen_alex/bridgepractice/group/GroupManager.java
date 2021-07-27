package me.alen_alex.bridgepractice.group;

import me.alen_alex.bridgepractice.configurations.GroupConfiguration;
import me.alen_alex.bridgepractice.data.DataManager;

public class GroupManager {



    public static void setupDatabase(){
        for(String groupName : GroupConfiguration.getGroupConfigurations().getKeys(false)){
            DataManager.createGroupDatabase(groupName);
        }
    }

    public static void deleteGroup(String groupName){
        DataManager.deleteGroupDatabase(groupName);
    }

    public static void registerUserInGroup(){

    }


}

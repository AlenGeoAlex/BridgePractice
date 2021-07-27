package me.alen_alex.bridgepractice.commands.admin;

import me.alen_alex.bridgepractice.configurations.GroupConfiguration;
import me.alen_alex.bridgepractice.group.GroupManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class GroupCommand {

    private static YamlConfiguration groupConfiguration = GroupConfiguration.getGroupConfigurations();

    public static void createGroup(Player player, String groupName){
        if(!groupConfiguration.contains(groupName)){
            groupConfiguration.set(groupName+".leaderboardEnabled", true);
            groupConfiguration.set(groupName+".rewardMoney", 0);
            Messages.sendMessage(player,"&aCreated Groups Succesfully, &fUse /practiceadmin save and restart the server",true);
        }else {
            Messages.sendMessage(player, "This group already exist!", true);
            return;
        }
    }

    public static void deleteGroup(Player player, String groupName){
        if(groupConfiguration.contains(groupName)){
            groupConfiguration.set(groupName,null);
            if(GroupManager.getCachedGroups().containsKey(groupName)) {
                GroupManager.getCachedGroups().remove(groupName);
                Bukkit.getLogger().info("Removed Group Object!");
            }
            if(GroupManager.doGroupExist(groupName)){
                GroupManager.deleteGroup(groupName);
                Bukkit.getLogger().info("Removed Group From Database!");
            }
            GroupConfiguration.saveGroupConfigurations();
            Messages.sendMessage(player,"&aSuccesfully removed group", true);
        }else {
            Messages.sendMessage(player,"&cThis group doesn't exist.", true);
            return;
        }
    }

    public static void saveGroupDetails(Player player){
        GroupConfiguration.saveGroupConfigurations();
        Messages.sendMessage(player,"&aSaved group configuration", true);
    }

}

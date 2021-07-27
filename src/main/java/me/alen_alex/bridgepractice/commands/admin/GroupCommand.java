package me.alen_alex.bridgepractice.commands.admin;

import me.alen_alex.bridgepractice.configurations.GroupConfiguration;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class GroupCommand {

    private static YamlConfiguration groupConfiguration = GroupConfiguration.getGroupConfigurations();

    public static void createGroup(Player player, String groupName){

    }

    public static void deleteGroup(Player player, String groupName){

    }

    public static void saveGroupDetails(Player player){
        GroupConfiguration.saveGroupConfigurations();
        Messages.sendMessage(player,"&aSaved group configuration", true);
    }

}

package me.alen_alex.bridgepractice.commands.admin;

import me.alen_alex.bridgepractice.configurations.ArenaConfigurations;
import me.alen_alex.bridgepractice.configurations.GroupConfiguration;
import me.alen_alex.bridgepractice.group.GroupManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GroupCommand {

    private static YamlConfiguration groupConfiguration = GroupConfiguration.getGroupConfigurations();

    public static void createGroup(Player player, String Groupname){
        List<String> _grps = new ArrayList<>();
        _grps = groupConfiguration.getStringList("groups");
        if(_grps.contains(Groupname)){
            Messages.sendMessage(player,"&cThis group already exist!!", true);
            return;
        }else {
            _grps.add(Groupname);
            Messages.sendMessage(player,"Succesfully created group", true);
        }
    }

    public static void deleteGroup(Player player, String Groupname){
        List<String> _grps = new ArrayList<>();
        _grps = groupConfiguration.getStringList("groups");
        if(!_grps.contains(Groupname)){
            Messages.sendMessage(player,"&cThis group does not exist!", true);
            return;
        }else {
            _grps.remove(Groupname);
            GroupManager.deleteGroup(Groupname);
            Messages.sendMessage(player,"Succesfully deleted group", true);
        }
    }

    public static void saveGroupDetails(Player player){
        GroupConfiguration.saveGroupConfigurations();
        Messages.sendMessage(player,"&aSaved group configuration", true);
    }

}

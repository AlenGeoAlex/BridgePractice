package me.alen_alex.bridgepractice.commands.admin;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.utility.CitizensUtility;
import me.alen_alex.bridgepractice.configurations.GroupConfiguration;
import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.group.GroupManager;
import me.alen_alex.bridgepractice.utility.Messages;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class GroupCommand {

    private static YamlConfiguration groupConfiguration = GroupConfiguration.getGroupConfigurations();
    private static final List<String> topPlayerHolo = new ArrayList<String>(){{
        add("Add leaderboard placeholder here");
        add("");
    }};
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

    public static void setNPC(CommandSender player, String groupName, int position){

        if(!BridgePractice.isCitizensEnabled()){
            Messages.sendMessage(player,"&cThis command requires &6&lCitizens to run!",false);
            return;
        }

        if (groupName == null || position < 0) {
            Messages.sendMessage(player, MessageConfiguration.getUnknownCommand(),false);
            return;
        }

        if(position >= 0 && position < 10 ) {
            if(!GroupConfiguration.getGroupConfigurations().contains(groupName+".npc.enabled"))
                GroupConfiguration.getGroupConfigurations().set(groupName+".npc.enabled", true);
            GroupConfiguration.getGroupConfigurations().set(groupName+".npc."+String.valueOf(position)+".npcID", CitizensUtility.spawnCitizenNPC(((Player) player).getLocation(),""));
            GroupConfiguration.getGroupConfigurations().set(groupName+".npc."+String.valueOf(position)+".hologram", topPlayerHolo);
            GroupConfiguration.getGroupConfigurations().set(groupName+".npc."+String.valueOf(position)+".hologramOffset-Y", 3);
            GroupConfiguration.getGroupConfigurations().set(groupName+".npc."+String.valueOf(position)+".hologram-enabled", true);
            BridgePractice.getCitizensRegistry().saveToStore();
            Messages.sendMessage(player,"&aTopboard NPC Has been set successfully, use /practiceadmin save and restart the server",false);
        }else{
            Messages.sendMessage(player,"&cYou can't set leaderboard position to less than 0 or above 9",false);
            return;
        }
    }

    public static void saveGroupDetails(Player player){
        GroupConfiguration.saveGroupConfigurations();
        Messages.sendMessage(player,"&aSaved group configuration", true);
    }

}

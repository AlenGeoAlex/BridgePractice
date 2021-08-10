package me.alen_alex.bridgepractice.commands;

import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.island.Island;
import me.alen_alex.bridgepractice.island.IslandManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ResetIsland implements CommandExecutor, TabCompleter {

    private String islandName = null;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.hasPermission("practice.admin.reset")){
            Messages.sendMessage(commandSender, MessageConfiguration.getNoPermission(),false);
            return true;
        }

        if(strings.length == 0){
            Messages.sendMessage(commandSender,MessageConfiguration.getUnknownCommand(),false);
            Messages.sendMessage(commandSender,"Specifiy island name",false);
            return true;
        }else if (strings.length == 1){
            if(islandName == null){
                if(!IslandManager.getIslandData().containsKey(strings[0])){
                    Messages.sendMessage(commandSender,"&cIsland doesnot exist",true);
                    return true;
                }
                Messages.sendMessage(commandSender,"Are you sure you want to reset the island.",false);
                Messages.sendMessage(commandSender,"enter the command /resetisland "+strings[0]+" to confirm",false);
                islandName = strings[0];
            }else{
                if(!IslandManager.getIslandData().containsKey(islandName)){
                    Messages.sendMessage(commandSender,"&cIsland doesnot exist",true);
                    return true;
                }
                Messages.sendMessage(commandSender, ChatColor.RED+"Starting Island reset!",true);
                IslandManager.getIslandData().get(islandName).resetIsland();
                Messages.sendMessage(commandSender,"Island has been successfully reset-ed",false);
                islandName = null;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1) {
            List<String> islandList = new ArrayList<>(IslandManager.getIslandData().keySet());
            return islandList;
        }
        return null;
    }
}

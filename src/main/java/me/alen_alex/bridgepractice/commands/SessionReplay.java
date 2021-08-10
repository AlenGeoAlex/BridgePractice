package me.alen_alex.bridgepractice.commands;

import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.menu.MenuManager;
import me.alen_alex.bridgepractice.placeholderapi.PlaceholderDataManager;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
import me.alen_alex.bridgepractice.utility.ReplayUtility;
import me.jumper251.replay.filesystem.saving.ReplaySaver;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SessionReplay implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            Messages.sendMessage(commandSender, MessageConfiguration.getFromConsole(),false);
            return true;
        }
        Player player = (Player) commandSender;
        if(strings.length == 0){
            if(player.hasPermission("practice.gui.replay")){
                MenuManager.openReplayMenu(player);
                return true;
            }else{
                //TODO SEND HELP MESSAGE
            }
        }else if(strings.length == 1){
            switch (strings[0].toUpperCase()){
                case "CLEAR":
                    PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getPlayerReplays().forEach((replayName) ->{
                        ReplayUtility.deleteReplay(player,replayName);
                    });
                    Messages.sendMessage(player,MessageConfiguration.getReplayCleared(),false);
                    break;
                case "TOGGLE":
                    if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
                        Messages.sendMessage(player,MessageConfiguration.getInSession(),false);
                        return true;
                    }

                    if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isRecordingEnabled()){
                        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setRecordingEnabled(false);
                        Messages.sendMessage(player,MessageConfiguration.getReplayDisable(),false);
                    }else{
                        PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setRecordingEnabled(true);
                        Messages.sendMessage(player,MessageConfiguration.getReplayEnable(),false);
                    }

                    break;
                default:
                    Messages.sendMessage(player,MessageConfiguration.getUnknownCommand(),false);
            }
        }else if(strings.length == 2){
            switch (strings[0].toUpperCase()){
                case "DELETE":
                    if(ReplaySaver.exists(strings[1])){
                        ReplayUtility.deleteReplay(player,strings[1]);
                    }else{
                        Messages.sendMessage(player,MessageConfiguration.getReplayNotExist(),false);
                    }
                    break;
                case "PLAY":
                    ReplayUtility.playReplay(player,strings[1]);
                    break;
                default:
                    Messages.sendMessage(player,MessageConfiguration.getUnknownCommand(),false);

            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> replayList = new ArrayList<>();
        if(strings.length == 1){
            replayList.clear();
            replayList.add("play");
            replayList.add("delete");
            replayList.add("clear");
            replayList.add("toggle");
            return replayList;
        }
        if(strings.length == 2){
            if(strings[0].equalsIgnoreCase("play") || strings[0].equalsIgnoreCase("delete")){
                return PlayerDataManager.getCachedPlayerData().get(((Player) commandSender ).getUniqueId()).getPlayerReplays();
            }
        }
        return null;
    }
}

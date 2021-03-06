package me.alen_alex.bridgepractice.commands;

import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.menu.MenuManager;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class Timer implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(!player.hasPermission("practice.timer")){
                Messages.sendMessage(player,"&cYou don't have permission to use this command!", true);
                return true;
            }
            if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
                Messages.sendMessage(player,"&cYou can't set timer while in session!", false);
                return true;
            }
            if(strings.length == 0){
                if(player.hasPermission("practice.gui.timer")) {
                    MenuManager.openTimerMenu(player);
                }else{
                    Messages.sendMessage(player,"&c&l-----&b&l-----&c&l-----&b&l-----&c&l-----&b&l-----&c&l-----&b&l-----",false);
                    Messages.sendJSONSuggestMessage(player,"&bSet yourself a timer","/timer set ","&eClick here to set a timer",false);
                    Messages.sendJSONExecuteCommand(player,"&bClear your current timer","/timer clear","&eClick here to clear your timer",false);
                    Messages.sendJSONExecuteCommand(player,"&bChoose setbacks enabled or not","/timer setbacks","&eClick here to set setbacks",false);
                    Messages.sendMessage(player,"&c&l-----&b&l-----&c&l-----&b&l-----&c&l-----&b&l-----&c&l-----&b&l-----",false);
                }
            }else{
                switch (strings[0].toUpperCase()){
                    case "SET":
                        if(strings.length == 2){
                            int time = Integer.parseInt(strings[1]);
                            if(time > 20){
                                Messages.sendMessage(commandSender,"&eYour timer has been set to &6"+time,true);
                                PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setPlayerTimer(time);
                            }else{
                                Messages.sendMessage(commandSender,"&cYou can't set time less than &b&l20 secs!", false);
                                return true;
                            }
                        }else{
                            Messages.sendMessage(commandSender,"&cWrong usage.",false);
                            Messages.sendMessage(commandSender,"&e/timer set [sec]",false);
                        }
                        break;
                    case "CLEAR":
                        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getPlayerTimer() > -1){
                            PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setPlayerTimer(-1);
                            Messages.sendMessage(player,"&aYour timer has been cleared", false);
                        }else{
                            Messages.sendMessage(player,"&aYou currently do not have a player timer set", false);
                        }
                        break;
                    case "SETBACK":
                        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isSetbackEnabled()){
                            PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setSetbackEnabled(false);
                            Messages.sendMessage(player,"&cTimer Setback has been disabled",false);
                        }else{
                            PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setSetbackEnabled(true);
                            Messages.sendMessage(player,"&aTimer Setback has been enabled",false);
                        }
                    default:
                }
            }
        }else
            Messages.sendMessage(commandSender,"This command cannot be execute from console!", false);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}

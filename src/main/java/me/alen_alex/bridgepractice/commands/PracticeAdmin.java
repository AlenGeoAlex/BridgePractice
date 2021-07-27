package me.alen_alex.bridgepractice.commands;

import me.alen_alex.bridgepractice.commands.admin.CreationCommand;
import me.alen_alex.bridgepractice.commands.admin.GroupCommand;
import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class PracticeAdmin implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You cannot execute this command from console");
            return true;
        }
        if(!sender.hasPermission("practice.admin")){
            sender.sendMessage("You do not have permission to execute the command");
            return true;
        }
        Player player = (Player) sender;

        if(args.length == 0){
            //TODO PRINT PLUGIN INFO
        }else{
            if(args.length >= 1){
                switch (args[0].toUpperCase()){
                    case "ISLAND":
                        if(args.length == 2){
                            if(args[1].equalsIgnoreCase("setspawn")) {
                                CreationCommand.setSpawnPoint(player);
                                return true;
                            }
                            if(args[1].equalsIgnoreCase("setend")) {
                                CreationCommand.setEndPoint(player);
                                return true;
                            }
                            if(args[1].equalsIgnoreCase("setlobby")) {
                                CreationCommand.setLobbyWorld(player);
                                return true;
                            }
                            if(args[1].equalsIgnoreCase("setpos1")) {
                                CreationCommand.setPosition1(player);
                                return true;
                            }
                            if(args[1].equalsIgnoreCase("setpos2")) {
                                CreationCommand.setPosition2(player);
                                return true;
                            }
                        }else if(args.length == 3){
                            if(args[1].equalsIgnoreCase("create")) {
                                CreationCommand.createAnIsland(player, args[2]);
                                return true;
                            }
                            if(args[1].equalsIgnoreCase("setgroup")) {
                                CreationCommand.setGroup(player, args[2]);
                                return true;
                            }
                            if(args[1].equalsIgnoreCase("setperm")) {
                                CreationCommand.setPermission(player, args[2]);
                                return true;
                            }
                        }else {
                            Messages.sendIncorrectUsage(player);
                            return true;
                        }
                        break;
                    case "GROUP":
                        if(Configuration.doUseGroups()) {
                            if (args.length <= 3) {
                                Messages.sendIncorrectUsage(player);
                                return true;
                            }
                            if (args[1].equalsIgnoreCase("create"))
                                GroupCommand.createGroup(player, args[2]);
                            if (args[1].equalsIgnoreCase("delete"))
                                GroupCommand.deleteGroup(player, args[2]);
                        }else{
                            Messages.sendMessage(player,"&cYou have not enabled groups", true);
                        }
                        break;
                    case "EDIT":

                        break;
                    case "SAVE":
                        CreationCommand.saveIslandDetails(player);
                        GroupCommand.saveGroupDetails(player);
                        break;

                    case "BUILD":
                        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isBuildModeEnabled()) {
                            PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setBuildModeEnabled(false);
                            Messages.sendMessage(player,"&cDisabled Build Mode", true);
                            player.setGameMode(GameMode.SURVIVAL);
                        }
                        else {
                            PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setBuildModeEnabled(true);
                            Messages.sendMessage(player,"&eEnabled Build Mode", true);
                            player.setGameMode(GameMode.CREATIVE);
                            player.setFlying(true);
                        }
                        break;
                    default:

                }
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}

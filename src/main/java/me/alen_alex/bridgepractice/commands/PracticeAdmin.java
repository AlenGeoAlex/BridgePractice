package me.alen_alex.bridgepractice.commands;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.commands.admin.CreationCommand;
import me.alen_alex.bridgepractice.commands.admin.EditCommand;
import me.alen_alex.bridgepractice.commands.admin.GroupCommand;
import me.alen_alex.bridgepractice.configurations.ArenaConfigurations;
import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.data.DataManager;
import me.alen_alex.bridgepractice.holograms.Holograms;
import me.alen_alex.bridgepractice.holograms.HolographicManager;
import me.alen_alex.bridgepractice.island.Island;
import me.alen_alex.bridgepractice.island.IslandManager;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Location;
import me.alen_alex.bridgepractice.utility.Messages;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
                            if(args[1].equalsIgnoreCase("delete")){
                                CreationCommand.deleteIsland(player, args[2]);
                                return true;
                            }
                        }else {
                            Messages.sendIncorrectUsage(player);
                            return true;
                        }
                        break;
                    case "PLAYER":
                        if(args.length <= 2){
                            Messages.sendIncorrectUsage(player);
                            return true;
                        }else if(args.length == 3){
                            if(args[1].equalsIgnoreCase("clear")){
                                if(args[2].isEmpty()) {Messages.sendMessage(player,"&cPlayer name must not be null",false);return  true;}
                                if(Bukkit.getPlayer(args[2]).isOnline()){
                                    Messages.sendMessage(player,"&d&lThe provided player is online, can't perform while player is online",false);
                                    return true;
                                }

                                try {
                                    if(DataManager.isUserRegisetered(args[2])){
                                        final boolean[] success = {false};
                                         Bukkit.getScheduler().runTaskAsynchronously(BridgePractice.getPlugin(), new Runnable() {
                                            @Override
                                            public void run() {
                                                success[0] = DataManager.deletePlayerFromDatabase(args[2]);
                                            }
                                        });
                                        if(success[0]){
                                            Messages.sendMessage(player,"&aSuccessfully deleted the player "+args[2]+" from database",false);
                                        }else{
                                            Messages.sendMessage(player,"&cFailed to delete user data of "+args[2]+". Check console for any errors",false);
                                            return true;
                                        }
                                    }
                                } catch (SQLException e) {
                                    Messages.sendMessage(player,"&cUnable to delete player data, Check console for errors!",false);
                                    e.printStackTrace();
                                }
                            }
                        }
                        break;
                    case "GROUP":
                        if(Configuration.doUseGroups()) {
                            if (args.length <= 2) {
                                Messages.sendIncorrectUsage(player);
                                return true;
                            }else if(args.length == 3) {
                                if (args[1].equalsIgnoreCase("create")) {
                                    GroupCommand.createGroup(player, args[2]);
                                }
                                if (args[1].equalsIgnoreCase("delete"))
                                    GroupCommand.deleteGroup(player, args[2]);
                            }else if(args.length ==4){
                                if(args[1].equalsIgnoreCase("setnpc")){
                                    GroupCommand.setNPC(sender,args[2],Integer.parseInt(args[3]));
                                }
                            }
                        }else{
                            Messages.sendMessage(player,"&cYou have not enabled groups", true);
                        }
                        break;
                    case "EDIT":
                        if(args.length == 2){
                            if(args[1].equalsIgnoreCase("setspawn")) {
                                EditCommand.setSpawnPoint(player);
                                return true;
                            }
                            if(args[1].equalsIgnoreCase("setend")) {
                                EditCommand.setEndPoint(player);
                                return true;
                            }
                            if(args[1].equalsIgnoreCase("setlobby")) {
                                EditCommand.setLobbyPoint(player);
                                return true;
                            }
                            if(args[1].equalsIgnoreCase("setpos1")) {
                                EditCommand.setPos1(player);
                                return true;
                            }
                            if(args[1].equalsIgnoreCase("setpos2")) {
                                EditCommand.setPos2(player);
                                return true;
                            }
                        }else if(args.length == 3){
                            if(args[1].equalsIgnoreCase("island")) {
                                EditCommand.setEditIslands(player, args[2]);
                                return true;
                            }
                        }
                        break;
                    case "SAVE":
                        CreationCommand.saveIslandDetails(player);
                        GroupCommand.saveGroupDetails(player);
                        EditCommand.saveEditIslands(player);
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
                    case "DISABLE":
                        if(args.length == 2) {
                            if(!IslandManager.getIslandData().containsKey(args[1])){
                                Messages.sendMessage(player,"&cIsland doesnot exist or not loaded", true);
                                return true;
                            }
                            IslandManager.getIslandData().get(args[1]).disableIsland();
                            ArenaConfigurations.getArenaConfiguration().set(args[1]+".enabled",false);
                            ArenaConfigurations.saveArenaConfiguration();
                            Messages.sendMessage(player,"&cDisabled the arena "+args[1], true);
                        }else
                            Messages.sendIncorrectUsage(player);
                        break;
                    case "ENABLE":
                        if(args.length == 1) {
                            for(String name : ArenaConfigurations.getArenaConfiguration().getKeys(false)){
                                if(!ArenaConfigurations.getArenaConfiguration().getBoolean(name+".enabled")){
                                    ArenaConfigurations.getArenaConfiguration().set(name+".enabled",true);
                                    Messages.sendMessage(player,"&aEnabled the arena &6"+name, true);
                                }
                            }
                            ArenaConfigurations.saveArenaConfiguration();
                        }else if(args.length ==2){
                            if(!IslandManager.getIslandData().containsKey(args[1])) {
                                Messages.sendMessage(player, "&cIsland doesnot exist or not loaded", true);
                                return true;
                            }
                            }else if(args.length == 3) {
                                if(args[2].equalsIgnoreCase("--force")) {
                                    ArenaConfigurations.getArenaConfiguration().set(args[1] + ".enabled", true);
                                    Messages.sendMessage(player, "&aEnabled the arena &6" + args[1], true);
                                    ArenaConfigurations.saveArenaConfiguration();
                                }
                        }else{
                            Messages.sendIncorrectUsage(player);
                        }
                        break;
                    case "HOLO":
                        if(BridgePractice.isHologramsEnabled()) {
                            if (args.length == 2) {
                                if (args[1].equalsIgnoreCase("reload")) {
                                    for (Map.Entry<String, Holograms> hologramsEntry : HolographicManager.getHoloData().entrySet()) {
                                        hologramsEntry.getValue().deleteHolo();
                                        hologramsEntry.getValue().createHolograms();
                                    }
                                    Messages.sendMessage(sender, "&aSuccesfully reloaded all holograms", true);
                                    return true;
                                }
                            } else if (args.length == 3) {
                                if (args[1].equalsIgnoreCase("reload")) {
                                    if (HolographicManager.getHoloData().containsKey(args[2])) {
                                        HolographicManager.getHoloData().get(args[2]).deleteHolo();
                                        HolographicManager.getHoloData().get(args[2]).createHolograms();
                                    } else {
                                        Messages.sendMessage(player, "&cIsland with this name does not exist", true);
                                    }
                                    return true;
                                }
                            }
                        }else{
                            Messages.sendMessage(player,"&cUnable to hook with Holographic Displays",false);
                        }
                        break;
                    case "SETLOBBY":
                        Configuration.getConfig().set("settings.lobby-location",Location.parseLocation(player.getLocation()));
                        Messages.sendMessage(player,"&6Lobby point has been set!",false);
                        break;
                    case "MESSAGES":
                        MessageConfiguration.createLangaugeFile();
                        Messages.sendMessage(player,"&cMessages has been succesfully reloaded",false);
                        break;
                    case "SPAWN":
                        player.teleport(Location.getLocation(Configuration.getLobbyLocation()));
                        Messages.sendMessage(player,"&aYou have been teleported to spawn!",false);
                        break;
                    default:
                        Messages.sendMessage(player,"&cUnknown subcommand!", true);
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

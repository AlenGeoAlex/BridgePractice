package me.alen_alex.bridgepractice.commands;

import me.alen_alex.bridgepractice.commands.player.PlayerCommands;
import me.alen_alex.bridgepractice.game.Gameplay;
import me.alen_alex.bridgepractice.menu.MenuManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class IslandCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You cannot execute this command from console");
            return true;
        }
        Player player = (Player) sender;
        if(args.length == 0){
            Messages.sendMessage(player,"&c&l-----&b&l-----&c&l-----&b&l-----&c&l-----&b&l-----&c&l-----&b&l-----",false);
            Messages.sendMessage(player,"      &d&lBridge Practice",false);
            Messages.sendMessage(player,"&cDeveloped By&8: &6Alen_Alex",false);
            Messages.sendMessage(player,"&cDeveloped For&8: &6KGO Network", false);
            Messages.sendJSONLink(player, "&cDependency Provided&8: &6CoreAPI &8(Avenger AK&8)","https://github.com/AbhigyaKrishna/CoreAPI","&fClick to check out the API");
            Messages.sendMessage(player,"",false);
            Messages.sendMessage(player,"&e&lNOTE : &bThe complete authority & permissions ",false);
            Messages.sendMessage(player,"&bfor this plugin is with Alen_Alex & KGO Network",false);
            Messages.sendMessage(player,"",false);
            Messages.sendJSONExecuteCommand(player,"&8Click here for help","/island help", "&fClicking here will open help menu", false);
            Messages.sendMessage(player,"&c&l-----&b&l-----&c&l-----&b&l-----&c&l-----&b&l-----&c&l-----&b&l-----",false);
        }else{
            switch (args[0].toUpperCase()){
                case "REQUEST":
                    if(args.length == 1)
                    PlayerCommands.islandRequestCommand(player);
                    else if(args.length == 2)
                    PlayerCommands.islandRequestCommand(player, args[1]);
                    break;
                case "LEAVE":
                    PlayerCommands.leaveRequestCommand(player);
                    break;
                case "BLOCKS":
                    MenuManager.openMaterialMenu(player);
                    break;
                case "TEST":
                    Gameplay.handleGameEnd(player, true);
                    break;
                case "REPLAY":
                    MenuManager.openReplayMenu(player);
                    break;
                default:

            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabCompleter = new ArrayList<>();
        if(args.length == 1){
            tabCompleter.clear();
            tabCompleter.add("request");
            tabCompleter.add("leave");
            tabCompleter.add("help");
            return tabCompleter;
        }
        return null;
    }
}

package me.alen_alex.bridgepractice.commands;

import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

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
            Messages.sendMessage(player,"&c&l-----&b&l-----&c&l-----&b&l-----",false);
            Messages.sendMessage(player,"      &d&lBridge Practice",false);
            Messages.sendMessage(player,"&cDeveloped By&8: &6Alen_Alex",false);
            Messages.sendMessage(player,"&cDeveloped For&8: &6KGO Network", false);
            Messages.sendMessage(player,"&e&lNOTE : &bThe complete authority & permissions ",false);
            Messages.sendMessage(player,"&bfor this plugin is with Alen_Alex & KGO Network",false);
            Messages.sendMessage(player,"",false);
            Messages.sendJSONExecuteCommand(player,"&8Click here for help","/island help", "&fClicking here will open help menu", false);
            Messages.sendMessage(player,"&c&l-----&b&l-----&c&l-----&b&l-----",false);
        }else{
            switch (args[0].toUpperCase()){

            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}

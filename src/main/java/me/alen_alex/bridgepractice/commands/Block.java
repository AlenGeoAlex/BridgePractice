package me.alen_alex.bridgepractice.commands;

import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.menu.MenuManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Block implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage(MessageConfiguration.getFromConsole());
            return true;
        }

        Player player = (Player) commandSender;

        if(!player.hasPermission("practice.gui.block")){
            Messages.sendMessage(player,MessageConfiguration.getNoPermission(),false);
            return true;
        }
        if(strings.length == 0){
            MenuManager.openMaterialMenu(player);
        }else
            Messages.sendMessage(player,MessageConfiguration.getUnknownCommand(),false);

        return true;
    }
}

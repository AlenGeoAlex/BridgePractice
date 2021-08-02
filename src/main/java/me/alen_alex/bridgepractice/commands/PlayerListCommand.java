package me.alen_alex.bridgepractice.commands;

import me.alen_alex.bridgepractice.menu.MenuManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            MenuManager.openSpectatorMenu(((Player) commandSender).getPlayer());
            return true;
        }


        return true;
    }
}

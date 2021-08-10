package me.alen_alex.bridgepractice.commands;

import me.alen_alex.bridgepractice.game.Gameplay;
import me.alen_alex.bridgepractice.menu.MenuManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerListCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            MenuManager.openSpectatorMenu(((Player) commandSender).getPlayer());
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1){
            List<String> playerList = new ArrayList<>();
            Gameplay.getPlayerIslands().keySet().forEach((playerData -> playerList.add(playerData.getPlayerName()) ));
            return playerList;
        }
        return null;
    }
}

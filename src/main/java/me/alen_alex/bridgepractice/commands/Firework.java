package me.alen_alex.bridgepractice.commands;

import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.menu.MenuManager;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Firework implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission("practice.gui.firework")) {
                Messages.sendMessage(player, "&cYou don't have permission to use this command!", true);
                return true;
            }
            if (PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING) {
                Messages.sendMessage(player, "&cYou can't set timer while in session!", false);
                return true;
            }
            MenuManager.openFireworkMenu(player);
        }
        return false;
    }
}

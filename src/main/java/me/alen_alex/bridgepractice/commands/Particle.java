package me.alen_alex.bridgepractice.commands;

import me.alen_alex.bridgepractice.menu.MenuManager;
import me.alen_alex.bridgepractice.utility.Messages;
import me.alen_alex.bridgepractice.utility.PlayerParticles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Particle implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(!player.hasPermission("practice.gui.particle")){
                Messages.sendMessage(commandSender,"&cYou don't have permission",true);
                return true;
            }
                MenuManager.openParticleMenu(player);

        }

        return false;
    }
}

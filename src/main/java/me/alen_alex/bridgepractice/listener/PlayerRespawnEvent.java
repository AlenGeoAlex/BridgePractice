package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.utility.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerRespawnEvent implements Listener {

    @EventHandler
    public void onPlayerRespawnEvent(org.bukkit.event.player.PlayerRespawnEvent event){
        Player player = event.getPlayer();
        player.teleport(Location.getLocation(Configuration.getLobbyLocation()));
    }

}

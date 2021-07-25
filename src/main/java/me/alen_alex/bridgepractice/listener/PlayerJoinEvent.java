package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.data.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event){
        if(!(event instanceof Player))
            return;
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if(!DataManager.isUserRegisetered(playerUUID))
            DataManager.registerUser(player);




    }

}

package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerItemPickupEvent implements Listener {

    @EventHandler
    public void onPlayerItemPickupEvent(PlayerPickupItemEvent event){
        if(event.isCancelled())
            return;

        Player player = event.getPlayer();

        if(Configuration.isItemPickupOnlyOnSession()){
            if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() != null){
                event.setCancelled(true);
            }
        }else
            event.setCancelled(true);

    }

}

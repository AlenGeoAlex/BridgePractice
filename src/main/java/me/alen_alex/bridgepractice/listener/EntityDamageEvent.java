package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageEvent implements Listener {

    @EventHandler
    public void onEntityDamageEvent(org.bukkit.event.entity.EntityDamageEvent event){
        if(event.isCancelled())
            return;
        if(event.getEntity() instanceof Player){
            Player player =(Player) event.getEntity();
            if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() != null){
                if(event.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL) {
                    event.setCancelled(true);
                    return;
                }else {
                    event.setCancelled(false);
                    return;
                }
            }
        }
    }

}

package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class PlayerCraftItemEvent implements Listener {


    @EventHandler
    public void onPlayerCraftItemEvent(CraftItemEvent event){
        Player player = (Player) event.getWhoClicked();
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() != null){
            event.setCancelled(true);
        }
    }

}

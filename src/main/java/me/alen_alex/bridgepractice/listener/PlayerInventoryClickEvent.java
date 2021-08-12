package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerInventoryClickEvent implements Listener {

    @EventHandler
    public void onPlayerInventoryClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if(event.getCurrentItem() == null)
            return;

        if(event.getCurrentItem().getType() == Material.BARRIER){
            if(!PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isBuildModeEnabled()){
                event.setCancelled(true);
                return;
            }
        }
    }


}

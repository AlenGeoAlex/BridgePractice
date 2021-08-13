package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerInteractEvent implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(org.bukkit.event.player.PlayerInteractEvent event){
            Player player = event.getPlayer();
            if(event.getItem() == null)return;
            if(!event.getItem().getItemMeta().hasDisplayName()) return;

            if(event.getItem().getItemMeta().getDisplayName().equals(Messages.parseColor("&cLeave Session")) && event.getItem().getData().getItemType() == Material.BARRIER) {
                player.performCommand("island leave");
                return;
            }
    }

}

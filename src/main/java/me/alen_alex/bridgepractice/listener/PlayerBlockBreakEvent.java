package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerBlockBreakEvent implements Listener {

    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isBuildModeEnabled())
            return;
        else {
            event.setCancelled(true);
            return;
        }
    }



}

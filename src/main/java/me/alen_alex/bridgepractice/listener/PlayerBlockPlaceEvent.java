package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.game.Gameplay;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerBlockPlaceEvent implements Listener {

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.IDLE_ISLAND){
            Gameplay.onFirstBlockPlace(player,event.getBlockPlaced().getLocation());
            return;
        }

        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
            Gameplay.onBlockPlace(player,event.getBlockPlaced().getLocation());
            return;
        }

        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == null){
            event.setCancelled(true);
        }


    }

}

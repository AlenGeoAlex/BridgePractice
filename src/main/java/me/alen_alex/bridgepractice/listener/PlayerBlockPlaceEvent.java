package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.game.Gameplay;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerBlockPlaceEvent implements Listener {

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(event.isCancelled())
            return;
        if(event.getBlockPlaced().getType() == Material.BARRIER){
            if(!PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isBuildModeEnabled()) {
                event.setCancelled(true);
                return;
            }
        }

        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() != null){
            if(!Configuration.getBlacklistedBlocks().isEmpty()) {
                if (Configuration.getBlacklistedBlocks().contains(event.getBlockAgainst().getType().name()) || Configuration.getBlacklistedBlocks().contains(event.getBlockPlaced().getRelative(BlockFace.DOWN).getType().name()) || Configuration.getBlacklistedBlocks().contains(event.getBlockPlaced().getRelative(BlockFace.UP).getType().name()) || Configuration.getBlacklistedBlocks().contains(event.getBlockPlaced().getRelative(BlockFace.EAST).getType().name()) || Configuration.getBlacklistedBlocks().contains(event.getBlockPlaced().getRelative(BlockFace.WEST).getType().name())) {
                    Messages.sendMessage(player, MessageConfiguration.getCheatPlacedBlocksFail(), false);
                    Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).teleportToIslandSpawn(player);
                    Gameplay.onBlockPlace(player, event.getBlockPlaced().getLocation());
                    Gameplay.handleGameEnd(player, false);
                    return;
                }
            }
        }

        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.IDLE_ISLAND){
            Gameplay.onFirstBlockPlace(player,event.getBlockPlaced().getLocation());
            return;
        }

        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
            Gameplay.onBlockPlace(player,event.getBlockPlaced().getLocation());
            return;
        }



        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isBuildModeEnabled()){
            return;
        }

        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == null){
            event.setCancelled(true);
        }

    }

}

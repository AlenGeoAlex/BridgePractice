package me.alen_alex.bridgepractice.listener;

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
        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.IDLE_ISLAND){
            Gameplay.onFirstBlockPlace(player,event.getBlockPlaced().getLocation());
            return;
        }

        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
            Gameplay.onBlockPlace(player,event.getBlockPlaced().getLocation());
            return;
        }

        if(event.getBlockAgainst().getType() == Material.BARRIER || event.getBlockPlaced().getRelative(BlockFace.DOWN).getType() == Material.BARRIER|| event.getBlockPlaced().getRelative(BlockFace.UP).getType() == Material.BARRIER|| event.getBlockPlaced().getRelative(BlockFace.EAST).getType() == Material.BARRIER ||event.getBlockPlaced().getRelative(BlockFace.WEST).getType() == Material.BARRIER){
            if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING)
                Messages.sendMessage(player,"&eTrying the easy way!!", false);
            Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).teleportToIslandSpawn(player);
        }

        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isBuildModeEnabled()){
            return;
        }

        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == null){
            event.setCancelled(true);
        }

        //TODO
        /*if(event.getBlockAgainst() == Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation()){
            System.out.println(3);
        }*/


    }

}

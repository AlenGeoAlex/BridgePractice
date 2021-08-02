package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.game.Gameplay;
import me.alen_alex.bridgepractice.island.IslandManager;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerMoveEvent implements Listener {

    @EventHandler
    public void onPlayerMoveEvent(org.bukkit.event.player.PlayerMoveEvent event){
        Player player = event.getPlayer();

        if (event.getTo().getBlockX() == event.getFrom().getBlockX() && event.getTo().getBlockY() == event.getFrom().getBlockY() && event.getTo().getBlockZ() == event.getFrom().getBlockZ()) {
            return;
        }


        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
            System.out.println((Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockX() == event.getTo().getX() && Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockY() == event.getTo().getBlockY() && Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockZ() == event.getTo().getBlockZ() )+ " -+- "+Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockX()+"--"+event.getTo().getBlockX()+ " -+- "+Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockY()+"--"+event.getTo().getBlockY()+ " -+- "+Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockZ()+"--"+event.getTo().getBlockZ());
            System.out.println((Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockX() == event.getPlayer().getLocation().getBlockX()) + "-+-"+ (Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockY() == event.getPlayer().getLocation().getBlockY())+"-+-"+ (Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockZ() == event.getPlayer().getLocation().getBlockZ()));
            if(Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockX() == event.getPlayer().getLocation().getBlockX() && Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockY() == event.getPlayer().getLocation().getBlockY() && Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockZ() == event.getPlayer().getLocation().getBlockZ() ) {
                System.out.println(2);
                if (PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getBlocksPlacedOnCurrentGame() < Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getMinBlocks()) {
                    Gameplay.handleGameEnd(player, false);
                    Messages.sendMessage(player, "&cThere are some minimum required blocks, How did you reach here before that?", false);
                    return;
                }
                //TODO CHECK MINIMUM TIME TAKEN
                Gameplay.handleGameEnd(player, true);
            }
        }

        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == null){
            if(!Configuration.isVoidDectionOnlyOnIslands()){
                if(event.getTo().getBlockY() < Configuration.getVoidDetectionLevel()){
                    player.teleport(player.getWorld().getSpawnLocation());
                    Messages.sendMessage(player,"&eYou have been teleported as per world safety", false);
                }
            }
        }else{
            if(event.getTo().getBlockY() < Configuration.getVoidDetectionLevel()){
                Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).teleportToIslandSpawn(player);
                if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING) {
                    Messages.sendMessage(player, "&b&lSeems like you are slipping foot, &6Returning to the starting point!", false);
                    Gameplay.handleGameEnd(player,false);
                }
            }
        }


    }

}

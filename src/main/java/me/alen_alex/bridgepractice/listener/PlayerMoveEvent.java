package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.game.Gameplay;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
import me.alen_alex.bridgepractice.utility.TimeUtility;
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
            if(Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockX() == event.getPlayer().getLocation().getBlockX() && Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockY() == event.getPlayer().getLocation().getBlockY() && Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation().getBlockZ() == event.getPlayer().getLocation().getBlockZ() ) {
                if (PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getBlocksPlacedOnCurrentGame() < Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getMinBlocks()) {
                    Gameplay.handleGameEnd(player, false);
                    Messages.sendMessage(player, MessageConfiguration.getCheatBlockFail(), false);
                    return;
                }
                if(TimeUtility.getSecondsFromLongTime(System.currentTimeMillis() - (PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getStartTime())) < Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getMinSeconds()){
                    Gameplay.handleGameEnd(player,false);
                    Messages.sendMessage(player,MessageConfiguration.getCheatTimeFail(), false);
                    return;
                }

                Gameplay.handleGameEnd(player, true);
            }
        }

        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == null){
            if(!Configuration.isVoidDectionOnlyOnIslands()){
                if(event.getTo().getBlockY() < Configuration.getVoidDetectionLevel())
                    player.teleport(player.getWorld().getSpawnLocation());
            }
        }else{
            if(event.getTo().getBlockY() < Configuration.getVoidDetectionLevel()){
                Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).teleportToIslandSpawn(player);
                if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING) {
                    Messages.sendMessage(player, MessageConfiguration.getPlayerFellVoid(), false);
                    Gameplay.handleGameEnd(player,false);
                }
            }
        }


    }

}

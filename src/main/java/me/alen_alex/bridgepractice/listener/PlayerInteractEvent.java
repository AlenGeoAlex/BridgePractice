package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.game.Gameplay;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
import me.alen_alex.bridgepractice.utility.TimeUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

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

    @EventHandler
    public void onPressurePlateTrigger(org.bukkit.event.player.PlayerInteractEvent event){
        if(event.isCancelled())
            return;

        if(event.getClickedBlock().isEmpty())
            return;

        Player player = event.getPlayer();
        if(! (event.getClickedBlock().getType() == Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndPointMaterial()) && event.getAction() != Action.PHYSICAL)
            return;

        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
            if(event.getClickedBlock().getType() == Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndPointMaterial()){
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
    }

}

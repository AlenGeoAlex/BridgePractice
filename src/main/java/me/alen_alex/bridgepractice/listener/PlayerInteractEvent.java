package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.game.GameplayHandler;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Location;
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

        if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
            if(event.getAction() == Action.PHYSICAL) {
                if (event.getClickedBlock().getType() == BridgePractice.getGameplayHandler().getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndPointMaterial()) {
                    if (Location.compareLocations(event.getClickedBlock().getLocation(), BridgePractice.getGameplayHandler().getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getEndLocation())) {

                        if (PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getBlocksPlacedOnCurrentGame() < BridgePractice.getGameplayHandler().getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getMinBlocks()) {
                            BridgePractice.getGameplayHandler().handleGameEnd(player, false);
                            Messages.sendMessage(player, MessageConfiguration.getCheatBlockFail(), false);
                            return;
                        }
                        if (TimeUtility.getSecondsFromLongTime(System.currentTimeMillis() - (PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getStartTime())) < BridgePractice.getGameplayHandler().getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId())).getMinSeconds()) {
                            BridgePractice.getGameplayHandler().handleGameEnd(player, false);
                            Messages.sendMessage(player, MessageConfiguration.getCheatTimeFail(), false);
                            return;
                        }
                        BridgePractice.getGameplayHandler().handleGameEnd(player, true);
                    }
                }
            }
        }
    }

}

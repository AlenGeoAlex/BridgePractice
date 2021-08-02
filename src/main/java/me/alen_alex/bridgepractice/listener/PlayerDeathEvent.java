package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.game.Gameplay;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDeathEvent implements Listener {

    @EventHandler
    public void onPlayerDeathEvent(org.bukkit.event.entity.PlayerDeathEvent event){
        if(event.getEntity().getPlayer() instanceof Player){
            Player player = event.getEntity().getPlayer();

            if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() != null){
                if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING)
                    Gameplay.handleGameEnd(player, false);
                Gameplay.handleGameLeave(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()));
                Messages.sendMessage(player,"&eYou had a death. Your playerdata has been readjusted!", false);
            }
        }
    }
}

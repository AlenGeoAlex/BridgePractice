package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.placeholderapi.PlaceholderDataManager;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Location;
import me.jumper251.replay.api.ReplaySessionFinishEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerReplaySessionFinishEvent implements Listener {

    @EventHandler
    public void onPlayerReplaySessionFinishEvent(ReplaySessionFinishEvent event){
        Player player = event.getPlayer();
        if(PlayerDataManager.getCachedPlayerData().containsKey(player.getUniqueId())){
            if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isWatchingReplay()){
                PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).setWatchingReplay(false);
                if(Configuration.isTpBacktoLobbyAfterReplay()) {
                    Bukkit.getScheduler().runTask(BridgePractice.getPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            player.teleport(Location.getLocation(Configuration.getLobbyLocation()));
                        }
                    });

                }
            }else{
                Bukkit.getLogger().info("Failed to set false flag for "+player.getName()+" on watching replay");
            }
        }
    }

}

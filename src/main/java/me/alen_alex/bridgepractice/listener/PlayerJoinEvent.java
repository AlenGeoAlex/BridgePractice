package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.api.PlayerIslandJoinEvent;
import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.data.DataManager;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.sql.SQLException;
import java.util.UUID;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        try {
            if(!DataManager.isUserRegisetered(playerUUID)) {
                DataManager.registerUser(player);
            }
        } catch (SQLException e) {
            player.kickPlayer("&cDatabase seems to be offline. Enquire with adminstrators");
            e.printStackTrace();
        }
        try {
            PlayerDataManager.loadPlayerData(playerUUID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            player.kickPlayer("Seems like its unable to load the player data");
        }
        if(PlayerDataManager.getCachedPlayerData().containsKey(playerUUID)) {
            if(Configuration.isSpawnOnJoinEnabled()){
                PlayerDataManager.getCachedPlayerData().get(playerUUID).teleportPlayerToSpawn();
            }
            if (Configuration.isClearPlayerOnJoinEnabled()) {
                if (Configuration.getClearPlayerJoinDelay() <= 1) {
                    PlayerDataManager.getCachedPlayerData().get(playerUUID).setToLobbyState();
                } else {
                    Bukkit.getScheduler().runTaskLaterAsynchronously(BridgePractice.getPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            PlayerDataManager.getCachedPlayerData().get(playerUUID).setToLobbyState();
                        }
                    }, (long) Configuration.getClearPlayerJoinDelay());
                }
            }
        }else{
            player.kickPlayer("Seems like its unable to load the player data");
        }
    }

    @EventHandler
    public void onAsyncPlayerJoin(AsyncPlayerPreLoginEvent event){
        UUID playerUUID = event.getUniqueId();
        try {
            if(!DataManager.isUserRegisetered(playerUUID)) {
                DataManager.registerUser(event.getName(),event.getUniqueId().toString());
            }
        } catch (SQLException e) {
            event.setKickMessage("&cDatabase seems to be offline. Enquire with adminstrators");
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            e.printStackTrace();
        }
    }

}

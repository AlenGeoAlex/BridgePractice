package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.api.PlayerIslandJoinEvent;
import me.alen_alex.bridgepractice.data.DataManager;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
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
        if(!DataManager.isUserRegisetered(playerUUID)) {
            DataManager.registerUser(player);
        }
        try {
            PlayerDataManager.loadPlayerData(playerUUID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            player.kickPlayer("Seems like its unable to load the player data");
        }
        //PlayerDataManager.getCachedPlayerData().get(playerUUID).setLobbyItems();
    }

    @EventHandler
    public void onAsyncPlayerJoin(AsyncPlayerPreLoginEvent event){
        UUID playerUUID = event.getUniqueId();
        if(!DataManager.isUserRegisetered(playerUUID)) {
            DataManager.registerUser(event.getName(),event.getUniqueId().toString());
        }
    }

}

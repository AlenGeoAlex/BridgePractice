package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.data.DataManager;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Blocks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.SQLException;
import java.util.UUID;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        PlayerData joinedPlayer = null;
        if(!DataManager.isUserRegisetered(playerUUID)) {
            DataManager.registerUser(player);
        }
        try {
            PlayerDataManager.loadPlayerData(playerUUID);
            joinedPlayer = PlayerDataManager.getCachedPlayerData().get(playerUUID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            player.kickPlayer("Seems like its unable to load the player data");
        }
        joinedPlayer.setLobbyItems();



    }

}

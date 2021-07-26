package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.data.DataManager;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerLeaveEvent implements Listener {

    @EventHandler
    public void PlayerLeaveEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = event.getPlayer().getUniqueId();
        PlayerData leftPlayerData = PlayerDataManager.getCachedPlayerData().get(playerUUID);
        if(leftPlayerData == null) {
            Bukkit.getServer().getLogger().severe("Failed to save player data  "+player.getName()+" ["+playerUUID+"] due to the reason of null PlayerData while fetching. Contact Author if you see the message frequently");
            return;
        }
        boolean saveSuccess = DataManager.savePlayerData(leftPlayerData);
        if(!saveSuccess)
            Bukkit.getServer().getLogger().severe("Failed to save player data of "+player.getName()+" ["+playerUUID+"] to the database. Contact Author if you see the message frequently");
    }

}

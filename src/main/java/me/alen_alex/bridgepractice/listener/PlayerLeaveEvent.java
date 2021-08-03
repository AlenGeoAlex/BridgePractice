package me.alen_alex.bridgepractice.listener;

import me.Abhigya.core.particle.ParticleEffect;
import me.alen_alex.bridgepractice.data.DataManager;
import me.alen_alex.bridgepractice.game.Gameplay;
import me.alen_alex.bridgepractice.island.IslandManager;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
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
        if(Gameplay.getPlayerIslands().containsKey(PlayerDataManager.getCachedPlayerData().get(playerUUID))){
            Gameplay.getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(playerUUID)).setCurrentPlayer(null);
            Gameplay.getPlayerIslands().remove(PlayerDataManager.getCachedPlayerData().get(playerUUID));
        }

        boolean saveSuccess = DataManager.savePlayerData(leftPlayerData);
        if(!saveSuccess)
            Bukkit.getServer().getLogger().severe("Failed to save player data of "+player.getName()+" ["+playerUUID+"] to the database. Contact Author if you see the message frequently");
        PlayerDataManager.getCachedPlayerData().get(playerUUID).resetPlacedBlocks();
        PlayerDataManager.getCachedPlayerData().remove(playerUUID);

        if(Gameplay.getSpectators().containsKey(player))
            Gameplay.getSpectators().remove(player);

        if(Gameplay.getSpectators().containsValue(player)){
            Gameplay.getCurrentlySpectatingPlayers(player).forEach(player1 -> {
                Gameplay.handleLeaveSpectating(player1,player);
                Messages.sendMessage(player1,"&cYou have been teleported back to lobby since the player has left the server",false);
            });
        }
    }

}

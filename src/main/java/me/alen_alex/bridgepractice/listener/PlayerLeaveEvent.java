package me.alen_alex.bridgepractice.listener;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.data.DataManager;
import me.alen_alex.bridgepractice.game.GameplayHandler;
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
    public void onPlayerLeaveEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = event.getPlayer().getUniqueId();
        PlayerData leftPlayerData = PlayerDataManager.getCachedPlayerData().get(playerUUID);

        if(leftPlayerData == null) {
            Bukkit.getServer().getLogger().severe("Failed to save player data  "+player.getName()+" ["+playerUUID+"] due to the reason of null PlayerData while fetching. Contact Author if you see the message frequently");
            return;
        }
        if(BridgePractice.getGameplayHandler().getPlayerIslands().containsKey(PlayerDataManager.getCachedPlayerData().get(playerUUID))){
            BridgePractice.getGameplayHandler().getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(playerUUID)).setCurrentPlayer(null);
            BridgePractice.getGameplayHandler().getPlayerIslands().get(PlayerDataManager.getCachedPlayerData().get(playerUUID)).setOccupied(false);
            BridgePractice.getGameplayHandler().getPlayerIslands().remove(PlayerDataManager.getCachedPlayerData().get(playerUUID));
        }

        boolean saveSuccess = DataManager.savePlayerData(leftPlayerData);
        if(!saveSuccess)
            Bukkit.getServer().getLogger().severe("Failed to save player data of "+player.getName()+" ["+playerUUID+"] to the database. Contact Author if you see the message frequently");
        PlayerDataManager.getCachedPlayerData().get(playerUUID).resetPlacedBlocks();
        PlayerDataManager.getCachedPlayerData().remove(playerUUID);

        if(BridgePractice.getGameplayHandler().getSpectators().containsKey(player))
            BridgePractice.getGameplayHandler().getSpectators().remove(player);

        if(BridgePractice.getGameplayHandler().getSpectators().containsValue(player)){
            BridgePractice.getGameplayHandler().getCurrentlySpectatingPlayers(player).forEach(player1 -> {
                BridgePractice.getGameplayHandler().handleLeaveSpectating(player1,player);
                Messages.sendMessage(player1, MessageConfiguration.getSpectatorPlayerLeft(),false);
            });
        }
    }

}

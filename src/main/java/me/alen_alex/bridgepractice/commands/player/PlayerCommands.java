package me.alen_alex.bridgepractice.commands.player;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.game.Gameplay;
import me.alen_alex.bridgepractice.island.Island;
import me.alen_alex.bridgepractice.island.IslandManager;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerCommands {

    public static void islandRequestCommand(Player player){
        Bukkit.getScheduler().runTaskAsynchronously(BridgePractice.getPlugin(), new Runnable() {
            @Override
            public void run() {
                PlayerData playerData = PlayerDataManager.getCachedPlayerData().get(player.getUniqueId());
                if(playerData == null) {
                    Messages.sendMessage(player,"&cUnable to fetch playerdata. Please disconnect and relogin", true);
                    return;
                }
                if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.IDLE_ISLAND || PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getCurrentState() == PlayerState.PLAYING){
                    Messages.sendMessage(player,"&cYou are already on an island.",true);
                    return;
                }

                if(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).isSpectating()){
                    Messages.sendMessage(player,"&cYou can't join session while specing",true);
                }

                Messages.sendMessage(player,"&bSearching for an island", true);
                Island playerIsland = IslandManager.getAnIsland(player);
                if(playerIsland == null) {
                    Messages.sendMessage(player, "&cThere are no free islands left!", true);
                    return;
                }
                Gameplay.handleGameJoin(playerData,playerIsland);
            }
        });
    }

    public static void islandRequestCommand(Player player, String groupName){


    }

    public static void leaveRequestCommand(Player player){
        PlayerData playerData = PlayerDataManager.getCachedPlayerData().get(player.getUniqueId());
        if(playerData == null) {
            Messages.sendMessage(player,"&cUnable to fetch playerdata. Please disconnect and relogin", true);
            return;
        }

        if(playerData.getCurrentState() == null){
            Messages.sendMessage(player,"&cYou are not yet on an island!",true);
            return;
        }

        Gameplay.handleGameLeave(playerData);
    }



}

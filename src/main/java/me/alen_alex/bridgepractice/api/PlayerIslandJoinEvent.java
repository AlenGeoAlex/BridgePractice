package me.alen_alex.bridgepractice.api;

import me.alen_alex.bridgepractice.island.Island;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerIslandJoinEvent extends Event {

    private PlayerData playerData;
    private Island islandData;

    public PlayerIslandJoinEvent(PlayerData playerData, Island islandData) {
        this.playerData = playerData;
        this.islandData = islandData;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public Island getIslandData() {
        return islandData;
    }


    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

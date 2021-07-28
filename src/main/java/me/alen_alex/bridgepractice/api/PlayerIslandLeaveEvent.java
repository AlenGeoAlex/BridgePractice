package me.alen_alex.bridgepractice.api;

import me.alen_alex.bridgepractice.island.Island;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerIslandLeaveEvent extends Event {

    private Island islandData;
    private PlayerData playerData;


    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public PlayerIslandLeaveEvent(Island islandData, PlayerData playerData) {
        this.islandData = islandData;
        this.playerData = playerData;
    }

    public Island getIslandData() {
        return islandData;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }
}

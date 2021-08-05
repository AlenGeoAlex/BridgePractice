package me.alen_alex.bridgepractice.api;

import me.alen_alex.bridgepractice.island.Island;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerGameEndEvent extends Event {

    private PlayerData playerData;
    private Island islandData;
    private boolean completed;

    public PlayerGameEndEvent(PlayerData playerData, Island islandData, boolean completed) {
        this.playerData = playerData;
        this.islandData = islandData;
        this.completed = completed;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public Island getIslandData() {
        return islandData;
    }

    public boolean isCompleted() {
        return completed;
    }
}

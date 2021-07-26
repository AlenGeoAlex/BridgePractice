package me.alen_alex.bridgepractice.playerdata;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {

    private  String playerName;
    private  UUID playerUUID;
    private  Material playerMaterial;
    private int gamesPlayed;
    private  long currentTime, bestTime, blocksPlaced;
    private List<Block> placedBlocks = new ArrayList<Block>();

    public PlayerData(String playerName, UUID playerUUID, Material playerMaterial, int gamesPlayed, long currentTime, long bestTime, long blocksPlaced) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.playerMaterial = playerMaterial;
        this.gamesPlayed = gamesPlayed;
        this.currentTime = currentTime;
        this.bestTime = bestTime;
        this.blocksPlaced = blocksPlaced;
    }

    public String getPlayerName() {
        return playerName;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public Material getPlayerMaterial() {
        return playerMaterial;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public long getBestTime() {
        return bestTime;
    }

    public long getBlocksPlaced() {
        return blocksPlaced;
    }

    public void setPlayerMaterial(Material playerMaterial) {
        this.playerMaterial = playerMaterial;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void setBestTime(long bestTime) {
        this.bestTime = bestTime;
    }

    public void setBlocksPlaced(long blocksPlaced) {
        this.blocksPlaced = blocksPlaced;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public List<Block> getPlacedBlocks() {
        return placedBlocks;
    }

    public String getStringUUID(){
        return playerUUID.toString();
    }

    public String getStringMaterial(){
        return playerMaterial.toString();
    }

    public boolean isPlayerOnline(){
        return Bukkit.getPlayer(playerUUID).isOnline();
    }

    public Player getOnlinePlayer(){
        return Bukkit.getPlayer(playerUUID);
    }

    public OfflinePlayer getOfflinePlayer(){
        return Bukkit.getOfflinePlayer(playerUUID);
    }

    @Deprecated
    public Player getPlayer(){
        if(Bukkit.getPlayer(playerUUID).isOnline()){
            return Bukkit.getPlayer(playerUUID);
        }else
            return (Player) Bukkit.getPlayer(playerUUID);
    }



    //TODO -> Player Saving savePlayer();

}

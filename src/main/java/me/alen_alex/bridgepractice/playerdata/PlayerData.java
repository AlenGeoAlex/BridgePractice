package me.alen_alex.bridgepractice.playerdata;

import me.Abhigya.core.particle.ParticleBuilder;
import me.Abhigya.core.particle.ParticleEffect;
import me.Abhigya.core.util.tasks.Workload;

import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.utility.Blocks;
import me.alen_alex.bridgepractice.utility.Messages;
import me.alen_alex.bridgepractice.utility.WorkloadScheduler;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.UUID;

public class PlayerData {

    private  String playerName;
    private  UUID playerUUID;
    private  Material playerMaterial;
    private int gamesPlayed;
    private  long currentTime, bestTime, blocksPlaced,startTime,endTime;
    private PlayerState currentState;
    private LinkedList<Location> placedBlocks = new LinkedList<Location>();
    private boolean buildModeEnabled;
    private ParticleEffect playerParticle;
    public PlayerData(String playerName, UUID playerUUID, Material playerMaterial, int gamesPlayed, long currentTime, long bestTime, long blocksPlaced) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.playerMaterial = playerMaterial;
        this.gamesPlayed = gamesPlayed;
        this.currentTime = currentTime;
        this.bestTime = bestTime;
        this.blocksPlaced = blocksPlaced;
        this.buildModeEnabled = false;
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

    public LinkedList<Location> getPlacedBlocks() {
        return placedBlocks;
    }

    public void setPlacedBlocks(LinkedList<Location> placedBlocks) {
        this.placedBlocks = placedBlocks;
    }

    public void addPlacedBlocks(Location location){
        placedBlocks.add(location);
    }

    public void addPlayerPlacedBlock(){
        blocksPlaced+=1;
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

    public PlayerState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(PlayerState currentState) {
        this.currentState = currentState;
    }



    @Deprecated
    public Player getPlayer(){
        if(Bukkit.getPlayer(playerUUID).isOnline()){
            return Bukkit.getPlayer(playerUUID);
        }else
            return (Player) Bukkit.getPlayer(playerUUID);
    }

    public boolean isBuildModeEnabled() {
        return buildModeEnabled;
    }

    public void setBuildModeEnabled(boolean buildModeEnabled) {
        this.buildModeEnabled = buildModeEnabled;
    }

    public void fillPlayerBlocks(){
        final int blockQuantity = 64*5;
        Player player = getOnlinePlayer();
        ItemStack material;

        if(Blocks.doPlayerHavePreferencePermission(player)){
            System.out.println("Material has been set to playermaterial");
            material = new ItemStack(PlayerDataManager.getCachedPlayerData().get(player.getUniqueId()).getPlayerMaterial());
        }else {
            System.out.println("Material has been set to random");
            material = new ItemStack(Blocks.getRandomBlock());
        }
        player.setHealthScale(20.00);
        player.setFoodLevel(19);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
            for(int i=0; i<blockQuantity; i++){
                player.getInventory().addItem(material);
            }

        ItemStack leaveGame = new ItemStack(Material.BARRIER);
        ItemMeta leaveGameMeta = leaveGame.getItemMeta();
        leaveGameMeta.setDisplayName(Messages.parseColor("&cLeave Session"));
        leaveGame.setItemMeta(leaveGameMeta);
        player.getInventory().setItem(8, leaveGame);
    }

    public void setLobbyItems(){
        Player player = getOnlinePlayer();
        player.setHealthScale(20.00);
        player.getInventory().clear();
    }

    public void resetPlacedBlocks(){
        if(placedBlocks.isEmpty())
            return;
        ParticleBuilder particleBuilder = new ParticleBuilder(ParticleEffect.SMOKE_NORMAL,placedBlocks.get(0));
        placedBlocks.forEach((location -> {
            Workload load = () -> {
                location.getBlock().setType(Material.AIR);
                particleBuilder.setLocation(location).display();
            };
            WorkloadScheduler.getSyncThread().add(load);
        }));
        placedBlocks.clear();
    }

    public int getBlocksPlacedOnCurrentGame(){
        return placedBlocks.size();
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public ParticleEffect getPlayerParticle() {
        return playerParticle;
    }

    public void setPlayerParticle(ParticleEffect playerParticle) {
        this.playerParticle = playerParticle;
    }

    //TODO -> Player Saving savePlayer();

}

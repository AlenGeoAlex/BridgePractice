package me.alen_alex.bridgepractice.island;
import me.alen_alex.bridgepractice.game.Gameplay;
import me.alen_alex.bridgepractice.group.Group;
import me.alen_alex.bridgepractice.holograms.Holograms;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class Island {
    private String name, worldName,permission;
    private Location spawnLocation,endLocation,quitLocation,pos1,pos2;
    private boolean ressetting, active, occupied;
    private PlayerData currentPlayer = null;
    private Group islandGroup;
    private int minSeconds, minBlocks;

    public Island(@NotNull String name, @NotNull String worldName, Group islandGroup, String permission, @NotNull Location spawnLocation, @NotNull Location endLocation, @NotNull Location quitLocation, @NotNull Location pos1, @NotNull Location pos2, int minSeconds, int minBlocks, @NotNull boolean active) {
        this.name = name;
        this.worldName = worldName;
        this.islandGroup = islandGroup;
        this.permission = permission;
        this.spawnLocation = spawnLocation;
        this.endLocation = endLocation;
        this.quitLocation = quitLocation;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.active = active;
        this.minSeconds = minSeconds;
        this.minBlocks = minBlocks;
    }

    public String getName() {
        return name;
    }

    public String getWorldName() {
        return worldName;
    }


    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public Location getQuitLocation() {
        return quitLocation;
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public boolean isRessetting() {
        return ressetting;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setRessetting(boolean ressetting) {
        this.ressetting = ressetting;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public String getPermission() {
        return permission;
    }

    /*public boolean hasPermission(Player player){
        boolean hasPerm = false;
        if(permission == null || permission == "" || player.isOp() || player.hasPermission(permission))
            hasPerm = true;
        return hasPerm;
    }*/

    public boolean hasIslandPermission(Player player){
        if(permission == null)
            return true;
        else
            return player.hasPermission(permission);
    }

    public boolean hasGroup(){
        return islandGroup != null;
    }

    public void teleportToIslandSpawn(Player player){
        player.teleport(spawnLocation);
        player.getLocation().setPitch(0.0F);
    }

    public void teleportToQuitlobby(Player player){
        player.teleport(quitLocation);
        player.getLocation().setPitch(0.0F);
    }


    public World getIslandWorld(){
        return Bukkit.getWorld(worldName);
    }

    public PlayerData getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerData currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void removeCurrentPlayer(){
        currentPlayer = null;
    }

    public Group getIslandGroup() {
        return islandGroup;
    }

    public int getMinSeconds() {
        return minSeconds;
    }

    public int getMinBlocks() {
        return minBlocks;
    }

    public boolean hasMinimumReqTime(){
        return minSeconds != 0;
    }

    public boolean hasMinBlock(){
        return minBlocks != 0;
    }

    public void disableIsland(){
        this.setActive(false);
        if(Gameplay.getPlayerIslands().containsValue(this)){
            for(Map.Entry<PlayerData,Island> islandEntry : Gameplay.getPlayerIslands().entrySet()){
                if(islandEntry.getValue() == this){
                    this.teleportToQuitlobby(islandEntry.getKey().getOnlinePlayer());
                    Messages.sendMessage(islandEntry.getKey().getOnlinePlayer(), "&cThis island has been disabled by an admin, redirecting fallback location",false);
                    Gameplay.handleGameLeave(islandEntry.getKey());
                }
            }
        }
    }

    /*public void resetIsland(){
        final LinkedList<Location> locs = new LinkedList<>();
        int taskId;
        final int smallx = Math.min(pos1.getBlockX(),pos2.getBlockX());
        final int largex  = Math.max(pos1.getBlockX(),pos2.getBlockX());
        final int smally = Math.min(pos1.getBlockY(),pos2.getBlockY());
        final int largey = Math.max(pos1.getBlockY(),pos2.getBlockY());
        final int smallz = Math.min(pos1.getBlockZ(),pos2.getBlockZ());
        final int largez = Math.max(pos1.getBlockZ(),pos2.getBlockZ());
        for(int x = smallx;x<=largex;x++){
            for(int y = smally;y<=largey;y++){
                for(int z = smallz;z<=largez;z++){
                    Location loc = new Location(getIslandWorld(),x,y,z);
                    if(Blocks.getBlock().contains(loc.getBlock().getType()))
                        locs.add(loc);
                }
            }
        }
        System.out.println(locs.get(0));
        locs.forEach(location -> {
            Workload load = () -> Blocks.setResetBlocks(location.getWorld(),location.getBlockX(),location.getBlockY(),location.getBlockZ(),0,((Integer) 0).byteValue());
            //Workload load = () -> location.getBlock().setType(Material.AIR);
            WorkloadScheduler.getSyncThread().add(load);
        });
        System.out.println("Finished!!");

    }*/

}

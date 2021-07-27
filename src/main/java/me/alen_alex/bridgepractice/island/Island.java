package me.alen_alex.bridgepractice.island;

import co.aikar.taskchain.TaskChain;
import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import me.alen_alex.bridgepractice.utility.Blocks;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

public class Island {
    private String name, worldName,group,permission;
    private Location spawnLocation,endLocation,quitLocation,pos1,pos2;
    private boolean ressetting, active, occupied;
    private PlayerData currentPlayer = null;

    public Island(@NotNull String name, @NotNull String worldName, String group, String permission, @NotNull Location spawnLocation, @NotNull Location endLocation, @NotNull Location quitLocation, @NotNull Location pos1, @NotNull Location pos2, @NotNull boolean active) {
        this.name = name;
        this.worldName = worldName;
        this.group = group;
        this.permission = permission;
        this.spawnLocation = spawnLocation;
        this.endLocation = endLocation;
        this.quitLocation = quitLocation;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public String getWorldName() {
        return worldName;
    }

    public String getGroup() {
        return group;
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

    public boolean hasPermission(Player player){
        boolean hasPerm = false;
        if(permission == null || permission == "" || player.isOp() || player.hasPermission(permission))
            hasPerm = true;
        return hasPerm;
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

    /*public void resetIsland(){
        final List<Location> locs = null;
        int taskId;
        final int smallx = Math.min(pos1.getBlockX(),pos2.getBlockX());
        final int largex  = Math.max(pos1.getBlockX(),pos2.getBlockX());
        final int smally = Math.min(pos1.getBlockY(),pos2.getBlockY());
        final int largey = Math.max(pos1.getBlockY(),pos2.getBlockY());
        final int smallz = Math.min(pos1.getBlockZ(),pos2.getBlockZ());
        final int largez = Math.max(pos1.getBlockZ(),pos2.getBlockZ());
        final int[] cnt = {0};
        final int maxCnt = 5000;
        final int completed = 0;
        for(int x = smallx;x<=largex;x++){
            for(int y = smally;y<=largey;y++){
                for(int z = smallz;z<=largez;z++){
                    Location loc = new Location(getIslandWorld(),x,y,z);
                    if(Blocks.getBlock().contains(loc.getBlock().getType()))
                        locs.add(loc);
                }
            }
        }


          taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(BridgePractice.getPlugin(), new Runnable() {
            @Override
            public void run() {
                Iterator<Location> locationIterator = locs.iterator();
                while (cnt[0] < maxCnt && locationIterator.hasNext()){
                    Block block = locationIterator.next().getBlock();
                    block.setType(Material.AIR);
                    locationIterator.remove();
                    cnt[0] +=1;
                }
                if(!locationIterator.hasNext()){

                }



            }
        },0,2);



    }*/

}

package me.alen_alex.bridgepractice.utility;

import me.alen_alex.bridgepractice.enumerators.LocationType;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Location {

    public static String parseLocation(@NotNull Player player){
        org.bukkit.Location location = player.getLocation();
        if(location == null)
            return null;
        return (location.getWorld().getName()+"/"+location.getBlockX()+"/"+location.getBlockY()+"/"+location.getBlockZ()+"/"+location.getPitch()+"/"+location.getYaw());
    }

    public static String parseLocation(@NotNull org.bukkit.Location _loc){
        org.bukkit.Location location = _loc;
        if(location == null)
            return null;
        return (location.getWorld().getName()+"/"+location.getBlockX()+"/"+location.getBlockY()+"/"+location.getBlockZ()+"/"+location.getPitch()+"/"+location.getYaw());
    }
    
    public static org.bukkit.Location getLocation(@NotNull String _loc){
        String[] data = _loc.split("/");
        org.bukkit.Location location = new org.bukkit.Location(getWorldFromData(_loc),getBlockLocation(_loc,LocationType.X),getBlockLocation(_loc,LocationType.Y),getBlockLocation(_loc,LocationType.Z));
        location.setPitch(getBlockLocation(_loc,LocationType.PITCH));
        location.setYaw(getExtrasBlockLocation(_loc,LocationType.YAW));
        return location;
    }

    public static int getBlockLocation(String _loc, LocationType type){
        int redata=0;
        String[] rawData = _loc.split("/");

        if(_loc == null && rawData == null)
            return 0;

        switch (type){
            case X:
                redata = Integer.parseInt(rawData[1]);
                break;
            case Y:
                redata = Integer.parseInt(rawData[2]);
                break;
            case Z:
                redata = Integer.parseInt(rawData[3]);
                break;
            default:
                return 0;
        }
        return redata;
    }

    public static float getExtrasBlockLocation(@NotNull String _location,@NotNull LocationType type){
        float redata=0;
        String[] rawData = _location.split("/");

        if(_location == null && rawData == null)
            return 0;

        switch (type){
            case PITCH:
                redata = Float.parseFloat(rawData[4]);
                break;
            case YAW:
                redata = Float.parseFloat(rawData[5]);
                break;
            default:
                return 0;
        }
        return redata;
    }

    public static World getWorldFromData(@NotNull String _location){
        String[] rawData = _location.split("/");
        return Bukkit.getWorld(rawData[0]);
    }

    public static boolean compareLocations(org.bukkit.Location location1, org.bukkit.Location location2){
        if(location1.getBlockX() == location2.getBlockX() && location1.getBlockY() == location2.getBlockY() && location1.getBlockZ() == location2.getBlockZ())
            return true;
        else
            return false;
    }

}

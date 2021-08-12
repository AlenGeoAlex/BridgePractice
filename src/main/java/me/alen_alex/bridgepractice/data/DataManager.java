package me.alen_alex.bridgepractice.data;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.configurations.GroupConfiguration;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DataManager {

    public static void registerUser(String name, String UUID){
        try {
            PreparedStatement statement = Data.getDatabaseConnection().getConnection().prepareStatement("INSERT INTO `playerdata`(`name`, `uuid`, `blocksplaced`, `gamesplayed`, `besttime`, `currenttime`, `material`, `particle`) VALUES ('"+name+"','"+UUID+"',0,0,'"+System.currentTimeMillis()+"',0,'WOOL','SMOKE_NORMAL');");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for(String groupName : GroupConfiguration.getGroupConfigurations().getKeys(false)){
            try {
                PreparedStatement statement = Data.getDatabaseConnection().getConnection().prepareStatement("INSERT INTO `"+groupName+"` (`name`,`besttime`) VALUES ('"+name+"','"+System.currentTimeMillis()+"');");
                statement.executeUpdate();
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public static void registerUser(Player player){
        try {
            PreparedStatement statement = Data.getDatabaseConnection().getConnection().prepareStatement("INSERT INTO `playerdata`(`name`, `uuid`, `blocksplaced`, `gamesplayed`, `besttime`, `currenttime`, `material`, `particle`) VALUES ('"+player.getName()+"','"+player.getUniqueId()+"',0,0,'"+System.currentTimeMillis()+"',0,'WOOL','SMOKE_NORMAL');");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for(String groupName : GroupConfiguration.getGroupConfigurations().getKeys(false)){
            try {
                PreparedStatement statement = Data.getDatabaseConnection().getConnection().prepareStatement("INSERT INTO `"+groupName+"` (`name`,`besttime`) VALUES ('"+player.getName()+"','"+System.currentTimeMillis()+"');");
                statement.executeUpdate();
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static boolean isUserRegisetered(UUID playerUUID){
        boolean registered = false;
        try {
            ResultSet set = Data.getDatabaseConnection().executeQuery("SELECT * FROM `playerdata` WHERE `uuid` = '"+playerUUID.toString()+"';");
            while (set.next())
                registered = true;
            set.getStatement().close();
            set.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return registered;
    }

    public static ResultSet fetchPlayerData(UUID playerUUID){
        try {
            return Data.getDatabaseConnection().executeQuery("SELECT * FROM playerdata WHERE `uuid` = '"+playerUUID.toString()+"';");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static boolean savePlayerData(PlayerData playerData){
        if(playerData == null)
            return false;
        try {
            PreparedStatement ps = Data.getDatabaseConnection().getConnection().prepareStatement("UPDATE playerdata SET `blocksplaced` = '"+playerData.getBlocksPlaced()+"' ,`gamesplayed` = '"+playerData.getGamesPlayed()+"' , `besttime` = '"+playerData.getBestTime()+"', `currenttime` = '"+playerData.getCurrentTime()+"' , `material` = '"+ playerData.getStringMaterial()+"' , `particle` = '"+ playerData.getStringParticleName()+"' WHERE `uuid` =  '"+playerData.getStringUUID()+"';");
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static void createGroupDatabase(String groupName){
        try {
            Data.getDatabaseConnection().createTable(groupName,"`id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY","`name` VARCHAR(30) NOT NULL","`besttime` BIGINT(30) NOT NULL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteGroupDatabase(String groupName){
        try {
            PreparedStatement ps = Data.getDatabaseConnection().getConnection().prepareStatement("DROP TABLE `"+groupName+"`;");
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    
    public static long getBestFromGroup(String GroupName, String PlayerName){
        try {
            return Data.getDatabaseConnection().getLong(GroupName,"besttime", "name", PlayerName);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static void setBestFromGroup(String groupName, String playerName, long newDuration){
        try {
            Data.getDatabaseConnection().set(groupName,"name",playerName,"besttime",newDuration);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

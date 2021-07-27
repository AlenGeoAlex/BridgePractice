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

    public static void registerUser(Player player){
        Data.getDatabaseConnection().insertData("`name`,`uuid`,`blocksplaced`,`gamesplayed`,`besttime`,`currenttime`,`material`","'"+player.getName()+"','"+player.getUniqueId().toString()+"',0,0,0,0,'WOOL'","playerdata");
        Bukkit.getScheduler().runTaskAsynchronously(BridgePractice.getPlugin(), new Runnable() {
            @Override
            public void run() {
                for(String groupName : GroupConfiguration.getGroupConfigurations().getKeys(false)){
                    Data.getDatabaseConnection().insertData("`name`,`besttime`","'"+player.getName()+"','0'", groupName);
                }
            }
        });

    }

    public static boolean isUserRegisetered(UUID playerUUID){
        return Data.getDatabaseConnection().exists("playerdata","uuid",playerUUID.toString());
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
            PreparedStatement ps = Data.getDatabaseConnection().getConnection().prepareStatement("UPDATE playerdata SET `gamesplayed` = '"+playerData.getGamesPlayed()+"' , `besttime` = '"+playerData.getBestTime()+"', `currenttime` = '"+playerData.getCurrentTime()+"' , `material` = '"+ playerData.getStringMaterial()+"' WHERE `uuid` =  '"+playerData.getStringUUID()+"';");
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static void createGroupDatabase(String groupName){
        Data.getDatabaseConnection().createTable(groupName,"`id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY","`name` VARCHAR(30) NOT NULL","`besttime` BIGINT(30) NOT NULL");
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


}

package me.alen_alex.bridgepractice.data;

import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DataManager {

    public static void registerUser(Player player){
        Data.getDatabaseConnection().insertData("name,uuid,blocksplaced,gamesplayed,besttime,currenttime,material",player.getName()+","+player.getUniqueId().toString()+",0,0,0,0,'WOOL'","playerdata");
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

}

package me.alen_alex.bridgepractice.data;

import me.Abhigya.core.database.sql.SQL;
import me.Abhigya.core.database.sql.SQLDatabase;
import me.Abhigya.core.database.sql.hikaricp.HikariClientBuilder;
import me.Abhigya.core.database.sql.mysql.MySQL;
import me.Abhigya.core.database.sql.sqlite.SQLite;
import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.configurations.Configuration;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Data {
    private static BridgePractice plugin = BridgePractice.getPlugin();
    private static SQLDatabase database;
    private static SQL sql;

    public Data(){
        if(Configuration.isUseMysql()){
               this.database = new HikariClientBuilder("jdbc:mysql://"+Configuration.getHost()+":"+Configuration.getPort()+"/"+Configuration.getDatabase(),Configuration.getUsername(),Configuration.getPassword(),true).setMaximumPoolSize(10).build();
               //this.database = new MySQL(Configuration.getHost(),Integer.parseInt(Configuration.getPort()),Configuration.getDatabase(),Configuration.getUsername(),Configuration.getPassword(),true,Configuration.isUseSSL());
            try {
                database.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            this.database = new SQLite(plugin, new File(plugin.getDataFolder(),"database.db"),true);
            try {
                database.connect();
                System.out.println(database.getConnection().getAutoCommit());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            sql = new SQL(this.database.getConnection());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void reconnect(){
        if(Configuration.isUseMysql()){
            this.database = new HikariClientBuilder("jdbc:mysql://"+Configuration.getHost()+":"+Configuration.getPort()+"/"+Configuration.getDatabase(),Configuration.getUsername(),Configuration.getPassword(),true).setMaximumPoolSize(10).build();
            //this.database = new MySQL(Configuration.getHost(),Integer.parseInt(Configuration.getPort()),Configuration.getDatabase(),Configuration.getUsername(),Configuration.getPassword(),true,Configuration.isUseSSL());
            try {
                database.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            this.database = new SQLite(plugin, new File(plugin.getDataFolder(),"database.db"),true);
            try {
                database.connect();
                System.out.println(database.getConnection().getAutoCommit());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            sql = new SQL(this.database.getConnection());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public static SQL getDatabaseConnection() {
        return sql;
    }

    public static void createDatabase(){
        try {
            sql.createTable("playerdata","`id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY","`name` VARCHAR(30) NOT NULL","`uuid` VARCHAR(50) NOT NULL","`blocksplaced` INTEGER(10) NOT NULL","`gamesplayed` INTEGER(10) NOT NULL","`besttime` BIGINT(30) NOT NULL","`currenttime` BIGINT(30) NOT NULL","`material` VARCHAR(30)","`particle` VARCHAR(30)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static SQLDatabase getDatabase() {
        return database;
    }

    public static boolean isConnected(){
        return database.isConnected();
    }

    public static void disconnect(){
        if(isConnected()) {
            try {
                database.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

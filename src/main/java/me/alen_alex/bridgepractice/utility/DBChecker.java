package me.alen_alex.bridgepractice.utility;

import me.Abhigya.core.database.sql.SQL;
import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.data.DataManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.SQLException;

public class DBChecker extends BukkitRunnable {

    private BridgePractice plugin;
    private SQL databaseConnection;
    private int interval,retryin;


    public DBChecker(BridgePractice plugin, SQL databaseConnection, int interval, int retryin) {
        this.plugin = plugin;
        this.databaseConnection = databaseConnection;
        this.interval = interval;
        this.retryin = retryin;
    }


    @Override
    public void run() {
        try {
            if(databaseConnection.getConnection().isClosed()){
                DataManager.setDatabaseOnline(false);
                plugin.getLogger().severe("Database is disconnected. Checking again in "+retryin+" secs");
                plugin.getLogger().severe("");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

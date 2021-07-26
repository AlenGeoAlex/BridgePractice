package me.alen_alex.bridgepractice;

import me.Abhigya.core.database.sql.SQL;
import me.alen_alex.bridgepractice.configurations.ArenaConfigurations;
import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.data.Data;
import me.alen_alex.bridgepractice.listener.PlayerJoinEvent;
import me.alen_alex.bridgepractice.listener.PlayerLeaveEvent;
import me.alen_alex.bridgepractice.utility.Validation;
import org.bukkit.plugin.java.JavaPlugin;

public final class BridgePractice extends JavaPlugin {

    private static BridgePractice plugin;
    private static SQL connection;

    @Override
    public void onEnable() {
        plugin = this;
        Configuration.createConfiguration();
        if(!Validation.validateDatabase())
            {
                plugin.getLogger().severe("Database creditionals is invalid");
                plugin.getLogger().severe("Disabling plugin!");
                plugin.getPluginLoader().disablePlugin(this);
                return;
            }
        Data dataConnection = new Data();
        connection = dataConnection.getDatabaseConnection();
        Data.createDatabase();
        ArenaConfigurations.createArenaConfigurations();
        registerListener();
    }

    @Override
    public void onDisable() {
    }

    public void registerListener(){
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveEvent(), this);
    }

    public static BridgePractice getPlugin() {
        return plugin;
    }

    public static SQL getConnection() {
        return connection;
    }
}
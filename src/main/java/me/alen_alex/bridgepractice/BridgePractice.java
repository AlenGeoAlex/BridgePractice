package me.alen_alex.bridgepractice;

import me.Abhigya.core.database.sql.SQL;
import me.alen_alex.bridgepractice.commands.IslandCommand;
import me.alen_alex.bridgepractice.commands.PracticeAdmin;
import me.alen_alex.bridgepractice.configurations.ArenaConfigurations;
import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.configurations.GroupConfiguration;
import me.alen_alex.bridgepractice.data.Data;
import me.alen_alex.bridgepractice.group.GroupManager;
import me.alen_alex.bridgepractice.island.IslandManager;
import me.alen_alex.bridgepractice.listener.PlayerBlockPlaceEvent;
import me.alen_alex.bridgepractice.listener.PlayerJoinEvent;
import me.alen_alex.bridgepractice.listener.PlayerLeaveEvent;
import me.alen_alex.bridgepractice.utility.Validation;
import me.alen_alex.bridgepractice.utility.WorkloadScheduler;
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
        WorkloadScheduler.intializeThread();
        connection = dataConnection.getDatabaseConnection();
        Data.createDatabase();
        if(Configuration.doUseGroups()){
            GroupConfiguration.createGroupConfigurations();
            GroupManager.fetchGroups();
        }
        ArenaConfigurations.createArenaConfigurations();
        IslandManager.fetchIslandsFromFile();
        registerListener();
        registerCommands();
    }

    @Override
    public void onDisable() {
    }

    public void registerCommands(){
        getCommand("practiceadmin").setExecutor(new PracticeAdmin());
        getCommand("island").setExecutor(new IslandCommand());
        getCommand("island").setTabCompleter(new IslandCommand());
    }
    public void registerListener(){
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockPlaceEvent(), this);
    }

    public static BridgePractice getPlugin() {
        return plugin;
    }

    public static SQL getConnection() {
        return connection;
    }

}

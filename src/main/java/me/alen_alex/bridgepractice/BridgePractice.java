package me.alen_alex.bridgepractice;

import me.Abhigya.core.database.sql.SQL;
import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.menu.ItemMenu;
import me.Abhigya.core.menu.size.ItemMenuSize;
import me.Abhigya.core.version.CoreVersion;
import me.alen_alex.bridgepractice.commands.IslandCommand;
import me.alen_alex.bridgepractice.commands.PlayerListCommand;
import me.alen_alex.bridgepractice.commands.PracticeAdmin;
import me.alen_alex.bridgepractice.configurations.ArenaConfigurations;
import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.configurations.GroupConfiguration;
import me.alen_alex.bridgepractice.data.Data;
import me.alen_alex.bridgepractice.group.GroupManager;
import me.alen_alex.bridgepractice.island.IslandManager;
import me.alen_alex.bridgepractice.listener.*;
import me.alen_alex.bridgepractice.placeholderapi.PlaceholderAPI;
import me.alen_alex.bridgepractice.utility.Messages;
import me.alen_alex.bridgepractice.utility.Validation;
import me.alen_alex.bridgepractice.utility.WorkloadScheduler;
import org.bukkit.plugin.java.JavaPlugin;

public final class BridgePractice extends JavaPlugin {

    private static BridgePractice plugin;
    private static SQL connection;
    private static ItemMenu materialMenu,spectatorMenu;
    @Override
    public void onEnable() {
        if(!Validation.ValidateCoreAPI()){
            this.getLogger().severe("=================================================================");
            this.getLogger().info("");
            this.getLogger().info("This plugin requires CoreAPI version - 1.1.2");
            this.getLogger().info("Newer versions or older version won't be working for it");
            this.getLogger().info("You can download one at");
            this.getLogger().info("https://github.com/AbhigyaKrishna/CoreAPI/tree/1.1.2");
            this.getLogger().info("");
            this.getLogger().severe("Disabling plugin");
            this.getLogger().severe("=================================================================");
            this.getPluginLoader().disablePlugin(this);
        }
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
        //BlockConfiguration.createBlockConfigurations();
        //Blocks.fetchMaterialData();
        new PlaceholderAPI(this).register();
        registerListener();
        registerCommands();
        registerMenus();
    }

    @Override
    public void onDisable() {
        Data.disconnect();
    }

    public void registerCommands(){
        getCommand("practiceadmin").setExecutor(new PracticeAdmin());
        getCommand("island").setExecutor(new IslandCommand());
        getCommand("island").setTabCompleter(new IslandCommand());
        getCommand("playerlist").setExecutor(new PlayerListCommand());
    }
    public void registerListener(){
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockPlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockBreakEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageEvent(), this);
    }

    public void registerMenus(){
        materialMenu = new ItemMenu(Messages.parseColor("&c&lMaterial Preference"), ItemMenuSize.TWO_LINE,null,null);
        materialMenu.registerListener(this);
        spectatorMenu = new ItemMenu(Messages.parseColor("&d&lChoose Player"),ItemMenuSize.SIX_LINE,null,null);
        spectatorMenu.registerListener(this);
    }

    public static BridgePractice getPlugin() {
        return plugin;
    }

    public static SQL getConnection() {
        return connection;
    }

    public static ItemMenu getMaterialMenu() {
        return materialMenu;
    }

    public static ItemMenu getSpectatorMenu() {
        return spectatorMenu;
    }
}

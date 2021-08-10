package me.alen_alex.bridgepractice;

import me.Abhigya.core.database.sql.SQL;
import me.Abhigya.core.menu.ItemMenu;
import me.Abhigya.core.menu.size.ItemMenuSize;
import me.Abhigya.core.particle.ParticleEffect;
import me.alen_alex.bridgepractice.commands.*;
import me.alen_alex.bridgepractice.configurations.ArenaConfigurations;
import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.configurations.GroupConfiguration;
import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.data.Data;
import me.alen_alex.bridgepractice.group.GroupManager;
import me.alen_alex.bridgepractice.holograms.HolographicManager;
import me.alen_alex.bridgepractice.island.IslandManager;
import me.alen_alex.bridgepractice.leaderboards.LeaderboardManager;
import me.alen_alex.bridgepractice.listener.*;
import me.alen_alex.bridgepractice.placeholderapi.PlaceholderAPI;
import me.alen_alex.bridgepractice.utility.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.Random;

public final class BridgePractice extends JavaPlugin {

    private static BridgePractice plugin;
    private static SQL connection;
    private static ItemMenu materialMenu,spectatorMenu,timerMenu,particleMenu,fireworkMenu,replayMenu;
    private static boolean hologramsEnabled = false, advanceReplayEnabled = false;
    /*private static ReplayAPI replayAPIInstance;
    private static IReplayHook replayHook;*/
    private static Random randomInstance = new Random();
    private ResetUtility worldHandler = new ResetUtility();


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
        MessageConfiguration.createLangaugeFile();
        Validation.checkLobbyLocation();
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
        if(connection == null)
        {
            getLogger().severe("Error while connecting to database. DISABLING PLUGIN");
            getServer().getPluginManager().disablePlugin(this);
        }
        Data.createDatabase();
        if(Configuration.doUseGroups()){
            GroupConfiguration.createGroupConfigurations();
            GroupManager.fetchGroups();
        }
        ArenaConfigurations.createArenaConfigurations();
        IslandManager.fetchIslandsFromFile();
        if(Validation.ValidatePlaceholderAPI()) {
            getLogger().info("Hooked with PlaceholderAPI");
            new PlaceholderAPI(this).register();
            LeaderboardManager.runRefresh();
            LeaderboardManager.checkLeaderboard();
        }
        if(Configuration.isUseHolograms()) {
            if (Validation.validateHolograms()) {
                getLogger().info("Hooked with HolographicDisplays-API");
                hologramsEnabled = true;
                HolographicManager.fetchHologramsFromIslands();
            }
        }
        if(Validation.isAdvancedReplayEnabled()){
            advanceReplayEnabled = true;
            getLogger().info("Hooked with AdvancedReply-API");
            getServer().getPluginManager().registerEvents(new PlayerReplaySessionFinishEvent(), this);
            getCommand("sessionreplay").setExecutor(new SessionReplay());
            getCommand("sessionreplay").setTabCompleter(new SessionReplay());
        }
        PlayerParticles.loadAllAvailableEffectToCache();
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
        getCommand("playerlist").setTabCompleter(new PlayerListCommand());
        getCommand("timer").setExecutor(new Timer());
        getCommand("effect").setExecutor(new Particle());
        getCommand("firework").setExecutor(new Firework());
        getCommand("resetisland").setExecutor(new ResetIsland());
        getCommand("resetisland").setTabCompleter(new ResetIsland());

    }
    public void registerListener(){
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockPlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockBreakEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerInventoryClickEvent(), this);
    }

    public void registerMenus(){
        materialMenu = new ItemMenu(Messages.parseColor("&c&lMaterial Preference"), ItemMenuSize.TWO_LINE,null,null);
        materialMenu.registerListener(this);
        spectatorMenu = new ItemMenu(Messages.parseColor("&d&lChoose Player"),ItemMenuSize.SIX_LINE,null,null);
        spectatorMenu.registerListener(this);
        timerMenu = new ItemMenu(Messages.parseColor("&b&lChoose timer"),ItemMenuSize.ONE_LINE,null,null);
        timerMenu.registerListener(this);
        particleMenu = new ItemMenu(Messages.parseColor("&e&lChoose your particle?"),ItemMenuSize.FIVE_LINE,null,null);
        particleMenu.registerListener(this);
        fireworkMenu = new ItemMenu(Messages.parseColor("&e&lChoose your firework"),ItemMenuSize.ONE_LINE,null,null);
        fireworkMenu.registerListener(this);
        replayMenu = new ItemMenu(Messages.parseColor("&e&lChoose your reply"), ItemMenuSize.SIX_LINE, null, null);
        replayMenu.registerListener(this);
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

    public static ItemMenu getTimerMenu() {
        return timerMenu;
    }

    public static ItemMenu getParticleMenu() {
        return particleMenu;
    }

    public static ItemMenu getFireworkMenu() {
        return fireworkMenu;
    }

    public static ItemMenu getReplayMenu() {
        return replayMenu;
    }

    public static Random getRandomInstance() {
        return randomInstance;
    }

    public ResetUtility getWorldHandler() {
        return worldHandler;
    }

    public static boolean isHologramsEnabled() {
        return hologramsEnabled;
    }

    public static boolean isAdvanceReplayEnabled() {
        return advanceReplayEnabled;
    }



}

package me.alen_alex.bridgepractice;

import me.Abhigya.core.database.sql.SQL;
import me.Abhigya.core.menu.inventory.ItemMenu;
import me.Abhigya.core.menu.inventory.size.ItemMenuSize;
import me.alen_alex.bridgepractice.citizens.listener.CitizenEnableEvent;
import me.alen_alex.bridgepractice.commands.*;
import me.alen_alex.bridgepractice.configurations.ArenaConfigurations;
import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.configurations.GroupConfiguration;
import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.data.Data;
import me.alen_alex.bridgepractice.data.DataManager;
import me.alen_alex.bridgepractice.group.GroupManager;
import me.alen_alex.bridgepractice.holograms.HolographicManager;
import me.alen_alex.bridgepractice.island.IslandManager;
import me.alen_alex.bridgepractice.leaderboards.LeaderboardManager;
import me.alen_alex.bridgepractice.listener.*;
import me.alen_alex.bridgepractice.placeholderapi.PlaceholderAPI;
import me.alen_alex.bridgepractice.utility.*;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Random;

public final class BridgePractice extends JavaPlugin {

    private static BridgePractice plugin;
    private static SQL connection;
    private static ItemMenu materialMenu,spectatorMenu,timerMenu,particleMenu,fireworkMenu,replayMenu;
    private static boolean hologramsEnabled = false, advanceReplayEnabled = false,citizensEnabled = false,citizensLoadedNPC=false,vaultEnabled = false;
    private static final Random randomInstance = new Random();
    private final ResetUtility worldHandler = new ResetUtility();
    private static NPCRegistry citizensRegistry;
    private static Data dataConnection;
    private static Economy vaultEconomy;
    @Override
    public void onEnable() {
        if(!Validation.ValidateCoreAPI()){
            this.getLogger().severe("=================================================================");
            this.getLogger().info("");
            this.getLogger().info("This plugin requires CoreAPI version - 1.2.1");
            this.getLogger().info("Newer versions or older version won't be working for it");
            this.getLogger().info("You can download one at");
            //TODO Update Once he updates!
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
                plugin.getLogger().severe("Database details is invalid");
                plugin.getLogger().severe("Disabling plugin!");
                plugin.getPluginLoader().disablePlugin(this);
                return;
            }
        dataConnection = new Data();
        WorkloadScheduler.intializeThread();
        connection = Data.getDatabaseConnection();
        try {
            if(connection == null || connection.getConnection().isClosed())
            {
                getLogger().severe("Error while connecting to database. DISABLING PLUGIN");
                getServer().getPluginManager().disablePlugin(this);
            }
        } catch (SQLException e) {
            DataManager.setDatabaseOnline(false);
            e.printStackTrace();
        }
        DataManager.setDatabaseOnline(true);
        Data.createDatabase();
        if(Configuration.isHookCitizensEnabled()) {
            if (Validation.validateCitizens()) {
                citizensRegistry = CitizensAPI.getNPCRegistry();
                if (citizensRegistry == null)
                    Bukkit.getLogger().severe("There was an issue hooking with Citizens API");
                else {
                    getLogger().info("Hooked with CitizensAPI");
                    getServer().getPluginManager().registerEvents(new CitizenEnableEvent(), this);
                    citizensEnabled = true;
                }
            }
        }
        if(Configuration.doUseGroups()){
            GroupConfiguration.createGroupConfigurations();
            GroupManager.fetchGroups();
        }
        ArenaConfigurations.createArenaConfigurations();
        IslandManager.fetchIslandsFromFile();
        if(Configuration.isHookPlaceholderAPI()) {
            if (Validation.ValidatePlaceholderAPI()) {
                getLogger().info("Hooked with PlaceholderAPI");
                new PlaceholderAPI(this).register();
                LeaderboardManager.runRefresh();
                LeaderboardManager.checkLeaderboard();
            }
        }
        if(Configuration.isHookHologramsEnabled()) {
                if (Validation.validateHolograms()) {
                    getLogger().info("Hooked with HolographicDisplays-API");
                    hologramsEnabled = true;
                    HolographicManager.fetchHologramsFromIslands();
                }
        }
        if(Configuration.isHookAdvancedReplayEnabled()) {
            if (Validation.isAdvancedReplayEnabled()) {
                advanceReplayEnabled = true;
                getLogger().info("Hooked with AdvancedReply-API");
                getLogger().info("NOTE: This Hook can sometimes lag your server. Use this on hook your own risk");
                getServer().getPluginManager().registerEvents(new PlayerReplaySessionFinishEvent(), this);
                getCommand("sessionreplay").setExecutor(new SessionReplay());
                getCommand("sessionreplay").setTabCompleter(new SessionReplay());
            }
        }
        if(Configuration.isHookVaultAPI()){
            if(Validation.validateVaultAPI()){
                RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
                if(rsp != null){
                    vaultEconomy = rsp.getProvider();
                    vaultEnabled = true;
                    getLogger().info("Hooked with Vault-API");
                }
            }
        }

        PlayerParticles.loadAllAvailableEffectToCache();
        registerListener();
        registerCommands();
        registerMenus();
    }

    @Override
    public void onDisable() {
        try {
            if(!connection.getConnection().isClosed()) {
                Data.disconnect();
                getLogger().info("Database has been disconnected!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getLogger().info("Disabling plugin!");

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
        getCommand("block").setExecutor(new Block());

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
        getServer().getPluginManager().registerEvents(new PlayerCraftItemEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnEvent(), this);
        if(Configuration.isDisableItemDrop())
            getServer().getPluginManager().registerEvents(new PlayerItemDropEvent(), this);
        if(Configuration.isDisableItempickup())
            getServer().getPluginManager().registerEvents(new PlayerItemPickupEvent(), this);
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

    public static boolean isCitizensEnabled() {
        return citizensEnabled;
    }

    public static NPCRegistry getCitizensRegistry() {
        return citizensRegistry;
    }

    public static boolean isCitizensLoadedNPC() {
        return citizensLoadedNPC;
    }

    public static void setCitizensLoadedNPC(boolean citizensLoadedNPC) {
        BridgePractice.citizensLoadedNPC = citizensLoadedNPC;
    }

    public static boolean isVaultEnabled() {
        return vaultEnabled;
    }

    public static Economy getVaultEconomy() {
        return vaultEconomy;
    }
}

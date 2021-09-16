package me.alen_alex.bridgepractice.configurations;

import de.leonhard.storage.Config;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import me.alen_alex.bridgepractice.BridgePractice;
import net.md_5.bungee.api.ChatColor;

import java.io.File;
import java.util.List;

public class Configuration {

    private static BridgePractice plugin = BridgePractice.getPlugin();
    private static Config config;
    private static String host,username,password,port,database;
    private static boolean useMysql,useSSL, useGroups, voidDectionOnlyOnIslands, broadcastNewRecord,tpBacktoLobbyAfterReplay;
    private static String prefixMain,lobbyLocation;
    private static int voidDetectionLevel,hologramsOffsetY,leaderboardRefreshTimeout,clearPlayerJoinDelay;
    private static List<String> hologramsStartingLines,hologramsEndingLines;
    private static List<String> blacklistedBlocks;
    private static boolean blacklistOnlyWhilePlaying;
    private static boolean disableItemDrop, itemDropOnlyOnSession, disableItempickup, itemPickupOnlyOnSession, clearPlayerOnJoinEnabled,spawnOnJoinEnabled;
    private static boolean hookCitizensEnabled,hookHologramsEnabled,hookAdvancedReplayEnabled,hookPlaceholderAPI,hookVaultAPI;

    public static void createConfiguration(){
            File configFile = new File(plugin.getDataFolder(),"config.yml");

            if(!plugin.getDataFolder().exists())
                plugin.getDataFolder().mkdirs();

            if(!configFile.exists()){
                plugin.getLogger().info("Generating configuration file.");
                Config createConfig = LightningBuilder
                        .fromFile(configFile)
                        .addInputStream(plugin.getResource("config.yml"))
                        .setDataType(DataType.SORTED)
                        .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                        .setReloadSettings(ReloadSettings.MANUALLY)
                        .createConfig();
            }

            config = new Config("config.yml","plugins/BridgePractice");
            plugin.getLogger().info("Configuration has been located");
            config.setDefault("version",BridgePractice.getPlugin().getDescription().getVersion());
            loadConfiguration();
        }

    private static void loadConfiguration(){
        prefixMain = config.getString("prefix");
        host = config.getString("server.host");
        port = config.getString("server.port");
        username = config.getString("server.username");
        password = config.getString("server.password");
        database = config.getString("server.database");
        useMysql = config.getBoolean("server.use-mysql");
        useSSL = config.getBoolean("server.usessl");
        useGroups = config.getBoolean("enable-groups");
        voidDetectionLevel = config.getInt("void-detection.level");
        voidDectionOnlyOnIslands = config.getBoolean("void-detection.only-when-on-island");
        broadcastNewRecord = config.getBoolean("broadcast-new-record");
        hologramsOffsetY = config.getInt("holograms.hologramoffset-Y");
        hologramsStartingLines = config.getStringList("holograms.startingLocation");
        hologramsEndingLines = config.getStringList("holograms.endingLocation");
        lobbyLocation = config.getString("settings.lobby-location");
        leaderboardRefreshTimeout = config.getInt("settings.leaderboard-refresh-mins");
        tpBacktoLobbyAfterReplay = config.getBoolean("settings.teleport-to-lobby-after-replay-ends");
        blacklistedBlocks = config.getStringList("blacklist.material");
        disableItemDrop = config.getBoolean("settings.item-drop.disable-item-drop");
        itemDropOnlyOnSession = config.getBoolean("settings.item-drop.only-check-while-playing");
        disableItempickup = config.getBoolean("settings.item-pickup.disable-item-pickup");
        itemPickupOnlyOnSession = config.getBoolean("settings.item-pickup.only-check-while-playing");
        clearPlayerOnJoinEnabled = config.getBoolean("settings.clear-player-inventory-on-join.enable");
        clearPlayerJoinDelay = config.getInt("settings.clear-player-inventory-on-join.delay");
        spawnOnJoinEnabled = config.getBoolean("settings.spawn-on-join");
        hookCitizensEnabled = config.getBoolean("hook.citizens-api");
        hookHologramsEnabled = config.getBoolean("hook.holographicdisplays-api");
        hookPlaceholderAPI = config.getBoolean("hook.placeholder-api");
        hookAdvancedReplayEnabled = config.getBoolean("hook.advancedreplay-api");
        hookVaultAPI = config.getBoolean("hook.vault-api");
        blacklistOnlyWhilePlaying = config.getBoolean("blacklist.only-while-playing");
        plugin.getLogger().info("Configuration has been loaded");
    }

    public static Config getConfig() {
        return config;
    }

    public static String getHost() {
        return host;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getPort() {
        return port;
    }

    public static String getDatabase() {
        return database;
    }

    public static boolean isUseMysql() {
        return useMysql;
    }

    public static boolean isUseSSL() {
        return useSSL;
    }

    public static String getPrefixMain() {
        return ChatColor.translateAlternateColorCodes('&',prefixMain);
    }

    public static boolean doUseGroups() {
        return useGroups;
    }

    public static boolean isVoidDectionOnlyOnIslands() {
        return voidDectionOnlyOnIslands;
    }

    public static int getVoidDetectionLevel() {
        return voidDetectionLevel;
    }

    public static boolean doBroadcastNewRecord() {
        return broadcastNewRecord;
    }

    public static int getHologramsOffsetY() {
        return hologramsOffsetY;
    }

    public static List<String> getHologramsStartingLines() {
        return hologramsStartingLines;
    }

    public static List<String> getHologramsEndingLines() {
        return hologramsEndingLines;
    }

    public static String getLobbyLocation() {
        return lobbyLocation;
    }

    public static int getLeaderboardRefreshTimeout() {
        return leaderboardRefreshTimeout;
    }

    public static boolean isTpBacktoLobbyAfterReplay() {
        return tpBacktoLobbyAfterReplay;
    }

    public static List<String> getBlacklistedBlocks() {
        return blacklistedBlocks;
    }

    public static boolean isDisableItemDrop() {
        return disableItemDrop;
    }

    public static boolean isItemDropOnlyOnSession() {
        return itemDropOnlyOnSession;
    }

    public static boolean isDisableItempickup() {
        return disableItempickup;
    }

    public static boolean isItemPickupOnlyOnSession() {
        return itemPickupOnlyOnSession;
    }

    public static int getClearPlayerJoinDelay() {
        return clearPlayerJoinDelay;
    }

    public static boolean isClearPlayerOnJoinEnabled() {
        return clearPlayerOnJoinEnabled;
    }

    public static boolean isSpawnOnJoinEnabled() {
        return spawnOnJoinEnabled;
    }

    public static boolean isHookCitizensEnabled() {
        return hookCitizensEnabled;
    }

    public static boolean isHookHologramsEnabled() {
        return hookHologramsEnabled;
    }

    public static boolean isHookAdvancedReplayEnabled() {
        return hookAdvancedReplayEnabled;
    }

    public static boolean isHookPlaceholderAPI() {
        return hookPlaceholderAPI;
    }

    public static boolean isHookVaultAPI() {
        return hookVaultAPI;
    }

    public static boolean isBlacklistOnlyWhilePlaying() {
        return blacklistOnlyWhilePlaying;
    }
}



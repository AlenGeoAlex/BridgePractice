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
    private static boolean useMysql,useSSL, useGroups, voidDectionOnlyOnIslands, broadcastNewRecord,useHolograms;
    private static String prefixMain;
    private static int voidDetectionLevel,hologramsOffsetY;
    private static List<String> hologramsStartingLines,hologramsEndingLines;

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
            config.setDefault("version","1.0");
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
        useHolograms = config.getBoolean("holograms.enabled");
        hologramsOffsetY = config.getInt("holograms.hologramoffset-Y");
        hologramsStartingLines = config.getStringList("holograms.startingLocation");
        hologramsEndingLines = config.getStringList("holograms.endingLocation");
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

    public static boolean isUseHolograms() {
        return useHolograms;
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
}



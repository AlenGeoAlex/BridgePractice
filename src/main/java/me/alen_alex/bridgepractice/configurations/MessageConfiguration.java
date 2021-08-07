package me.alen_alex.bridgepractice.configurations;

import de.leonhard.storage.Yaml;
import me.alen_alex.bridgepractice.BridgePractice;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessageConfiguration {


    private static BridgePractice plugin = BridgePractice.getPlugin();
    private static YamlConfiguration messageConfigurations;
    private static final File languageFolder = new File(plugin.getDataFolder()+File.separator+"Language");
    private static final File languageFile = new File(plugin.getDataFolder()+File.separator+"Language","messages.yml");
    //-------------------------------------------------------------------------------------------------------------------------------
    private static String noPermission,unknownCommand,wrongUsageAdmin,wrongUsagePlayers,fromConsole;
    private static String playerDataError,alreadyHaveIsland,cannotWhileSpecing,noFreeIslands,inSession,foundIslandPL,playerLeftIsland,timerStarted;
    private static String lessTimerValue,timerSetPL,setbackEnabled,setBackDisabled,timerCleared,noActiveTimer;
    private static String spectatorPlayerLeft,spectatorPlayerOffline;
    private static String placeholderRefreshing,placeholderNA,placeholderUnknownIsland,placeholderUnknownGroup;
    //-------------------------------------------------------------------------------------------------------------------------------
    public static void createLangaugeFile(){
        if(!languageFolder.exists())
            languageFolder.mkdirs();

        if(!languageFile.exists()){
            plugin.getLogger().info("Seems like messages are missing, Generating one!");
                //languageFile.createNewFile();
                new Yaml("messages.yml","plugins/BridgePractice/Language",BridgePractice.getPlugin().getResource("messages.yml"));
                plugin.getLogger().info("Messages has been successfully generated");

        }
        messageConfigurations = YamlConfiguration.loadConfiguration(languageFile);
        loadMessages();
    }

    private static void loadMessages(){
        if(messageConfigurations == null){
            createLangaugeFile();
        }

        noPermission = messageConfigurations.getString("messages.errors.no-permissions");
        unknownCommand = messageConfigurations.getString("messages.errors.unknown-command");
        wrongUsageAdmin =  messageConfigurations.getString("messages.errors.wrong-usage-admin");
        wrongUsagePlayers = messageConfigurations.getString("messages.errors.wrong-usage-player");
        fromConsole = messageConfigurations.getString("messages.errors.cannot-execute-from-console");
        playerDataError = messageConfigurations.getString("messages.player-messages.unknown-player-data-rejoin-message");
        alreadyHaveIsland = messageConfigurations.getString("messages.player-messages.already-have-an-island");
        cannotWhileSpecing = messageConfigurations.getString("messages.player-messages.cannot-interact-while-specing");
        noFreeIslands= messageConfigurations.getString("messages.player-messages.no-free-islands");
        inSession = messageConfigurations.getString("messages.player-messages.cannot-interact-while-in-practice");
        foundIslandPL = messageConfigurations.getString("messages.player-messages.player-island-found");
        playerLeftIsland = messageConfigurations.getString("messages.player-messages.player-left-island");
        timerStarted = messageConfigurations.getString("messages.player-messages.practice-timer-started");
        lessTimerValue = messageConfigurations.getString("messages.timer.cannot-set-timer-less-than");
        timerSetPL = messageConfigurations.getString("messages.timer.timer-has-been-set");
        setbackEnabled = messageConfigurations.getString("messages.timer.timer-setback-enabled");
        setBackDisabled = messageConfigurations.getString("messages.timer.timer-setback-disabled");
        timerCleared = messageConfigurations.getString("messages.timer.cleared-timer");
        noActiveTimer = messageConfigurations.getString("messages.timer.no-active-timer");
        spectatorPlayerLeft = messageConfigurations.getString("messages.spectators.spectating-player-left");
        spectatorPlayerOffline = messageConfigurations.getString("messages.spectators.selected-player-is-offline");
        placeholderRefreshing = messageConfigurations.getString("messages.placeholders.refreshing");
        placeholderNA = messageConfigurations.getString("messages.placeholders.not-available");
        placeholderUnknownGroup = messageConfigurations.getString("messages.placeholders.unknown-group");
        placeholderUnknownIsland = messageConfigurations.getString("messages.placeholders.unknown-island");
        messageConfigurations = null;
    }

    public static String getNoPermission() {
        return noPermission;
    }

    public static String getUnknownCommand() {
        return unknownCommand;
    }

    public static String getWrongUsageAdmin() {
        return wrongUsageAdmin;
    }

    public static String getWrongUsagePlayers() {
        return wrongUsagePlayers;
    }

    public static String getFromConsole() {
        return fromConsole;
    }

    public static String getPlayerDataError() {
        return playerDataError;
    }

    public static String getAlreadyHaveIsland() {
        return alreadyHaveIsland;
    }

    public static String getCannotWhileSpecing() {
        return cannotWhileSpecing;
    }

    public static String getNoFreeIslands() {
        return noFreeIslands;
    }

    public static String getInSession() {
        return inSession;
    }

    public static String getFoundIslandPL() {
        return foundIslandPL;
    }

    public static String getPlayerLeftIsland() {
        return playerLeftIsland;
    }

    public static String getTimerStarted() {
        return timerStarted;
    }

    public static String getLessTimerValue() {
        return lessTimerValue;
    }

    public static String getTimerSetPL() {
        return timerSetPL;
    }

    public static String getSetbackEnabled() {
        return setbackEnabled;
    }

    public static String getSetBackDisabled() {
        return setBackDisabled;
    }

    public static String getTimerCleared() {
        return timerCleared;
    }

    public static String getNoActiveTimer() {
        return noActiveTimer;
    }

    public static String getSpectatorPlayerLeft() {
        return spectatorPlayerLeft;
    }

    public static String getSpectatorPlayerOffline() {
        return spectatorPlayerOffline;
    }

    public static String getPlaceholderRefreshing() {
        return placeholderRefreshing;
    }

    public static String getPlaceholderNA() {
        return placeholderNA;
    }

    public static String getPlaceholderUnknownIsland() {
        return placeholderUnknownIsland;
    }

    public static String getPlaceholderUnknownGroup() {
        return placeholderUnknownGroup;
    }
}

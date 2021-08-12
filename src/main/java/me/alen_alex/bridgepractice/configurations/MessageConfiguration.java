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
    private static String playerDataError,alreadyHaveIsland,cannotWhileSpecing,noFreeIslands,inSession,foundIslandPL,playerLeftIsland,timerStarted,cannotWhileReplay,brokeRecordPL,brokeAllTimeHighest,playerCompletedSessionPL,cheatTimeFail,cheatBlockFail,cheatPlacedBlocksFail,playerFellVoid;
    private static String lessTimerValue,timerSetPL,setbackEnabled,setBackDisabled,timerCleared,noActiveTimer;
    private static String spectatorPlayerLeft,spectatorPlayerOffline;
    private static String placeholderRefreshing,placeholderNA,placeholderUnknownIsland,placeholderUnknownGroup,placeholderCurrentlyRecording;
    private static String replayEnable,replayDisable,replayNotExist,replayCleared;
    private static String bbTimerRunning20,bbTimerRunning10,bbTimerRunning5,bbTimerup;
    //-------------------------------------------------------------------------------------------------------------------------------
    public static void createLangaugeFile(){
        if(!languageFolder.exists())
            languageFolder.mkdirs();

        if(!languageFile.exists()){
            plugin.getLogger().info("Seems like messages are missing, Generating one!");
                new Yaml("messages.yml","plugins/BridgePractice/Language",BridgePractice.getPlugin().getResource("messages.yml")).setDefault("version",BridgePractice.getPlugin().getDescription().getVersion());
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
        placeholderCurrentlyRecording = messageConfigurations.getString("messages.placeholders.currently-recording");
        cannotWhileReplay = messageConfigurations.getString("messages.player-messages.cannot-while-watching-replay");
        replayEnable = messageConfigurations.getString("messages.replay.replay-toggle-enable");
        replayDisable = messageConfigurations.getString("messages.replay.replay-toggle-disable");
        replayNotExist = messageConfigurations.getString("messages.replay.replay-not-exist");
        replayCleared = messageConfigurations.getString("messages.replay.replay-cleared");
        bbTimerRunning20 = messageConfigurations.getString("messages.bossbar.timer-onCountdown-20");
        bbTimerRunning10 = messageConfigurations.getString("messages.bossbar.timer-onCountdown-10");
        bbTimerRunning5 = messageConfigurations.getString("messages.bossbar.timer-onCountdown-5");
        bbTimerup = messageConfigurations.getString("messages.bossbar.timer-onTimeup");
        brokeRecordPL = messageConfigurations.getString("messages.player-messages.player-broke-previous-record");
        brokeAllTimeHighest = messageConfigurations.getString("messages.player-messages.player-broke-alltime-best");
        playerCompletedSessionPL = messageConfigurations.getString("messages.player-messages.player-completed");
        cheatTimeFail = messageConfigurations.getString("messages.player-messages.player-cheat-failed-time");
        cheatBlockFail = messageConfigurations.getString("messages.player-messages.player-cheat-failed-blocks");
        cheatPlacedBlocksFail = messageConfigurations.getString("messages.player-messages.player-cheat-failed-blacklisted-blocks");
        playerFellVoid = messageConfigurations.getString("messages.player-messages.player-void-fail");
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

    public static String getPlaceholderCurrentlyRecording() {
        return placeholderCurrentlyRecording;
    }

    public static String getCannotWhileReplay() {
        return cannotWhileReplay;
    }

    public static String getReplayEnable() {
        return replayEnable;
    }

    public static String getReplayDisable() {
        return replayDisable;
    }

    public static String getReplayNotExist() {
        return replayNotExist;
    }

    public static String getReplayCleared() {
        return replayCleared;
    }

    public static String getBbTimerRunning20() {
        return bbTimerRunning20;
    }

    public static String getBbTimerRunning10() {
        return bbTimerRunning10;
    }

    public static String getBbTimerRunning5() {
        return bbTimerRunning5;
    }

    public static String getBbTimerup() {
        return bbTimerup;
    }

    public static String getBrokeRecordPL() {
        return brokeRecordPL;
    }

    public static String getBrokeAllTimeHighest() {
        return brokeAllTimeHighest;
    }

    public static String getPlayerCompletedSessionPL() {
        return playerCompletedSessionPL;
    }

    public static String getCheatBlockFail() {
        return cheatBlockFail;
    }

    public static String getCheatTimeFail() {
        return cheatTimeFail;
    }

    public static String getCheatPlacedBlocksFail() {
        return cheatPlacedBlocksFail;
    }

    public static String getPlayerFellVoid() {
        return playerFellVoid;
    }
}

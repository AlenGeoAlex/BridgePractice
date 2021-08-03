package me.alen_alex.bridgepractice.placeholderapi;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.utility.Messages;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPI extends PlaceholderExpansion {

    private BridgePractice plugin;
    private static final String ERROR = Messages.parseColor("&c&lError");
    public PlaceholderAPI(BridgePractice plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean persist(){
        return true;
    }


    @Override
    public @NotNull String getIdentifier() {
        return "bp";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Alen_Alex";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if(identifier.equalsIgnoreCase("playerisland"))
            return PlaceholderDataManager.getPlayerIslandName(player);


        if(identifier.equalsIgnoreCase("currenttime"))
            return PlaceholderDataManager.getPlayerCurrentTime(player);

        if(identifier.equalsIgnoreCase("blocksplaced"))
            return PlaceholderDataManager.getBlocksPlacedOnCurrentGame(player);

        if(identifier.equalsIgnoreCase("playerstate"))
            return PlaceholderDataManager.getPlayerCurrentState(player);


        if(identifier.equalsIgnoreCase("alltimebest"))
            return PlaceholderDataManager.getAllTimeBestPlayer(player);


        if(identifier.equalsIgnoreCase("alltimeblocksplaced"))
            return PlaceholderDataManager.getAllTimeBlocksPlaced(player);

        if(identifier.equalsIgnoreCase("gamesplayed"))
            return PlaceholderDataManager.getGamesPlayed(player);

        if(identifier.equalsIgnoreCase("isspectator"))
            return PlaceholderDataManager.isPlayerSpectating(player);

        if(identifier.equalsIgnoreCase("getspectatingplayer"))
            return PlaceholderDataManager.getPlayerSpectating(player);

        if(identifier.equalsIgnoreCase("getspectatingisland"))
            return PlaceholderDataManager.getSpectatingIsland(player);

        if(identifier.equalsIgnoreCase("runningtimer"))
            return PlaceholderDataManager.getRunningTimer(player);

        if(identifier.startsWith("group")){
            String[] args = identifier.split("_");
            if(args.length <= 2)
                return PlaceholderDataManager.getIslandGroup(args[1]);
            else
                return ERROR;
        }

        if(identifier.startsWith("islandplayer")){
            String[] args = identifier.split("_");
            if(args.length <= 2)
                return PlaceholderDataManager.getIslandPlayer(args[1]);
            else
                return ERROR;
        }

        return null;
    }
}

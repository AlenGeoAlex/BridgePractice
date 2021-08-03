package me.alen_alex.bridgepractice.placeholderapi;

import me.alen_alex.bridgepractice.BridgePractice;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPI extends PlaceholderExpansion {

    private BridgePractice plugin;

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
}

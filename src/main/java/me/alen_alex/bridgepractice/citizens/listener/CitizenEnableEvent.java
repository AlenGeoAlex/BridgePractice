package me.alen_alex.bridgepractice.citizens.listener;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.leaderboards.LeaderboardManager;
import net.citizensnpcs.api.event.CitizensEnableEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CitizenEnableEvent implements Listener {

    @EventHandler
    public void onCitizenEnableEvent(CitizensEnableEvent event){
        if(!BridgePractice.isCitizensEnabled())
            return;
        BridgePractice.getPlugin().getLogger().info("Citizens has finished loading NPC's");
        BridgePractice.getPlugin().getLogger().info("Starting NPC Hooking!");
        BridgePractice.setCitizensLoadedNPC(true);
        LeaderboardManager.connectAllNPC();
    }


}

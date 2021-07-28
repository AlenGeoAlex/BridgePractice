package me.alen_alex.bridgepractice.utility;

import me.Abhigya.core.util.acionbar.ActionBarUtils;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.game.Gameplay;
import me.alen_alex.bridgepractice.playerdata.PlayerData;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Countdown extends BukkitRunnable {

    Player player;
    UUID playerUUID;
    int time;

    public Countdown(Player player){
        this.player = player;
        time = 0;
        playerUUID = player.getUniqueId();
    }

    @Override
    public void run() {
        if(!PlayerDataManager.getCachedPlayerData().containsKey(this.playerUUID)){
            cancelTimer(this.playerUUID);
            return;
        }
        if(PlayerDataManager.getCachedPlayerData().get(this.playerUUID).getCurrentState() == PlayerState.PLAYING){
            ActionBarUtils.send(player,Messages.parseColor("&c&lTimer &7» &b"+String.format("%02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60)));
            time+=1;
        }else
            cancelTimer(this.playerUUID);
    }

    public static void cancelTimer(UUID uuid){
        if(Gameplay.getPlayerCountdown().containsKey(uuid)){
            Bukkit.getScheduler().cancelTask(Gameplay.getPlayerCountdown().get(uuid));
            Gameplay.getPlayerCountdown().remove(uuid);
        }
    }

}

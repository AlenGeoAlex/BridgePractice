package me.alen_alex.bridgepractice.utility;

import me.Abhigya.core.util.acionbar.ActionBarUtils;
import me.Abhigya.core.util.bossbar.BossBar;
import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.configurations.MessageConfiguration;
import me.alen_alex.bridgepractice.enumerators.PlayerState;
import me.alen_alex.bridgepractice.game.GameplayHandler;
import me.alen_alex.bridgepractice.playerdata.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class CountdownTask extends BukkitRunnable {

    Player player;
    UUID playerUUID;
    int time;
    final int checkTimer;
    double remaining;
    BossBar bossBar;
    public CountdownTask(Player player, int checkTimer){
        this.player = player;
        this.time = 0;
        this.playerUUID =player.getUniqueId();

        if(checkTimer>20) {
            this.checkTimer = checkTimer;
            this.bossBar = BossBar.createBossBar(player, Messages.parseColor("&a&lTime Remaining&7 : &f&l"+checkTimer+" secs"),1.0);
            this.bossBar.hide();
        }else
            this.checkTimer = -1;
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
            if(remaining > -1) {
                remaining = checkTimer - time;
                if (remaining <= 20 && remaining > 10) {
                    if(bossBar != null) {
                        this.bossBar.show();
                        this.bossBar.setTitle(Messages.parseColor(MessageConfiguration.getBbTimerRunning20().replaceAll("%remaining%",String.valueOf(remaining))));
                    }
                } else if (remaining <= 10 && remaining > 5) {
                    if(bossBar != null) {
                        this.bossBar.setTitle(Messages.parseColor(MessageConfiguration.getBbTimerRunning10().replaceAll("%remaining%",String.valueOf(remaining))));
                        this.bossBar.setProgress(0.7);
                    }
                } else if (remaining <= 5 && remaining >= 1) {
                    if(bossBar != null) {
                        this.bossBar.setTitle(Messages.parseColor(MessageConfiguration.getBbTimerRunning5().replaceAll("%remaining%",String.valueOf(remaining))));
                        this.bossBar.setProgress(0.4);
                    }
                } else if (remaining == 1) {
                    if(bossBar != null) {
                        this.bossBar.setTitle(Messages.parseColor(MessageConfiguration.getBbTimerup()));
                        this.bossBar.setProgress(0.0);
                    }
                }else if (remaining == 0) {
                    this.bossBar.hide();
                    if(PlayerDataManager.getCachedPlayerData().get(playerUUID).isSetbackEnabled())
                        BridgePractice.getGameplayHandler().handleGameEnd(player,false);
                }
            }
        }else
            cancelTimer(this.playerUUID);
    }

    public void cancelTimer(UUID uuid){
        if(BridgePractice.getGameplayHandler().getPlayerCountdown().containsKey(uuid)){
            Bukkit.getScheduler().cancelTask(BridgePractice.getGameplayHandler().getPlayerCountdown().get(uuid));
            if(bossBar != null && bossBar.getPlayer().isOnline())
            this.bossBar.hide();
            BridgePractice.getGameplayHandler().getPlayerCountdown().remove(uuid);
        }
    }

}

package me.alen_alex.bridgepractice.utility;

import me.Abhigya.core.util.acionbar.ActionBarUtils;
import me.Abhigya.core.util.bossbar.BarColor;
import me.Abhigya.core.util.bossbar.BarFlag;
import me.Abhigya.core.util.bossbar.BarStyle;
import me.Abhigya.core.util.bossbar.BossBar;
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
    final int checkTimer;
    double remaining;
    BossBar bossBar;
    public Countdown(Player player, int checkTimer){
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
                        this.bossBar.setTitle(Messages.parseColor("&a&lTime Remaining&7 : &f&l" + remaining + " secs"));
                    }
                } else if (remaining <= 10 && remaining > 5) {
                    if(bossBar != null) {
                        this.bossBar.setTitle(Messages.parseColor("&e&lTime Remaining&7 : &f&l" + remaining + " secs"));
                        this.bossBar.setProgress(0.7);
                    }
                } else if (remaining <= 5 && remaining >= 1) {
                    if(bossBar != null) {
                        this.bossBar.setTitle(Messages.parseColor("&c&lTime Remaining&7 : &f&l" + remaining + " secs"));
                        this.bossBar.setProgress(0.4);
                    }
                } else if (remaining == 1) {
                    if(bossBar != null) {
                        this.bossBar.setTitle(Messages.parseColor("&4&lTime Up!"));
                        this.bossBar.setProgress(0.0);
                    }
                }else if (remaining == 0) {
                    this.bossBar.hide();
                }
            }
        }else
            cancelTimer(this.playerUUID);
    }

    public void cancelTimer(UUID uuid){
        if(Gameplay.getPlayerCountdown().containsKey(uuid)){
            Bukkit.getScheduler().cancelTask(Gameplay.getPlayerCountdown().get(uuid));
            if(bossBar != null)
            this.bossBar.hide();
            Gameplay.getPlayerCountdown().remove(uuid);
        }
    }

}

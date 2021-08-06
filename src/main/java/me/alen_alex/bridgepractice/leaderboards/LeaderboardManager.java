package me.alen_alex.bridgepractice.leaderboards;

import me.alen_alex.bridgepractice.BridgePractice;
import me.alen_alex.bridgepractice.configurations.Configuration;
import me.alen_alex.bridgepractice.group.Group;
import me.alen_alex.bridgepractice.group.GroupManager;
import me.alen_alex.bridgepractice.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Map;

public class LeaderboardManager {
    private static int timer = 0;
    private static final int refreshTimeout = Configuration.getLeaderboardRefreshTimeout()*60;


    public static void checkLeaderboard(){
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(BridgePractice.getPlugin(), new Runnable() {
            @Override
            public void run() {
                timer+=1;
                if(timer>=refreshTimeout){
                    long start = System.currentTimeMillis();
                    runRefresh();
                    long end = System.currentTimeMillis();
                    Bukkit.getLogger().info("Leaderboards has been refreshed. It took "+(end-start)+" ms to complete!");
                    timer = 0;
                }
            }
        },100,20);
    }

    public static int getRemainingTime() {
        return refreshTimeout - timer;
    }



    public static boolean isRefreshing(){
        return timer >= refreshTimeout;
    }

    public static void runRefresh(){
        for(Map.Entry<String, Group> groupEntry : GroupManager.getCachedGroups().entrySet()){
            groupEntry.getValue().updateLeaderboard();
        }
    }

}

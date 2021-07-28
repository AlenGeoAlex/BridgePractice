package me.alen_alex.bridgepractice.utility;

import me.Abhigya.core.util.scheduler.SchedulerUtils;
import me.Abhigya.core.util.tasks.WorkloadDistributor;
import me.Abhigya.core.util.tasks.WorkloadThread;
import me.alen_alex.bridgepractice.BridgePractice;
import org.bukkit.scheduler.BukkitTask;

public class WorkloadScheduler {

    private  final static WorkloadDistributor distributor = new WorkloadDistributor();
    private static WorkloadThread syncThread;

    public static void intializeThread() {
        syncThread = distributor.createThread(220000L);
        BukkitTask syncTask;
        syncTask = SchedulerUtils.runTaskTimer(syncThread, 1L, 1L, BridgePractice.getPlugin());
    }

    public static WorkloadThread getSyncThread() {
        return syncThread;
    }
}

package me.alen_alex.bridgepractice.utility;

//Cloned from JeffLib
//https://github.com/JEFF-Media-GbR/JeffLib/blob/master/core/src/main/java/de/jeff_media/jefflib/ServerUtils.java

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServerReloadTask {


    public enum ServerLifePhase {
        STARTUP, RUNNING, SHUTDOWN, UNKNOWN
    }

    public static ServerLifePhase getLifePhase() {
        try {
            final Field currentTickField = Bukkit.getScheduler().getClass().getDeclaredField("currentTick");
            currentTickField.setAccessible(true);
            final int currentTick = currentTickField.getInt(Bukkit.getScheduler());
            if(currentTick==-1) {
                return ServerLifePhase.STARTUP;
            }
            final Method getServerMethod = Bukkit.getServer().getClass().getMethod("getServer");
            final Object nmsServer = getServerMethod.invoke(Bukkit.getServer());
            final Method isRunningMethod = nmsServer.getClass().getMethod("isRunning");
            return ((boolean) isRunningMethod.invoke(nmsServer)) ? ServerLifePhase.RUNNING : ServerLifePhase.SHUTDOWN;
        } catch (final NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException exception) {
            return ServerLifePhase.UNKNOWN;
        }
    }

}

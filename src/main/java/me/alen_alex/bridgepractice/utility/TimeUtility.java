package me.alen_alex.bridgepractice.utility;

import java.util.concurrent.TimeUnit;

public class TimeUtility {

    public static String getDurationFromLongTime(long _time){
        String timeMnin = String.valueOf(TimeUnit.MILLISECONDS.toMinutes(_time) % 60);
        String timeSec = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(_time)% 60 );
        String timeMicro = String.valueOf(TimeUnit.MILLISECONDS.toMillis(_time) % 1000);
        String _formated = timeMnin+":"+timeSec+":"+timeMicro;
        return _formated;
    }

    public static int getSecondsFromLongTime(long time){
        return Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(time)% 60));
    }

}

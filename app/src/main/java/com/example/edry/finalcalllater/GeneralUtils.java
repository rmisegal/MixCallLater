package com.example.edry.finalcalllater;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import static com.example.edry.finalcalllater.Constants.ALARM_START_SLEEPER_ID;

/**
 * Created by Bota
 */

public class GeneralUtils {

    public static boolean isSDK23()
    {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static void cancelSleepAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent myIntent = new Intent(context, ExistSleepModeReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                ALARM_START_SLEEPER_ID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntent.cancel();
        alarmManager.cancel(pendingIntent);
    }

    public static String getFormatedTimeDifference(long milliSeconds) {
        long timeDiff = milliSeconds - System.currentTimeMillis();
        long diffSeconds = timeDiff / 1000 % 60;
        long diffMinutes = timeDiff / (60 * 1000) % 60;
        long diffHours = timeDiff / (60 * 60 * 1000) % 24;

        if(diffHours > 0)
            return String.format("%02d:%02d:%02d", diffHours, diffMinutes, diffSeconds);
        else
            return String.format("%02d:%02d", diffMinutes, diffSeconds);
    }
}
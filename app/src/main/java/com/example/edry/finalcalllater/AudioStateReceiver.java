package com.example.edry.finalcalllater;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.media.AudioManager;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("Flow: AudioStateReceiver : onReceive  ");

        AudioManager MyVolume =  (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        if(true) {

            switch (MyVolume.getRingerMode()) {

                case AudioManager.RINGER_MODE_SILENT:

                     if(! isMyServiceRunning(SleepModeService.class,context) && isAprroved( context) ) {

                       startSleeperWindow Slper = new startSleeperWindow(context);
                     }

                    break;

                case AudioManager.RINGER_MODE_NORMAL:

                     if(isMyServiceRunning(SleepModeService.class,context) && MyVolume.getStreamVolume(AudioManager.STREAM_RING) == 1) {

                        StopSleeperWindow Slper = new StopSleeperWindow(context);
                     }

                    break;

                case AudioManager.RINGER_MODE_VIBRATE:

                    break;


                default:

                    break;


            }

        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass , Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isAprroved(Context context)  {

        boolean x = false;
        try{
            x = new SimpleDateFormat("MM/yyyy").parse("3/2018").after(new Date());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Flow: isAprroved : isMyServiceRunning  " + x);


        if (x)
            return true;
        else
        {

            Toast.makeText(context,"Please Download a new version", Toast.LENGTH_LONG).show();
            return false;

        }


    }
}

package com.example.edry.finalcalllater;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

public class RingtonePlayerService extends Service {

    private Ringtone ringtone;


    public RingtonePlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
       }

    public int onStartCommand(Intent intent, int flags, int startId)
    {

        System.out.println("Flow: RingtonePlayerService : onStartCommand ") ;
        System.out.println("Flow: step 5" );


        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        this.ringtone = RingtoneManager.getRingtone(this, notification);

        ringtone.play();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        ringtone.stop();

        System.out.println("Flow: RingtonePlayerService : onDestroy ") ;
    }

}

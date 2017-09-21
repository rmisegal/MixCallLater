package com.example.edry.finalcalllater;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class SleepModeService extends Service {

    IncomingPhoneCallReceiver Caller_Receiver;
    IntentFilter filter;
    public SleepModeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {

        System.out.println("Flow: SleepModeService : sleep till " + (long)intent.getLongExtra("PERIOD",0)) ;

        Caller_Receiver = new IncomingPhoneCallReceiver();

        filter = new IntentFilter("android.intent.action.PHONE_STATE" );

        registerReceiver(Caller_Receiver, filter);

        return START_NOT_STICKY;

    }



    public void onDestroy()
    {
        System.out.println("Flow: SleepModeService : onDestroy ") ;

        unregisterReceiver(Caller_Receiver);

        MyPhoneState SoundUp = new MyPhoneState();

        SoundUp.onCallStateChanged(getApplicationContext(),3,null);
    }
}

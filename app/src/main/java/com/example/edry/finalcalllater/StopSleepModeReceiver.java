package com.example.edry.finalcalllater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StopSleepModeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent stopSleepMode= new Intent(context,SleepModeService.class );
        context.stopService(stopSleepMode);

    }
}

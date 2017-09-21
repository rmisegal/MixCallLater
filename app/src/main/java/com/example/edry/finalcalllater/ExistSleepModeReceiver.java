package com.example.edry.finalcalllater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Bota
 */

public class ExistSleepModeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newCallOutIntent = new Intent(context, SleepModeService.class);
        context.stopService(newCallOutIntent);
    }
}
package com.example.edry.finalcalllater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class OutGoingPhoneCallReceiver extends BroadcastReceiver {



    public void onReceive(Context context, Intent intent) {



        //System.out.println("Flow: OutGoingPhoneCallReceiver : onReceive  ");

        Intent newCallInIntent = new Intent(context, OnPhoneCallService.class);

        newCallInIntent.putExtra("EXTRA_NUMBER",intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER));

        newCallInIntent.putExtra("CASE","OUTGOING");

        context.startService(newCallInIntent);

    }



}



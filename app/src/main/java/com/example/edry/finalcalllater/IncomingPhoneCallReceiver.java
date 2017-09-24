package com.example.edry.finalcalllater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class IncomingPhoneCallReceiver extends BroadcastReceiver {

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static String savedNumber;  //because the passed incoming is only valid in ringing


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
        }
        else{
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state = 0;
            if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                state = TelephonyManager.CALL_STATE_IDLE;
            }
            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            }
            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                state = TelephonyManager.CALL_STATE_RINGING;
            }


            onCallStateChanged(context, state, number);
        }
    }

    public void onCallStateChanged(Context context, int state, String number) {
        if(lastState == state){
            //No changes
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                MyPhoneState SoundUp = new MyPhoneState();
                SoundUp.onCallStateChanged(context,0,null);
                Toast.makeText(context, "CURRENT STATE: IDLE STATE", Toast.LENGTH_LONG).show();
                break;

            case TelephonyManager.CALL_STATE_RINGING:
                Intent newCallInIntent = new Intent(context, OnPhoneCallService.class);
                newCallInIntent.putExtra("EXTRA_NUMBER", number);
                newCallInIntent.putExtra("CASE","INCOMING");
                context.startService(newCallInIntent);
                Toast.makeText(context, "CURRENT STATE: RINGING STATE", Toast.LENGTH_LONG).show();
                break;

            case TelephonyManager.CALL_STATE_OFFHOOK:
                Toast.makeText(context, "CURRENT STATE: OFFHOOK STATE", Toast.LENGTH_LONG).show();
                break;

            default:
                Toast.makeText(context, "CURRENT STATE: DEFAULT STATE", Toast.LENGTH_LONG).show();
                break;
        }
        lastState = state;
    }
}

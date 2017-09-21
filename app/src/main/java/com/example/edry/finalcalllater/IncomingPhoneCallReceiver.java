package com.example.edry.finalcalllater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class IncomingPhoneCallReceiver extends BroadcastReceiver {

    private String PhoneState;

    private String adiitionalPhoneNumber;


    public void onReceive(Context context, Intent intent) {

        //System.out.println("Flow: IncomingPhoneCallReceiver : onReceive  ");

        PhoneState = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);

        adiitionalPhoneNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

        int curState = getState();

        switch (curState) {

            case TelephonyManager.CALL_STATE_IDLE:

                MyPhoneState SoundUp = new MyPhoneState();

                SoundUp.onCallStateChanged(context,0,null);

                break;

            case TelephonyManager.CALL_STATE_RINGING:

                Intent newCallInIntent = new Intent(context, OnPhoneCallService.class);

                newCallInIntent.putExtra("EXTRA_NUMBER",adiitionalPhoneNumber);

                newCallInIntent.putExtra("CASE","INCOMING");

                context.startService(newCallInIntent);

                break;

            case TelephonyManager.CALL_STATE_OFFHOOK:


                break;

            default:


                break;


        }

        //System.out.println("Flow: IncomingPhoneCallReceiver : case " + PhoneState +" " + adiitionalPhoneNumber);


    }




    private int getState( )
    {

        int state = -1;
        if(PhoneState.equals("IDLE")){
            state = TelephonyManager.CALL_STATE_IDLE;
        }
        else if(PhoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            state = TelephonyManager.CALL_STATE_OFFHOOK;
        }
        else if(PhoneState.equals("RINGING")){
            state = TelephonyManager.CALL_STATE_RINGING;
        }
        return state;
    };
}

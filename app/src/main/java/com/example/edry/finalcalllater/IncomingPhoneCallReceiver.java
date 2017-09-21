package com.example.edry.finalcalllater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class IncomingPhoneCallReceiver extends BroadcastReceiver {

    private String PhoneState;

    private String adiitionalPhoneNumber;


    public void onReceive(Context context, Intent intent) {

        //System.out.println("Flow: IncomingPhoneCallReceiver : onReceive  ");

        TelephonyManager tmgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneStateListener PhoneListener = new MyPhoneStateListener(context, intent);
        tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

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

    private class MyPhoneStateListener extends PhoneStateListener {

        Context context;
        Intent intent;

        MyPhoneStateListener(Context context, Intent intent) {
            this.context = context;
            this.intent = intent;
        }

        public void onCallStateChanged(int state, String incomingNumber) {

            Log.d("MyPhoneListener",state+"   incoming no:"+incomingNumber);

            PhoneState = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);

            adiitionalPhoneNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

            int curState = getState();

            switch (curState) {

                case TelephonyManager.CALL_STATE_IDLE:

                    MyPhoneState SoundUp = new MyPhoneState();

                    SoundUp.onCallStateChanged(context,0,null);
                    Toast.makeText(context, "CURRENT STATE: IDLE STATE", Toast.LENGTH_LONG).show();

                    break;

                case TelephonyManager.CALL_STATE_RINGING:

                    Intent newCallInIntent = new Intent(context, OnPhoneCallService.class);

                    newCallInIntent.putExtra("EXTRA_NUMBER",adiitionalPhoneNumber);

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
        }
    }
}

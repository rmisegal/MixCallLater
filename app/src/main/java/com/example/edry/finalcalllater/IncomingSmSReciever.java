package com.example.edry.finalcalllater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class IncomingSmSReciever extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {

        System.out.println("Flow: IncomingSmSReciever : onReceive ") ;

        Bundle intentExtras = intent.getExtras();

            if (intentExtras != null) {

                Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);

                String smsMessageStr = "";

                for (int i = 0; i < sms.length; ++i) {

                    String format = intentExtras.getString("format");

                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i], format);

                    String smsBody = smsMessage.getMessageBody().toString();

                    String address = smsMessage.getOriginatingAddress();

                    smsMessageStr += "SMS From: " + address ;

                    smsMessageStr += smsBody + "\n";

                    Intent newCallOutIntent = new Intent(context, OnPhoneCallService.class);

                    newCallOutIntent.putExtra("CASE","RESULT");

                    newCallOutIntent.putExtra("EXTRA_NUMBER",address);

                    newCallOutIntent.putExtra("EXTRA_CODE",smsBody.substring(0,4));

                    context.startService(newCallOutIntent);

                }

            }






    }
}

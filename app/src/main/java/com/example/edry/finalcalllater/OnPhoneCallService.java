package com.example.edry.finalcalllater;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import java.util.Random;

public class OnPhoneCallService extends Service {


    String SEND_AUT_CODE = " IF YOU DONT HAVE THIS APP, DOWNLOAD IT!";

    IncomingSmSReciever reciever;

    TelephonyManager telephonyManager;

    PhoneStateListener callStateListener;

    IntentFilter filter;

    String PersonalCode;

    String PeerPhoneNumber;

    static String LastState = "NONE";


    public OnPhoneCallService() {


    }

    @Override
    public void onCreate() {
        super.onCreate();


        try {
            filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

            reciever = new IncomingSmSReciever();

            registerReceiver(reciever, filter);

            telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            //Thread.sleep(300);

            callStateListener = new PhoneStateListener() {

                public void onCallStateChanged(int state, String incomingNumber) {


                    if (state == TelephonyManager.CALL_STATE_IDLE) {

                        Done();

                    }


                }
            };
            telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
            return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {

        String Case = intent.getStringExtra("CASE");

        String EXTRA_NUMBER = intent.getStringExtra("EXTRA_NUMBER");

        System.out.println("Flow: OnPhoneCallService : onStartCommand " + Case + " " + EXTRA_NUMBER + " " +  LastState) ;

        switch(Case)
        {

            case "INCOMING":

                PersonalCode = GenerateRandomCode();

                PeerPhoneNumber = EXTRA_NUMBER;

                SendSms(PersonalCode + SEND_AUT_CODE , EXTRA_NUMBER);

                break;

            case "OUTGOING":

                break;

            case "RESULT":

                String EXTRA_CODE = intent.getStringExtra("EXTRA_CODE");

                switch(LastState)
                {
                    case "INCOMING":

                        System.out.println("Flow: step 1" + EXTRA_CODE + "  " + PersonalCode + "  " + EXTRA_CODE.equals(PersonalCode));

                        if(EXTRA_NUMBER.equals(PeerPhoneNumber) && EXTRA_CODE.equals(PersonalCode))
                            {
                                System.out.println("Flow: step 2" );

                                MyPhoneState SoundUp = new MyPhoneState();

                                SoundUp.onCallStateChanged(getApplicationContext(),1,null);
                            }

                        break;

                    case "OUTGOING":

                        System.out.println("Flow: case result : OUTGOING");

                        new EmergencyWindow(getApplicationContext(), EXTRA_NUMBER , EXTRA_CODE);

                        //SendSms(EXTRA_CODE , EXTRA_NUMBER);

                        break;

                    default:

                        System.out.println("Flow: case result : default");

                    break;

                }


                break;

            default:

                break;
        }


        LastState = Case;

        return START_NOT_STICKY;

    }

    private void SendSms(String text , String PhoneNumber)
    {
        System.out.println("Flow: OnPhoneCallService : sending Massage " + text +" To " + PhoneNumber) ;

        SmsManager sms = SmsManager.getDefault();

        sms.sendTextMessage(PhoneNumber, null,text, null, null);

    }

    private void Done()
    {
        System.out.println("Flow: OnPhoneCallService : onDestroy ") ;

        unregisterReceiver(reciever);

       telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE);

        stopSelf();
    }

    private String GenerateRandomCode()
    {
        Random rand = new Random();

        int  n = rand.nextInt(10000) + 10000;

        String Code = String.valueOf(n).substring(1);

        System.out.println("Flow: Code" + Code);

        return Code;
    }
}

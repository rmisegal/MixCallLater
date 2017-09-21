
package com.example.edry.finalcalllater;

/**
 * Created by edry on 07/08/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.widget.Toast;

public class MyPhoneState extends PhoneStateListener{

    public enum Cases {
        INCOMING_CALL , IDLE
    }
    //hghghjghjghjghj

    public void onCallStateChanged(Context context, int state, String number)
    {

        AudioManager MyVolume =  (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        System.out.println("Flow: step 3" );


        switch(state)
        {
            case 0:
                try {

                    Intent ringerServic = new Intent(context,RingtonePlayerService.class);

                    context.stopService(ringerServic);

                    MyVolume.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0,0);
                    //MyVolume.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);


                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("broadcast audioState defual");
                }

                break;

            case 1:
                try {

                   //MyVolume.setStreamVolume(AudioManager.STREAM_RING, MyVolume.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
                    //MyVolume.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                    System.out.println("Flow: step 4" );

                    MyVolume.setStreamVolume(AudioManager.STREAM_NOTIFICATION,MyVolume.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION), 0);

                   Intent ringerServic = new Intent(context,RingtonePlayerService.class);

                   context.startService(ringerServic);


                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Flow : no change at ringer");
                    Toast.makeText(context,number,Toast.LENGTH_LONG).show();

                }





                break;
            case 4:

                try {
                    //MyVolume.setStreamVolume(AudioManager.STREAM_RING, 0, 0);

                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case 3:

                try {
                    MyVolume.setStreamVolume(AudioManager.STREAM_RING, MyVolume.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
                }
                catch (Exception e) {
                    e.printStackTrace();


                }


                break;
            default:
                System.out.println("defual");
                break;
        }

    }



}

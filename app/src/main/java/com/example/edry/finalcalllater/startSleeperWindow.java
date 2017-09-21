package com.example.edry.finalcalllater;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.edry.finalcalllater.Constants.ALARM_START_SLEEPER_ID;
import static com.example.edry.finalcalllater.GeneralUtils.cancelSleepAlarm;
import static com.example.edry.finalcalllater.GeneralUtils.getFormatedTimeDifference;

/**
 * Created by edry on 06/09/2017.
 */

public class startSleeperWindow extends PopUpWindow {

    TimePicker timePicker;


    public startSleeperWindow(Context myContext) {
        super(myContext);
    }

    @Override
    public void setInflator() {
        this.floatyView = ((LayoutInflater) this.myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.start_app_view, interceptorLayout);
    }


    public void setScreenContent() {
        Button confirm = (Button)  floatyView.findViewById(R.id.confirmButton);

        Button ExitButton  = (Button) floatyView.findViewById(R.id.ExitButton);

        timePicker = (TimePicker) floatyView.findViewById(R.id.simpleTimePicker);

        timePicker.setIs24HourView(true);

        confirm.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Calendar calNow = Calendar.getInstance();
                Calendar calSet = (Calendar) calNow.clone();

                int hoursDiff = timePicker.getHour() - calNow.get(Calendar.HOUR_OF_DAY);
                int minuteDiff = timePicker.getMinute() - calNow.get(Calendar.MINUTE);

                calSet.add(Calendar.HOUR_OF_DAY, hoursDiff);
                calSet.add(Calendar.MINUTE, minuteDiff);
                calSet.add(Calendar.SECOND, -calSet.get(Calendar.SECOND));
                calSet.add(Calendar.MILLISECOND, -calSet.get(Calendar.MILLISECOND));

                if(calSet.compareTo(calNow) <= 0){
                    //Today Set time passed, count to tomorrow
                    calSet.add(Calendar.DATE, 1);
                }


                Toast.makeText(myContext,"Sleeping for " + getFormatedTimeDifference(calSet.getTimeInMillis()),Toast.LENGTH_LONG).show();

                MyPhoneState SoundUp = new MyPhoneState();

                SoundUp.onCallStateChanged(myContext,0,null);

                Intent newCallOutIntent = new Intent(myContext, SleepModeService.class);

                newCallOutIntent.putExtra("PERIOD",calSet.getTimeInMillis());

                myContext.startService(newCallOutIntent);

                AlarmManager alarmManager=
                        (AlarmManager)myContext.getSystemService(Context.ALARM_SERVICE);

                cancelSleepAlarm(myContext);

                Intent i = new Intent(myContext, ExistSleepModeReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(myContext, ALARM_START_SLEEPER_ID, i, PendingIntent.FLAG_UPDATE_CURRENT);
                if(GeneralUtils.isSDK23())
                    alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pi);
                else
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pi);

                removeView();

            }
        });

        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView();
            }
        });
    }
}
package com.example.edry.finalcalllater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by edry on 06/09/2017.
 */

public class StopSleeperWindow extends PopUpWindow {


    Button KeepRemainingButton;

    Button ExitButton;


    public StopSleeperWindow(Context myContext) {
        super(myContext);
    }

    @Override
    public void setInflator() {

        this.floatyView = ((LayoutInflater) this.myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.stop_app_view, interceptorLayout);

    }

    @Override
    public void setScreenContent() {

        KeepRemainingButton = (Button) floatyView.findViewById(R.id.KeepRemainingButton);

        ExitButton = (Button) floatyView.findViewById(R.id.ExitButton);

        KeepRemainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPhoneState SoundUp = new MyPhoneState();

                SoundUp.onCallStateChanged(myContext,0,null);

                removeView();
            }
        });

        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Flow: StopSleeperService : sleep ");

                Intent newCallOutIntent = new Intent(myContext, SleepModeService.class);

                myContext.stopService(newCallOutIntent);

                removeView();
            }
        });

    }
}

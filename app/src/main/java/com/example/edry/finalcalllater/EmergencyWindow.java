package com.example.edry.finalcalllater;

import android.content.Context;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by edry on 06/09/2017.
 */

public class EmergencyWindow extends PopUpWindow  implements View.OnClickListener{

    Button DismissButton;

    Button ApproveButton;

    String phoneNumber;

    String code;


    public EmergencyWindow(Context myContext, String phoneNumber , String code) {

        super(myContext);

        this.phoneNumber = phoneNumber;

        this.code = code;
    }

    @Override
    public void setInflator() {

        this.floatyView = ((LayoutInflater) this.myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.emergency_app_view, interceptorLayout);

    }

    @Override
    public void setScreenContent() {

        DismissButton = (Button) floatyView.findViewById(R.id.DismissButton);

        ApproveButton =  (Button) floatyView.findViewById(R.id.confirmButton);

        DismissButton.setOnClickListener(this);

        ApproveButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.DismissButton:

                break;

            case R.id.confirmButton:

                SendSms(this.code , this.phoneNumber);

                break;

            default:

                break;
        }

        removeView();
    }

    private void SendSms(String text , String PhoneNumber)
    {
        System.out.println("Flow: OnPhoneCallService : sending Massage " + text +" To " + PhoneNumber) ;

        SmsManager sms = SmsManager.getDefault();

        sms.sendTextMessage(PhoneNumber, null,text, null, null);

    }
}

package com.example.edry.finalcalllater;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by edry on 06/09/2017.
 */

public abstract class PopUpWindow implements View.OnTouchListener {

    private WindowManager windowManager;

    protected View floatyView;

    protected FrameLayout interceptorLayout;

    Context myContext;



    public PopUpWindow(Context myContext) {

        this.myContext = myContext;

        windowManager = (WindowManager) myContext.getSystemService(Context.WINDOW_SERVICE);

        addOverlayView();

    }


    private void addOverlayView() {

        final WindowManager.LayoutParams params =
                new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_PHONE,
                        0,
                        PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER | Gravity.START;
        params.x = 0;
        params.y = 0;


        interceptorLayout = new FrameLayout(myContext) {

            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {

                // Only fire on the ACTION_DOWN event, or you'll get two events (one for _DOWN, one for _UP)
                if (event.getAction() == KeyEvent.ACTION_DOWN) {

                    // Check if the HOME button is pressed
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

                        Log.v("ttr", "BACK Button Pressed");

                        // As we've taken action, we'll return true to prevent other apps from consuming the event as well
                        return true;
                    }
                }

                // Otherwise don't intercept the event
                return super.dispatchKeyEvent(event);
            }
        };



        setInflator();

        setScreenContent();

        floatyView.setOnTouchListener(this);

        windowManager.addView(floatyView, params);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {

        // System.out.println("Flow: detectDndModeReciever : calender " + MyVolume.getRingerMode() + SetTime.onPeriod) ;
        if (floatyView != null) {


        }


        return true;
    }

    protected void removeView()
    {
        windowManager.removeView(floatyView);
    }


    public abstract void setInflator();

    public abstract void setScreenContent();
}

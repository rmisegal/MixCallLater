package com.example.edry.finalcalllater;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import static com.example.edry.finalcalllater.PowerSaverHelper.prepareIntentForWhiteListingOfBatteryOptimization;


public class MainActivity extends AppCompatActivity {



        private static final int READ_SMS_PERMISSIONS_REQUEST = 1;
        private static final int SEND_SMS_PERMISSIONS_REQUEST = 2;
        private static final int PROCESS_OGC_PERMISSIONS_REQUEST = 3;
        private static final int PHONE_STATE_PERMISSIONS_REQUEST = 4;


    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;


    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            checkPopUpPermission();


            // permmisions ! //
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

            //ActivityCompat.requestPermissions(this, new String[]  {Manifest.permission.PROCESS_OUTGOING_CALLS} , 1);
          //  notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {

                Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

                startActivity(intent);

              }



        requestProcessOutgoingCallPermission();
        requestReadPhoneStatePermission();
        requestReadSmsPermission();
        requestSendSmsPermission();

        }



        private void requestProcessOutgoingCallPermission() {

            String permission = Manifest.permission.PROCESS_OUTGOING_CALLS;

            int grant = ContextCompat.checkSelfPermission(getApplicationContext(),permission);

            if (grant != PackageManager.PERMISSION_GRANTED) {

                String[] permission_list = new String[1];

                permission_list[0] = permission;

                ActivityCompat.requestPermissions(this, permission_list, PROCESS_OGC_PERMISSIONS_REQUEST);
            }
        }


        private void requestReadPhoneStatePermission() {

            String permission = Manifest.permission.READ_PHONE_STATE;

            int grant = ContextCompat.checkSelfPermission(getApplicationContext(), permission);

            if (grant != PackageManager.PERMISSION_GRANTED) {

                String[] permission_list = new String[1];

                permission_list[0] = permission;

                ActivityCompat.requestPermissions(this, permission_list, PHONE_STATE_PERMISSIONS_REQUEST);
            }
        }

        private void requestSendSmsPermission() {

            String permission = Manifest.permission.SEND_SMS;

            int grant = ContextCompat.checkSelfPermission(getApplicationContext(), permission);

            if (grant != PackageManager.PERMISSION_GRANTED) {

                String[] permission_list = new String[1];

                permission_list[0] = permission;

                ActivityCompat.requestPermissions(this, permission_list, SEND_SMS_PERMISSIONS_REQUEST);
            }
        }

    public void checkDozePermission() {
        Intent intent = prepareIntentForWhiteListingOfBatteryOptimization(MainActivity.this, "com.example.edry.finalcalllater", false);
        if(intent != null)
            startActivity(intent);
    }


    private void requestReadSmsPermission() {

            String permission = Manifest.permission.READ_SMS;

            int grant = ContextCompat.checkSelfPermission(getApplicationContext(), permission);

            if (grant != PackageManager.PERMISSION_GRANTED) {

                String[] permission_list = new String[1];

                permission_list[0] = permission;

                ActivityCompat.requestPermissions(this, permission_list, READ_SMS_PERMISSIONS_REQUEST);
            }
        }


        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {

            if(grantResults.length > 0)

                switch(requestCode)

                {
                    case READ_SMS_PERMISSIONS_REQUEST:

                        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                            System.out.println("Flow: READ_SMS_PERMISSIONS_REQUEST : PackageManager.PERMISSION_GRANTED");


                        } else {

                            //Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();

                        }


                        break;

                    case SEND_SMS_PERMISSIONS_REQUEST:
                        System.out.println("Flow: READ_SMS_PERMISSIONS_REQUEST : SEND_SMS_PERMISSIONS_REQUEST");


                        break;

                    case PROCESS_OGC_PERMISSIONS_REQUEST:
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)

                            System.out.println("Flow: READ_SMS_PERMISSIONS_REQUEST : PROCESS_OGC_PERMISSIONS_REQUEST");


                        break;

                    case PHONE_STATE_PERMISSIONS_REQUEST:

                        System.out.println("Flow: READ_SMS_PERMISSIONS_REQUEST : PHONE_STATE_PERMISSIONS_REQUEST");

                        break;



                }
                else
                System.out.println("0");


        }


        public void checkPopUpPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
                }
            }
        }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
                if (!Settings.canDrawOverlays(this)) {
                    // You don't have permission
                    //checkPopUpPermission();

                }
                else
                {
                    //do as per your logic
                }

            }



        }

}


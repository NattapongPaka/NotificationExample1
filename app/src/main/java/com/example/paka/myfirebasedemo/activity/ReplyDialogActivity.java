package com.example.paka.myfirebasedemo.activity;


import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.paka.myfirebasedemo.ReplyDialogFragment;
import com.example.paka.myfirebasedemo.util.Util;

/**
 * Created by Noth on 21/6/2559.
 */
public class ReplyDialogActivity extends AppCompatActivity implements MyInterface.IFinishActivity {

    private String TAG = ReplyDialogActivity.class.getSimpleName();
    private FrameLayout frameLayout;
    private String title;
    private String group_id;
    private int notification_size;
    private String cal_id;
    int in_mNotificationID;
    private MyInterface myInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Get intent string
         */
        String in_group_id = getIntent().getStringExtra("group_id");
        String in_title = getIntent().getStringExtra("title");
        in_mNotificationID = getIntent().getIntExtra("notificationID", -1);
        try {
            Log.i("ReplyDialogActivity", "GroupID : " + in_group_id + " :Title: " + in_title + " : " + String.valueOf(in_mNotificationID));
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            title = preferences.getString("TITLE", "");
            group_id = preferences.getString("group_id", "");
            cal_id = preferences.getString("cal_id", "");

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(in_mNotificationID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        /**
         * Android less then 23 22 21 ...
         */
        if (!Util.isAndroidVersionN()) {
            /**
             * Set theme transparent no action bar
             */
            getWindow().getDecorView().setBackgroundColor(Color.BLACK);
//            Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//            ActionBar mActionBar = getSupportActionBar();
//            if (mToolbar != null && mActionBar != null) {
//                setSupportActionBar(mToolbar);
//                mActionBar.setDisplayShowCustomEnabled(true);
//                mActionBar.setDisplayHomeAsUpEnabled(true);
//                mToolbar.setTitle(in_title);
//            }
            DialogFragment dialogFragment = ReplyDialogFragment.newInstance(title, group_id, cal_id);
            dialogFragment.setCancelable(false);
            dialogFragment.show(getSupportFragmentManager(),"ReplyDialogFragment");
        } else {
            /**
             * Android API 24 more then
             */
            try {
                Bundle remoteInput = RemoteInput.getResultsFromIntent(getIntent());
                CharSequence msgResult = remoteInput.getCharSequence("key_text_reply");
                if (msgResult != null && msgResult.length() > 0) {
//                    Random r = new Random();
//                    String messageValue = "";
//                    String lat = "";
//                    String lng = "";
//                    String chat_id = String.valueOf(r.nextInt(9999999));
//                    MessageManager messageManager = new MessageManager(this);
//                    messageManager.sendMessageToServer(messageValue, lat, lng, chat_id, group_id, title);
                    Log.i("ReplyDialogActivity", "MSG : " + msgResult.toString());
                } else {
                    Log.i("ReplyDialogActivity", "MSG Length == 0");
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(in_mNotificationID);
            //finish();
        }
        registerInteneFilter();
        MyInterface.getInstance().setOnMyInstanceChangerListener(this);
    }

    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    private void registerInteneFilter(){
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mIntentFilter.addAction(Intent.ACTION_SCREEN_ON);
        mIntentFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(mBroadcastReceiver,mIntentFilter);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
                Log.i(TAG,"ScreenOk");
            }else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
                Log.i(TAG,"ScreenOff");
            }else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
                Log.i(TAG,"ScreenPresent");
            }
        }
    };

    @Override
    public void onAttachedToWindow() {
        Window window = getWindow();
        window.addFlags(
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }

    @Override
    public void OnFinishActivity() {
        finish();
    }
}

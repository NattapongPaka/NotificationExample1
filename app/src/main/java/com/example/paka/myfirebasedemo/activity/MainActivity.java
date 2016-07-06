package com.example.paka.myfirebasedemo.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;



import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnAdd;
    private Button btnRemove;
    private Context mContext;
    private int mNotificationId;
    private PendingIntent resultPendingIntent;
    private CharSequence _message;
    private String group_id;
    private String group_name;
    private int color;
    private Bitmap bitmapLargeIcon;

    private String ACTION_DIALOG_START = "com.intent.action.start_dialog";
    private String ACTION_DIALOG_HIDDEN = "com.intent.action.hidden_dialog";
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRemove = (Button) findViewById(R.id.btnRemove);

        btnAdd.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAdd:
//                mockupDataNotification();
//                if(Util.isAndroidVersionN()){
//                    generateNotificationMoreThenN();
//                }else{
//                    generateNotificationLessThenN();
//                }
//                finish();
                if(getPreference("ReplyDialogServiceStart").equals("onCreate") && getPreference("ReplyDialogServiceStart") != null){
                    Intent intent = new Intent();
                    intent.setAction(ACTION_DIALOG_START);
                    sendBroadcast(intent);
                    Log.i(TAG,"SendBroadcast");
                }else{
                    Log.i(TAG,"StartService");
                    startService(new Intent(this,ReplyDialogService.class));
                }

                break;

            case R.id.btnRemove:
                Log.i(TAG,"PreferenceRemove");
                setPreference("Remove");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        try{
            setPreference("onDestroy");
            stopService(new Intent(this,ReplyDialogService.class));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        super.onDestroy();
    }

    private String getPreference(String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        return preferences.getString(key,"");
    }

    private void setPreference(String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor mEditor = preferences.edit();
        mEditor.putString("ReplyDialogServiceStart",key);
        mEditor.apply();
    }

    private void mockupDataNotification(){
        Random random = new Random();
        color = getResources().getColor(R.color.primary);
        mContext = MainActivity.this;
        mNotificationId = random.nextInt(999999);
        //private PendingIntent resultPendingIntent;
        _message = "MockUp Data "+String.valueOf(mNotificationId);
        group_id = String.valueOf(mNotificationId);
        group_name = "Title DHAS";
        bitmapLargeIcon = BitmapFactory.decodeResource(getResources(),R.drawable.appicon);
    }

    private void generateNotificationMoreThenN(){
        final String KEY_TEXT_REPLY = "key_text_reply";
        //int notificationId = 200;
        Intent intent = new Intent(mContext, ReplyDialogActivity.class);
        intent.putExtra("notificationID", mNotificationId);
        intent.putExtra("group_id", group_id);
        intent.putExtra("title", group_name);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent mPendingIntentChatDialog = PendingIntent.getActivity(mContext, mNotificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        String replyLabel = "Reply message";
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_reply_grey_600_48dp,
                "Reply", mPendingIntentChatDialog)
                .addRemoteInput(remoteInput)
                .build();

        // Build the notification and add the action
        Notification newMessageNotification = new NotificationCompat.Builder(mContext)
                .setAutoCancel(true)
                .setContentTitle(mContext.getResources().getString(R.string.app_name))
                //.setStyle(inboxStyle)
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(_message)
                .setGroup("DHAS")
                .setLargeIcon(bitmapLargeIcon)
                .setColor(color)
                .setSmallIcon(R.drawable.ic_stat_onesignal_default)
                .setGroupSummary(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .addAction(action)
                .build();

        try {
            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(mNotificationId, newMessageNotification);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void generateNotificationLessThenN(){
        Intent intent = new Intent(mContext, ReplyDialogActivity.class);
        intent.putExtra("notificationID", mNotificationId);
        intent.putExtra("group_id", group_id);
        intent.putExtra("title", group_name);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent mPendingIntentChatDialog = PendingIntent.getActivity(mContext, mNotificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = new NotificationCompat.Builder(mContext)
                .setAutoCancel(true)
                .setContentTitle(mContext.getResources().getString(R.string.app_name))
                //.setStyle(inboxStyle)
                //.setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(_message)
                .setGroup("DHAS")
                .setLargeIcon(bitmapLargeIcon)
                .setColor(color)
                .setSmallIcon(R.drawable.ic_stat_onesignal_default)
                .setGroupSummary(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .addAction(R.drawable.ic_reply_grey_600_48dp, "Reply", mPendingIntentChatDialog)
                .build();
        try {
            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(mNotificationId, notification);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    private void registerInteneFilter(){
//        IntentFilter mIntentFilter = new IntentFilter();
//        mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
//        mIntentFilter.addAction(Intent.ACTION_SCREEN_ON);
//        mIntentFilter.addAction(Intent.ACTION_USER_PRESENT);
//        registerReceiver(mBroadcastReceiver,mIntentFilter);
//    }


}

//package com.example.paka.notificationexample.firebase;
//
//import android.app.IntentService;
//import android.content.Intent;
//import android.util.Log;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//
//
//
///**
// * Created by Noth on 5/7/2559.
// */
//
//public class MyFirebaseMessagingBackground extends IntentService {
//
//    private String TAG = MyFirebaseMessagingBackground.class.getSimpleName();
//
//    public MyFirebaseMessagingBackground(String name) {
//        super(name);
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//// Make a call to Instance API
//        FirebaseInstanceId instanceID = FirebaseInstanceId.getInstance();
//        String senderId = getResources().getString(R.string.gcm_defaultSenderId);
//        // request token that will be used by the server to send push notifications
//        String token = instanceID.getToken();
//        Log.d(TAG, "FCM Registration Token: " + token);
//
//        // pass along this data
//        sendRegistrationToServer(token);
//    }
//
//    private void sendRegistrationToServer(String token) {
//        // Add custom implementation, as needed.
//    }
//}

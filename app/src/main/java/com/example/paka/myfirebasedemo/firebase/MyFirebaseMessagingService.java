package com.example.paka.myfirebasedemo.firebase;


import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



public class MyFirebaseMessagingService extends FirebaseMessagingService {
	private String TAG = MyFirebaseMessagingService.class.getSimpleName();

	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		super.onMessageReceived(remoteMessage);

		// If the application is in the foreground handle both data and notification messages here.
		// Also if you intend on generating your own notifications as a result of a received FCM
		// message, here is where that should be initiated. See sendNotification method below.

//		RemoteMessage.Notification notification = remoteMessage.getNotification();
//		Map<String, String> map = remoteMessage.getData();
		try {
			Log.d(TAG, "From: " + remoteMessage.getFrom());
			Log.d(TAG, "Data : " + remoteMessage.getData().toString());
			if(!remoteMessage.getNotification().getBody().isEmpty()) {
				Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
			}
		}catch (NullPointerException ex){
			ex.printStackTrace();
		}

//		sendNotification(notification.getTitle(), notification.getBody(), map);
	}

//	private void sendNotification(String title, String body, Map<String, String> map) {
//		Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
//
//		Intent intent = new Intent(this, MainActivity.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//
//		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//				.setContentTitle(title)
//				.setContentText(body)
//				.setAutoCancel(true)
//				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//				.setContentIntent(pendingIntent)
//				.setContentInfo(title)
//				.setLargeIcon(icon)
//				.setSmallIcon(R.drawable.appicon);
//
//		try {
//			String picture_url = map.get("picture_url");
//			if (picture_url != null && !"".equals(picture_url)) {
//				URL url = new URL(picture_url);
//				Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//				notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(body));
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
//		notificationBuilder.setLights(Color.YELLOW, 1000, 300);
//
//		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		notificationManager.notify(0, notificationBuilder.build());
//	}
}
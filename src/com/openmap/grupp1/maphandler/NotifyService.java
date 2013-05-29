package com.openmap.grupp1.maphandler;
/*
 * Notifycenter
 * Used the old version due to the need of high level of api for the new one
 * Is called on from NearEventNotifier, 
 */


import com.openmap.grupp1.MainActivity;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;

public class NotifyService extends Service {

	final static String ACTION = "NotifyServiceAction";
	final static String STOP_SERVICE = "";
	final static int RQS_STOP_SERVICE = 1;

	NotifyServiceReceiver notifyServiceReceiver;

	private static final int MY_NOTIFICATION_ID=1;
	private NotificationManager notificationManager;
	private Notification myNotification;
	private final String PREFS_NAME = "MySharedPrefs";

	@Override
	public void onCreate() {
		//Creates a notifyServiceReciever
		notifyServiceReceiver = new NotifyServiceReceiver();
		super.onCreate();
	}

	@SuppressWarnings("deprecation")
	@Override
	//Launched when starting the notification
	public int onStartCommand(Intent intent, int flags, int startId) {
		//Gets the title and description of the event which the user gets notified about
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE); 
		String notificationTitle = " " + settings.getString("Notification", "Error when receiving title");
		String notificationText = settings.getString("Notificationdetails", "Error when receiving description");

		//Creates a new IntentFilter and adds the NotifyServiceAction to it
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION);
		//Registers the receiver to receive based on the above defined filter
		registerReceiver(notifyServiceReceiver, intentFilter);

		// Send Notification
		notificationManager =
				(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		//CHANGE THIS PICTURE
		myNotification = new Notification(R.drawable.alert_dark_frame,
				"You are near " + notificationTitle,
				System.currentTimeMillis());
		Context context = getApplicationContext();

		//Sets the intent to be called when the notification is pressed
		Intent myIntent =new Intent(this, MainActivity.class);
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP 
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent pendingIntent
		= PendingIntent.getActivity(getBaseContext(),
				0, myIntent,
				Intent.FLAG_ACTIVITY_CLEAR_TOP);


		//Sets the attributes of the notification
		myNotification.defaults |= Notification.DEFAULT_SOUND;
		myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
		myNotification.setLatestEventInfo(context,
				notificationTitle,
				notificationText,
				pendingIntent);
		//Sends the notification
		notificationManager.notify(MY_NOTIFICATION_ID, myNotification);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	//Removes the receiver when the notification is destroyed
	public void onDestroy() {
		//Removes the receiver
		this.unregisterReceiver(notifyServiceReceiver);
		super.onDestroy();
	}

	@Override
	//Implemented because of the extension
	public IBinder onBind(Intent arg0) {
		return null;
	}

	//Defines the receiver for the notification
	public class NotifyServiceReceiver extends BroadcastReceiver{

		@Override
		//Checks what intent is received, stops the service if the received one equals RQS_STOP_SERVICE
		public void onReceive(Context arg0, Intent arg1) {
			int rqs = arg1.getIntExtra("RQS", 0);
			if (rqs == RQS_STOP_SERVICE){
				//Stops the service
				stopSelf();
			}
		}
	}

}
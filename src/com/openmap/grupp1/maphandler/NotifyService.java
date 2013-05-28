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
// TODO Auto-generated method stub
notifyServiceReceiver = new NotifyServiceReceiver();
super.onCreate();
}

@SuppressWarnings("deprecation")
@Override
public int onStartCommand(Intent intent, int flags, int startId) {
// TODO Auto-generated method stub
	SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE); 
	String notificationTitle = " " + settings.getString("Notification", "Error in receiving");
	String notificationText = settings.getString("Notificationdetails", "Error in receiving");


IntentFilter intentFilter = new IntentFilter();
intentFilter.addAction(ACTION);
registerReceiver(notifyServiceReceiver, intentFilter);

// Send Notification
notificationManager =
 (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//CHANGE THIS PICTURE
	myNotification = new Notification(R.drawable.alert_dark_frame,
  "You are near " + notificationTitle,
  System.currentTimeMillis());
Context context = getApplicationContext();


Intent myIntent =new Intent(this, MainActivity.class);
intent.setAction(Intent.ACTION_MAIN);
intent.addCategory(Intent.CATEGORY_LAUNCHER);

myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP 
		| Intent.FLAG_ACTIVITY_SINGLE_TOP);

PendingIntent pendingIntent
  = PendingIntent.getActivity(getBaseContext(),
    0, myIntent,
    Intent.FLAG_ACTIVITY_CLEAR_TOP);


myNotification.defaults |= Notification.DEFAULT_SOUND;
myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
myNotification.setLatestEventInfo(context,
   notificationTitle,
   notificationText,
   pendingIntent);
notificationManager.notify(MY_NOTIFICATION_ID, myNotification);

return super.onStartCommand(intent, flags, startId);
}

@Override
public void onDestroy() {
// TODO Auto-generated method stub
this.unregisterReceiver(notifyServiceReceiver);
super.onDestroy();
}

@Override
public IBinder onBind(Intent arg0) {
// TODO Auto-generated method stub
return null;
}

public class NotifyServiceReceiver extends BroadcastReceiver{

@Override
public void onReceive(Context arg0, Intent arg1) {
 // TODO Auto-generated method stub
 int rqs = arg1.getIntExtra("RQS", 0);
 if (rqs == RQS_STOP_SERVICE){
  stopSelf();
 }
}
}

}
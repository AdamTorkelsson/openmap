package com.openmap.grupp1;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

public class NearEventNotifier {
	private Location lastKnownLocation;
	private Location event;
	private GoogleMap myMap;
	private Context context;
	private final String PREFS_NAME = "My Settings";
	
	
	public NearEventNotifier(Location lastKnownLocation,GoogleMap myMap,Context context ){
		this.lastKnownLocation = lastKnownLocation;
		this.event = lastKnownLocation;
		this.myMap = myMap;
		this.context = context;
	}	
	
	public void checkEvent(Location loc){
		
		SharedPreferences notificationmessenger = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
		Boolean wantsNotifications = notificationmessenger.getBoolean("notifications", true);
		SharedPreferences.Editor editor = notificationmessenger.edit();
		editor.putString("Notification", "(title)");
		editor.putString("Notificationdetails", "(details)");
		editor.commit();
		//Lägg till: någon check i för max ett event och för att inte checka in på samma igen
		
		if(loc.distanceTo(lastKnownLocation) < 30 && loc.distanceTo(event) < 15 && wantsNotifications){
			Log.d("CheckEvent","eventhandlerduärinärheten");	
			// Send info to database that you have been near and add one person at location/event	
			Intent intent = new Intent(context,com.openmap.grupp1.NotifyService.class);
			 context.startService(intent);}
		}
		
	public void setEvent(Location event){
		this.event = event;
	}
	public void lastKnownLocation(Location lastKnownLocation){
		this.lastKnownLocation = lastKnownLocation;
	}

	public void NearEventNotifier(LatLng point) {
		event.setLatitude(point.latitude);
		event.setLongitude(point.longitude);
		
	}
	}


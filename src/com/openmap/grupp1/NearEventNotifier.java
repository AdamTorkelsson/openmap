package com.openmap.grupp1;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;
/*
 * Should be modified with something that adds the closest 
 * location instead of thos rules thats now created
 */
public class NearEventNotifier {
	private Location lastKnownLocation;
	private Location presentevent = new Location("test");
	private Location presentlocation = new Location("test");
	private GoogleMap myMap;
	private Context context;
	private final String PREFS_NAME = "My Settings";
	
	
	//easy way to see if it works
	private LatLng	checkedIn = new LatLng(0, 0);
	private LatLng atlocation = new LatLng(0, 0);
	
	public NearEventNotifier(Location lastKnownLocation,GoogleMap myMap,Context context ){
		this.lastKnownLocation = lastKnownLocation;
		this.myMap = myMap;
		this.context = context;
		
	}	
	
	

	public void checklocationandevent(Location loc){
		
		
		checkEvent(loc);
		checkLocation(loc);
		lastKnownLocation = loc;
	}

	private void checkLocation(Location loc) {
		if(loc.distanceTo(lastKnownLocation) > 50 && loc.distanceTo(presentlocation) > 30
				&& atlocation.latitude != 0 && atlocation.longitude != 0){
			atlocation = new LatLng(0,0);	
		}
		
		/*
		Database get LatLng
		for(all events near ? how near how to value this?
		LatLng latlngevent = getdatabaseLatlng();
		latitude = latlngevent.getLatitude();
		longitude = latlngevent.getLongitude();
		 */
		 
		Location event = new Location("test");
		/*event.setLatitude(latitude);
		event.setLongitude(longitude);*/
		
		if(loc.distanceTo(lastKnownLocation) < 50
				&& loc.distanceTo(event) < 30 
				&& checkedIn.latitude == 0 
				&& checkedIn.longitude == 0
				&& presentevent.getLongitude() == event.getLongitude()
				&& presentevent.getLatitude() == event.getLatitude()){
				this.presentlocation = presentlocation;
				//add to database;
			
		}
		}
		
	

	private void checkEvent(Location loc){
		//EVENT PART
		//Lägg till checka ut om du gått därifrån
		if(loc.distanceTo(lastKnownLocation) > 50 && loc.distanceTo(presentevent) > 30
				&& checkedIn.latitude != 0 && checkedIn.longitude != 0){
			checkedIn = new LatLng(0,0);	
		}
		
		/*
		Database get LatLng
		for(all events near ? how near how to value this?
		LatLng latlngevent = getdatabaseLatlng();
		latitude = latlngevent.getLatitude();
		longitude = latlngevent.getLongitude();
		 */
		 
		Location event = new Location("test");
		/*event.setLatitude(latitude);
		event.setLongitude(longitude);*/
		
		SharedPreferences notificationmessenger = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
		Boolean wantsNotifications = notificationmessenger.getBoolean("notifications", true);
		
		
		
		if(loc.distanceTo(lastKnownLocation) < 30 
				&& loc.distanceTo(event) < 15 
				&& checkedIn.latitude == 0 
				&& checkedIn.longitude == 0
				&& presentevent.getLongitude() == event.getLongitude()
				&& presentevent.getLatitude() == event.getLatitude()
				//Add wantsNotifications
				){
			/*
			 * Lägg till en Shared som gör så att onResume 
			 * fixar en popup med fråga om man är där 
			 * så man kan checka in
			 */
			
			SharedPreferences.Editor editor = notificationmessenger.edit();
			editor.putString("Notification", "Are you at" + "Title?");
			editor.putString("Notificationdetails", "(details)");
			editor.commit();
			checkedIn = new LatLng(event.getLatitude(),event.getLongitude());
			Log.d("CheckEvent","eventhandlerduärinärheten");	
			// Send info to database that you have been near and add one person at location/event	
			Intent intent = new Intent(context,com.openmap.grupp1.NotifyService.class);
			 context.startService(intent);
			 
			 }
		}
		

	}


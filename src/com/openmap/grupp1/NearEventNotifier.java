package com.openmap.grupp1;

import java.util.ArrayList;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;
/*
 * Checks if there is any locations or events near. 
 * If an event is near it checks with the user if they want to 
 * check in. 
 * 
 * Should be modified with something that adds the closest 
 * location instead of those rules thats now created
 */
public class NearEventNotifier {
	private Location lastKnownLocation; // Holds last known location
	private Location presentevent = new Location("test"); // Holds the event that you are at
	private Location presentlocation = new Location("test");  // Holds the location that you are at
	private GoogleMap myMap; 
	private Context context;
	private final String PREFS_NAME = "MySharedPrefs";
	
	private double shortest = 20; // minimum length to be at an event or an location
	private double lengthtoevent = 0; //Temporary , holds the closest event while going throw the array from the database
	private Location closestevent = new Location("Holds the closest event"); // Temporary holds the closest event
	private ArrayList<LatLng> databaselatlng = new ArrayList<LatLng>(); // the latlng from the database
	private Location event = new Location("Temporary holds events to check which closest");
	private Location questionlocation = new Location("Holds the closest location until checked in");
	//easy way to see if it works
	private LatLng	checkedIn = new LatLng(0, 0);
	private LatLng atlocation = new LatLng(0, 0);
	private SharedPreferences notificationmessenger = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
	private SharedPreferences.Editor editor = notificationmessenger.edit();
	private final String A = "NearEventNotifier";
	
	public NearEventNotifier(Location lastKnownLocation,GoogleMap myMap,Context context ){
		Log.d(A, "NearEventstep0");
		this.lastKnownLocation = lastKnownLocation;
		this.myMap = myMap;
		this.context = context;
		Log.d(A, "NearEventstep1");
	}	
	
	public void checklocationandevent(Location myloc){	
		checkEvent(myloc);
		//Add this later on, fix event first
		//checkLocation(myloc);
		lastKnownLocation = myloc;	
		}
		
	private void checkEvent(Location loc){
		//EVENT PART
		//L�gg till checka ut om du g�tt d�rifr�n
		Log.d(A, "NearEventstep2");
		if(loc.distanceTo(lastKnownLocation) > 50 
				&& loc.distanceTo(presentevent) > 30
				&& checkedIn.latitude != 0 
				&& checkedIn.longitude != 0){
				checkedIn = new LatLng(0,0);
		}
		Log.d(A, "NearEventstep3");
		
		/*
		Database get LatLng
		for(all events near ? how near how to value this?
		LatLng latlngevent = getdatabaseLatlng();
		latitude = latlngevent.getLatitude();
		longitude = latlngevent.getLongitude();
		 */
		 
		
		/*event.setLatitude(latitude);
		event.setLongitude(longitude);*/
		
	

	
	//	for(LatLng ll : Arraylist<LatLng> database)
		if(loc.distanceTo(lastKnownLocation) < 30 
				//maybe not working due to that the checkedIn.latitude shows in longer numbers
				&& checkedIn.latitude == 0 
				&& checkedIn.longitude == 0
				){
			Log.d(A, "NearEventstep6");
				
				shortest = 15;
				/*
				 * get all markers within LatLng 0.00015 + lastKnownLocation
				 * https://code.google.com/p/ense/wiki/LatitudOchLongitudIDecimalgrader
				 * this will be good enough , can doublecheck with a locationlength
				 */
				double myLatitude = loc.getLatitude();
				double myLongitude = loc.getLongitude();
				double upperleftlat = myLatitude + 0.00015;
				double upperleftlong = myLongitude - 0.00015 ;
				Log.d(A, "NearEventstep5");
				
				//Finds the closest event
				for(LatLng ll : databaselatlng){
					event.setLatitude(ll.latitude);
					event.setLongitude(ll.longitude);
					lengthtoevent = loc.distanceTo(event);
					if(lengthtoevent < shortest){
						Log.d(A, "NearEventstep7");
						closestevent = event;
						shortest = lengthtoevent;
					}}
					//checks so this event is closer than 15 meters 
					if(shortest < 15){
						Log.d(A, "NearEventstep8");
						questionlocation = closestevent;
						/*
						 * L�gg till en Shared som g�r s� att onResume 
						 * fixar en popup med fr�ga om man �r d�r 
						 * s� man kan checka in. 
						 */
						/*	if(wantsNotifications){*/
						Boolean wantsNotifications = notificationmessenger.getBoolean("notifications", true);
						Log.d(A, "NearEventstep4");
						
						Log.d(A, "NearEventstep9");
						editor.putBoolean("CheckInPopup", true);
						//Skicka med title och describe( kanske f�rkorta describe)
						//editor.putDouble("CheckInPopup", 5);
						Log.d(A, "NearEventstep9.5");
						editor.putString("Notification", "Are you at" + "Title?");
						editor.putString("Notificationdetails", "(details)");
						editor.commit();
						Log.d(A, "NearEventstep10");
						checkedIn = new LatLng(event.getLatitude(),event.getLongitude());
						Log.d("CheckEvent","eventhandlerdu�rin�rheten");	
						// Send info to database that you have been near and add one person at location/event	
						Log.d(A, "NearEventstep11");
						Intent intent = new Intent(context,com.openmap.grupp1.NotifyService.class);
						context.startService(intent);
						Log.d(A, "NearEventstep12");
						/*Move This to main? 
						 * CreateDialogs checkinDialog = new CreateDialogs();
						checkinDialog.checkInDialog(context, myMap);*/
			 
			 }}
		
		}
	
	
	
	private void checkLocation(Location myloc) {
		if(	myloc.distanceTo(lastKnownLocation) > 50 
			&& myloc.distanceTo(presentlocation) > 30
			&& atlocation.latitude != 0 
			&& atlocation.longitude != 0){
				atlocation = new LatLng(0,0);	
		}
		
		/*
		Database get LatLng
		for(all events near ? how near how to value this?
		LatLng latlngevent = getdatabaseLatlng();
		latitude = latlngevent.getLatitude();
		longitude = latlngevent.getLongitude();
		 */
		 
		Location location = new Location("test");
		/*	event.setLatitude(latitude);
			event.setLongitude(longitude);*/
		/*
		 * start with within 2 meter(0.00001) and then continue to add 0.1 
		 * get all markers within LatLng 0.00015 + lastKnownLocation
		 * https://code.google.com/p/ense/wiki/LatitudOchLongitudIDecimalgrader
		 * this will be good enough , can doublecheck with a locationlength
		 */
		
		if(myloc.distanceTo(lastKnownLocation) < 50
				&& myloc.distanceTo(location) < 30 
				&& atlocation.latitude == 0 
				&& atlocation.longitude == 0){
				this.presentlocation = location;
				//add to database;		
			}
		}

	}


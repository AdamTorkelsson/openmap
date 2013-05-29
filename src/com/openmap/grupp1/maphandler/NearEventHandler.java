package com.openmap.grupp1.maphandler;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;



import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.model.LatLng;
import com.openmap.grupp1.database.LocationMarker;
import com.openmap.grupp1.database.LocationTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;
/*
 * Checks if there is any locations or events near. 
 * If an event is near it asks the user if they want to 
 * check in. 
 * 
 * Add so the user don't have to choose no two times
 * You have to be within 50 meters from an event to 
 * get an notification or to be able to checkin.
 * To get a notification you have to stay within 200 meters  
 * from the last location.
 */
public class NearEventHandler {
	private Location lastKnownLocation; // Holds last known location
	private Location presentevent = new Location("test"); // Holds the event that you are at
	private Location presentlocation = new Location("test");  // Holds the location that you are at
	private Context context;
	private final String PREFS_NAME = "MySharedPrefs";

	private double shortest = 15; // minimum length to be at an event or an location
	private double lengthtoevent = 0; //Temporary , holds the closest event while going throw the array from the database
	private LocationMarker closestevent; // Temporary holds the closest event
	private ArrayList<LatLng> databaselatlng = new ArrayList<LatLng>(); // the latlng from the database
	private Location event = new Location("Temporary holds events to check which closest");
	//easy way to see if it works
	private LatLng	checkedIn = new LatLng(0, 0);
	private LatLng atLocation = new LatLng(0, 0);
	private GoogleMap myMap;

	private final String A = "NearEventNotifier";
	private double area =  0.015;
	private ArrayList<LocationMarker> dataBaseArray;

	public NearEventHandler(Location lastKnownLocation,GoogleMap myMap,Context context ){
		this.myMap = myMap;
		this.lastKnownLocation = lastKnownLocation;
		this.context = context;
	}	

	public void checkEvent(Location loc){	
		//Lägg till checka ut om du gått därifrån

		//If distance to the last known location is more than 200 and the checkedIn location isn't set to the artificial point (0,0), set the checkedIn location to (0,0)
		if(loc.distanceTo(lastKnownLocation) > 200
				&& checkedIn.latitude != 0 
				&& checkedIn.longitude != 0){
			//Sets the checkedIn location to the artificial point (0,0)
			checkedIn = new LatLng(0,0);
		}

		/*
		 * If distance to the last known location is less than 200 and the checkedIn location is set to the artificial point (0,0),
		 * check if any event is closer than 50 meters, if multiple, find nearest event and send a notification
		 */
		if(loc.distanceTo(lastKnownLocation) < 200
				&& checkedIn.latitude == 0 
				&& checkedIn.longitude == 0
				){

			shortest = 50;			
			//Sets the minimum/maximum latitude/longitude to search within, which equals an area
			dataBaseArray = new LocationTask().getLocation(
					new LatLng(loc.getLatitude() - area,loc.getLongitude() - area),
					new LatLng(loc.getLatitude()+area, loc.getLongitude() + area));
			

			//Finds the closest event in the dataBaseArray
			for(LocationMarker ll : dataBaseArray){	
				event.setLatitude(ll.getLatitute());
				event.setLongitude(ll.getLongitude());
				lengthtoevent = loc.distanceTo(event);
				if(lengthtoevent < shortest){
					closestevent = ll;
					checkedIn = ll.getLatLng();
					shortest = lengthtoevent;
				}}

			//If this event is closer than 50 meter, create a notification
			if(shortest < 50){			
				createNotification();

			}}
		//Set last known location to the current location
		lastKnownLocation = loc;	
	}

	//Creates a notification
	private void createNotification(){
		//Create reference to SharePreferences to be able to get data from it
		SharedPreferences notificationmessenger = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
		//If notifications is enabled(true), send a notification
		if( notificationmessenger.getBoolean("notifications", true)){
			//Sets the data in SharedPreferences to be able to get it in the other activity
			SharedPreferences.Editor editor = notificationmessenger.edit();
			editor.putString("Notification", "Are you at" + closestevent.getTitle());
			editor.putString("Notificationdetails", closestevent.getDescription());
			editor.commit();
			//Set the checked in location to the location of the event
			checkedIn = new LatLng(event.getLatitude(),event.getLongitude());
			//Creates a new intent and starts it
			Intent intent = new Intent(context,com.openmap.grupp1.maphandler.NotifyService.class);
			context.startService(intent);
		}
	}

	//Returns true if close enough to the specified point to be able to check in to it
	public boolean isCloseEnough(LatLng eventPoint) {
		//Creates a new location at the given point
		Location eventlocation = new Location("temp");
		eventlocation.setLatitude(eventPoint.latitude);
		eventlocation.setLongitude(eventPoint.longitude);
		//Gets my current location
		LocationHandler lochandler = new LocationHandler(myMap, context);
		Location mylocation = new Location("temp");
		mylocation.setLatitude(lochandler.getMylocation().latitude);
		mylocation.setLongitude(lochandler.getMylocation().longitude);
		//If the distance between the event location and the current location is less than 50 meters, return true
		if(eventlocation.distanceTo(mylocation) < 50){
			return true;}
		//Else return false
		else{
			return false;}
	}

}


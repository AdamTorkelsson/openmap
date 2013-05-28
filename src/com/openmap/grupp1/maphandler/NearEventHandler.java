package com.openmap.grupp1.maphandler;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;



import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.model.LatLng;
import com.openmap.grupp1.database.GetLocationTask;
import com.openmap.grupp1.database.LocationMarker;

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
 * 
 * 
 * 
 * 
 * 
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
	private LatLng atlocation = new LatLng(0, 0);
	private GoogleMap myMap;

	private final String A = "NearEventNotifier";
	private double area =  0.015;
	private ArrayList<LocationMarker> databasearray;
	
	public NearEventHandler(Location lastKnownLocation,GoogleMap myMap,Context context ){
		Log.d(A, "NearEventstep0");
		this.myMap = myMap;
		this.lastKnownLocation = lastKnownLocation;
		this.context = context;
	}	

	public void checklocationandevent(Location myloc){	
		checkEvent(myloc);
		//Add this later on
		//checkLocation(myloc);
		lastKnownLocation = myloc;	
	}

	private void checkEvent(Location loc){
		//EVENT PART
		//Lägg till checka ut om du gått därifrån
		if(loc.distanceTo(lastKnownLocation) > 200
				&& checkedIn.latitude != 0 
				&& checkedIn.longitude != 0){
			checkedIn = new LatLng(0,0);
		}
		
		if(loc.distanceTo(lastKnownLocation) < 200
				&& checkedIn.latitude == 0 
				&& checkedIn.longitude == 0
				){
			
			shortest = 50;			
			GetLocationTask glt = new GetLocationTask();
			glt = new GetLocationTask();
			glt.setMinMaxLatLng(loc.getLatitude() - area,loc.getLongitude() - area,
					loc.getLatitude()+area, loc.getLongitude() + area);
			glt.execute();

			try {
				databasearray = glt.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//Finds the closest event
			for(LocationMarker ll : databasearray){	
				event.setLatitude(ll.getLatitute());
				event.setLongitude(ll.getLongitude());
				lengthtoevent = loc.distanceTo(event);
				if(lengthtoevent < shortest){
					closestevent = ll;
					checkedIn = ll.getLatLng();
					shortest = lengthtoevent;
				}}
			
			//checks so this event is closer than 15 meters 
			if(shortest < 50){			
				createNotification();
		
			}}
	}

	private void createNotification(){
		SharedPreferences notificationmessenger = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
		if( notificationmessenger.getBoolean("notifications", true)){
		SharedPreferences.Editor editor = notificationmessenger.edit();
		editor.putString("Notification", "Are you at" + closestevent.getTitle());
		editor.putString("Notificationdetails", closestevent.getDescription());
		editor.commit();
		Log.d(A, "NearEventstep10");
		checkedIn = new LatLng(event.getLatitude(),event.getLongitude());
		Log.d("CheckEvent","eventhandlerduärinärheten");	
		// Send info to database that you have been near and add one person at location/event	
		Log.d(A, "NearEventstep11");
		Intent intent = new Intent(context,com.openmap.grupp1.maphandler.NotifyService.class);
		context.startService(intent);
		Log.d(A, "NearEventstep12");}

	}

	// A method to check if close enough to check in
	public boolean checkIfInDistanceTo(LatLng point) {
		Log.d(A, "NearEventstep13" + point);
		Location eventlocation = new Location("temp");
		eventlocation.setLatitude(point.latitude);
		eventlocation.setLongitude(point.longitude);
		Log.d(A, "NearEventstep13" + eventlocation.distanceTo(lastKnownLocation));
		Log.d(A, "NearEventstep13" + lastKnownLocation.toString());
		LocationHandler lochandler = new LocationHandler(myMap, context);
		Location mylocation = new Location("temp");
		mylocation.setLatitude(lochandler.getMylocation().latitude);
		mylocation.setLongitude(lochandler.getMylocation().longitude);
		if(eventlocation.distanceTo(mylocation) < 50){
			return true;}
		else{
			return false;}
	}

}


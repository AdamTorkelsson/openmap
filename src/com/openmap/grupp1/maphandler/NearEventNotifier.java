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
 * 
 */
public class NearEventNotifier {
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

	private final String A = "NearEventNotifier";
	private double area =  0.00015;
	private ArrayList<LocationMarker> databasearray;
	
	public NearEventNotifier(Location lastKnownLocation,GoogleMap myMap,Context context ){
		Log.d(A, "NearEventstep0");
		this.lastKnownLocation = lastKnownLocation;
		this.context = context;
	}	

	public void checklocationandevent(Location myloc){	
		checkEvent(myloc);
		//Add this later on, fix event first
		//checkLocation(myloc);
		lastKnownLocation = myloc;	
	}

	private void checkEvent(Location loc){
		//EVENT PART
		//Lägg till checka ut om du gått därifrån
		Log.d(A, "NearEventstep2");
		if(loc.distanceTo(lastKnownLocation) > 200
				&& loc.distanceTo(presentevent) > 200
				&& checkedIn.latitude != 0 
				&& checkedIn.longitude != 0){
			checkedIn = new LatLng(0,0);
		}
		Log.d(A, "NearEventstep3");
		

		//	for(LatLng ll : Arraylist<LatLng> database)
		if(loc.distanceTo(lastKnownLocation) < 200
				//maybe not working due to that the checkedIn.latitude shows in longer numbers
				&& checkedIn.latitude == 0 
				&& checkedIn.longitude == 0
				){
			Log.d(A, "NearEventstep6");



			shortest = 400;
			
			
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
					Log.d(A, "NearEventstep7");
					closestevent = ll;
					shortest = lengthtoevent;
				}}
			//checks so this event is closer than 15 meters 
			if(shortest < 400){
				Log.d(A, "NearEventstep8");
				/*
				 * Lägg till en Shared som gör så att onResume 
				 * fixar en popup med fråga om man är där 
				 * så man kan checka in. 
				 */
				/*editor.putBoolean("CheckInPopup", true);
				editor.commit();*/
				//
				Log.d(A, "NearEventstep4");
				/*	if(wantsNotifications){*/
				Log.d(A, "NearEventstep9");

				//Skicka med title och describe( kanske förkorta describe)
				//editor.putDouble("CheckInPopup", 5);
				Log.d(A, "NearEventstep9.5");
				
				createNotification();
				/*Move This to main? 
				 * CreateDialogs checkinDialog = new CreateDialogs();
							checkinDialog.checkInDialog(context, myMap);*/

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

	public boolean checkin(LatLng point) {
		Location eventlocation = new Location("temp");
		eventlocation.setLatitude(point.latitude);
		eventlocation.setLongitude(point.longitude);
		if(eventlocation.distanceTo(lastKnownLocation) < 100){
			return true;}
		else{
			return false;}
	}

}


/*package com.openmap.grupp1;
<<<<<<< HEAD

import java.security.Provider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
=======
>>>>>>> 3f681e7290ce086383d268f5de010dab6fc99153

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LocationUpdate extends Service implements LocationListener{
	 Location lastLocation;
	 Location presentLocation;
	 GoogleMap myMap;
	
	public void onCreate(){
		
		  Log.d("TestLocationProvider", "Step3");
		//  locationManager = (LocationManager) locmanager;

		  //gets your location and transforms it to an LatLng

	    // Get the location manager
	   LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	   Log.d("TestLocationProvider", "Step4"); 
	   // Define the criteria how to select the locatioin provider -> use
	    // default
	    Criteria criteria = new Criteria();
	    String provider = locationManager.getBestProvider(criteria, false);
	    presentLocation = locationManager.getLastKnownLocation(provider);
	    
	    // Initialize the location fields
	    if (presentLocation != null && presentLocation != lastLocation) {
	      System.out.println("Provider " + provider + " has been selected.");
	      lastLocation =  presentLocation;
	      onLocationChanged(presentLocation);
	      Log.d("TestLocationProvider", "Step5");
	    } else {
	        System.out.println("Provider not available");
	    }
	    Log.d("TestLocationProvider", "Step6");
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onLocationChanged(Location arg0) {
		  Log.d("TestLocationProvider", "Step7");
		  MainActivity.myMap.updateLocation(arg0);
		
	}
	
	public Location getLocation(){
		return presentLocation;
	}
	
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}}*/


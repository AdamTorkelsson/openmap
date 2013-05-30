package com.openmap.grupp1.maphandler;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


/**
 * A class to handle the users location and have methods to
 *  update the camera to the current location ,return the current location.
 */
public class LocationHandler implements LocationListener {
	private GoogleMap myMap;
	private Criteria criteria;
	private String provider;
	private LocationManager locmanager;
	private NearEventHandler nen;

	/**
	 * Constructor
	 * @param myMap The GoogleMap to be handled
	 * @param context The current context
	 */
	public LocationHandler(GoogleMap myMap,Context context){
		this.myMap = myMap;
		//connects the locationmanager to the available locationservice of the phone
		this.locmanager =  (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		LocationManager lm = (LocationManager) locmanager;

		//sets criteria and gets the best provider with that criteria
		criteria = new Criteria();
		provider = lm.getBestProvider(criteria, false);

		/*
		 * Creates a startlocation for the neareventhandler, 
		 * this specific location is only used for logical reasoning in the neareventhandler.
		 * Creates the neareventhandler that handles the criterias that the user have to fulfil to
		 * be attending to events or get notifications
		 */
		Location ss = new Location("nein");
		ss.setLatitude(0);
		ss.setLongitude(0);
		nen= new NearEventHandler(ss,myMap,context );

		//Request updates to (onlocationchanged) every 50 second
		lm.requestLocationUpdates(provider, 50000, 1, this);		

	}
	/**
	 *  A method to return the current location
	 * @return The current location in a LatLng
	 */
	public LatLng getMylocation(){
		return new LatLng(((LocationManager) locmanager).getLastKnownLocation(
				provider).getLatitude(),((LocationManager) 
						locmanager).getLastKnownLocation(provider).getLongitude());
	}

	/**
	 * A method to move the camera to the users current location
	 */
	public void updateToMyLocation(){
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
				new LatLng(((LocationManager) locmanager).getLastKnownLocation(
						provider).getLatitude(),((LocationManager) 
								locmanager).getLastKnownLocation(provider).getLongitude()),14 );

		myMap.animateCamera(update);
	}
	/*
	 * (non-Javadoc)
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 * If your location have changed it calls on NearEventHandler thats checks how you moved
	 * and/or if any events is near
	 * 
	 */
	@Override
	public void onLocationChanged(Location arg0) {
		nen.checkEvent(arg0);

	}
	/*
	 * (non-Javadoc)
	 * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled(String arg0) {

	}
	/*
	 * (non-Javadoc)
	 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(String arg0) {

	}
	/*
	 * (non-Javadoc)
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

	}

}

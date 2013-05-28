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

public class LocationHandler implements LocationListener {
	private GoogleMap myMap;
	private Criteria criteria;
	private String provider;
	private LocationManager locmanager;
	private NearEventHandler nen;
	public LocationHandler(GoogleMap myMap,Context context){
		this.myMap = myMap;
		this.locmanager =  (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		LocationManager lm = (LocationManager) locmanager;

		criteria = new Criteria();
		provider = lm.getBestProvider(criteria, false);
		Location ss = new Location("nein");
		 ss.setLatitude(0);
		 ss.setLongitude(0);
		 
		 nen= new NearEventHandler(ss,myMap,context );
		lm.requestLocationUpdates(provider, 50000, 1, this);		
		
	}

	public LatLng getMylocation(){
		return new LatLng(((LocationManager) locmanager).getLastKnownLocation(
				provider).getLatitude(),((LocationManager) 
						locmanager).getLastKnownLocation(provider).getLongitude());
	}
	
	public void updateToMyLocation(){
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
				new LatLng(((LocationManager) locmanager).getLastKnownLocation(
						provider).getLatitude(),((LocationManager) 
								locmanager).getLastKnownLocation(provider).getLongitude()),14 );
		
		myMap.animateCamera(update);
	}
	
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		nen.checkEvent(arg0);
		
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
		
	}

}

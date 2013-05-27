package com.openmap.grupp1.maphandler;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.openmap.grupp1.CreateDialogs;

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
	private Context context;
	private LocationManager locmanager;
	private CreateDialogs insertinfo = new CreateDialogs();
	private NearEventNotifier nen;
	private Boolean notifications = true;
	private OnCameraChanges occ;
	
	public LocationHandler(GoogleMap myMap,Context context){
		this.myMap = myMap;
		this.context = context;
		this.locmanager =  (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		LocationManager lm = (LocationManager) locmanager;

		criteria = new Criteria();
		provider = lm.getBestProvider(criteria, false);
		lm.requestLocationUpdates(provider, 5000, 1, this);		
		startNearEventNotifier();
	}

	public LatLng getMylocation(){
		return new LatLng(((LocationManager) locmanager).getLastKnownLocation(
				provider).getLatitude(),((LocationManager) 
						locmanager).getLastKnownLocation(provider).getLongitude());
	}
	
	public void startNearEventNotifier(){
		
		Location ss = new Location("nein");
		 ss.setLatitude(0);
		 ss.setLongitude(0);
		 nen= new NearEventNotifier(ss,myMap,context );
	}
	
	public void updateLocation(){
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
				new LatLng(((LocationManager) locmanager).getLastKnownLocation(
						provider).getLatitude(),((LocationManager) 
								locmanager).getLastKnownLocation(provider).getLongitude()),14 );
		
		myMap.animateCamera(update);
	}
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
		nen.checklocationandevent(arg0);
		
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

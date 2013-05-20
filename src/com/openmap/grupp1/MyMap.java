package com.openmap.grupp1;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;



public class MyMap extends Activity 
implements OnMapClickListener, OnMapLongClickListener, 
OnMarkerClickListener , LocationListener {
	 private GoogleMap myMap;
	 private Criteria criteria;
	 private String provider;
	 private Context context;
	 private Resources res;
	 private LatLng MYLOCATION ;
	 private CameraUpdate update;
	 private LocationManager locmanager;
	 private LatLng point;
	 
	 public MyMap(FragmentManager myFragmentManager, Object locmanager,Context context,Resources res) {
		//Map creator
		 
		 this.locmanager = (LocationManager) locmanager;
		 this.context = context;
		 this.res = res;
		  Log.d(TEXT_SERVICES_MANAGER_SERVICE, "ListenerStep0");
		  MapFragment myMapFragment  = (MapFragment)myFragmentManager.findFragmentById(R.id.map);
		  myMap = myMapFragment.getMap();

		  
		 
		  
		  //different map types
		  myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		  //myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		  //myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		  //myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

		  //enables all click
		  myMap.setOnMapClickListener(this);
		  myMap.setOnMapLongClickListener(this);
		  myMap.setOnMarkerClickListener(this);
		  
		  myMap.setMyLocationEnabled(true);
		  
		  //Creates an provider with the best criteria
		    
		 // onLocationChanged(myMap.getMyLocation());
		//  updateLocation(myMap.getMyLocation());
		  

		  LocationManager lm = (LocationManager) locmanager;
		  criteria = new Criteria();
		  provider = lm.getBestProvider(criteria, false);
		  //onLocationChanged(((LocationManager) locmanager).getLastKnownLocation(provider));
		  lm.requestLocationUpdates(provider, 3000, 1, this);
		  this.onLocationChanged(((LocationManager) locmanager).getLastKnownLocation(provider));
		  
	 }


	 @Override
	 public void onMapClick(LatLng point) {
		 
		 
	 }
	
	 CreateDialogs insertinfo = new CreateDialogs();
	 

	 @Override
	 public void onMapLongClick(LatLng point) {
		 this.point = point;
		// create interactive dialog window
		 	
		 	Log.d(TEXT_SERVICES_MANAGER_SERVICE, "hej1");
		 	insertinfo.insertInfo(context, point, res, myMap); 
		 	Log.d(TEXT_SERVICES_MANAGER_SERVICE, "hej2");
		 	}

	 public void createonemoreDialog(){
		 insertinfo.showsearchEvents(context); 
	 }
	 
	 
	@Override
	public boolean onMarkerClick(Marker marker) {
		marker.setVisible(false);
		CreateDialogs showinfo = new CreateDialogs();
	 	Log.d(TEXT_SERVICES_MANAGER_SERVICE, "hej1");
	 	showinfo.showInfo(context, marker.getPosition(), res, myMap);
	 	marker.setVisible(true);
		return true; 
	}


	@Override
	public void onLocationChanged(Location arg0) {
		
		MYLOCATION = new LatLng(arg0.getLatitude(), arg0.getLongitude());
		  //move camera to your positon
		  CameraUpdate update = CameraUpdateFactory.newLatLngZoom(MYLOCATION,14 );
		  myMap.animateCamera(update);
		  myMap.addMarker(new MarkerOptions().position(MYLOCATION).title("Your Position2"));
		
	}


	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		Log.d("LocationListener", "onProviderDisabled");
		
	}


	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		Log.d("LocationListener", "onProviderEnabled");
		
	}


	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		Log.d("LocationListener", "onStatusChanged");
		// TODO Auto-generated method stub
		
	}







}

package com.openmap.grupp1;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;



public class MyMap extends Activity 
implements OnMapClickListener, OnMapLongClickListener, 
OnMarkerClickListener , LocationListener , OnCameraChangeListener{
	 private GoogleMap myMap;
	 private Criteria criteria;
	 private String provider;
	 private Context context;
	 private Resources res;
	 private String map;
	 private LatLng MYLOCATION ;
	 private CameraUpdate update;
	 private LocationManager locmanager;
	 private LatLng point;
	 private NearEventNotifier neEvNotifier;
	 private CameraPosition cameraposition;
	 private LoadMarkers loadmarkers;
	 
	
	 public MyMap(FragmentManager myFragmentManager, Object locmanager,Context context,Resources res) {
		//Map creator
		 
		 this.locmanager = (LocationManager) locmanager;
		 this.context = context;
		 this.res = res;
		  MapFragment myMapFragment  = (MapFragment)myFragmentManager.findFragmentById(R.id.map);
		  myMap = myMapFragment.getMap();
		  loadmarkers = new LoadMarkers(myMap);

		  //enables all click
		  myMap.setOnMapClickListener(this);
		  myMap.setOnMapLongClickListener(this);
		  myMap.setOnMarkerClickListener(this);
		  
		  myMap.setMyLocationEnabled(true);

		  myMap.setOnCameraChangeListener(this);

		  LocationManager lm = (LocationManager) locmanager;

		  criteria = new Criteria();
		  provider = lm.getBestProvider(criteria, false);
	
		 	CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
		 			new LatLng(((LocationManager) locmanager).getLastKnownLocation(
		 					provider).getLatitude(),((LocationManager) 
		 			locmanager).getLastKnownLocation(provider).getLongitude()),14 );
		 	myMap.animateCamera(update);
		 	Location mylocation = new Location("temp");
	
		  
		  //Makes a NearEventNotifier thats check if you have been near an event more than 10 seconds
		  neEvNotifier = new NearEventNotifier(((LocationManager) locmanager).getLastKnownLocation(provider), myMap);

		  //starting at your location
		  onLocationChanged(((LocationManager) locmanager).getLastKnownLocation(provider));
		  // request updates every 100 second, Change to every 5 minutes
		  lm.requestLocationUpdates(provider, 10000, 1, this);
		 // 
		  Log.d(TEXT_SERVICES_MANAGER_SERVICE, "ListenerStep2");
	 }


	 @Override
	 public void onMapClick(LatLng point) {
		 
		 
	 }
	
	 CreateDialogs insertinfo = new CreateDialogs();
	 

	 @Override
	 public void onMapLongClick(LatLng point) {
		 this.point = point;

			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(point,14 );
			myMap.animateCamera(update);
			Marker m = myMap.addMarker(new MarkerOptions().position(point).title("This location?"));
			m.showInfoWindow();
		// create interactive dialog window
		 	neEvNotifier.NearEventNotifier(point);
		 	Log.d(TEXT_SERVICES_MANAGER_SERVICE, "hej1");
		 	insertinfo.confirmLocationPopup(context, point, res, myMap); 
		 	Log.d(TEXT_SERVICES_MANAGER_SERVICE, "hej2");
		 	}

	 public void createonemoreDialog(){
		 insertinfo.showsearchEvents(context); 
	 }
	 
	 public void setMap(String map){
		 
   	  	 
		 if (map.equals("Default"))
			 myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		 if (map.equals("Kukkarta"))
			 myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		 if (map.equals("Fittkarta"))
			 myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		 if(map.equals("Runkkarta"))	 
			 myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
	 }
	 
	 
	@Override
	public boolean onMarkerClick(Marker marker) {
		//Flytta möjligtvis(lägg i konstrukorn, själva object skapandet)
		CreateDialogs showinfo = new CreateDialogs();
	 	Log.d(TEXT_SERVICES_MANAGER_SERVICE, "hej1");
	 	showinfo.showInfo(context, marker.getPosition(), res, myMap);
	 	marker.setVisible(true);
		return true; 
	}


	@Override
	public void onCameraChange(CameraPosition arg0) {
		loadmarkers.addMarkersInCameraView(arg0);
		
	}
	
	@Override
	public void onLocationChanged(Location arg0) {
		
		
		MYLOCATION = new LatLng(arg0.getLatitude(), arg0.getLongitude());
		  //move camera to your positon
		  myMap.addMarker(new MarkerOptions().position(MYLOCATION).title("Your Position2"));
		  neEvNotifier.checkEvent(arg0);
		
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

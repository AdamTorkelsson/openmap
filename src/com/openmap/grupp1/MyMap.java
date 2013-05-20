package com.openmap.grupp1;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;



public class MyMap extends Activity 
implements OnMapClickListener, OnMapLongClickListener, OnMarkerClickListener{
	 private GoogleMap myMap;
	 Location myLocation;
	 private LocationManager locationManager;
	 private Criteria criteria;
	 private String provider;
	 private Context context;
	 private Resources res;
	 private String map;
	 
	 
	 
	
	 public MyMap(FragmentManager myFragmentManager, Object locmanager,Context context,Resources res) {
		//Map creator
		 this.context = context;
		 this.res = res;
		 
		  MapFragment myMapFragment  = (MapFragment)myFragmentManager.findFragmentById(R.id.map);
		  myMap = myMapFragment.getMap();

		  //enables my location
		  myMap.setMyLocationEnabled(true);
		  
		  //different map types
	
		  
		  
		  //myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		  //myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		  //myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		  //myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

		  //enables all click
		  myMap.setOnMapClickListener(this);
		  myMap.setOnMapLongClickListener(this);
		  myMap.setOnMarkerClickListener(this);
		  
		  // Makes it start at your location + adds an marker to that position
		  locationManager = (LocationManager) locmanager;
		 
		  //Creates an provider with the best criteria
		  criteria = new Criteria();
		  provider = locationManager.getBestProvider(criteria, false);
		  
		  //gets your location and transforms it to an LatLng
		  Location location = locationManager.getLastKnownLocation(provider);
		  LatLng MYLOCATION = new LatLng(location.getLatitude(), location.getLongitude());
		  
		  //move camera to your positon
		  CameraUpdate update = CameraUpdateFactory.newLatLngZoom(MYLOCATION,10 );
		  myMap.animateCamera(update);
		  myMap.addMarker(new MarkerOptions().position(MYLOCATION).title("Your Position"));
		  
	 }


	 @Override
	 public void onMapClick(LatLng point) {
		 
		 
	 }
	
	 CreateDialogs insertinfo = new CreateDialogs();
	 private LatLng point;

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
		marker.setVisible(false);
		CreateDialogs showinfo = new CreateDialogs();
	 	Log.d(TEXT_SERVICES_MANAGER_SERVICE, "hej1");
	 	showinfo.showInfo(context, marker.getPosition(), res, myMap);
	 	marker.setVisible(true);
		return true; 
	}

}

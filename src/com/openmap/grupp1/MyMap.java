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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.plus.model.people.Person.Image;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/*
 * add button to get directions
 * 
 */

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
	 private LatLng onMapLongPoint;
	 private CreateDialogs insertinfo = new CreateDialogs();
	 
	
	 public MyMap(FragmentManager myFragmentManager, Object locmanager,Context context,Resources res) {
		//Map creator
		 
		 this.locmanager = (LocationManager) locmanager;
		 this.context = context;
		 this.res = res;
		  MapFragment myMapFragment  = (MapFragment)myFragmentManager.findFragmentById(R.id.map);
		  myMap = myMapFragment.getMap();
		 loadmarkers = new LoadMarkers(myMap,res);
		  Log.d(TEXT_SERVICES_MANAGER_SERVICE, "duärhär");
		  //enables all click
		  myMap.setOnMapClickListener(this);
		  myMap.setOnMapLongClickListener(this);
		  myMap.setOnMarkerClickListener(this); 
		  
		  //
		  myMap.setMyLocationEnabled(true);
		  myMap.setOnCameraChangeListener(this);
		  
		  LocationManager lm = (LocationManager) locmanager;
		  Log.d(TEXT_SERVICES_MANAGER_SERVICE, "duärhär2");
		  criteria = new Criteria();
		  provider = lm.getBestProvider(criteria, false);
	
		 	CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
		 			new LatLng(((LocationManager) locmanager).getLastKnownLocation(
		 					provider).getLatitude(),((LocationManager) 
		 			locmanager).getLastKnownLocation(provider).getLongitude()),14 );
		 	myMap.animateCamera(update);
		 	 Log.d(TEXT_SERVICES_MANAGER_SERVICE, "duärhär3");
		  //Makes a NearEventNotifier thats check if you have been near an event more than 10 seconds

		  //starting at your location
		//  onLocationChanged(((LocationManager) locmanager).getLastKnownLocation(provider));

	/*	neEvNotifier = new NearEventNotifier(((LocationManager) locmanager).
				  getLastKnownLocation(provider), myMap,context);*/
		  Log.d(TEXT_SERVICES_MANAGER_SERVICE, "duärhär4");
		  // request updates every 100 second, Change to every 5 minutes
		  lm.requestLocationUpdates(provider, 10000, 1, this);
		 
		  Log.d(TEXT_SERVICES_MANAGER_SERVICE, "ListenerStep2");
		  
	 }
	 int i = 0;
	 private void testNrOfPoints(LatLng point){
		 i++;
		 myMap.addMarker(new MarkerOptions().position(point));
		 if(i ==200){
		 				}
		 else {
			 testNrOfPoints(new LatLng(point.latitude - 0.5, point.longitude));}
	 }

	 @Override
	 public void onMapClick(LatLng point) {
		
		 	 
	 }
	
	 @Override
	 public void onMapLongClick(LatLng point) {
		 //Error if they are exactly the same point, but due to the many decimals this is very unusual
		 	Log.d("Hejhej", "LatLnguniqe" + point.toString());
		 	this.onMapLongPoint = point;
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(point,myMap.getMaxZoomLevel()-2);/*ADD MAX ZOOM HERE INSTEAD, GOOGLE*/ 
			myMap.animateCamera(update);
			Marker marker = myMap.addMarker(new MarkerOptions().position(point).title("This location?"));
			
			marker.showInfoWindow();
			// create interactive dialog window
		 	Log.d(TEXT_SERVICES_MANAGER_SERVICE, "hej1");
		 	insertinfo.confirmLocationPopup(context, marker, myMap); 
		 	Log.d(TEXT_SERVICES_MANAGER_SERVICE, "hej2");
		 	}

	 public void createonemoreDialog(){
		
	 }
	 
	 public void setMap(String map){
		 if (map.equals("Hybrid"))
			 myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		 if (map.equals("Satellite"))
			 myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		 if (map.equals("Normal"))
			 myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		 if(map.equals("Terrain"))	 
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
		//loadmarkers.addMarkersInCameraView(arg0);
		
	}
	
	@Override
	public void onLocationChanged(Location arg0) {
		MYLOCATION = new LatLng(arg0.getLatitude(), arg0.getLongitude());
		 //move camera to your positon


		//myMap.addMarker(new MarkerOptions().position(MYLOCATION).title("Your Position2"));
	//	neEvNotifier.checklocationandevent(arg0);
		
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


	public void addMarker() {
		// ADD title and type here in markerfactory to create different markers
		MarkerFactory markerFactory = new MarkerFactory();
		Bitmap scr = markerFactory.createPic("title", res, "Location");
		Marker m = myMap.addMarker(new MarkerOptions().position(onMapLongPoint).icon(BitmapDescriptorFactory.fromBitmap(scr)));
		m.isVisible();
		/*myMap.addMarker(new MarkerOptions()
		.position(onMapLongPoint)*/
		
		}
	
	/*.icon(BitmapDescriptorFactory
		.fromBitmap(markerfactory.createPic("Title",res,"Event"))*/

	
	public void addMarker(LatLng location , String Title) {
		
		// TODO Auto-generated method stub
		
	}

	
	public void addMarker(LatLng location , String Title, String Description) {
		
		// TODO Auto-generated method stub
		
	}
	
	public void addMarker(LatLng location , String Title ,String Description , Image img) {
		
		// TODO Auto-generated method stub
		
	}






}

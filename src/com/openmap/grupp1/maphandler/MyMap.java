package com.openmap.grupp1.maphandler;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Random;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.plus.model.people.Person.Image;
import com.openmap.grupp1.CreateDialogs;
import com.openmap.grupp1.R;
import com.openmap.grupp1.R.id;

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
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/*
 * This class is the heart of this application
 * It creates the map, 
 * handles the fragment manager,
 * listen on different user interactions on the screen. 
 * It is also the class that changes what the user is 
 * going to see due to having the GoogleMap object.
 * 
 * 
 */

public class MyMap extends Activity 
implements OnMapClickListener, OnMapLongClickListener,Serializable, 
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
	private CameraPosition cameraposition;
	private LatLng onMapLongPoint; // holds the location temporary for the user while creating the event
	private CreateDialogs insertinfo = new CreateDialogs();
	private final String PREFS_NAME ="MySharedPrefs"; 
	private NearEventNotifier nen;
	private Boolean notifications = true;
	private LoadMarkersTempAsyncTask lmtat;
	private LatLngBounds database;


	public MyMap(Context context) {
		//Map creator

		this.locmanager =  (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		this.context = context;
		this.res = context.getResources();

		myMap = ((MapFragment) ((Activity) context).getFragmentManager().findFragmentById(R.id.map)).getMap();
		Log.d(TEXT_SERVICES_MANAGER_SERVICE, "duärhär");
		//enables all click
		myMap.setOnMapClickListener(this);
		myMap.setOnMapLongClickListener(this);
		myMap.setOnMarkerClickListener(this); 

		//
		myMap.setMyLocationEnabled(true);
		myMap.setOnCameraChangeListener(this);
	/*	LoadMarkersAsyncTask lmat = new LoadMarkersAsyncTask(myMap, res);
		 lmat.execute();*/
		
		
		 Log.d(TEXT_SERVICES_MANAGER_SERVICE, "Neareventnotifier1");
		
		 
		 Location ss = new Location("nein");
		 ss.setLatitude(0);
		 ss.setLongitude(0);
		 nen= new NearEventNotifier(ss,myMap,context );
		
		 
		 Log.d(TEXT_SERVICES_MANAGER_SERVICE, "Neareventnotifier2");
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

		Log.d(TEXT_SERVICES_MANAGER_SERVICE, "duärhär4");
		// request updates every 100 second, Change to every 5 minutes
		lm.requestLocationUpdates(provider, 5000, 1, this);

		Log.d(TEXT_SERVICES_MANAGER_SERVICE, "ListenerStep2");
	/*	mylocation = new LatLng(((LocationManager) locmanager).getLastKnownLocation(
				provider).getLatitude(),((LocationManager) 
						locmanager).getLastKnownLocation(provider).getLongitude());*/

	}
	int i = 0;
	public LatLngBounds setandgetBounds(){
		if(i == 0){
			LatLng mylocation = new LatLng(((LocationManager) locmanager).getLastKnownLocation(
					provider).getLatitude(),((LocationManager) 
							locmanager).getLastKnownLocation(provider).getLongitude());
			database = new LatLngBounds(new LatLng(mylocation.latitude - 1, mylocation.longitude-1),new LatLng(
					 mylocation.latitude+1,mylocation.longitude+1));
			i++;
			return database;
		}
		
		else{
			Projection p = myMap.getProjection();
			LatLng nearLeft = p.getVisibleRegion().nearLeft;
			LatLng farRight = p.getVisibleRegion().farRight;
			database = new LatLngBounds(new LatLng(nearLeft.latitude - 1, nearLeft.longitude-1),new LatLng(
				 farRight.latitude+1,farRight.longitude+1));
			return database;}
	
	}

	@Override
	public void onMapClick(LatLng point) {
		

	}
	public void checkIn(LatLng point){
		insertinfo.checkInDialog(context, myMap);
	}

		@Override
		public void onMapLongClick(LatLng point) {
			//Error if they are exactly the same point, but due to the many decimals this is very unusual
			//Log.d("Hejhej", "LatLnguniqe" + point.toString());
			this.onMapLongPoint = point;
			//String tempLat = String.valueOf(point.latitude);
			//String tempLng = String.valueOf(point.longitude);
			Log.d("Text", "LatLngpoint " + point);
			//Saves the latitude and longitude in the shared preferences to use in AddTagActivity
			SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("markerLat", String.valueOf(point.latitude));
			editor.putString("markerLng", String.valueOf(point.longitude));
			editor.commit();
			
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(point,myMap.getMaxZoomLevel()-4); 
			myMap.animateCamera(update);
			Marker marker = myMap.addMarker(new MarkerOptions().position(point).title("This location?"));
			marker.showInfoWindow();
			// create interactive dialog window
			insertinfo.confirmLocationPopup(context, marker, myMap); 
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
	MarkerInfoWindow infowindow = new MarkerInfoWindow();

	@Override
	public boolean onMarkerClick(Marker marker) {
		//Flytta möjligtvis(lägg i konstrukorn, själva object skapandet)
		/*
		 * Database Get Title and description
		 * 
		 * Tip!
		 * can get the location identifier by using marker.getPosition();
		 * change it to LatLng by " new LatLng(marker.getPosition().latitude,...)
		 */

		Log.d(TEXT_SERVICES_MANAGER_SERVICE, "onMarkerClick" + marker.getPosition());
		infowindow.showInfo(context, marker.getPosition(), res, myMap);
		return true; 
	}

	
	@Override
	public void onCameraChange(CameraPosition arg0) {
		Log.d(TEXT_SERVICES_MANAGER_SERVICE, "OnCameraChange");
		Projection p = myMap.getProjection();
		LatLng nearLeft = p.getVisibleRegion().nearLeft;
		LatLng farRight = p.getVisibleRegion().farRight;
		if(arg0.zoom > 6 && !database.contains(farRight) &&
				!database.contains(nearLeft)){
			Log.d(TEXT_SERVICES_MANAGER_SERVICE, "OnCameraChange2");
			myMap.clear();
			database = new LatLngBounds(
					new LatLng(nearLeft.latitude-1,nearLeft.longitude-1),
					new LatLng(farRight.latitude+1,farRight.longitude +1));
			
			LoadMarkersTempAsyncTask lmtat = new LoadMarkersTempAsyncTask(myMap,res,database);
			lmtat.execute();
	
		}
		
		
		
	}




	@Override
	public void onLocationChanged(Location arg0) {
		if(notifications){
		nen.checklocationandevent(arg0);}


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



	public GoogleMap getMap() {
		return myMap;
	}


	public void startNotification() {
		notifications = true;
		
	}


	public void stopNotification() {
		notifications = false;
		
	}



	/*public void addMarker(String Title) {
		MarkerFactory markerFactory = new MarkerFactory();
		Bitmap scr = markerFactory.createPic(Title, res, "Location");
		Marker m = myMap.addMarker(new MarkerOptions().position(onMapLongPoint).icon(BitmapDescriptorFactory.fromBitmap(scr)));
		m.isVisible();
	}*/







}

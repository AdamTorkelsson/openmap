package com.openmap.grupp1.mapview;



import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openmap.grupp1.R;
import com.openmap.grupp1.PopupandDialogHandler;
import com.openmap.grupp1.maphandler.LocationHandler;
import com.openmap.grupp1.maphandler.CameraChangeHandler;
import com.openmap.grupp1.maphandler.MarkerHandler;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/*
 * It creates the map, 
 * handles the fragment manager,
 * 
 */

public class MyMap 
implements  OnMapLongClickListener,OnMarkerClickListener{

	private GoogleMap myMap;
	private Context mCtx;
	private CameraChangeHandler occ;
	private final String PREFS_NAME ="MySharedPrefs";

	public MyMap(Context context) {
		//Map creator
		
		this.mCtx = context;

		myMap = ((MapFragment) ((Activity) mCtx).getFragmentManager().findFragmentById(R.id.map)).getMap();

		//enables all click
	
		myMap.setMyLocationEnabled(true);
		myMap.setOnMapLongClickListener(this);
		myMap.setOnMarkerClickListener(this); 
		
		LocationHandler lh = new LocationHandler(myMap, mCtx);
		//Makes the camera move to your location
		lh.updateToMyLocation();
		//Creates The CameraChangeHandler
		occ = new CameraChangeHandler(myMap,mCtx,lh.getMylocation());
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

	public GoogleMap getMap() {
		return myMap;
	}
	
	@Override
	public void onMapLongClick(LatLng point) {	
		//Saves the latitude and longitude in the shared preferences to use in AddTagActivity
		SharedPreferences settings = mCtx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("markerLat", String.valueOf(point.latitude));
		editor.putString("markerLng", String.valueOf(point.longitude));
		editor.commit();
		
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(point,myMap.getMaxZoomLevel()-4); 
		myMap.animateCamera(update);
		Marker marker = myMap.addMarker(new MarkerOptions().position(point).title("This location?"));
		marker.showInfoWindow();
		// create interactive dialog window
		PopupandDialogHandler insertinfo = new PopupandDialogHandler(mCtx);
		insertinfo.confirmLocationPopup(marker, myMap); 
		
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		MarkerHandler infowindow = new MarkerHandler();
		infowindow.showInfo(mCtx, marker.getPosition(), mCtx.getResources(), myMap);
		return true; 
	}



}

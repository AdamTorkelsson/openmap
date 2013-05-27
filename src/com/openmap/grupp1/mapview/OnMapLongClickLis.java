package com.openmap.grupp1.mapview;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openmap.grupp1.CreateDialogs;

public class OnMapLongClickLis implements OnMapLongClickListener{
private Context context;
private final String PREFS_NAME ="MySharedPrefs";
private GoogleMap myMap;
private CreateDialogs insertinfo = new CreateDialogs();

public OnMapLongClickLis(GoogleMap myMap,Context context){
		this.context = context;
		myMap.setOnMapLongClickListener(this);
		this.myMap = myMap;
	}
	@Override
	public void onMapLongClick(LatLng point) {
		
		//Saves the latitude and longitude in the shared preferences to use in AddTagActivity
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
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

}

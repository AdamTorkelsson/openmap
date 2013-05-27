package com.openmap.grupp1.mapview;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;

public class OnMarkerClickLis implements OnMarkerClickListener {
private MarkerInfoWindow infowindow = new MarkerInfoWindow();
private Context context;
private GoogleMap myMap;
	public OnMarkerClickLis(GoogleMap myMap, Context context) {
		this.context = context;
		this.myMap = myMap;
		myMap.setOnMarkerClickListener(this); 
	}

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


		infowindow.showInfo(context, marker.getPosition(), context.getResources(), myMap);
		return true; 
	}

}

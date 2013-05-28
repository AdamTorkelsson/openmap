package com.openmap.grupp1.maphandler;

import java.util.Random;

import android.content.Context;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class CameraChangeHandler implements  OnCameraChangeListener{
private GoogleMap myMap;
private Context context;
private LatLng mylocation;
private LatLngBounds database;
private int i = 0;
private MarkerHandler markerhandler;

	public CameraChangeHandler(GoogleMap myMap,Context context,LatLng mylocation){
		myMap.setOnCameraChangeListener(this);
		this.context = context;
		this.myMap = myMap;
		this.mylocation = mylocation;
		markerhandler = new MarkerHandler();
	}
/*
 * If you moves your camera it tells LoadMarkersAsynctask to load markers in the
 * new area if the zoom level is higher than 6( this is because we want to prevent
 *  to much markers on the screen. 
 * */
	
	@Override
	public void onCameraChange(CameraPosition arg0) {
		Projection p = myMap.getProjection();
		LatLng nearLeft = p.getVisibleRegion().nearLeft;
		LatLng farRight = p.getVisibleRegion().farRight;
		if(arg0.zoom > 6 && !database.contains(farRight) &&
				!database.contains(nearLeft)){
			
			database = new LatLngBounds(
					new LatLng(nearLeft.latitude,nearLeft.longitude),
					new LatLng(farRight.latitude,farRight.longitude));
			
			markerhandler.addMarkersToScreen(myMap,context.getResources(),database);

		}}
		
		
	/*setandget bounds, first time it sets your location + some area around it. 
	 * The rest of the times it sets it around the location were you have the camera.
	*/
		
	public LatLngBounds setandgetBounds(){
			
			if(i == 0){
				database = new LatLngBounds(new LatLng(mylocation.latitude, mylocation.longitude),new LatLng(
						 mylocation.latitude,mylocation.longitude));
				i++;
				return database;
			}
			
			else{
				Projection p = myMap.getProjection();
				LatLng nearLeft = p.getVisibleRegion().nearLeft;
				LatLng farRight = p.getVisibleRegion().farRight;
				database = new LatLngBounds(new LatLng(nearLeft.latitude, nearLeft.longitude),new LatLng(
					 farRight.latitude,farRight.longitude));
				return database;}
		
		}

	public void refreshMarkers() {
		markerhandler.addMarkersToScreen(myMap,context.getResources()
				,setandgetBounds());
		
	}
		
		
		
	}


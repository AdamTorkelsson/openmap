/*package com.openmap.grupp1.maphandler;

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
	private LatLngBounds latLngBounds;
	private int i = 0;
	private MarkerHandler markerhandler;

	public CameraChangeHandler(GoogleMap myMap,Context context,LatLng mylocation){
		myMap.setOnCameraChangeListener(this);
		this.context = context;
		this.myMap = myMap;
		this.mylocation = mylocation;
		markerhandler = new MarkerHandler();
	}
<<<<<<< HEAD

	/*
	 * If you move your camera it tells LoadMarkersAsynctask to load markers in the
	 * new area if the zoom level is higher than 6 (this is because we want to prevent
	 * too much markers on the screen). 
	 */
/*
 * If you moves your camera it tells LoadMarkersAsynctask to load markers in the
 * new area if the zoom level is higher than 6( this is because we want to prevent
 *  to much markers on the screen. 
 * */
	/*
>>>>>>> e4d91407096508bcf9ecc20e11485c9580fc3541
	@Override
	public void onCameraChange(CameraPosition arg0) {
		Projection p = myMap.getProjection();
		LatLng nearLeft = p.getVisibleRegion().nearLeft;
		LatLng farRight = p.getVisibleRegion().farRight;
		//If zoom level is larger than 6, add the markers within the bounds of the screen
		if(arg0.zoom > 6 /*&& (!database.contains(farRight) &&
				!database.contains(nearLeft)) || 
				markerhandler.IfFull()){
			//Defines the bounds of the current screen
			latLngBounds = new LatLngBounds(
					new LatLng(nearLeft.latitude,nearLeft.longitude),
					new LatLng(farRight.latitude,farRight.longitude));
			//Adds the markers to the screen
			markerhandler.addMarkersToScreen(myMap,context.getResources(),latLngBounds);

		}}



}
*/
package com.openmap.grupp1.mapview;

import android.content.Context;
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
		infowindow.showInfo(context, marker.getPosition(), context.getResources(), myMap);
		return true; 
	}

}

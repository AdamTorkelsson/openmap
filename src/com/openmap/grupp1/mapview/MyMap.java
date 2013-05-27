package com.openmap.grupp1.mapview;



import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.openmap.grupp1.CreateDialogs;
import com.openmap.grupp1.R;
import com.openmap.grupp1.maphandler.LocationHandler;
import com.openmap.grupp1.maphandler.OnCameraChanges;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

/*
 * This class is the heart of this application
 * It creates the map, 
 * handles the fragment manager,
 * 
 */

public class MyMap extends Activity 
implements OnMapClickListener{

	private GoogleMap myMap;
	private Context context;
	private CreateDialogs insertinfo = new CreateDialogs();
	private OnCameraChanges occ;


	public MyMap(Context context) {
		//Map creator

		
		this.context = context;

		myMap = ((MapFragment) ((Activity) context).getFragmentManager().findFragmentById(R.id.map)).getMap();
		Log.d(TEXT_SERVICES_MANAGER_SERVICE, "duärhär");
		//enables all click
		myMap.setOnMapClickListener(this);
		myMap.setMyLocationEnabled(true);

		LocationHandler lh = new LocationHandler(myMap, context);
		lh.updateLocation();
		
		new OnMapLongClickLis(myMap,context);
		new OnMarkerClickLis(myMap,context);
		occ = new OnCameraChanges(myMap,context,lh.getMylocation());

	}
	

	public LatLngBounds setandgetBounds(){
		return occ.setandgetBounds();
			}
	

	@Override
	public void onMapClick(LatLng point) {
		

	}
	public void checkIn(LatLng point){
		insertinfo.checkInDialog(context, myMap);
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




}

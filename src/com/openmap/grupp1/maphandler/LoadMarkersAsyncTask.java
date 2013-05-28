package com.openmap.grupp1.maphandler;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openmap.grupp1.database.GetLocationTask;

import com.openmap.grupp1.database.LocationMarker;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

public class LoadMarkersAsyncTask extends AsyncTask<Void,LocationMarker,Integer>{
	private GoogleMap myMap;
	private Resources res;
	private ArrayList<LocationMarker> databaselocationpair;
	private ArrayList<LocationMarker> createdLatLng = new ArrayList<LocationMarker>();
	private GetLocationTask glt;
	private MarkerHandler  mf = new MarkerHandler();
	LatLngBounds bounds;
	
	public LoadMarkersAsyncTask(GoogleMap myMap, Resources res, LatLngBounds bounds){
		this.bounds = bounds;
		this.myMap = myMap;
		this.res = res;
		Log.d("Step1", "AsyncTaskStep2");
		glt = new GetLocationTask();
		
		glt.setMinMaxLatLng(bounds.southwest.latitude, bounds.southwest.longitude,
				bounds.northeast.latitude, bounds.northeast.longitude);
		glt.execute();
	
		//getfromdatabase(nearleft - p ,farright + p );

	
		
		//database = new LatLngBounds(nearleft,farright);

	}
	/*
	 */
	@Override
	protected Integer doInBackground(Void... params) {
		try {
			databaselocationpair = glt.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			for(LocationMarker ll : databaselocationpair){
			//	Log.d("Text","LoadMarkersAsyncTask2.2");
				if(!createdLatLng.contains(ll)){
					//scr = createPic(ll.getTitle(), "Location");
				//	Log.d("Text","LoadMarkersAsyncTask2.3");
					
					publishProgress(ll);
					createdLatLng.add(ll);	
		
				//	Log.d("Text","LoadMarkersAsyncTask3" + databaselocationpair.get(2).getLatLng().toString());
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//}
				}}
			
			return 1;
	}
	protected void onProgressUpdate(LocationMarker... params){
		myMap.addMarker(new MarkerOptions()
		.position(params[0].getLatLng())
		.icon(BitmapDescriptorFactory
				.fromBitmap(mf.createPic(params[0].getTitle(), res, "Location"))));
		
	}}

package com.openmap.grupp1.maphandler;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.LatLngBoundsCreator;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.maps.model.VisibleRegionCreator;
import com.openmap.grupp1.R;
import com.openmap.grupp1.R.drawable;
import com.openmap.grupp1.database.GetLocationTask;
import com.openmap.grupp1.database.LocationMarker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

public class LoadMarkersTempAsyncTask extends AsyncTask<Void,LocationMarker,Integer>{
	private GoogleMap myMap;
	private Resources res;
	private Bitmap scr;
	private double p = 0.1;
	private Boolean over100markers = false;
	private ArrayList<LocationMarker> databaselocationpair;
	private ArrayList<LocationMarker> createdLatLng = new ArrayList<LocationMarker>();
	private ArrayList<Marker> createdMarkers = new ArrayList<Marker>();
	private LatLng farright;
	private LatLng nearleft;
	private LatLng databasenearleft;
	private LatLng databasefarright;
	private LatLngBounds llb;
	private LatLngBounds database;
	private VisibleRegion visibleregion;
	private GetLocationTask glt;
	private int deletenumber = 0;
	private int k = 0;
	public LoadMarkersTempAsyncTask(GoogleMap myMap, Resources res, LatLngBounds bounds){

		this.myMap = myMap;
		this.res = res;
		Log.d("Step1", "AsyncTaskStep2");
		
		glt = new GetLocationTask();
		glt.setMinMaxLatLng(bounds.southwest.latitude, bounds.southwest.longitude,
				bounds.northeast.latitude, bounds.northeast.longitude);
		glt.execute();

		try {
			databaselocationpair = glt.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//getfromdatabase(nearleft - p ,farright + p );

	
		
		//database = new LatLngBounds(nearleft,farright);

	}
	/*
	 */
	@Override
	protected Integer doInBackground(Void... params) {
		
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
		Marker m = myMap.addMarker(new MarkerOptions()
		.position(params[0].getLatLng())
		.icon(BitmapDescriptorFactory
				.fromBitmap(createPic(params[0].getTitle(), "Location"))));
	}


	private Bitmap createPic(String stringTitle,  String type){
		int color;
		if(type == "Event"){
			color = Color.BLACK;
		}
		else if(type == "Location"){
			color = Color.GREEN;}
		else if(type == "Owners"){
			color = Color.BLUE;}
		else{
			Log.d("TEST", "wrong type");
			color = Color.RED;
		}
		color = Color.BLACK;

		Bitmap srv = BitmapFactory.decodeResource(res, R.drawable.markerpick); 
		Bitmap src = Bitmap.createScaledBitmap(srv, srv.getWidth()+ srv.getWidth(), 
				srv.getHeight() + srv.getHeight(), false);
		Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);


		if(stringTitle.length() > 7){
			stringTitle = (String) stringTitle.subSequence(0, 6);
			stringTitle = stringTitle + "...";
		}

		Canvas cs = new Canvas(dest);
		Paint tPaint = new Paint();
		tPaint.setTextSize(35);
		tPaint.setColor(color);
		tPaint.setStyle(Style.FILL);
		cs.drawBitmap(src, 0f, 0f, null);
		float height = tPaint.measureText("yY");
		float width = tPaint.measureText(stringTitle);
		float x_coord = (src.getWidth() - width)/2;
		cs.drawText(stringTitle, x_coord, height-3f, tPaint); 
		return dest;}

}


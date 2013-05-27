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
import com.openmap.grupp1.database.LocationPair;

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

public class LoadMarkersAsyncTask extends AsyncTask<Void,LocationPair,Integer>{
	private GoogleMap myMap;
	private Resources res;
	private Bitmap scr;
	private double p = 0.1;
	private Boolean over100markers = false;
	private ArrayList<LocationPair> databaselocationpair;
	private ArrayList<LocationPair> createdLatLng = new ArrayList<LocationPair>();
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
	
	public LoadMarkersAsyncTask(GoogleMap myMap, Resources res){

		this.myMap = myMap;
		this.res = res;
		Log.d("Step1", "AsyncTaskStep2");
		
		glt = new GetLocationTask();
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

		database = new LatLngBounds(new LatLng(0,0),new LatLng(90,90));
		visibleregion = myMap.getProjection().getVisibleRegion();
		farright =	visibleregion.farRight;
		nearleft =	visibleregion.nearLeft;
		databasenearleft = visibleregion.nearLeft;
		databasefarright = visibleregion.farRight;
		llb = new LatLngBounds(this.nearleft,this.farright);
		Log.d("Text","LoadMarkersAsyncTask1" + farright.toString());
		Log.d("Text","LoadMarkersAsyncTask1" + nearleft.toString());
		Log.d("Text","LoadMarkersAsyncTask12" + databaselocationpair.get(0).getLatitute());
		Log.d("Text","LoadMarkersAsyncTask12" + databaselocationpair.get(0).getLongitude());
		Log.d("Text","LoadMarkersAsyncTask1" + database.toString());
		checkAndSetNewBounds();

	}
	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 * 
	 * Rules for loading markers
	 * 
	 * Do as long as it is less than 100 markers continue to add
	 * For every locationpair check if its inside boundary
	 * Boundary is checked out and changed if necessary every 6 loop
	 * if boundary is the same six locations outside is added( 0.1 lat/lng outside)
	 * after every publish the thread sleep for 0.1 second
	 * 
	 * Maybe change so the locationPair ll : LatLng goes 
	 * around instead of start from the beginning each time
	 * 
	 * Maybe add so after 200 markers it starts to remove the first one added
	 * removes all markers from the oldarray and 
	 * 
	 * Is one problem , i still deletes those within the screen because the 
	 * farright and nearleft is used double and is an logical problem
	 */
	@Override
	protected Integer doInBackground(Void... params) {

		//Log.d("Text","LoadMarkersAsyncTask2" + databaselocationpair.size());
		while(createdLatLng.size() < 100){
			if(isCancelled()){ 
				cancel(true);
			}
			Log.d("Text","LoadMarkersAsyncTask2.1");
			int i = 0;
			for(LocationPair ll : databaselocationpair){
			//	Log.d("Text","LoadMarkersAsyncTask2.2");
				if(llb.contains(ll.getLatLng())
						//AKTA UTROPSTECKNET HÄR
						&& !createdLatLng.contains(ll)){
					scr = createPic(ll.getTitle(), "Location");
				//	Log.d("Text","LoadMarkersAsyncTask2.3");
					if(!isCancelled()){
					publishProgress(ll);}
					createdLatLng.add(ll);	
					i++;
					
					if(i == 6){
						break;
					}
				//	Log.d("Text","LoadMarkersAsyncTask3" + databaselocationpair.get(2).getLatLng().toString());
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//}
				}}
			if(i == 6){
				//Log.d("Text","LoadMarkersAsyncTask2.7.1" );
				checkAndSetNewBounds();	
			//	Log.d("Text","LoadMarkersAsyncTask2.7.14" );
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}}
			//	Log.d("Text","LoadMarkersAsyncTask2.7.13");}
			else{
			//	Log.d("Text","LoadMarkersAsyncTask2.7.12" );
				farright = new LatLng(farright.latitude + p,farright.longitude + p);
				nearleft = new LatLng(nearleft.latitude - p, nearleft.latitude - p);
				llb = new LatLngBounds(nearleft,farright);}
			//checkAndSetNewBounds();
			}
		Log.d("Text","LoadMarkersAsyncTask2.7YOU" );
		over100markers();
			//over100markers();
			return 1;
	}
	public void choosedeletion(){
		//Log.d("Text","LoadMarkersAsyncTask2.7YOU0" );
		checkAndSetNewBounds();
		for(int j = 0;j < createdLatLng.size();j++){
			if(!llb.contains(createdLatLng.get(j).getLatLng())){
				deletenumber = j;
				break;
			}
			else if(j == createdLatLng.size() - 1){
			//	Log.d("Text","LoadMarkersAsyncTask2.7YOU1" );
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				deletenumber = 0;
				
			}
		}
	}
	
	
	public void over100markers(){
		Boolean	iscancelled = true;
		over100markers = true;
		Log.d("Text","LoadMarkersAsyncTask4" + databaselocationpair.size());
		while(iscancelled){
			if(isCancelled()){
				iscancelled = true;
				cancel(true);
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	Log.d("Text","LoadMarkersAsyncTask4.1");
			int i = 0;
			for(LocationPair ll : databaselocationpair){
			//	Log.d("Text","LoadMarkersAsyncTask4.2");
				if(llb.contains(ll.getLatLng())
						//AKTA UTROPSTECKNET HÄR
						&& !createdLatLng.contains(ll)){
					scr = createPic(ll.getTitle(), "Location");
			//		Log.d("Text","LoadMarkersAsyncTask4.3");
					//choosedeletion();
					if(!isCancelled()){
					publishProgress(ll);}
					createdLatLng.add(ll);	
					i++;		
					if(i == 6){
						break;
					}
				//	Log.d("Text","LoadMarkersAsyncTask4" + databaselocationpair.get(2).getLatLng().toString());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//}
				}}
			if(i == 6){
				//Log.d("Text","LoadMarkersAsyncTask4.7.1" );
				checkAndSetNewBounds();	
			//	Log.d("Text","LoadMarkersAsyncTask4.7.14" );
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}}
				//Log.d("Text","LoadMarkersAsyncTask4.7.13");}
			else{
			//	Log.d("Text","LoadMarkersAsyncTask4.7.12");
				farright = new LatLng(farright.latitude + p,farright.longitude + p);
				nearleft = new LatLng(nearleft.latitude - p, nearleft.latitude - p);
				llb = new LatLngBounds(nearleft,farright);}
			//checkAndSetNewBounds();
			}


	}
		
	private void checkAndSetNewBounds() {
		Log.d("Text","LoadMarkersAsyncTask2.8" + visibleregion.toString());
		Log.d("Text","LoadMarkersAsyncTask2.81" + farright.toString());
		Log.d("Text","LoadMarkersAsyncTask2.82" + visibleregion.farRight.toString());
		if(farright != visibleregion.farRight ){
			this.farright =	visibleregion.farRight;
			this.nearleft =	visibleregion.nearLeft;
			Log.d("Text","LoadMarkersAsyncTask2.83" + database.contains(farright));
			Log.d("Text","LoadMarkersAsyncTask2.84" + database.contains(nearleft));
			if(!database.contains(farright) || !database.contains(nearleft)){
				//databaselocationpair = getfromdatabase(nearleft - p ,farright + p );
				database = new LatLngBounds(
						new LatLng(databasenearleft.latitude - p,databasenearleft.longitude - p),
						new LatLng(databasefarright.latitude + p,databasefarright.longitude + p));
			}
			Log.d("Text","LoadMarkersAsyncTask2.8411" + llb.toString());
			llb = new LatLngBounds(this.nearleft,this.farright);}
		Log.d("Text","LoadMarkersAsyncTask2.841" + llb.toString());

	}


	protected void onProgressUpdate(LocationPair... params){
		if(over100markers){
			createdMarkers.get(deletenumber).remove();
			createdMarkers.remove(deletenumber);
			createdLatLng.remove(deletenumber);
		}
		if(k == 5){
		k = 0;
		visibleregion = myMap.getProjection().getVisibleRegion();
		k++;}
		Log.d("Text","LoadMarkersAsyncTask2.7cr" + params[0].getLatLng().toString());
		Marker m = myMap.addMarker(new MarkerOptions()
		.position(params[0].getLatLng())
		.icon(BitmapDescriptorFactory
				.fromBitmap(scr)));
		createdMarkers.add(m);
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


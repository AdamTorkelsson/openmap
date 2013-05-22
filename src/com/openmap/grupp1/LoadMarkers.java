package com.openmap.grupp1;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.maps.model.VisibleRegionCreator;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Point;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

//This Class will load the markers on the screen
public class LoadMarkers {
	private LatLng focusedposition;
	private ArrayList<Location> madeupposition = new ArrayList<Location>();
	private Location locationTemp = new Location("test");
	private GoogleMap myMap;
	private VisibleRegion visibleregion;
	private Projection p;
	private LatLng farleft;
	private LatLng farright;
	private LatLng nearleft;
	private LatLng nearright;
	private ArrayList<LatLng> inviewmarkers = new ArrayList<LatLng>();
	private ArrayList<Marker> createdmarkers = new ArrayList<Marker>();
	private MarkerFactory markerFactory = new MarkerFactory();
	private Resources res;
	
	public LoadMarkers(GoogleMap myMap, Resources res){
		this.myMap = myMap;
		this.res = res;
		}
		
	private void getInCameraMarkers(CameraPosition arg0){
		//gets all corners of the screen to prepare for loading markers from database
		p = myMap.getProjection();
		visibleregion = p.getVisibleRegion();
		farleft = 	visibleregion.farLeft;
		farright =	visibleregion.farRight;
		nearleft =	visibleregion.nearLeft;
		nearright =	visibleregion.nearRight;
		
		/*
		 * Connect to database
		 * And then
		 * myMap.addMarker(new MarkerOptions().position(______).title("focusedposition"));
			and put all markerslocation in position
		 */
		for(LatLng ll : inviewmarkers){
			/*
			 * connect to database get markerinfo
			 * then add everything to a myMap that is here
			 */
			Bitmap scr = markerFactory.createPic("title", res, "Location");
			Marker m = myMap.addMarker(new MarkerOptions()
			.position(ll).icon(BitmapDescriptorFactory
			.fromBitmap(scr)));
			m.isVisible();
			Log.d("Test", "Test" + "title");
		}
		
			
	}
	
	public void addMarkersInCameraView(CameraPosition arg0){
		/*	
		 * 
		 */
		getInCameraMarkers(arg0);
		
	
		}
		
	}

	
	


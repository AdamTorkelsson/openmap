package com.openmap.grupp1;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.maps.model.VisibleRegionCreator;

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
	
	public LoadMarkers(GoogleMap myMap){
		this.myMap = myMap;
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
				
		locationTemp.setLatitude(arg0.target.latitude);
		locationTemp.setLongitude(arg0.target.longitude);
		locationTemp.setTime(new Date().getTime()); //Set time as current Date
		madeupposition.add(locationTemp);
				//locationTemp.toString();	
	}
	
	public void addMarkersInCameraView(CameraPosition arg0){
		/*	
		 * 
		 */
		getInCameraMarkers(arg0);
		
	
		}
		
	}

	
	


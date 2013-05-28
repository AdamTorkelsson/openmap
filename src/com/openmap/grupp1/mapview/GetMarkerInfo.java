package com.openmap.grupp1.mapview;

import java.util.concurrent.ExecutionException;

import com.google.android.gms.maps.model.LatLng;
import com.openmap.grupp1.database.GetLocationTask;
import com.openmap.grupp1.database.LocationMarker;

public class GetMarkerInfo {
	LocationMarker lp;
	GetLocationTask glt;
	public GetMarkerInfo(){
		glt = new GetLocationTask();
		
	}
	
	public void setMarker(LatLng point){
		try {
			glt.getLocationPairFromLatLng(point);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		glt.execute();
		try {
			lp = glt.get().get(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getMarkerTitle() {
	
		return lp.getTitle();}
	
	public String getMarkerDescription(){
		return lp.getDescription();
	}
		
			
	
	

}

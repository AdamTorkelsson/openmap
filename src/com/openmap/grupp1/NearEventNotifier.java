package com.openmap.grupp1;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

public class NearEventNotifier {
	private Location lastKnownLocation;
	private Location event;
	private Context context;
	
	public NearEventNotifier(Location event,Location lastKnownLocation,Context context ){
		this.lastKnownLocation = lastKnownLocation;
		this.event = event;
		this.context = context;
	}	
		
	public void checkEvent(Location loc){
		if(loc.distanceTo(lastKnownLocation) < 30 && loc.distanceTo(event) < 15 ){
			Log.d("CheckEvent","eventhandlerduärinärheten");	
			Intent intent = new Intent(context,com.openmap.grupp1.NotifyService.class);
			 context.startService(intent);}
		}
		
	public void setEvent(Location event){
		this.event = event;
	}
	public void lastKnownLocation(Location lastKnownLocation){
		this.lastKnownLocation = lastKnownLocation;
	}
	}


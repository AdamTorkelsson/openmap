package com.openmap.grupp1.mapview;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;

import com.openmap.grupp1.database.LocationTask;

public class AddUserToLocation {
	public static final String PREFS_NAME = "MySharedPrefs";

	public AddUserToLocation(){

	}

	@SuppressWarnings("unchecked")
	public void addUser(LatLng point, Context context) {
		SharedPreferences settings= context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); 
		
		new LocationTask().addAttender(settings.getString("LoginUsername", "Adam"), point);
		
	}
}

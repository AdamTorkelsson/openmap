package com.openmap.grupp1.mapview;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.openmap.grupp1.database.AddLocationTask;
import com.openmap.grupp1.database.UserLoginAndRegistrationTask;

public class AddUserToLocation {
	public static final String PREFS_NAME = "MySharedPrefs";
	
	public AddUserToLocation(){
		
	}

	
	public void addUser(LatLng point, Context context) {
		SharedPreferences settings= context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); 
		AddLocationTask alt = new AddLocationTask();
		alt.execute(alt.addAttender(point, settings.getString("LoginUsername", "Adam")));
		
	}
	


}

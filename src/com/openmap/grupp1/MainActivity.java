package com.openmap.grupp1;

/*
 * The main activity is started after splash screen.
 * Creates myMapFragment that handles the mapfragment. 
 */

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;



import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import com.openmap.grupp1.database.UserLoginAndRegistrationTask;
import com.openmap.grupp1.helpfunctions.SearchTagActivity;
import com.openmap.grupp1.helpfunctions.SettingsActivity;
import com.openmap.grupp1.maphandler.MyMap;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;


public class MainActivity extends Activity 
{

	private MyMap myMap;
	public static final String PREFS_NAME = "MySharedPrefs";
	private SharedPreferences settings;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		
		//Sets the animation when opening this activity
		overridePendingTransition(R.anim.map_in,R.anim.other_out);

		//The Action Bar replaces the title bar and provides an alternate location for an on-screen menu button on some devices. 
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		//Creating content view
		setContentView(R.layout.activity_main);
	
		//Creates the MyMapFragment which handle the map part of this activity
		myMap = new MyMap(this);
	
		/*Gets the previous login values if the user have logged in before
		If the user haven't logged in before the login activity starts
		Starts an TutorialDialog also if the user have to log in or register
		*/
		settings= getSharedPreferences(PREFS_NAME, MODE_PRIVATE); 

		UserLoginAndRegistrationTask ular = 
				new UserLoginAndRegistrationTask(
						settings.getString("LoginUsername", "Adam"),
						settings.getString("LoginPassword", "1234"));
		ular.loginUser();

		try {
			if(!ular.get()){
				startActivity(new Intent(this,LoginRegisterActivity.class));
				PopupandDialogHandler TPD = new PopupandDialogHandler(this);
				TPD.dialogHandler();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	 

	
	@Override
	public void onResume(){
		
		//Sets the animation when resuming this activity
		overridePendingTransition(R.anim.map_in,R.anim.other_out);

		// Checks and modifies the maptype if its been changed in the settings
		myMap.onCameraChange(new CameraPosition(new LatLng(0,0),7,0,0));
		String mapSetting = settings.getString("map", "Error");
		myMap.setMap(mapSetting);
		super.onResume();

	}

	@Override
	//Defines the attributes of the options menu
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//Inflate the startmenu into the bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.startmenu, menu);
		// Sets the title and logo to be hidden
		ActionBar ab = getActionBar();
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);
		return true;

	}

	//Defines the action to be taken when clicking the menu buttons
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//Clears the tag filter if the user clicks the clear button
		case R.id.btn_clear:
			SharedPreferences settings = this.getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			Set<String> set = new HashSet<String>();
			editor.putStringSet("tagSet", set);
			editor.commit();
			myMap.onCameraChange(new CameraPosition(new LatLng(0,0),7,0,0));
			return true;
		//Starts the settings activity and pauses the current activity if the user clicks the settings button
		case R.id.btn_settings:
			Intent settingsIntent =new Intent(this, SettingsActivity.class);
			startActivity(settingsIntent);
			this.onPause();
			return true;
		//Starts the search tag activity and pauses the current activity if the user clicks the search button
		case R.id.btn_search:
			Intent searchIntent =new Intent(this, SearchTagActivity.class);
			startActivity(searchIntent);
			return true;
		//Default
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
	

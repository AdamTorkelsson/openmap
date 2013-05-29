package com.openmap.grupp1;

/*
 * The main activity is started after splashscreen.
 * Creates myMapFragment that handels the mapfragment. 
 */

import java.util.concurrent.ExecutionException;


import com.openmap.grupp1.database.UserLoginAndRegistrationTask;
import com.openmap.grupp1.helpfunctions.SearchTagActivity;
import com.openmap.grupp1.helpfunctions.SettingsActivity;
import com.openmap.grupp1.mapview.MyMapFragment;



import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;


public class MainActivity extends Activity 
{

	private MyMapFragment myMap;
	public static final String PREFS_NAME = "MySharedPrefs";
	private  boolean mBound = false;
	private SharedPreferences settings;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		//create lite osäkert men alltid här

		super.onCreate(savedInstanceState);
	
		//The Action Bar replaces the title bar and provides an alternate location for an on-screen menu button on some devices. 
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		//Creating content view
		setContentView(R.layout.activity_main);
	
		//Creates the MyMapFragment which handle the map part of this activity
		myMap = new MyMapFragment(this);
	
		/*Gets the previous login values if the user have logged in before
		If the user havent logged in before the login activity starts
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

		// Checks and modifies the maptype if its been changed
		String mapSetting = settings.getString("map", "Error");
		myMap.setMap(mapSetting);
		super.onResume();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TEXT_SERVICES_MANAGER_SERVICE, "OnResumeAddMarker3");
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.startmenu, menu);
		ActionBar ab = getActionBar();
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);
		Log.d(TEXT_SERVICES_MANAGER_SERVICE, "OnResumeAddMarker4");
		return true;

	}


	@Override
	//lägg in denna i searchtagactivity när den är korrekt
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.btn_select:
			return true;
		case R.id.btn_settings:
			Log.d(TEXT_SERVICES_MANAGER_SERVICE, "step3");
			Intent settingsIntent =new Intent(this, SettingsActivity.class);
			Log.d(TEXT_SERVICES_MANAGER_SERVICE, "step4");
			startActivity(settingsIntent);
			Log.d(TEXT_SERVICES_MANAGER_SERVICE, "step5");
			this.onPause();
			return true;
		case R.id.btn_search:
			Intent searchIntent =new Intent(this, SearchTagActivity.class);
			startActivity(searchIntent);

			return true;


		default:
			return super.onOptionsItemSelected(item);
		}
		
	


	}}
	

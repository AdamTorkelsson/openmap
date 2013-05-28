package com.openmap.grupp1;



import java.util.concurrent.ExecutionException;


import com.google.android.gms.maps.model.LatLng;
import com.openmap.grupp1.database.UserLoginAndRegistrationTask;
import com.openmap.grupp1.helpfunctions.SearchTagActivity;
import com.openmap.grupp1.helpfunctions.SettingsActivity;
import com.openmap.grupp1.maphandler.LoadMarkersAsyncTask;
import com.openmap.grupp1.mapview.MyMap;
import com.openmap.grupp1.temp.notfinished.LoadMarkersAsyncTaskTemp;


import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;


public class MainActivity extends Activity 
{

	private MyMap myMap;
	public static final String PREFS_NAME = "MySharedPrefs";
	private  boolean mBound = false;
	private SharedPreferences settings;
	private LoadMarkersAsyncTaskTemp lmat;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		//create lite osäkert men alltid här

		super.onCreate(savedInstanceState);
	
		//The Action Bar replaces the title bar and provides an alternate location for an on-screen menu button on some devices. 
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		//Creating content view
		setContentView(R.layout.activity_main);
	
		myMap = new MyMap(this);
		
		myMap.setMap("Hybrid");
	
		/*Gets the previous login values if the user have logged in before
		If the user havent logged in before the login activity starts
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
				TutorialPopupDialog TPD = new TutorialPopupDialog(this);
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

		//overridePendingTransition(R.anim.map_in,R.anim.other_out);
		//Log.d(TEXT_SERVICES_MANAGER_SERVICE, "OnResumeAddMarker2");
		myMap.getMap().clear();
		myMap.refreshMarkers();
	
		
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
		
	


	}
	
	/*public boolean onQueryTextChange(String newText) {
	 if (!cSearchPopup.isShowingPopup())
		 cSearchPopup.createPopup();
	 else;
	 cSearchPopup.showResults(newText + "*");
	//kanske bra searchView.clearFocus();
    return true;
 }

 public boolean onQueryTextSubmit(String query) {
	 if (!cSearchPopup.isShowingPopup())
		 cSearchPopup.createPopup();
	 else;
	 Log.d("testar", "före showresults");
	 cSearchPopup.showResults(query + "*");
    return true;
=======
>>>>>>> f7da9a88e45b3a0049c847cde09be7359c02faab
=======
>>>>>>> f7da9a88e45b3a0049c847cde09be7359c02faab
 }

 public boolean onClose() {
	cSearchPopup.dismissPopup();
    return false;
 }*/

}


/*BRA för framtiden , krävs för att starta aktivitet på annat ställe , dock måste den göras som en aktivitet med denna som hierarisk
 * Intent intent =new Intent(this, SharedPrefs.class);
	startActivity(intent);*/

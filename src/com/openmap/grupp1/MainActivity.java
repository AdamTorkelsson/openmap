package com.openmap.grupp1;


import com.google.android.gms.maps.model.LatLng;


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
import android.widget.SearchView;
import android.widget.Toast;


public class MainActivity extends Activity implements SearchView.OnQueryTextListener,
SearchView.OnCloseListener
 {
 private MyMap myMap;
 public static final String PREFS_NAME = "MySettings";
 private CreateDialogs createDialog; 
 private SearchView searchView;
 boolean mBound = false;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
 
	 //create lite osäkert men alltid här
  super.onCreate(savedInstanceState);
  //The Action Bar replaces the title bar and provides an alternate location for an on-screen menu button on some devices. 
  getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
  //Creating content view
  setContentView(R.layout.activity_main);
  myMap = new MyMap(getFragmentManager(), getSystemService(Context.LOCATION_SERVICE),this,getResources());
  myMap.setMap("Default");
  
  createDialog = new CreateDialogs();
  
  myMap = new MyMap(getFragmentManager(), getSystemService(Context.LOCATION_SERVICE),this,getResources());

 }
 @Override
 public void onResume(){
	 Log.d(TEXT_SERVICES_MANAGER_SERVICE, "step4ny");
	 super.onResume();
	 Log.d(TEXT_SERVICES_MANAGER_SERVICE, "step5ny");
	 SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE); 
	 Log.d(TEXT_SERVICES_MANAGER_SERVICE, "step6ny");
	 String mapSetting = settings.getString("map", null);
	 Log.d(TEXT_SERVICES_MANAGER_SERVICE, mapSetting);
	 myMap.setMap(mapSetting);
	 Log.d(TEXT_SERVICES_MANAGER_SERVICE, "step8ny");
 }

 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
     super.onCreateOptionsMenu(menu);
     MenuInflater inflater = getMenuInflater();
     inflater.inflate(R.menu.start_menu, menu);
     ActionBar ab = getActionBar();
     ab.setDisplayShowTitleEnabled(false);
     ab.setDisplayShowHomeEnabled(false);
     
     searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
    //ta beslut här searchView.setIconifiedByDefault(false);
     searchView.setOnQueryTextListener(this);
     searchView.setOnCloseListener(this);
     
     return true;
 
 }

 
 @Override
 public boolean onOptionsItemSelected(MenuItem item) {

	 

      switch (item.getItemId()) {

          case R.id.btn_select:
          Log.d("textservices", "bajs");
          return true;
      case R.id.btn_settings:

    	  Log.d(TEXT_SERVICES_MANAGER_SERVICE, "step3.5");
    	  Intent intent =new Intent(this, settings.class);
    	  Log.d(TEXT_SERVICES_MANAGER_SERVICE, "step4");
    	  
    	  startActivity(intent);
    	  this.onPause();
    	  return true;
    	  
      default:
          return super.onOptionsItemSelected(item);
     }
	 
 }

    


         


 
 

 public void buttonCamera(View v){
	
}

 public void buttonGroups(View v){
	// myMap.createonemoreDialog();
	 
}

 public void buttonEvent(View v){
	/* Intent settingsintent =new Intent(this, settings.class);
		startActivity(settingsintent);*/
}
 
 
 
 
 




 public boolean onQueryTextChange(String newText) {
	 if (!createDialog.isShowingSearch())
		 createDialog.createSearchPopup(this);
	 else;
	 // showResults(newText + "*");
    return false;
 }

 public boolean onQueryTextSubmit(String query) {
	 Log.d(TEXT_SERVICES_MANAGER_SERVICE, "OnQueryStep1");
	 if (!createDialog.isShowingSearch())
		 createDialog.createSearchPopup(this);
	 else;
   // showResults(query + "*");
    return true;
 }

 public boolean onClose() {
	createDialog.dismissSearch();
    return false;
 }

}


/*BRA för framtiden , krävs för att starta aktivitet på annat ställe , dock måste den göras som en aktivietet med denna som hierarisk
 * Intent intent =new Intent(this, SharedPrefs.class);
	startActivity(intent);*/

package com.openmap.grupp1;


import com.google.android.gms.maps.model.LatLng;


import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;


public class MainActivity extends Activity 
 {

 private MyMap myMap; 

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

 
 }

 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
     super.onCreateOptionsMenu(menu);
     MenuInflater inflater = getMenuInflater();
     inflater.inflate(R.menu.start_menu, menu);
     ActionBar ab = getActionBar();
     ab.setDisplayShowTitleEnabled(false);
     ab.setDisplayShowHomeEnabled(false);
     return true;
 
 }
 
 
 @Override
 public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId()) {
      case R.id.btn_select:
          Log.d("textservices", "bajs");
          return true;
      case R.id.btn_settings:
    	/*  Intent intent =new Intent(this, settings.class);
    		startActivity(intent);*/
          return true;
      default:
          return super.onOptionsItemSelected(item);
          }}
 

 

 public void buttonCamera(View v){
	 Intent intent =new Intent(this, PhotoTaker.class);
		startActivity(intent);
    
}

 public void buttonGroups(View v){
	// myMap.createonemoreDialog();

	 
}

 public void buttonEvent(View v){
	/* Intent settingsintent =new Intent(this, settings.class);
		startActivity(settingsintent);*/
}
 
 
 
 
 
 @Override
 protected void onResume() {
  // TODO Auto-generated method stub
  super.onResume(); }


}




/*BRA för framtiden , krävs för att starta aktivitet på annat ställe , dock måste den göras som en aktivietet med denna som hierarisk
 * Intent intent =new Intent(this, SharedPrefs.class);
	startActivity(intent);*/

package com.openmap.grupp1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

public class settings extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		 //create lite osäkert men alltid här
	  super.onCreate(savedInstanceState);
	  //The Action Bar replaces the title bar and provides an alternate location for an on-screen menu button on some devices. 

	  //Creating content view
	  setContentView(R.layout.settingsview);
	  
	 
	}
	
	 public boolean onCreateOptionsMenu(Menu menu) {
	     super.onCreateOptionsMenu(menu);
	     MenuInflater inflater = getMenuInflater();
	     inflater.inflate(R.menu.settings_menu, menu);
	     ActionBar ab = getActionBar();
	     ab.setDisplayShowTitleEnabled(false);
	     ab.setDisplayShowHomeEnabled(false);
	     return true;
	 }
	 
	 public boolean onOptionsItemSelected(MenuItem item) {
		 
		 finish();

         return true;
         }
	 
}

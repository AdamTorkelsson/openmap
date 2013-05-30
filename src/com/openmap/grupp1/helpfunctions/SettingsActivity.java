package com.openmap.grupp1.helpfunctions;

import com.openmap.grupp1.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ToggleButton;

/**
 * Activity containing all the settings available settings, currently change of map type and enabling/disabling notifications 
 */
public class SettingsActivity extends Activity{
	private Spinner spinner;
	private Button btnApply;
	private Button btnCancel;
	private ToggleButton btnNotifications;
	private final String PREFS_NAME ="MySharedPrefs";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		//Sets the animation when starting this activity
		overridePendingTransition(R.anim.map_out,R.anim.other_in);

		//Sets the the view for the activity
		setContentView(R.layout.settingsview);
		
		//Adds the interface elements and their listeners
		addMapsSpinner();
		addNotificationsToggle();
		addListenerOnApplyButton();
		addListenerOnCancelButton();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//Inflate the standardmenu into the menu
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.standardmenu, menu);
		//Sets the title and home buttons disabled
		ActionBar ab = getActionBar();
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F39C12")));
		return true;
	}
	//Defines the action to be taken when pressing the menu items
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//Finishes the current activity, returning to the map
		case R.id.btn_logo:
			finish();
			return true;
		//Default
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	/**
	 * Adds a spinner containing the different map types
	 */
	public void addMapsSpinner(){ 

		spinner = (Spinner) findViewById(R.id.spinner);
		// Create an ArrayAdapter using the string array with the different map types and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.maps_array, R.layout.spinner_textview);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(R.layout.spinner_textview);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

	}

	/**
	 *  Adds a toggle button which enables and disables the notifications
	 */
	public void addNotificationsToggle(){
		btnNotifications = (ToggleButton) findViewById(R.id.btnNotifications);
	}

	/**
	 * Adds the listener to the apply button
	 */
	public void addListenerOnApplyButton() {

		btnApply = (Button) findViewById(R.id.btnApply);
		btnApply.setOnClickListener(new OnClickListener(){

			//Sets the data to the specified settings which is then gotten at the appropriate places
			@Override
			public void onClick(View v) {
				String maptype = spinner.getSelectedItem().toString();
				SharedPreferences settings = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("map", maptype);
				editor.putBoolean("notifications", btnNotifications.isChecked());
				editor.commit();

				//Closes this activity and returns to the map
				finish();

			}

		});

	}

	/**
	 * Adds the listener to the cancel button
	 */
	public void addListenerOnCancelButton() {

		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener(){

			//Closes this activity and returns to the map
			@Override
			public void onClick(View v) {
				finish();

			}

		});

	}



}

package com.openmap.grupp1.helpfunctions;

import com.openmap.grupp1.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ToggleButton;

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


		//create lite osäkert men alltid här

		//The Action Bar replaces the title bar and provides an alternate location for an on-screen menu button on some devices. 

		//Sets the the view for the activity
		setContentView(R.layout.settingsview);
		
		//Adds the interface elements and their listeners
		addMapsSpinner();
		addNotificationsToggle();
		addListenerOnApplyButton();
		addListenerOnCancelButton();
	}

	@Override
	public void onResume(){
		super.onResume();
	}
	
	//Adds a spinner containing the different map types
	public void addMapsSpinner(){ 

		spinner = (Spinner) findViewById(R.id.spinner);
		// Create an ArrayAdapter using the string array with the different map types and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.maps_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

	}

	// Adds a toggle button which enables and disables the notifications
	public void addNotificationsToggle(){
		btnNotifications = (ToggleButton) findViewById(R.id.btnNotifications);
	}

	//Adds the listener to the apply button
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

	//Adds the listener to the cancel button
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

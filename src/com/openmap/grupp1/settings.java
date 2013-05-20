package com.openmap.grupp1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class settings extends Activity{
	private Spinner spinner;
	private Button btnSubmit;
	private final String PREFS_NAME ="MySettings";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	
		//create lite osäkert men alltid här
	  
	  //The Action Bar replaces the title bar and provides an alternate location for an on-screen menu button on some devices. 

	  //Creating content view
	  setContentView(R.layout.settingsview);
	  

	  addMapsSpinner();
	  addListenerOnButton();
	}
	@Override
	public void onResume(){
		super.onResume();
	}
	  
	  
	public void addMapsSpinner(){ 
	
	spinner = (Spinner) findViewById(R.id.spinner);
	// Create an ArrayAdapter using the string array and a default spinner layout
	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
	R.array.maps_array, android.R.layout.simple_spinner_item);
	// Specify the layout to use when the list of choices appears
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// Apply the adapter to the spinner
	spinner.setAdapter(adapter);
	
	}
	
	 public void addListenerOnButton() {
		
			
		 	
			btnSubmit = (Button) findViewById(R.id.btnSubmit);
		 
			btnSubmit.setOnClickListener(new OnClickListener(){
		 
			  @Override
			  public void onClick(View v) {
				  String text = spinner.getSelectedItem().toString();
				  SharedPreferences settings = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
				  SharedPreferences.Editor editor = settings.edit();
				  editor.putString("map", text);
				  editor.commit();
				  
				  endactivity();
				 
				  
			  }
		 
			});
			
		  }
	 private void endactivity(){
		 Log.d(TEXT_SERVICES_MANAGER_SERVICE, "step3");
		 this.finish();
	 }
	
	
}

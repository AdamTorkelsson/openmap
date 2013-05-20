/*package com.openmap.grupp1;

import android.app.Activity;
import android.content.Context;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//create lite osäkert men alltid här
	  super.onCreate(savedInstanceState);
	  //The Action Bar replaces the title bar and provides an alternate location for an on-screen menu button on some devices. 

	  //Creating content view
	  setContentView(R.layout.settingsview);
	  

	  addMapsSpinner();
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
	  
>>>>>>> 3f681e7290ce086383d268f5de010dab6fc99153
	}
	
	 public void addListenerOnButton() {
		 
			spinner = (Spinner) findViewById(R.id.spinner);
			
			btnSubmit = (Button) findViewById(R.id.btnSubmit);
		 
			btnSubmit.setOnClickListener(new OnClickListener(){
		 
			  @Override
			  public void onClick(View v) {
		 
				  Log.d("Test", "hej6");
			  }
		 
			});
		  }
	
	
}*/

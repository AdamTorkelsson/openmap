package com.openmap.grupp1;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.content.Intent;
public class CreateEventActivity extends Activity{
	
	 public void onCreate(Bundle savedInstanceState){
		 Log.d(TEXT_SERVICES_MANAGER_SERVICE, "INCREATEEVENT");
		 super.onCreate(savedInstanceState);
         setContentView(R.layout.createeventactivityview);
		 
		  Button buttonTag	  = (Button) findViewById(R.id.buttonTag);
		  Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
		  Button buttonCamera = (Button) findViewById(R.id.buttonCamera);
	
		
		  buttonTag.setClickable(true);
		  buttonCancel.setClickable(true);
		  buttonCamera.setClickable(true);
		  
		  buttonTag.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					startSearchActivity();
					}

				});
			
		  buttonCancel.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					stopThisActivity();
					}});
		  
		  buttonCamera.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					startCameraActivity();	
				}});	
	 }

 


	 private void stopThisActivity(){
		 	this.finish();
	 }
	 private void startSearchActivity() {
		// Intent intent =new Intent(this, SearchTagsActivity.class);
		startActivity(new Intent(this, SearchTagsActivity.class));	
		this.finish();
			
		}
	 private void startCameraActivity(){
		 Intent intentCamera = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
         File image=new File(Environment.getExternalStorageDirectory(),"test.jpg");
         intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(image));
         Uri photoUri=Uri.fromFile(image);
         startActivityForResult(intentCamera,1);}
	 
	 
	
}

	

      
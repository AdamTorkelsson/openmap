package com.openmap.grupp1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateDialogs {
	
	private LatLng point;
	private Resources res;
	private GoogleMap myMap;
	private Context context;


	
	public CreateDialogs(){
		
	}

	public void confirmLocationPopup(Context context,final Marker m, final GoogleMap myMap){
		this.context = context;
		//POPUP som fungerar
		   int popupWidth = 600;
		   int popupHeight = 100;
		  
		   // Inflate the popup_layout.xml
		LinearLayout viewGroup = (LinearLayout) ((Activity) context).findViewById(R.layout.activity_main);
		   LayoutInflater layoutInflater = (LayoutInflater) context
		     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  
		   View layout = layoutInflater.inflate(R.layout.confirmview, viewGroup);
		   // Creating the PopupWindow
		   final PopupWindow popup = new PopupWindow(context);
		   popup.setContentView(layout);
		   popup.setWidth(popupWidth);
		   popup.setHeight(popupHeight);
		   
		   Button buttonYES = (Button) layout.findViewById(R.id.buttonYes);
		   Button buttonNO = (Button) layout.findViewById(R.id.buttonNo);
			  
			  buttonYES.setClickable(true);
			  buttonNO.setClickable(true);
				 
				buttonYES.setOnClickListener(
						new OnClickListener(){

							@Override
							public void onClick(View arg0) {
								popup.dismiss();
								startNewActivity();
								 m.remove();
							}
							
						});
				
				buttonNO.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						popup.dismiss();
						 m.remove();
						
					}});
		   popup.showAtLocation(layout, Gravity.BOTTOM, 0,  0);
		   
	}

	private void startNewActivity() {
		Intent intent =new Intent(context, CreateEventActivity.class);
		context.startActivity(intent);		
	}

private void setValues(String title, String description){
	MarkerFactory markerFactory = new MarkerFactory();
	Bitmap scr = markerFactory.createPic(title, res, "Location");
	Marker m = myMap.addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory.fromBitmap(scr)));
	m.isVisible();
	Log.d("Test", "Test" + title);
}


public void showInfo(Context context,final LatLng point,
		final Resources res, 
		final GoogleMap myMap,
		String title,
		String description ){

   //POPUP som fungerar
  
	int popupWidth = 300;
	int popupHeight = 300;

   // Inflate the popup_layout.xml
LinearLayout viewGroup = (LinearLayout) ((Activity) context).findViewById(R.layout.activity_main);
   LayoutInflater layoutInflater = (LayoutInflater) context
     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

View layout = layoutInflater.inflate(R.layout.showinfopopup, viewGroup);


TextView titleView = (TextView) layout.findViewById(R.id.titleView1);
TextView descriptionView = (TextView) layout.findViewById(R.id.descriptionView1);

titleView.setText(title);
descriptionView.setText(description);

   // Creating the PopupWindow
   final PopupWindow popup = new PopupWindow(context);
   popup.setContentView(layout);
   popup.setWidth(popupWidth);
   popup.setHeight(popupHeight);
   popup.setFocusable(true);
   popup.setTouchable(true);
  
   // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.

   Projection projection =	myMap.getProjection();
   // Clear the default translucent background
 //  popup.setBackgroundDrawable(new BitmapDrawable());
 
   //Displaying the popup at the specified location, + offsets.
   popup.showAtLocation(layout, Gravity.NO_GRAVITY, 
		   projection.toScreenLocation(point).x -popupWidth/2, 
		   projection.toScreenLocation(point).y - popupHeight/2);
}

public void checkInDialog(Context context, final GoogleMap myMap) {
		
		this.context = context;
		//POPUP som fungerar
		   int popupWidth = 600;
		   int popupHeight = 200;
		  
		   // Inflate the popup_layout.xml
		LinearLayout viewGroup = (LinearLayout) ((Activity) context).findViewById(R.layout.activity_main);
		   LayoutInflater layoutInflater = (LayoutInflater) context
		     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  
		   View layout = layoutInflater.inflate(R.layout.confirmcheckinview, viewGroup);
		
		   // Creating the PopupWindow
		   final PopupWindow popup = new PopupWindow(context);
	
		   popup.setContentView(layout);
		  // popup.setWindowLayoutMode(layout.getWidth(), layout.getHeight());
		 popup.setWidth(popupWidth);	   
		 popup.setHeight(popupHeight);
		 
		   
		   Button buttonYES = (Button) layout.findViewById(R.id.buttonYes);
		   Button buttonNO = (Button) layout.findViewById(R.id.buttonNo);
			  
			  buttonYES.setClickable(true);
			  buttonNO.setClickable(true);
				 
				buttonYES.setOnClickListener(
						new OnClickListener(){

							@Override
							public void onClick(View arg0) {
								popup.dismiss();
								//*Connect to database and add the person to the event
							}
							
						});
				
				buttonNO.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						popup.dismiss();
						
					}});
		   popup.showAtLocation(layout, Gravity.BOTTOM, 0,  0);
		   
	}
	
}
			  




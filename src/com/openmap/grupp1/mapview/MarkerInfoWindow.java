package com.openmap.grupp1.mapview;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.openmap.grupp1.R;
import com.openmap.grupp1.TutorialPopupDialog;
import com.openmap.grupp1.database.GetLocationTask;
<<<<<<< HEAD:src/com/openmap/grupp1/mapview/MarkerInfoWindow.java
import com.openmap.grupp1.database.LocationPair;
import com.openmap.grupp1.maphandler.NearEventNotifier;
=======
import com.openmap.grupp1.database.LocationMarker;
>>>>>>> 882d9aa039d775475357c89cb33a9aea9ef5cdfc:src/com/openmap/grupp1/maphandler/MarkerInfoWindow.java

public class MarkerInfoWindow {
double s = 0;
	public MarkerInfoWindow(){
	
	}
	
	public void showInfo(final Context context,final LatLng point,
			final Resources res, 
			final GoogleMap myMap){
		
		final NearEventNotifier nen = new NearEventNotifier(new Location("test"), myMap, context);
	   //POPUP som fungerar
	  
		int popupWidth = 300;
		int popupHeight = 300;

	   // Inflate the popup_layout.xml
	LinearLayout viewGroup = (LinearLayout) ((Activity) context).findViewById(R.layout.activity_main);
	   LayoutInflater layoutInflater = (LayoutInflater) context
	     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	View layout = layoutInflater.inflate(R.layout.showinfopopup, viewGroup);
	Log.d("Text", "Hejbjornen");

	GetLocationTask glt = new GetLocationTask();
	try {
		glt.getLocationPairFromLatLng(point);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	glt.execute();

	TextView titleView = (TextView) layout.findViewById(R.id.titleView1);
	TextView descriptionView = (TextView) layout.findViewById(R.id.descriptionView1);
	double l = point.latitude;
	double ln = point.longitude;
	Log.d("LatLngpoint", "LatLngpoint" + l);
	Log.d("LatLngpoint", "LatLngpoint" + ln);
	try {
		Log.d("Text", "Hejbjornen1.2");
		
		Log.d("Text", "LatLngpoint" + point);

		LocationMarker lp = glt.get().get(0);
	
		Log.d("Text", "Hejbjornen1.4");
		titleView.setText(lp.getTitle());
		Log.d("Text", "Hejbjornen1.5");
		descriptionView.setText(lp.getDescription());
		Log.d("Text", "Hejbjornen1.6");
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (ExecutionException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	Log.d("Text", "Hejbjornen2");
	

	   // Creating the PopupWindow
	   final PopupWindow popup = new PopupWindow(context);
	   popup.setContentView(layout);
	   popup.setWidth(popupWidth);
	   popup.setHeight(popupHeight);
	   popup.setFocusable(true);
	   popup.setTouchable(true);
	   
	   Button buttonOK = (Button) layout.findViewById(R.id.OkB);
	   Button buttonNO = (Button) layout.findViewById(R.id.CancelB);
		  
		  buttonOK.setClickable(true);
		  buttonNO.setClickable(true);
			 
			buttonOK.setOnClickListener(
					new OnClickListener(){

						@Override
						public void onClick(View arg0) {
							if(!nen.checkin(point)){
								TutorialPopupDialog tpd = new TutorialPopupDialog(context);
								tpd.standardDialog(R.string.tofaraway, "Ok", false);
							}
							else{
								
								popup.dismiss();}
							//*Connect to database and add the person to the event
						}
						
					});
			
			buttonNO.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					popup.dismiss();	
				}});
	  
	   // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.

	   Projection projection =	myMap.getProjection();
	 
	   //Displaying the popup at the specified location, + offsets.
	   popup.showAtLocation(layout, Gravity.NO_GRAVITY, 
			   projection.toScreenLocation(point).x -popupWidth/2, 
			   projection.toScreenLocation(point).y - popupHeight/2);
	}}



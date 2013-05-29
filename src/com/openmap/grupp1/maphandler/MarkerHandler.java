package com.openmap.grupp1.maphandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openmap.grupp1.R;
import com.openmap.grupp1.PopupandDialogHandler;
import com.openmap.grupp1.database.LocationMarker;
import com.openmap.grupp1.database.LocationTask;
import com.openmap.grupp1.mapview.AddUserToLocation;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.location.Location;
import android.util.Log;
/*
 * MarkerFactory contains a method to create the markers with different strings,
 * Those are created to make it easy for the user too see what kind of events are near them
 */
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MarkerHandler {
	ArrayList<LocationMarker> createdLatLng;
	ArrayList<Marker> createdMarkers = new ArrayList<Marker>();
	ArrayList<Marker> createdMarkersRemove = new ArrayList<Marker>();
	HashSet<LatLng> hs;
	HashSet<LatLng> k = new HashSet<LatLng>();
	private final String PREFS_NAME = "MySharedPrefs";
	public MarkerHandler(){
		createdLatLng = new ArrayList<LocationMarker>();
	
	}
	

public Bitmap createPic(String stringTitle, Resources res, String type){
	int color;
	if(type == "Event"){
		color = Color.BLACK;
	}
	else if(type == "Location"){
		color = Color.GREEN;}
	else if(type == "Owners"){
		color = Color.BLUE;}
	else{
		Log.d("TEST", "wrong type");
		color = Color.RED;
	}
color = Color.BLACK;
	
	Bitmap srv = BitmapFactory.decodeResource(res, R.drawable.markerpick); 
	Bitmap src = Bitmap.createScaledBitmap(srv, srv.getWidth()+ srv.getWidth(), 
								srv.getHeight() + srv.getHeight(), false);
	Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);

	
	if(stringTitle.length() > 7){
		stringTitle = (String) stringTitle.subSequence(0, 6);
		stringTitle = stringTitle + "...";
	}
	
		Canvas cs = new Canvas(dest);
        Paint tPaint = new Paint();
        tPaint.setTextSize(35);
        tPaint.setColor(color);
        tPaint.setStyle(Style.FILL);
        cs.drawBitmap(src, 0f, 0f, null);
        float height = tPaint.measureText("yY");
        float width = tPaint.measureText(stringTitle);
        float x_coord = (src.getWidth() - width)/2;
        cs.drawText(stringTitle, x_coord, height-3f, tPaint); 
        return dest;
        }
	

public void showInfo(final Context context,final LatLng point,
		final Resources res, 
		final GoogleMap myMap){
	
	final NearEventHandler nen = new NearEventHandler(new Location("test"), myMap, context);
   //POPUP som fungerar
  
	int popupWidth = 300;
	int popupHeight = 300;

   // Inflate the popup_layout.xml
LinearLayout viewGroup = (LinearLayout) ((Activity) context).findViewById(R.layout.activity_main);
   LayoutInflater layoutInflater = (LayoutInflater) context
     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

View layout = layoutInflater.inflate(R.layout.showinfopopup, viewGroup);

TextView titleView = (TextView) layout.findViewById(R.id.txtTitle);
TextView descriptionView = (TextView) layout.findViewById(R.id.txtDescription);
TextView starttime = (TextView) layout.findViewById(R.id.startDate);
TextView endtime = (TextView) layout.findViewById(R.id.endDate);
TextView attenders = (TextView) layout.findViewById(R.id.txtAttenders);
LocationMarker lm = new LocationTask().getLocation(point);


titleView.setText(lm.getTitle());
descriptionView.setText(lm.getDescription());
int i = new LocationTask().getAttenders(lm.getLatLng());
attenders.setText(" " + i);


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
						if(!nen.isCloseEnough(point)){
							PopupandDialogHandler tpd = new PopupandDialogHandler(context);
							tpd.standardDialog(R.string.toofaraway, "Ok", false);
						}
						else{
							SharedPreferences settings= context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); 
							new LocationTask().addAttender(settings.getString("LoginUsername", "Adam"), point);
							
							AddUserToLocation autl = new AddUserToLocation();
							autl.addUser(point,context);
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
}



public void addMarkersToScreen(GoogleMap myMap, Resources res, LatLngBounds bounds,Context context) {
	//Move down
	ArrayList<LocationMarker> databaselocationpair = null;

	SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
	Set<String> set = settings.getStringSet("tagSet", new HashSet<String>());

	if(set.isEmpty()){
		databaselocationpair = 
			new LocationTask().getLocation(new LatLng(bounds.southwest.latitude, bounds.southwest.longitude), 
			new LatLng(bounds.northeast.latitude, bounds.northeast.longitude));
		
	}
	else{
		ArrayList<String> tags = new ArrayList<String>();
		tags.addAll(set);
		databaselocationpair = 
			new LocationTask().getLocation(new LatLng(bounds.southwest.latitude, bounds.southwest.longitude), 
			new LatLng(bounds.northeast.latitude, bounds.northeast.longitude),tags);}
	

	/*
	 * Tried to remove marker thats not in screen but only made the application
	 * slower with this amount of markers. 
	 
	if(createdMarkers.size()>50){
		for(Marker m : createdMarkers){
			if(!bounds.contains(m.getPosition())){
				k.remove(m.getPosition());
				m.remove();
				createdMarkersRemove.add(m);
			}
			
		}
		createdMarkers.removeAll(createdMarkersRemove);
		createdMarkersRemove.clear();
	}*/


	//Adds marker to the screen
		for(LocationMarker ll : databaselocationpair){
			Log.d("Sistagrejen", "Testa size" + createdMarkers.size());
			
			/*if(createdMarkers.size()> 50){
				break;}*/

			if(!k.contains(ll.getLatLng())){
				Marker m = myMap.addMarker(new MarkerOptions()
					.position(ll.getLatLng())
					.icon(BitmapDescriptorFactory
					.fromBitmap(createPic(ll.getTitle(), res, "Location"))));
				createdMarkers.add(m);

				k.add(ll.getLatLng());
		
			}}
		

}






}




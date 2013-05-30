package com.openmap.grupp1.maphandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openmap.grupp1.R;
import com.openmap.grupp1.PopupandDialogHandler;
import com.openmap.grupp1.database.EventMarker;
import com.openmap.grupp1.database.LocationMarker;
import com.openmap.grupp1.database.LocationTask;

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

/*
 * MarkerFactory contains a method to create the markers with different strings,
 * Those are created to make it easy for the user too see what kind of events are near them
 */
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 
 * Handles the marker. 
 * Has methods to create markers and the marker picture, show info for markers.
 * 
 */
public class MarkerHandler {

	private ArrayList<Marker> createdMarkers = new ArrayList<Marker>();
	private HashSet<String> hashSet = new HashSet<String>();
	private HashSet<LatLng> k = new HashSet<LatLng>();
	private final String PREFS_NAME = "MySharedPrefs";
	private Set<String> lastset;
	//private Context context;
	private LocationMarker lm;
	
	
	public MarkerHandler(){
	
	
	
	}
	
/*
 * This method creates the pictures for all the markers.
 * It can be modified to create different colors for different types of markers but
 * this is not used right now. Instead black is used all the time. It takes the bitmap and
 * add paint at string onto it. It decides the size of the bitmap(that is taken from 
 * drawable). If the string is longer than 7 letters it shortens it to six letters and 
 * add three dots to mark for the users that the title is longer than it stands on the marker.
 */
private Bitmap createPic(String stringTitle, Resources res, String type){
	int color;
	if(type == "Event"){
		color = Color.BLACK;
	}
	else if(type == "Location"){
		color = Color.GREEN;}
	else if(type == "Owners"){
		color = Color.BLUE;}
	else{
	
		color = Color.RED;
	}
color = Color.BLACK;
	
// creates a bitmap from the drawable markerpick
	Bitmap srv = BitmapFactory.decodeResource(res, R.drawable.markerpick); 
	//scale the bitmap
	Bitmap src = Bitmap.createScaledBitmap(srv, srv.getWidth()+ srv.getWidth(), 
								srv.getHeight() + srv.getHeight(), false);
	Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);

	/*
	 * If the string is longer than 7 there are no room on the marker
	 * so shorten it and add three dots so the user understand that 
	 * the title is longer in reality
	 */
	if(stringTitle.length() > 7){
		stringTitle = (String) stringTitle.subSequence(0, 6);
		stringTitle = stringTitle + "...";
	}
	//decides how the string should be added and which style/color/size
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
	

/*
 * When the user presses a marker this popup is called upon. 
 * It shows all the info available for the marker.
 * 
 */

public void showInfo(final Context context,final LatLng point,
		final Resources res, 
		final GoogleMap myMap){
	//this.context = context;
	//Creates a NearEventHandler to be able to check distance to an event
	final NearEventHandler nen = new NearEventHandler(new Location("test"), myMap, context);

   // Inflate the popup_layout.xml
LinearLayout viewGroup = (LinearLayout) ((Activity) context).findViewById(R.layout.activity_main);
   LayoutInflater layoutInflater = (LayoutInflater) context
     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

View layout = layoutInflater.inflate(R.layout.showinfopopup, viewGroup);

//All the textviews were info are going to be putted in
TextView titleView = (TextView) layout.findViewById(R.id.txtTitle);
TextView descriptionView = (TextView) layout.findViewById(R.id.txtDescription);
TextView starttime = (TextView) layout.findViewById(R.id.startDate);
TextView endtime = (TextView) layout.findViewById(R.id.endDate);
TextView attenders = (TextView) layout.findViewById(R.id.txtAttenders);
lm = new LocationTask().getLocation(point);

//Here the info are putted into the different textview
titleView.setText(lm.getTitle());
descriptionView.setText(lm.getDescription());
int i = new LocationTask().getAttenders(lm.getLatLng());

attenders.setText(" " + i);

if(lm.getClass() == EventMarker.class){
	EventMarker em = (EventMarker) lm;
	starttime.setText(em.getStartDay() + " " + em.getStartTime());
	endtime.setText(em.getStopDay() + " " + em.getStopTime());
}


   // Creating the PopupWindow with the developers decided settings 
   final PopupWindow popup = new PopupWindow(context);
   popup.setContentView(layout);
   popup.setWindowLayoutMode(0, ViewGroup.LayoutParams.WRAP_CONTENT);
   popup.setWidth(450);
   popup.setFocusable(true);
   popup.setTouchable(true);
   
   //The two buttons on the popup
   Button buttonOK = (Button) layout.findViewById(R.id.OkB);
   Button buttonNO = (Button) layout.findViewById(R.id.CancelB);
	  
   //Make them clickable
	  buttonOK.setClickable(true);
	  buttonNO.setClickable(true);
		 
	  /*
	   * Adds an listener to buttonOK
	   * When pressed checked if the user is near enough to checkin
	   * If not create an popup to inform the user about this.
	   * If the user is near enough, add him to the event and then 
	   * dismiss the popup
	   */
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
							new LocationTask().addAttender(settings.getString("LoginUsername", "Adam"), lm.getLatLng());
							popup.dismiss();}
						//*Connect to database and add the person to the event
					}
					
				});
		/*
		 * if no dismiss the popup and let the user continue to view the map
		 * 
		 */
		buttonNO.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				popup.dismiss();	
			}});
  /*
   * Gets the projection of the map so the popup can be added at the 
   * correct position on the screen. Some offset to align the popup 
   * a bit to the right, and a bit down, relative to button's position.
   */

   Projection projection =	myMap.getProjection();
 
   //Displaying the popup at the specified location, + offsets.
   popup.showAtLocation(layout, Gravity.NO_GRAVITY, 
		   projection.toScreenLocation(point).x -popup.getWidth()/2, 
		   projection.toScreenLocation(point).y - popup.getHeight()/2);
}

/**
 * 
 * Adds markers to the current screen and filters which markers
 * are added onto screen if the user has chosen to filter the tags.
 * 
 */

public void addMarkersToScreen(GoogleMap myMap, Resources res, LatLngBounds bounds,Context context) {
	//Move down
	//The array for the markers from the database
	ArrayList<LocationMarker> databaselocationpair = null;

	//Gets the sets of tags from the filter
	SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
	Set<String> set = settings.getStringSet("tagSet", hashSet);
	
	//If the filter is empty only get events/locations with specific boundarys
	if(set.isEmpty()){
		//if the user have removed the filter , clear the map and clear 
		//k(contains all added locations). Then download the new database
		if(lastset != set){
			myMap.clear();
			k.clear();
		}

		databaselocationpair = 
			new LocationTask().getLocation(new LatLng(bounds.southwest.latitude, bounds.southwest.longitude), 
			new LatLng(bounds.northeast.latitude, bounds.northeast.longitude));
		
	}
	else{
		/*
		 * If the user have choosen to filter tags
		 * Check if the user have changed the filter clear the map and k(contains the locations)
		 * Then download the new database with the new properties
		 */
		if(lastset != set){
			myMap.clear();
			k.clear();
		}
		ArrayList<String> tags = new ArrayList<String>();
		tags.addAll(set);
		
		databaselocationpair = 
			new LocationTask().getLocation(new LatLng(bounds.southwest.latitude, bounds.southwest.longitude), 
			new LatLng(bounds.northeast.latitude, bounds.northeast.longitude),tags);}
	//sets the lastset as the current one to be able to check correct in the next call of this method
	lastset = set;



	/*
	 * For every LocationMarker in the downloaded database:
	 * create the picture with the string in it and add it to the map.
	 * If it have added 30 markers , stop and let the user change the camera
	 * to see add another 30. This rule is invoked because the developer wanted
	 * to minimize the lag when the user moved the screen.  If the user want to see
	 * more tags in a specific area one choice is to zoom in. 
	 * 
	 * We add the latlng to k to be able to check so the marker havent been added before
	 * 
	 */
	//Adds marker to the screen 
		for(LocationMarker ll : databaselocationpair){
			
			
			if(createdMarkers.size()> 30){
				break;}

			if(!k.contains(ll.getLatLng())){
				Marker m = myMap.addMarker(new MarkerOptions()
					.position(ll.getLatLng())
					.icon(BitmapDescriptorFactory
					.fromBitmap(createPic(ll.getTitle(), res, "Location"))));
				createdMarkers.add(m);

				k.add(ll.getLatLng());
		
			}
			
	
		}
		//can only add at maximum 30 markers per screen to minimize lag
		createdMarkers.clear();	

}






}




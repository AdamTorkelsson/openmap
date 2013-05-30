package com.openmap.grupp1.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**This class handles all the request to the database that is
 * related to Locations(and events).
 */
public class LocationTask extends DataBaseTask<ArrayList<NameValuePair>, Object, String>{
	/* params is the ArrayList with the information that is to be sent
	 * to the database.
	 * action is the first NameValuePair in the params list and
	 * specifies the action to be performed by the database php.
	 *
	 * Most methods call this.execute and thereby call
	 * the doInBackground method inherited from AsyncTask.
	 * 
	 * Most of the methods that return something calls the get() inherited
	 * from Aync task, and get the result passed from doInBackground
	 * to onPostExecute.
	 */
	ArrayList<NameValuePair> params;
	BasicNameValuePair action;

	/**
	 * Add a location to the database. 
	 * @param lm the LocationMarker that is to be added.
	 */
	@SuppressWarnings("unchecked")
	public void addLocation(LocationMarker lm){
		params	=	lm.getPairsList();
		action	= new BasicNameValuePair("action","addLocation");
		params.add(action);
		this.execute(params);
	}
	
	/**
	 * adds an attender to a location(event) specified by a LatLng.
	 * @param username the username of the person attending
	 * @param ll the specified position of the location(event).
	 */
	@SuppressWarnings("unchecked")
	public void addAttender(String username, LatLng ll){
		params	=	new ArrayList<NameValuePair>();
		action = new BasicNameValuePair("action", "addAttender");
		params.add(action);
		params.add(new BasicNameValuePair("username",username));
		params.add(new BasicNameValuePair("lat", ""+ll.latitude));
		params.add(new BasicNameValuePair("lng", ""+ll.longitude));

		this.execute(params);
	}

	/**
	 * returns the number of attenders on an event specified by a LatLng.
	 * @param ll
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getAttenders(LatLng ll){

		params	=	new ArrayList<NameValuePair>();
		action = new BasicNameValuePair("action", "getAttenders");
		params.add(action);

		params.add(new BasicNameValuePair("lat",""+ll.latitude));
		params.add(new BasicNameValuePair("lng",""+ll.longitude));

		this.execute(params);
		
		/*
		 * Creates an integer from the JSON-array returned by the database.
		 * If an exception has occured 0 is returned.
		 */
		try {

			JSONArray jArray = new JSONArray(this.get());
			JSONObject json_data = new JSONObject();	
			json_data = jArray.getJSONObject(0);
			return Integer.parseInt(json_data.getString("attenders"));

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return 0;
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return 0;
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return 0;
		}


	}
	 /** Returns an ArrayList with all the Locations(Events) in
	  * the database. 
	  */
	@SuppressWarnings("unchecked")
	public ArrayList<LocationMarker> getLocation(){
		params	=	new ArrayList<NameValuePair>();
		action	=	new BasicNameValuePair("action","getAllLocations");
		params.add(action);
		this.execute(params);

		/*
		 * tries to parse the response (a JSON-array) to an ArrayList. Returns null
		 * if something goes wrong.
		 */
		try {

			return jSONtoLocationMarkerParser(this.get());

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * returns an ArrayList with 
	 * all Locations(events) inside a square limited by a down left corner
	 * and an up right corner.
	 * @param llmin the down left corner limit
	 * @param llmax the up right corner limit
	 * @return ArrayList with LocationMarkers.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<LocationMarker> getLocation(LatLng llmin, LatLng llmax){
		params	=	new ArrayList<NameValuePair>();
		action	= new BasicNameValuePair("action","getSquareLocations");
		params.add(action);
		params.add(new BasicNameValuePair("minLat",""+llmin.latitude));
		params.add(new BasicNameValuePair("minLng",""+llmin.longitude));
		params.add(new BasicNameValuePair("maxLat",""+llmax.latitude));
		params.add(new BasicNameValuePair("maxLng",""+llmax.longitude));

		this.execute(params);
		
		/*
		 * tries to parse the response (a JSON-array) to an ArrayList. Returns null
		 * if something goes wrong.
		 */
		try {

			return jSONtoLocationMarkerParser(this.get());

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * returns an ArrayList with 
	 * all Locations(events) 
	 * with specific tags in an ArrayList inside a square limited by a down left corner
	 * and an up right corner.
	 * @param llmin the down left corner limit
	 * @param llmax the up right corner limit
	 * @return ArrayList with LocationMarkers.
	 */
	public ArrayList<LocationMarker> getLocation(LatLng llmin, LatLng llmax, ArrayList<String> tags){
		params	=	new ArrayList<NameValuePair>();
		action	= new BasicNameValuePair("action","getSquareTagLocations");
		params.add(action);
		params.add(new BasicNameValuePair("minLat",""+llmin.latitude));
		params.add(new BasicNameValuePair("minLng",""+llmin.longitude));
		params.add(new BasicNameValuePair("maxLat",""+llmax.latitude));
		params.add(new BasicNameValuePair("maxLng",""+llmax.longitude));
		
		/*
		 * the tags are handled like tag1, tag2, ... tagN in the database.
		 */
		int i = 1;
		for(String s : tags){
			params.add(new BasicNameValuePair("tag"+i,""+s));
		}


		this.execute(params);
	
		/*
		 * tries to parse the response (a JSON-array) to an ArrayList. Returns null
		 * if something goes wrong.
		 */
		try {

			return jSONtoLocationMarkerParser(this.get());

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	/**
	 * returns the LocationMarker that is found on a specific LatLng ll.
	 * @param ll the position of the Location
	 * @return a LocationMarker
	 */
	@SuppressWarnings("unchecked")
	public LocationMarker getLocation(LatLng ll){

		params	= new ArrayList<NameValuePair>();
		action	= new BasicNameValuePair("action","getSpecificLocation");
		params.add(action);
		params.add(new BasicNameValuePair("lat",""+ll.latitude));
		params.add(new BasicNameValuePair("lng",""+ll.longitude));

		this.execute(params);
		
		/*
		 * tries to parse the response (a JSON-array) to an ArrayList. Returns null
		 * if something goes wrong.
		 */
		try {

			return jSONtoLocationMarkerParser(this.get()).get(0);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected String doInBackground(ArrayList<NameValuePair>... params) {
		/*sends params[0] to the database, and get
		 * the response by calling getFromDB().
		 */
		sendToDB(params[0]);
		return getFromDB();
	}

	@Override
	protected void onPostExecute(String result) {

	}
	/**
	 * Creates an ArrayList with locationmarkers from JSON-encoded strings. 
	 */
	protected ArrayList<LocationMarker> jSONtoLocationMarkerParser(String json) {
		try{
			ArrayList<LocationMarker> lpArray	= new ArrayList<LocationMarker>();
			JSONArray jArray					= new JSONArray(json);
			JSONObject json_data				= new JSONObject();		

			for (int i=0; i<jArray.length(); i++){

				json_data = jArray.getJSONObject(i);
				LocationMarker lp;
				String title = json_data.getString("title");
				Double lat = Double.parseDouble(json_data.getString("latitude"));
				Double lng = Double.parseDouble(json_data.getString("longitude"));
				String desc = json_data.getString("description");
				if (json_data.getString("startDay").equals("0")){ //If its a LocationMarker
					lp = new LocationMarker(title, lat, lng, desc, null);
				}else{ //If it is an EventMarker
					String startDay = json_data.getString("startDay");
					
					/*
					 * Make sure startDay is in the format YYYY-MM-DD
					 */
					startDay = startDay.substring(0,4)+'-'+startDay.substring(4,6)+'-'+ startDay.substring(6);
					
					String stopDay = json_data.getString("stopDay");
					
					/*
					 * Make sure stopDay is in the format YYYY-MM-DD
					 */
					stopDay = stopDay.substring(0,4)+'-'+stopDay.substring(4,6)+'-'+ stopDay.substring(6);
					
					String startTime = json_data.getString("startTime");
					
					/*
					 * Make sure startTime is in the format HH:MM
					 */
					while(startTime.length() < 4)
						startTime = "0".concat(startTime);
					startTime =  startTime.substring(0,2)+':'+ startTime.substring(2);
					
					String stopTime = json_data.getString("stopTime");
					
					/*
					 * Make sure stopTime is in the format HH:MM
					 */
					while(stopTime.length() < 4)
						stopTime = "0".concat(stopTime);	
					stopTime =  stopTime.substring(0,2)+':'+ stopTime.substring(2);
					
					lp = new EventMarker(title, lat, lng, desc, null, startDay, stopDay, startTime, stopTime);;
				}

				lpArray.add(lp);
			}

			return lpArray;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}

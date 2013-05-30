package com.openmap.grupp1.database;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class LocationTask extends DataBaseTask<ArrayList<NameValuePair>, Object, String>{
	ArrayList<NameValuePair> params;
	BasicNameValuePair action;

	@SuppressWarnings("unchecked")
	public void addLocation(LocationMarker lm){

		params	=	lm.getPairsList();

		action	= new BasicNameValuePair("action","addLocation");

		params.add(action);

		this.execute(params); //Lägg till dem... fungerar? Skall eller skall inte returna något?
	}

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

	@SuppressWarnings("unchecked")
	public int getAttenders(LatLng ll){

		params	=	new ArrayList<NameValuePair>();
		action = new BasicNameValuePair("action", "getAttenders");
		params.add(action);

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(0);


		Log.d("PAER1",""+ll.latitude);
		Log.d("PAER2",""+ll.latitude);
		Log.d("PAER1",""+nf.format(ll.latitude));
		Log.d("PAER2",""+nf.format(ll.latitude));

		params.add(new BasicNameValuePair("lat",""+ll.latitude));
		params.add(new BasicNameValuePair("lng",""+ll.longitude));

		this.execute(params);

		try {
			Log.d("Database:",this.get());
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

	@SuppressWarnings("unchecked")
	public ArrayList<LocationMarker> getLocation(){
		params	=	new ArrayList<NameValuePair>();
		action	=	new BasicNameValuePair("action","getAllLocations");
		params.add(action);
		this.execute(params);


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

	public ArrayList<LocationMarker> getLocation(LatLng llmin, LatLng llmax, ArrayList<String> tags){
		params	=	new ArrayList<NameValuePair>();
		action	= new BasicNameValuePair("action","getSquareTagLocations");
		params.add(action);
		params.add(new BasicNameValuePair("minLat",""+llmin.latitude));
		params.add(new BasicNameValuePair("minLng",""+llmin.longitude));
		params.add(new BasicNameValuePair("maxLat",""+llmax.latitude));
		params.add(new BasicNameValuePair("maxLng",""+llmax.longitude));

		int i = 1;
		for(String s : tags){
			params.add(new BasicNameValuePair("tag"+i,""+s));
		}


		this.execute(params);

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
	@SuppressWarnings("unchecked")
	public LocationMarker getLocation(LatLng ll){

		params	= new ArrayList<NameValuePair>();
		action	= new BasicNameValuePair("action","getSpecificLocation");
		params.add(action);
		params.add(new BasicNameValuePair("lat",""+ll.latitude));
		params.add(new BasicNameValuePair("lng",""+ll.longitude));

		this.execute(params);

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

		sendToDB(params[0]);
		return getFromDB();
	}

	@Override
	protected void onPostExecute(String result) {

	}

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
				if (json_data.getString("startDay").equals("0")){
					lp = new LocationMarker(title, lat, lng, desc, null);
				}else{
					String startDay = json_data.getString("startDay");
					startDay = startDay.substring(0,4)+'-'+startDay.substring(4,6)+'-'+ startDay.substring(6);
					String stopDay = json_data.getString("stopDay");
					stopDay = stopDay.substring(0,4)+'-'+stopDay.substring(4,6)+'-'+ stopDay.substring(6);
					String startTime = json_data.getString("startTime");
					if(startTime.length()==4)
						startTime =  startTime.substring(0,2)+':'+ startTime.substring(2);
					else if(startTime.length()==3)
						startTime = "0" + startTime.substring(0,1)+':'+ startTime.substring(1);
					else if(startTime.length()==2)
						startTime = "00:" + startTime.substring(0);
					else
						startTime = "00:0" +startTime.substring(0);
					String stopTime = json_data.getString("stopTime");
					if(stopTime.length()==4)
						stopTime =  stopTime.substring(0,2)+':'+ stopTime.substring(2);
					else if(startTime.length()==3)
						stopTime = "0" + stopTime.substring(0,1)+':'+ stopTime.substring(1);
					else if(startTime.length()==2)
						stopTime = "00:" + stopTime.substring(0);
					else
						stopTime = "00:0" +stopTime.substring(0);
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

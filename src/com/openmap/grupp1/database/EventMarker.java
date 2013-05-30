package com.openmap.grupp1.database;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.android.gms.maps.model.LatLng;

/**
 * EventMarker is used to describe an event. It shares attributes with the
 * LocationMarkers, but has also start- and stop day and time.
 * 
 *
 */
public class EventMarker extends LocationMarker {
	String startDay;
	String stopDay;
	String startTime;
	String stopTime;
	
	public EventMarker(String title, double lat, double lng, String desc, String[] tags, String startDay, String stopDay, String startTime, String stopTime ){
		this.title	=	title;
		this.lat	=	lat;
		this.lng	=	lng;
		this.loc	=	new LatLng(lat,lng);
		this.desc	=	desc;
		this.tags	=	tags;
		this.startDay = startDay;
		this.stopDay = stopDay;
		this.startTime = startTime;
		this.stopTime = stopTime;
		
	}

	public EventMarker(String title, LatLng loc, String desc, String[] tags, String startDay, String stopDay, String startTime, String stopTime){
		this.title	=	title;
		this.loc	=	loc;
		this.lat	=	loc.latitude;
		this.lng	=	loc.longitude;
		this.desc	=	desc;
		this.tags	=	tags;
		this.startDay = startDay;
		this.stopDay = stopDay;
		this.startTime = startTime;
		this.stopTime = stopTime;
	}
	
	public EventMarker(String title, double lat, double lng, String[] tags, String startDay, String stopDay, String startTime, String stopTime){
		this.title	=	title;
		this.lat	=	lat;
		this.lng	=	lng;
		this.loc	=	new LatLng(lat,lng);
		this.tags	=	tags;
		this.startDay = startDay;
		this.stopDay = stopDay;
		this.startTime = startTime;
		this.stopTime = stopTime;
	}

	public EventMarker(String title, LatLng loc, String[] tags, String startDay, String stopDay, String startTime, String stopTime){
		this.title	=	title;
		this.loc	=	loc;
		this.lat	=	loc.latitude;
		this.lng	=	loc.longitude;
		this.tags	=	tags;
		this.startDay = startDay;
		this.stopDay = stopDay;
		this.startTime = startTime;
		this.stopTime = stopTime;
	}
	
	public ArrayList<NameValuePair> getPairsList(){
		ArrayList<NameValuePair> nvpl = super.getPairsList();
		nvpl.add(new BasicNameValuePair("startDay", startDay));
		nvpl.add(new BasicNameValuePair("stopDay", stopDay));
		nvpl.add(new BasicNameValuePair("startTime", startTime));
		nvpl.add(new BasicNameValuePair("stopTime", stopTime));

		return nvpl;
	}
	
	
	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getStopDay() {
		return stopDay;
	}

	public void setStopDay(String stopDay) {
		this.stopDay = stopDay;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
}
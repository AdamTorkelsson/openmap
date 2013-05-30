package com.openmap.grupp1.database;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.android.gms.maps.model.LatLng;
	/**
	 * This is the class that is used to describe and create
	 * LocationMarkers. Its location is described in two ways:
	 * as separate coordinates and as a LatLng.
	 */
public class LocationMarker {
	
	String title;
	double lat;
	double lng;
	LatLng loc;
	String desc = "No description";
	String[] tags;


	public LocationMarker(){
		
	}
	
	public LocationMarker(String title, double lat, double lng, String desc, String[] tags){
		this.title	=	title;
		this.lat	=	lat;
		this.lng	=	lng;
		this.loc	=	new LatLng(lat,lng);
		this.desc	=	desc;
		this.tags	=	tags;
		
	}

	public LocationMarker(String title, LatLng loc, String desc, String[] tags){
		this.title	=	title;
		this.loc	=	loc;
		this.lat	=	loc.latitude;
		this.lng	=	loc.longitude;
		this.desc	=	desc;
		this.tags	=	tags;
	}
	
	public LocationMarker(String title, double lat, double lng, String[] tags){
		this.title	=	title;
		this.lat	=	lat;
		this.lng	=	lng;
		this.loc	=	new LatLng(lat,lng);
		this.tags	=	tags;
	}

	public LocationMarker(String title, LatLng loc, String[] tags){
		this.title	=	title;
		this.loc	=	loc;
		this.lat	=	loc.latitude;
		this.lng	=	loc.longitude;
		this.tags	=	tags;
	}
	/**
	 * this is used to get the NameValuePair in an arrayList that can be used
	 * when sending http database request.
	 * @return ArrayList<NameValuePair> an array list with keys for the attributes.
	 */
	public ArrayList<NameValuePair> getPairsList(){
		ArrayList<NameValuePair> nvpl = new ArrayList<NameValuePair>();
		nvpl.add(new BasicNameValuePair("title", title));
		nvpl.add(new BasicNameValuePair("lat", Double.toString(lat)));
		nvpl.add(new BasicNameValuePair("lng", Double.toString(lng)));
		nvpl.add(new BasicNameValuePair("desc", desc));
		
		//Add all tags as tag1,tag2,...,tagN
		int i = 0;
		for(String s : tags)
			nvpl.add(new BasicNameValuePair("tag"+i++, s));

		return nvpl;
	}
	
	//Get and set methods.
	
	/**
	 * Returns the title of the Marker.
	 * @return String the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * set the title of the Marker.
	 * 
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Returns the Latitude of the Marker.
	 * @return double the Latitude
	 */
	public double getLatitute() {
		return lat;
	}
	/**
	 * Set the latitude of the Marker.
	 */
	public void setLatitude(double lat) {
		this.lat = lat;
	}
	/**
	 * Returns the longitude of the Marker.
	 * @return double the Longitude
	 */
	public double getLongitude() {
		return lng;
	}
	/**
	 * Set the longitude of the Marker.
	 */
	public void setLongitude(double lng) {
		this.lng = lng;
	}
	/**
	 * Returns the description of the Marker.
	 * @return String the description.
	 */
	public String getDescription() {
		return desc;
	}
	/**
	 * Set the description of the Marker.
	 */
	public void setDescription(String desc) {
		this.desc = desc;
	}
	/**
	 * Returns the tags of the Marker.
	 * @return String[] the tags.
	 */
	public String[] getTags() {
		return tags;
	}
	/**
	 * Set the tags of the marker.
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	/**
	 * Returns the LatLng of the Marker.
	 * @return LatLng the LatLng.
	 */
	public LatLng getLatLng() {
	 	return loc;
	 }	 
	/**
	 * Set the LatLng of the marker.
	 */
	 public void setLatLng(LatLng loc){
	 	this.loc	=	loc;
	 }
		/**
		 * Set the LatLng from the latitude and longitude stored.
		 */
	 public void setLatLng(){
		 this.loc = new LatLng(this.lat,this.lng);
	 }
	
}

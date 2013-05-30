package com.openmap.grupp1.database;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * This class is used to check the database connection.
 */
public class CheckDBUrlTask extends DataBaseTask<Void,Boolean,Boolean> {
	/**
	 * Try the database connection.
	 * @return boolean true if connection succesful, else false.
	 */
	public Boolean tryDBConnection(){
		this.execute();
		try {
			return this.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	protected Boolean doInBackground(Void... args) {
		boolean success = false;
		
		try {
			HttpURLConnection urlConnection;
			URL u;
			u = new URL(url); //Create an URL from the url specified in DataBaseTask.
			urlConnection = (HttpURLConnection) u.openConnection();
			urlConnection.setConnectTimeout(5000);//If connection is not successful in 5 seconds, connection fails.
			urlConnection.connect();
			
			/*
			 * If the connection is successful, disconnect.
			 */
			if(HttpURLConnection.HTTP_OK == urlConnection.getResponseCode()); {
				urlConnection.disconnect();

			}
		}catch(Exception e){
			/*
			 * If an exception is thrown, the connection does not work, 
			 * and success' default value false is returned.
			 */
			return success; 
		}
		
		success = true; //Nothing went 
		return success;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {

	}
}

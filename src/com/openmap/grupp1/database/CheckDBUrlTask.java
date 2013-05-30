package com.openmap.grupp1.database;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.util.Log;

public class CheckDBUrlTask extends DataBaseTask<Void,Boolean,Boolean> {
	
	public Boolean tryDBConnection(){
		this.execute();
		try {
			Log.d("Database:", "check1");
			return this.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.d("Database:", "check2");
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.d("Database:", "check3");
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
			u = new URL(url);
			Log.d("Database:", "check4");
			urlConnection = (HttpURLConnection) u.openConnection();
			urlConnection.setConnectTimeout(5000);
			urlConnection.connect();
			Log.d("Database","JAA!");

			if(HttpURLConnection.HTTP_OK == urlConnection.getResponseCode()); {
				urlConnection.disconnect();
			Log.d("Database","JAA!");
			}
		}catch(Exception e){
			Log.d("Database:", "check5");
			Log.e("Connection Error!","Database connection failed!");
			return success;
		}
		success = true;
		return success;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {

	}
}

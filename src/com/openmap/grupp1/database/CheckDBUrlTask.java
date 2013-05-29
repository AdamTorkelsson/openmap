package com.openmap.grupp1.database;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.util.Log;

public class CheckDBUrlTask extends DataBaseTask<Void,Boolean,Boolean> {
	
	public Boolean tryDBConnection(){
		this.execute();
		boolean result = false;
		try {
			result = this.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@Override
	protected Boolean doInBackground(Void... args) {
		boolean success = false;
		
		try {
			HttpURLConnection urlConnection;
			URL u;
			u = new URL(url);
			urlConnection = (HttpURLConnection) u.openConnection();
		}catch(Exception e){
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

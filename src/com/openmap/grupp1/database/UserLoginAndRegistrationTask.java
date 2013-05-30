package com.openmap.grupp1.database;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class UserLoginAndRegistrationTask extends DataBaseTask<ArrayList<NameValuePair>, Void, Boolean>{
	ArrayList<NameValuePair> params;
	BasicNameValuePair action;

	public boolean loginUser(String username, String password){
		params = new ArrayList<NameValuePair>();
		action = new BasicNameValuePair("action","loginUser");
		params.add(action);		
		params.add(new BasicNameValuePair("username" , username));
		params.add(new BasicNameValuePair("password" , password));
		this.execute(params);
		try {
			return this.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean registerUser(String username, String password){
		params = new ArrayList<NameValuePair>();
		action = new BasicNameValuePair("action","registerUser");
		params.add(action);		
		params.add(new BasicNameValuePair("username" , username));
		params.add(new BasicNameValuePair("password" , password));
		this.execute(params);
		try {
			return this.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	protected Boolean doInBackground(ArrayList<NameValuePair>...params) {
		sendToDB(params[0]);
		if(getFromDB().equals("true"))
			return true;
			else
			return false;
	}
	// onPostExecute displays the results of the AsyncTask.
	@Override
	protected void onPostExecute(Boolean result) {
		
	}





}

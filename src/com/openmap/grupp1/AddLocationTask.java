package com.openmap.grupp1;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;


import android.os.AsyncTask;
import android.util.Log;


public class AddLocationTask extends AsyncTask<ArrayList<NameValuePair>, Void, ArrayList<String>>{
	private final String url = "http://129.16.205.115/php_mysql/addLocation.php";
	public AddLocationTask(){
		
	}
	
	public void addLocation(ArrayList<NameValuePair> params) throws Exception{
		HttpClient httpClient = new DefaultHttpClient();//used to execute post
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new UrlEncodedFormEntity(params));
		
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = httpClient.execute(httpPost, responseHandler);
						
	}
	@Override
	protected ArrayList<String> doInBackground(ArrayList<NameValuePair>... params) {
	try {
		addLocation(params[0]);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	return null;
	}
	
	@Override
	protected void onPostExecute(ArrayList<String> result){
		
	}
	
}


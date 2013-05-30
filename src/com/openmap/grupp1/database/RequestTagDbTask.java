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
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

public class RequestTagDbTask extends DataBaseTask<ArrayList<NameValuePair>, Void, String>{
	ArrayList<NameValuePair> params;
	BasicNameValuePair action;

	public ArrayList<String> getTagArray(String tagString) throws InterruptedException, ExecutionException{
		params = new ArrayList<NameValuePair>();
		action = new BasicNameValuePair("action","requestTags");
		params.add(action);
		params.add(new BasicNameValuePair("tagName", tagString));
		this.execute(params);
		return jSONToTagsParser(this.get());
	}





	@Override
	protected String doInBackground(ArrayList<NameValuePair>... params) {
		sendToDB(params[0]);
		try {
			return getFromDB();			
		} catch (Exception e) {
			return null;
		}
	}
	// onPostExecute displays the results of the AsyncTask.
	@Override
	protected void onPostExecute(String result) {

	}

	protected ArrayList<String> jSONToTagsParser(String json) {
		try{
			JSONArray jArray = new JSONArray(json);
			JSONObject json_data = new JSONObject();
			ArrayList<String> tagArray = new ArrayList<String>();
			String tagName;

			for (int i=0; i<jArray.length(); i++){
				json_data = jArray.getJSONObject(i);
				tagName = json_data.getString("tagName");
				tagArray.add(tagName);
			}
			
			return tagArray;
		}catch(Exception e){
			
			return null;
		}
	}
}

package com.openmap.grupp1;

import java.util.ArrayList;

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
import android.util.Log;


public class GetLocationTask extends AsyncTask<Void, Void, ArrayList<LocationPair>>{
	private final String url = "http://129.16.232.157/php_mysql/getLocation.php";
	public GetLocationTask(){
		
	}
	
	public ArrayList<LocationPair> getLocation() throws Exception{
		ArrayList<LocationPair> lpArray = new ArrayList<LocationPair>();
		
		HttpClient httpClient = new DefaultHttpClient();//used to execute post
		HttpPost httpPost = new HttpPost(url);
		ArrayList<NameValuePair> al = new ArrayList<NameValuePair>();
		al.add(new BasicNameValuePair("tag", "add"));
		httpPost.setEntity(new UrlEncodedFormEntity(al));
		
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = httpClient.execute(httpPost, responseHandler);
		
		JSONArray jArray = new JSONArray(responseBody);
		JSONObject json_data = new JSONObject();		
		Log.d("HEJ",""+jArray.length());
		for (int i=0; i<jArray.length(); i++){
		LocationPair lp = new LocationPair();
			json_data = jArray.getJSONObject(i);
			//JSONArray tags = new JSONArray(json_data.getString("tags"));
			//JSONArray locations = new JSONArray(json_data.getString("locations"));
			
			//for (int j = 0; j<locations.length(); j++){
				//JSONObject json_data = locations.getJSONObject(j);
				Log.d("HALLÅ",""+json_data.getString("title"));
				lp.setTitle(json_data.getString("title"));
				Log.d("HALLÅ",""+json_data.getString("latitude"));
				lp.setLatitude(Double.parseDouble(json_data.getString("latitude")));
				Log.d("HALLÅ",""+json_data.getString("longitude"));
				lp.setLongitude(Double.parseDouble(json_data.getString("longitude")));
				Log.d("HALLÅ",""+json_data.getString("description"));
				lp.setDescription(json_data.getString("description"));
			//}
				/*String[] tagArray = new String[tags.length()];
			for (int j = 0; j<tags.length(); j++){
				JSONObject tags_data = tags.getJSONObject(j);
				Log.d("HALLÅ",""+tags_data.getString("tag"));
				tagArray[j] = tags_data.getString("tag");
			}
			lp.setTags(tagArray);*/
			lp.setLatLng();
			lpArray.add(lp);
			//Log.i("tag"+i,"tag: " + tagName);
			Log.i("Jarray enght", ""+jArray.length());
			Log.d("how big is it now?",""+lpArray.size());
		}

		Log.i("koll1:",responseBody);

		Log.d("kolla här då!:", "The response is: " + lpArray.size());
		return lpArray;
		
						
	}
	@Override
	protected ArrayList<LocationPair> doInBackground(Void... params) {
	try {
		return getLocation();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	return null;
	}
	
	@Override
	protected void onPostExecute(ArrayList<LocationPair> result){
		
	}
	
}


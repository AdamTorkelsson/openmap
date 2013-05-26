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
import android.util.Log;

public class RequestTagDbTask extends AsyncTask<Void, Void, ArrayList<String>>{
	private final String url = "http://129.16.205.115/php_mysql/tagRequest.php";
	private List<NameValuePair> parameters;

	public RequestTagDbTask(){
		parameters = new ArrayList<NameValuePair>();

	}

	public ArrayList<String> getTagArray(String tagString) throws InterruptedException, ExecutionException{
		parameters.add(new BasicNameValuePair("tag", "request"));
		parameters.add(new BasicNameValuePair("tagName", tagString));
		this.execute();
		return this.get();
	}



	public ArrayList<String> requestTagArray() throws Exception{
		HttpClient httpClient = new DefaultHttpClient();//used to execute post
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new UrlEncodedFormEntity(parameters));

		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = httpClient.execute(httpPost, responseHandler);

		JSONArray jArray = new JSONArray(responseBody);
		JSONObject json_data = new JSONObject();
		ArrayList<String> tagArray = new ArrayList<String>();
		String tagName;

		for (int i=0; i<jArray.length(); i++){
			json_data = jArray.getJSONObject(i);
			tagName = json_data.getString("tagName");
			tagArray.add(tagName);
		}


		return tagArray;


	}

	@Override
	protected ArrayList<String> doInBackground(Void... v) {
		try {
			return requestTagArray();			
		} catch (Exception e) {
			return null;
		}
	}
	// onPostExecute displays the results of the AsyncTask.
	@Override
	protected void onPostExecute(ArrayList<String> result) {

	}

}

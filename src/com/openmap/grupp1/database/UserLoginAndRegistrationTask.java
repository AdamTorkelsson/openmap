package com.openmap.grupp1.database;

import java.util.ArrayList;
import java.util.List;

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

public class UserLoginAndRegistrationTask extends AsyncTask<Void, Void, Boolean>{
	private final String url = "http://129.16.234.200/php_mysql/test.php";
	private List<NameValuePair> parameters;

	public UserLoginAndRegistrationTask(String username, String password){
		parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("username" , username));
		parameters.add(new BasicNameValuePair("password" , password));
	}

	public void loginUser(){
		parameters.add(new BasicNameValuePair("tag", "login"));
		this.execute();
	}

	public void registerUser(){
		parameters.add(new BasicNameValuePair("tag", "register"));
		this.execute();
	}

	public boolean postMysql() throws Exception{
		Log.i("koll1:","1");
		HttpClient httpClient = new DefaultHttpClient();//used to execute post
		Log.i("koll1:","2");
		HttpPost httpPost = new HttpPost(url);
		Log.i("koll1:","3");
		httpPost.setEntity(new UrlEncodedFormEntity(parameters));
		Log.i("koll1:","4");
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		Log.i("koll1:","5");
		String responseBody = httpClient.execute(httpPost, responseHandler);

		Log.i("koll1:",responseBody);

		Log.d("kolla här då!:", "The response is: " + responseBody);
		if(responseBody.equals("true"))
		return true;
		else
		return false;
		//return responseBody;
	}

	@Override
	protected Boolean doInBackground(Void... v) {

		try {
			return postMysql();
		} catch (Exception e) {
			return false;
		}
	}
	// onPostExecute displays the results of the AsyncTask.
	@Override
	protected void onPostExecute(Boolean result) {
		
	}





}

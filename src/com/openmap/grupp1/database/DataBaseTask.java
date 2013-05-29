package com.openmap.grupp1.database;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.os.AsyncTask;


public abstract class DataBaseTask<T1,T2,T3> extends AsyncTask<T1, T2, T3> implements DatabaseInterface<NameValuePair>{
	final String url = "http://46.239.97.219/php_mysql/index.php";
	BasicNameValuePair action;
	String outparams;

	public void sendToDB(ArrayList<NameValuePair> inparams){

		HttpClient httpClient = new DefaultHttpClient();//used to execute post
		HttpPost httpPost = new HttpPost(url);		
		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(inparams));
			outparams = httpClient.execute(httpPost, responseHandler);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getFromDB(){

		return outparams;		
	}

}

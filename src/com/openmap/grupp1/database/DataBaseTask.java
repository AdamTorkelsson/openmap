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
import android.util.Log;

/**
 * 
 * DataBaseTask is the abstract class that is the superclass for all the
 * database related classes. It implements DatabaseInterface and contains 
 * both methods.
 * It also extends AsyncTask, to be able to do Http connections(Http connections
 * cannot be made in the main thread).
 * The three parameters are generic so that the subclasses may have different
 * output and input in the methods inherited from AsyncTask.
 */

public abstract class DataBaseTask<T1,T2,T3> extends AsyncTask<T1, T2, T3> implements DatabaseInterface<NameValuePair>{

	/*url: php-file url for database connection.
	*action: the action that should be performed by the php-file.
	*outparams: the string that is returned by the database.
	*/

	final String url = "http://129.16.234.200/php_mysql/index.php";
	String outparams;
	
	/**{@inheritDoc}
	 * @param inparams the pairs that should be sent to the database.
	 * the first string in the NameValuePair is used as a key in the
	 * php-database.
	 */
	public void sendToDB(ArrayList<NameValuePair> inparams){
		/* httpClient: the httpClient for a Http connection.
		 * httpPost: A prepared HttpPost to the database-url.
		 * responseHandler: A responseHandler is used to get
		 * response in string format.
		 */
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);		
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		
		/* Setting the httpPost entity to the inparameters url-encoded.
		 * outparams is set to the response from the database.
		 * If the request is not successful an exception is thrown and
		 * the operation is aborted.
		 */
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(inparams));
			Log.d("KOMMER HIR?","JA");
			outparams = httpClient.execute(httpPost, responseHandler);
			Log.d("KOMMER även?",outparams);
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
	
	/*
	 * (non-Javadoc)
	 * @see com.openmap.grupp1.database.DatabaseInterface#getFromDB()
	 */
	public String getFromDB(){

		return outparams;		
	}

}

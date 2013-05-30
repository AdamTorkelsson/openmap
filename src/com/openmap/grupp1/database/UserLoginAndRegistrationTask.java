package com.openmap.grupp1.database;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * 
 * This class is used for user login and registration.
 * Since it communicates to the database it extends
 * DataBaseTask.
 *
 */
public class UserLoginAndRegistrationTask extends DataBaseTask<ArrayList<NameValuePair>, Void, Boolean>{
	ArrayList<NameValuePair> params;
	BasicNameValuePair action;
	/**
	 * Userlogin. Takes a username and a password and returns true if
	 * the user is found in the database.
	 * @param username the username.
	 * @param password the password.
	 * @return true if login is successful, else false.
	 */
	@SuppressWarnings("unchecked")
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
	/**
	 * Takes a username and a password and register in the database.
	 * returns true if the registration is successful.
	 * @param username the username.
	 * @param password the password.
	 * @return true if register is successful, else false.
	 */
	@SuppressWarnings("unchecked")
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

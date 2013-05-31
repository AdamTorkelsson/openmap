package com.openmap.grupp1;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.openmap.grupp1.database.CheckDBUrlTask;
import com.openmap.grupp1.helpfunctions.RetryConnectionFragment;


/**
 * A splash screen for when the user starts the program. Active for 3 seconds.
 */
public class SplashScreenActivity extends FragmentActivity implements RetryConnectionFragment.RetryConnectionListener{
	private long ms=0;
	private long splashTime=3000;
	private boolean splashActive = true;
	private boolean paused=false;
	private DialogFragment dialog; 
	private boolean connection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		//Hides the titlebar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Defines the content of the activity
		setContentView(R.layout.splashscreen);
		
		//Check if database is available
		checkConnection();
		
		//Runs for three seconds, then checks the connection
		Thread mythread = new Thread() {
			public void run() {
				try {
					while (splashActive && ms < splashTime) {
						if(!paused)
							ms=ms+100;
						sleep(100);
					}
				} catch(Exception e) {}
				finally {
					if (connection) {
						Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
						startActivity(intent);
						}
				}
			}
		};
		mythread.start();
		

		
	}

	/**
	 * Checks if the connection to the database is available
	 */
	public void checkConnection() {
		//If the connection isn't available, show the RetryConnectionFragment
		if (!new CheckDBUrlTask().tryDBConnection()) {
			showRetryConnectionFragment();
			connection = false;
		}
		else{
			connection = true;
		}
	}

	/**
	 * Shows the RetryConnectionFragment 
	 */
	public void showRetryConnectionFragment() {
		// Create an instance of the dialog fragment and show it
		dialog = new RetryConnectionFragment();
		dialog.show(getFragmentManager(), "RetryConnectionFragment");
	}

	// The dialog fragment receives a reference to this Activity through the
	// Fragment.onAttach() callback, which it uses to call the following methods
	// defined by the RetryConnectionFragment.RetryConnectionListener interface

	@Override
	public void onDialogPositiveClick(android.app.DialogFragment dialog) {
		checkConnection();
	}
	@Override
	public void onDialogNegativeClick(android.app.DialogFragment dialog) {
		checkConnection();
	}

}

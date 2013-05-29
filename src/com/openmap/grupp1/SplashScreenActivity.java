package com.openmap.grupp1;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

import com.openmap.grupp1.database.CheckDBUrlTask;
import com.openmap.grupp1.helpfunctions.RetryConnectionFragment;
import com.openmap.grupp1.helpfunctions.SettingsActivity;


/*
 * A splashscreen for when the user starts the program. 
 * Is active for 3 seconds
 */
public class SplashScreenActivity extends FragmentActivity implements RetryConnectionFragment.RetryConnectionListener{
	private long ms=0;
	private long splashTime=3000;
	private boolean splashActive = true;
	private boolean paused=false;
	private DialogFragment dialog; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Hides the titlebar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splashscreen);

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
					//Check if database is available
					checkConnection();
				}
			}
		};
		mythread.start();
	}
	
	//Check if database is available
	public void checkConnection() {
		boolean conn = new CheckDBUrlTask().tryDBConnection();
		
		if (!conn) {
			Log.d("Database:","connection not available!");
			showRetryConnectionDialog();
		//Intent intent = new Intent(SplashScreenActivity.this, SettingsActivity.class);
		//startActivity(intent);
	}
		else{
			Log.d("Database:","connection available!");
			Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
			startActivity(intent);
		}
	}
	
    public void showRetryConnectionDialog() {
        // Create an instance of the dialog fragment and show it
         dialog = new RetryConnectionFragment();
 		 dialog.show(getFragmentManager(), "RetryConnectionFragment");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface

	@Override
	public void onDialogPositiveClick(android.app.DialogFragment dialog) {
		checkConnection();
	}
	@Override
	public void onDialogNegativeClick(android.app.DialogFragment dialog) {
		// TODO Auto-generated method stub
		checkConnection();
	}
    
    }

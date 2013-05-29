package com.openmap.grupp1;

import com.openmap.grupp1.database.CheckDBUrlTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
/*
 * A splashscreen for when the user starts the program. 
 * Is active for 3 seconds
 */
public class SplashScreenActivity extends Activity {
	private long ms=0;
	private long splashTime=3000;
	private boolean splashActive = true;
	private boolean paused=false;

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
					boolean conn = new CheckDBUrlTask().tryDBConnection();
					if (!conn)
						Log.d("Database:","connection not available!");
						//What will happen!? 
					else{
						Log.d("Database:","connection available!");
						Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
						startActivity(intent);
					}
				}
			}
		};
		mythread.start();
	}
}
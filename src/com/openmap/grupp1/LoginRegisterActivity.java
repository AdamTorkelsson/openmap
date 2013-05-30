package com.openmap.grupp1;
import com.openmap.grupp1.database.UserLoginAndRegistrationTask;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * This is the activity that creates the login/register-screen. 
 *
 *
 */
public class LoginRegisterActivity extends Activity implements OnClickListener{

	/*
	 * Mode is used to check what state the activity is in: Login or register.
	 * The different EditText, Button and TextView are the buttons and field
	 * associated to the login/register screen.
	 */
	private enum Mode {LOGIN, REGISTER}; 
	private Mode mode;
	private EditText userText;
	private EditText passwordText;
	private Button loginOrRegisterButton;
	private TextView notOrAlreadyUserString;
	private PopupandDialogHandler tpd = new PopupandDialogHandler(this);
	private final String PREFS_NAME = "MySharedPrefs";
	private SharedPreferences.Editor editor;
	private String username;
	private String password;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginregistrerview);

		//Sets the animation when opening this activity
		overridePendingTransition(R.anim.map_out,R.anim.other_in);

		//Set buttons and fields
		userText = (EditText) findViewById(R.id.usernameField);
		passwordText = (EditText) findViewById(R.id.passwordField);
		loginOrRegisterButton = (Button) findViewById(R.id.loginOrRegisterButton);
		notOrAlreadyUserString = (TextView) findViewById(R.id.notUserString);

		//Set whats clickable. Set listener to these.
		loginOrRegisterButton.setClickable(true);
		loginOrRegisterButton.setOnClickListener(this);

		notOrAlreadyUserString.setClickable(true);
		notOrAlreadyUserString.setOnClickListener(this);

		//Set default mode to Login.
		mode = Mode.LOGIN;
		
		//Make it possible to store username and password as a shared preference.
		SharedPreferences notificationmessenger = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
		editor = notificationmessenger.edit();



	}

	//Specifies the options menu, disabling the title and home button
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.standardmenu, menu);
		ActionBar ab = getActionBar();
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F39C12")));
		return true;
	}

	//Switch layout, from Login to Register or vice versa.
	public void switchLoginLayout(){

		switch(mode){

		case LOGIN:
			loginOrRegisterButton.setText(R.string.register_string);
			notOrAlreadyUserString.setText(R.string.already_user_string);
			mode = Mode.REGISTER;
			break;			

		case REGISTER:
			loginOrRegisterButton.setText(R.string.login_string);
			notOrAlreadyUserString.setText(R.string.not_user_string);
			mode = Mode.LOGIN;
			break;

		}

	}
	
	//Set whats going to happen when a button is pressed.
	@Override
	public void onClick(View v) {
		
		if (v == loginOrRegisterButton){ //If the button is the login/registerbutton:
			/*
			 * Get username and password from the text fields.
			 */
			username = userText.getText().toString();
			password = passwordText.getText().toString();
			
			/*
			 * Login or Register depending on mode.
			 */
			switch(mode){

			case LOGIN:

				if (new UserLoginAndRegistrationTask().loginUser(username, password))//If login is successful.
					loginSuccess();
				else
					loginFail();
				break;

			case REGISTER: 

				if (new UserLoginAndRegistrationTask().registerUser(username, password))//If registration is successful
					registerSuccess();
				else
					registerFail();
				break;
			}

		}else if (v == notOrAlreadyUserString){//If the text is clicked, change from Login to Register (or vice versa)
			switchLoginLayout();
		}	
	}
	/**
	 * what happens if registration is successful.
	 */
	private void registerSuccess() {
		InputMethodManager imm = (InputMethodManager)this.getSystemService( Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(((Activity) this).getCurrentFocus().getWindowToken(),      
				InputMethodManager.HIDE_NOT_ALWAYS);
		editor.putString("LoginUsername", username );
		editor.putString("LoginPassword", password);
		editor.commit();
		this.finish();
	}
	
	/**
	 * what happens if registration fails.
	 */
	private void registerFail() {
		tpd.standardDialog(R.string.registerfailed, "Ok", false);
		Log.d("info","what will happen on registerFail?");

	}
	
	/**
	 * what happens if login fails.
	 */
	private void loginFail() {
		tpd.standardDialog(R.string.loginfailed, "Ok", false);

	}
	/**
	 * what happens if login is successful.
	 */
	private void loginSuccess() {
		/*
		 * Hides the keyboard.
		 */
		InputMethodManager imm = (InputMethodManager)this.getSystemService( Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(((Activity) this).getCurrentFocus().getWindowToken(),      
				InputMethodManager.HIDE_NOT_ALWAYS);
		
		/*
		 * Stores the username and password into shared preferences.
		 */
		editor.putString("LoginUsername", username );
		editor.putString("LoginPassword", password);
		editor.commit();
		this.finish();
	}	

	@Override
	public void onBackPressed() {
		tpd.standardDialog(R.string.youhavetologin, "Ok", false);//If back is pressed, a massage pops up.
	}

}
package com.openmap.grupp1;


import java.util.concurrent.ExecutionException;

import com.openmap.grupp1.database.UserLoginAndRegistrationTask;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginRegisterActivity extends Activity implements OnClickListener{
	
	private enum Mode {LOGIN, REGISTER};
	private Mode mode;
	private EditText userText;
	private EditText passwordText;
	private Button loginOrRegisterButton;
	private TextView notOrAlreadyUserString;
	private TutorialPopupDialog tpd = new TutorialPopupDialog(this);
	private final String PREFS_NAME = "MySharedPrefs";
	private SharedPreferences.Editor editor;
	private String username;
	private String password;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginregistrerview);
		
		userText = (EditText) findViewById(R.id.usernameField);
		passwordText = (EditText) findViewById(R.id.passwordField);
		loginOrRegisterButton = (Button) findViewById(R.id.loginOrRegisterButton);
		notOrAlreadyUserString = (TextView) findViewById(R.id.notUserString);
		
		loginOrRegisterButton.setClickable(true);
		loginOrRegisterButton.setOnClickListener(this);
		
		notOrAlreadyUserString.setClickable(true);
		notOrAlreadyUserString.setOnClickListener(this);

		mode = Mode.LOGIN;
		
		SharedPreferences notificationmessenger = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
		editor = notificationmessenger.edit();
		
		

	}
	
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

	@Override
	public void onClick(View v) {
		
		if (v == loginOrRegisterButton){
			
			username = userText.getText().toString();
			password = passwordText.getText().toString();
			UserLoginAndRegistrationTask ular = new UserLoginAndRegistrationTask(username, password);
				
			switch(mode){
			
			case LOGIN: ular.loginUser();
				try {
					if (ular.get()){
						loginSuccess();
					}else{
						loginFail();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
			
			case REGISTER: ular.registerUser();
				try {
					if (ular.get()){
						registerSuccess();
					}else{
						registerFail();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
			}
			
		}else if (v == notOrAlreadyUserString){
			switchLoginLayout();
		}	
	}

	private void registerSuccess() {
		InputMethodManager imm = (InputMethodManager)this.getSystemService( Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(((Activity) this).getCurrentFocus().getWindowToken(),      
				InputMethodManager.HIDE_NOT_ALWAYS);
		editor.putString("LoginUsername", username );
		editor.putString("LoginPassword", password);
		editor.commit();
		this.finish();
	}

	private void registerFail() {
		tpd.standardDialog(R.string.registerfailed, "Ok", false);
		Log.d("info","what will happen on registerFail?");
		
	}

	private void loginFail() {
			tpd.standardDialog(R.string.loginfailed, "Ok", false);
		Log.d("info","what will happen on loginFail?");
		
	}

	private void loginSuccess() {
		InputMethodManager imm = (InputMethodManager)this.getSystemService( Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(((Activity) this).getCurrentFocus().getWindowToken(),      
				InputMethodManager.HIDE_NOT_ALWAYS);
		editor.putString("LoginUsername", username );
		editor.putString("LoginPassword", password);
		editor.commit();
		Log.d("info","what will happen on loginSuccess?");
		this.finish();
	}	
	
	@Override
	public void onBackPressed() {
		tpd.standardDialog(R.string.youhavetologin, "Ok", false);
	    // Do Here what ever you want do on back press;
	}
	
}
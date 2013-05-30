package com.openmap.grupp1.helpfunctions;
/*
 * Create event activity where the user sets title and description and maybe adds an photo.
 * Needs to be added an event time chooser
 * Connect title and description to database IF he clicks create in next step
 * 
 */


import java.io.File;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.openmap.grupp1.R;
import com.openmap.grupp1.PopupandDialogHandler;
import com.openmap.grupp1.helpfunctions.DatePickerFragment.DatePickerDialogListener;
import com.openmap.grupp1.helpfunctions.TimePickerFragment.TimePickerDialogListener;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
/**
 * Activity to be initiated when the user wants to create an event.
 */
public class CreateEventActivity extends FragmentActivity 
implements DatePickerDialogListener, TimePickerDialogListener{
	final static int TAKE_PICTURE_REQUEST_CODE = 1;
	private ImageView image;
	private TextView setStartDate;
	private TextView setEndDate;
	private TextView setStartTime;
	private TextView setEndTime;
	DatePickerFragment newDateFragment;
	TimePickerFragment newTimeFragment;
	private boolean typeOfDate;
	private boolean typeOfTime;
	private Context context = this;
	private final String PREFS_NAME = "MySharedPrefs";
	final Calendar c = Calendar.getInstance();


	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		

		setContentView(R.layout.createeventview);

		//Sets the animation when opening this activity
		overridePendingTransition(R.anim.map_out,R.anim.other_in);

		this.image = (ImageView) findViewById(R.id.imageView);

		//Creates the listeners for the start time, end time, start date, end date, camera button, cancel button and tag button
		setStartTimeListener();
		setEndTimeListener();
		setStartDateListener();
		setEndDateListener();
		setCameraListener();
		setCancelListener();
		setTagListener();

	}
	
	

	/**
	 * Sets the listener to the start time view
	 */
	public void setStartTimeListener() {
		setStartTime = (TextView)findViewById(R.id.setStartTime);
		setStartTime.setClickable(true);
		setStartTime.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//Shows the dialog containing the time picker
				showTimePickerDialog();
				typeOfTime = true;
			}
		});
	}

	/**
	 * Sets the listener to the end time view
	 */
	public void setEndTimeListener() {
		setEndTime = (TextView)findViewById(R.id.setEndTime);
		setEndTime.setClickable(true);
		setEndTime.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//Shows the dialog containing the time picker
				showTimePickerDialog();
				typeOfTime = false;
			}
		});
	}

	/**
	 * Sets the listener to the start date view
	 */
	public void setStartDateListener() {
		setStartDate = (TextView)findViewById(R.id.setStartDate);
		setStartDate.setClickable(true);
		setStartDate.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//Shows the dialog containing the date picker
				showDatePickerDialog();
				typeOfDate = true;
			}
		});
	}

	/**
	 * Sets the listener to the end date view
	 */
	public void setEndDateListener(){
		setEndDate = (TextView)findViewById(R.id.setEndDate);
		setEndDate.setClickable(true);
		setEndDate.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//Shows the dialog containing the date picker
				showDatePickerDialog();
				typeOfDate = false;
			}
		});
	}

	/**
	 * Sets the listener to the camera button
	 */
	public void setCameraListener() {
		Button buttonCamera = (Button) findViewById(R.id.buttonCamera);
		buttonCamera.setClickable(true);
		buttonCamera.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//Starts the camera activity
				startCameraActivity();	
			}
		});
	}

	/**
	 * Sets the listener to the tag button
	 */
	public void setTagListener() {
		Button buttonTag	  = (Button) findViewById(R.id.buttonTag);
		buttonTag.setClickable(true);
		buttonTag.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				EditText txtTitle = (EditText) findViewById(R.id.txtTitle);
				EditText txtDescription = (EditText) findViewById(R.id.txtDescription);

				String temp1 = txtTitle.getText().toString();
				String temp2 = txtDescription.getText().toString();

				//Creates a dialog saying that the user needs to fill in a title if there is none
				if(temp1.isEmpty()){
					createHelpDialog(R.string.setintitle);
				}
				//Creates a dialog saying that the title needs to contain at least two characters if it doesn't
				else if(temp1.length() == 1){
					createHelpDialog(R.string.tooshorttitle);
				}
				//Creates a dialog saying that the title needs to contain less than thirty characters if it contains thirty or more
				else if(temp1.length() > 30){
					createHelpDialog(R.string.toolongtitle);
				}
				//Creates a dialog saying that the description needs to contain less than 400 characters if it contains 400 or more
				else if(temp2.length() > 400){
					createHelpDialog(R.string.toolongdescription);
				}
				//Checks if any of the TextViews showing the start/end dates/times haven't changed from the starting text
				else if (setStartDate.getText().toString().compareTo("Set start date") == 0 || setEndDate.getText().toString().compareTo("Set end date") == 0  ||
						setStartTime.getText().toString().compareTo("Set start time") == 0 || setEndTime.getText().toString().compareTo("Set end time") == 0){
					//If no TextViews have been changed, save the data for the marker, mark it as a non-event marker and start AddEventActivity while closing this one
					if (setStartDate.getText().toString().compareTo("Set start date") == 0 && setEndDate.getText().toString().compareTo("Set end date") == 0  &&
							setStartTime.getText().toString().compareTo("Set start time") == 0 && setEndTime.getText().toString().compareTo("Set end time") == 0) {
						//Saves the markerTitle, markerDescription, Start- and end dates/times in the shared preferences to use in AddEventActivity
						SharedPreferences sharedprefs = context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedprefs.edit();
						editor.putString("markerTitle", temp1);
						editor.putString("markerDescription", temp2);
						editor.putBoolean("isEvent", false);
						editor.commit();

						//Start the next step, AddTagActivity
						startActivity(new Intent(context, com.openmap.grupp1.helpfunctions.AddEventActivity.class));
						finish();
					}
					//If one or more TextViews have been changed, show a dialog saying that the user needs to change all the TextView to continue
					else {
						createHelpDialog(R.string.dateTimeWrong);
					}
				}
				//If the start date of the event is set earlier than the current date, create a dialog telling the user that the date needs to be changed
				else if(c.get(Calendar.YEAR)*10000 + c.get(Calendar.MONTH)*100+100 + c.get(Calendar.DAY_OF_MONTH) > Integer.valueOf(setStartDate.getText().toString().replaceAll("-", ""))) {
					createHelpDialog(R.string.wrongStartDate);
				}
				//If the start date of the event equals the current date and the start time is earlier than the current time, create a dialog telling that the user the time needs to be changed
				else if(c.get(Calendar.YEAR)*10000 + c.get(Calendar.MONTH)*100+100 + c.get(Calendar.DAY_OF_MONTH) == Integer.valueOf(setStartDate.getText().toString().replaceAll("-", "")).intValue() &&
						c.get(Calendar.HOUR_OF_DAY)*100 + c.get(Calendar.MINUTE) > Integer.valueOf(setStartTime.getText().toString().replaceAll(":", ""))) {
					createHelpDialog(R.string.wrongStartTime);
				}
				//If the end date is set earlier than the start date, create a dialog telling the user that the dates need to be changed
				else if(Integer.valueOf(setStartDate.getText().toString().replaceAll("-", "")) > Integer.valueOf(setEndDate.getText().toString().replaceAll("-", ""))) {
					createHelpDialog(R.string.invalidDate);
				}
				//If the start date equals the end date and the end time is earlier than the start time, create a dialog telling the user that the times need to be changed
				else if(Integer.valueOf(setStartDate.getText().toString().replaceAll("-", "")).intValue() == Integer.valueOf(setEndDate.getText().toString().replaceAll("-", "")).intValue() &&
						Integer.valueOf(setStartTime.getText().toString().replaceAll(":", "")) > Integer.valueOf(setEndTime.getText().toString().replaceAll(":", ""))) {
					createHelpDialog(R.string.invalidTime);
				}
				//If the start date equals the end date and the start time equals the end time, create a dialog telling the user that the event needs a duration
				else if(Integer.valueOf(setStartDate.getText().toString().replaceAll("-", "")).intValue() == Integer.valueOf(setEndDate.getText().toString().replaceAll("-", "")).intValue() &&
						Integer.valueOf(setStartTime.getText().toString().replaceAll(":", "")).intValue() == Integer.valueOf(setEndTime.getText().toString().replaceAll(":", "")).intValue()) {
					createHelpDialog(R.string.noDuration);
				}
				//Save the data for the marker, mark it as a non-event marker and start AddEventActivity while closing this one
				else{ 

					//Saves the markerTitle, markerDescription, Start- and end dates/times in the shared preferences to use in AddTagActivity
					SharedPreferences sharedprefs = context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedprefs.edit();
					editor.putString("markerTitle", temp1);
					editor.putString("markerDescription", temp2);
					editor.putString("markerStartDate", setStartDate.getText().toString().replaceAll("-", ""));
					editor.putString("markerStartTime", setStartTime.getText().toString().replaceAll(":", ""));
					editor.putString("markerEndDate", setEndDate.getText().toString().replaceAll("-", ""));
					editor.putString("markerEndTime", setEndTime.getText().toString().replaceAll(":", ""));
					editor.putBoolean("isEvent", true);
					editor.commit();
					//Start the next step, AddTagActivity
					startActivity(new Intent(context, com.openmap.grupp1.helpfunctions.AddEventActivity.class));
					//Finish the activity
					finish();
				}
			}
		});
	}

	/**
	 * Sets the listener to the cancel button
	 */
	public void setCancelListener() {
		Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
		buttonCancel.setClickable(true);
		buttonCancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//Hides the keyboard to get a smoother transition when changing activity
				InputMethodManager imm = (InputMethodManager)context.getSystemService( Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),      
						InputMethodManager.HIDE_NOT_ALWAYS);
				//Finish the activity
				finish();
			}
		});
	}

	/**
	 * Create a dialog with the date picker
	 */
	private void showDatePickerDialog() {
		newDateFragment = new DatePickerFragment();
		newDateFragment.show(getFragmentManager(), "datePicker");
	}

	/**
	 * Create a dialog with the time picker
	 */
	private void showTimePickerDialog() {
		newTimeFragment = new TimePickerFragment();
		newTimeFragment.show(getFragmentManager(), "timePicker");
	}


	/**
	 * Create a  dialog containing the desired text with an okay and a cancel button
	 * @param text The text to be shown in the dialog
	 */
	private void createHelpDialog(int text) {
		PopupandDialogHandler TPD = new PopupandDialogHandler(this);
		TPD.standardDialog(text,"Ok",false);
	}

	/**
	 * Start the camera activity
	 */
	private void startCameraActivity(){
		Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File image=new File(Environment.getExternalStorageDirectory(),"test.jpg");
         intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(image));
         Uri photoUri=Uri.fromFile(image);
		startActivityForResult(intentCamera,TAKE_PICTURE_REQUEST_CODE);
	}

	//Gets the photo from the camera and adds it to the thumbnail
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == RESULT_OK){
			Log.d(TEXT_SERVICES_MANAGER_SERVICE, "Step2camera");
			Bitmap thumbnail = (Bitmap) intent.getExtras().get("data");
			Log.d(TEXT_SERVICES_MANAGER_SERVICE, "Step3camera");
			image.setImageBitmap(thumbnail);
		}
	}

	@Override
	//Sets the selected date in the correct TextView when closing the dialog
	public void onFinishDatePickerDialog(String newDate) {
		//If typeOfDate is true (being set when you press the startDate TextView), set the content to the start date TextView
		if(typeOfDate) {
			setStartDate.setText(newDate);
		}
		//If typeOfDate is false (being set when you press the endDate TextView), set the content to the end date TextView
		else if(!typeOfDate) {
			setEndDate.setText(newDate);
		}
		else {
			//
		}
	}

	@Override
	//Sets the selected time in the correct TextView when closing the dialog
	public void onFinishTimePickerDialog(String newTime) {
		//If typeOfTime is true (being set when you press the startTime TextView), set the content to the start time TextView
		if(typeOfTime) {
			setStartTime.setText(newTime);
		}
		//If typeOfTime is false (being set when you press the endTime TextView), set the content to the end time TextView
		else if(!typeOfTime) {
			setEndTime.setText(newTime);
		}
		else {
			//
		}
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//Closes the current application, returning to the map 
		case R.id.btn_logo:
			finish();
			return true;
		//Default
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}




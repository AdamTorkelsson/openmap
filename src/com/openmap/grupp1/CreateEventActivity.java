package com.openmap.grupp1;
/*
 * Create event activity where the user sets title and description and maybe adds an photo.
 * Needs to be added an event time chooser
 * Connect title and description to database IF he clicks create in next step
 * 
 */
import java.io.File;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
public class CreateEventActivity extends Activity{
	final static int TAKE_PICTURE_REQUEST_CODE = 1;
	private ImageView image; 
	private Context context = this;
	private final String PREFS_NAME = "MySharedPrefs";

	public void onCreate(Bundle savedInstanceState){
		Log.d(TEXT_SERVICES_MANAGER_SERVICE, "INCREATEEVENT");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createeventactivityview);
		overridePendingTransition(R.anim.map_out,R.anim.other_in);
		this.image = (ImageView) findViewById(R.id.imageView);

		Button buttonTag	  = (Button) findViewById(R.id.buttonTag);
		Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
		Button buttonCamera = (Button) findViewById(R.id.buttonCamera);

		final EditText txtTitle = (EditText) findViewById(R.id.txtTitle);
		final EditText txtDescription = (EditText) findViewById(R.id.txtDescription);

		buttonTag.setClickable(true);
		buttonCancel.setClickable(true);
		buttonCamera.setClickable(true);

		buttonTag.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String temp1 = txtTitle.getText().toString();
				String temp2 = txtDescription.getText().toString();

				if(temp1.length() < 1){
					createhelppopup(R.string.setintitle);
				}
				else if(temp1.length() == 1){
					createhelppopup(R.string.toshorttitle);
				}
				else if(temp1.length() > 30){
					createhelppopup(R.string.tolongtitle);
				}
				else if(temp2.length() > 400){
					createhelppopup(R.string.tolongdescription);
				}
				else{ 
					SharedPreferences sharedprefs = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedprefs.edit();
					editor.putString("markerTitle", temp1);
					editor.putString("markerDescription", temp2);
					editor.commit();
					startActivity(new Intent(context, AddTagActivity.class));
					finish();}

			}

		});

		buttonCancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				InputMethodManager imm = (InputMethodManager)context.getSystemService( Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),      
					    InputMethodManager.HIDE_NOT_ALWAYS);
				finish();
			}});

		buttonCamera.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				startCameraActivity();	
			}});	
	}

	private void createhelppopup(int text) {
		TutorialPopupDialog TPD = new TutorialPopupDialog(this);
		TPD.standardDialog(text,"Ok",false);

	}





	private void startCameraActivity(){
		Log.d(TEXT_SERVICES_MANAGER_SERVICE, "Step1camera");
		Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		/*File image=new File(Environment.getExternalStorageDirectory(),"test.jpg");
         intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(image));
         Uri photoUri=Uri.fromFile(image);*/
		startActivityForResult(intentCamera,TAKE_PICTURE_REQUEST_CODE);}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		if (requestCode == TAKE_PICTURE_REQUEST_CODE){
			if (resultCode == RESULT_OK){
				Log.d(TEXT_SERVICES_MANAGER_SERVICE, "Step2camera");
				Bitmap thumbnail = (Bitmap) intent.getExtras().get("data");
				Log.d(TEXT_SERVICES_MANAGER_SERVICE, "Step3camera");
				image.setImageBitmap(thumbnail);

			}
		}
	}



}




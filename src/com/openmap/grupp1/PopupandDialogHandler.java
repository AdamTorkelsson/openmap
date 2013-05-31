package com.openmap.grupp1;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.openmap.grupp1.helpfunctions.CreateEventActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
/**
 * Creates the different popups and dialogs shown in the application
 */
public class PopupandDialogHandler {
	private Context context;
	int i = 1;

	/**
	 * Creates the different popups and dialogs shown in the application
	 * @param context The context where the dialog is to be shown
	 */
	public PopupandDialogHandler(Context context){
		this.context = context;

	}

	/**
	 * Contains the tutorial and moves this forward
	 * until the user have finished it or pressed cancel
	 */
	public void tutorialDialog() {
		if(i==1){
			standardDialog(R.string.tutorialdialogview1,"Yes",true);
			i++;}
		else if(i==2){
			standardDialog(R.string.tutorialdialogview2,"Next",true);
			i++;}
		else if(i==3){
			standardDialog(R.string.tutorialdialogview3,"Next",true);
			i++;}
		else if(i==4){
			standardDialog(R.string.tutorialdialogview4,"Next",true);
			i++;}
		else if(i==5){
			standardDialog(R.string.tutorialdialogview5,"Next",true);
			i++;}
		else if(i==6){
			standardDialog(R.string.tutorialdialogview6,"Finish",false);
			i++;}


	}

	/**
	 *  A standard dialog, usually to be called when the user
	 * need to do something or be informed of something.
	 * @param stringValue The string to be shown in the dialog, resource value
	 * @param positiveButton The string to be shown on the positive button
	 * @param createloop Should be true if handling the tutorialDialog, else false
	 */
	public void standardDialog(int stringValue, String positiveButton,final Boolean createloop){
		//Gets the layout to be set in the dialog
		LayoutInflater li = LayoutInflater.from(context);
		View tutorialView= li.inflate(R.layout.tutorialdialogview, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		//Sets the text inside of the dialog
		TextView textview = (TextView) tutorialView.findViewById(R.id.textView1);
		textview.setText(stringValue);

		//Sets the view of the dialog
		alertDialogBuilder.setView(tutorialView);
		//Sets the name of the buttons of the dialog
		alertDialogBuilder
		.setCancelable(true)
		.setPositiveButton(positiveButton,
				new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog,int id) {
				//If createloop is set true, which it only should be when called from tutorialDialog, starts the next tutorialDialog
				if(createloop)	
					tutorialDialog();
				else
					dialog.cancel();
			}

		})
		//Sets the negative button to cancel
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();

			}
		});

		//Creates the alertDialog and sets it cancelable
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.setCancelable(true);


		//Displays the alertDialog
		alertDialog.show();
		//Sets the background of the buttons
		Button bn = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
		bn.setBackgroundColor(Color.parseColor("#F39C12"));
		Button bp = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
		bp.setBackgroundColor(Color.parseColor("#F39C12"));

	}

	/** 
	 * Lets the user confirm that the chosen location is the correct one
	 * @param m The marker at the chosen location
	 * @param myMap The GoogleMap object where the marker is placed
	 */
	public void confirmLocationPopup(final Marker m, final GoogleMap myMap){

		// Inflate the popup_layout.xml
		LinearLayout viewGroup = (LinearLayout) ((Activity) context).findViewById(R.layout.activity_main);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View layout = layoutInflater.inflate(R.layout.confirmview, viewGroup);
		// Creates the PopupWindow in the context specified in the constructor
		final PopupWindow popup = new PopupWindow(context);
		//Sets the content, width and height
		popup.setContentView(layout);
		   popup.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


		//Defines the buttons and sets them clickable
		Button buttonYES = (Button) layout.findViewById(R.id.buttonYes);
		Button buttonNO = (Button) layout.findViewById(R.id.buttonNo);
		buttonYES.setClickable(true);
		buttonNO.setClickable(true);

		//Sets the listener to the yes button, starts the createventactivity class if pressed
		buttonYES.setOnClickListener(
				new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						popup.dismiss();
						context.startActivity(new Intent(context, CreateEventActivity.class));		
						m.remove();
					}

				});

		//Sets the listener to the no button, removes the marker if pressed
		buttonNO.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				popup.dismiss();
				m.remove();

			}});
		//Specifies where the popup is to be shown
		popup.showAtLocation(layout, Gravity.BOTTOM, 0,  0);

	}




}

package com.openmap.grupp1;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.openmap.grupp1.helpfunctions.CreateEventActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
/*
 * Contains popup and dialogs thats been moved here for convenience
 * 
 * 
 */
public class PopupandDialogHandler {
	private Context context;
	int i = 1;
	
	//sets the context which it shall appear on
	public PopupandDialogHandler(Context context){
		this.context = context;
		
	}
	/*DialogHandler , contains the tutorial and moves this forward
	 * until the user have finished it or pressed cancel
	 */
	public void dialogHandler() {
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
	/*
	 * A standard dialog for all classes to use. Usually used when the user
	 * need to do something or be informed. 
	 */
public void standardDialog(int dialog, String rightButton,final Boolean createloop){
	LayoutInflater li = LayoutInflater.from(context);
	View tutorialView= li.inflate(R.layout.tutorialdialogview, null);
	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	// set prompts.xml to alertdialog builder
	//the two strings
	
	TextView textview = (TextView) tutorialView.findViewById(R.id.textView1);
	textview.setText(dialog);
		
	alertDialogBuilder.setView(tutorialView);
	// set dialog message
	
		alertDialogBuilder
	.setCancelable(true)
	.setPositiveButton(rightButton,
	new DialogInterface.OnClickListener() {		
			  public void onClick(DialogInterface dialog,int id) {
				  if(createloop)	
					  dialogHandler();
				  else
					  dialog.cancel();
			  }

			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
				
			    }
			  });
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.setCancelable(true);
		alertDialog.show();
}

/*
 * Lets the user confirm that the chosen location is the correct one
 * 
 */

public void confirmLocationPopup(final Marker m, final GoogleMap myMap){
	//POPUP som fungerar
	   int popupWidth = 600;
	   int popupHeight = 100;
	  
	   // Inflate the popup_layout.xml
	LinearLayout viewGroup = (LinearLayout) ((Activity) context).findViewById(R.layout.activity_main);
	   LayoutInflater layoutInflater = (LayoutInflater) context
	     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  
	   View layout = layoutInflater.inflate(R.layout.confirmview, viewGroup);
	   // Creating the PopupWindow
	   final PopupWindow popup = new PopupWindow(context);
	   popup.setContentView(layout);
	   popup.setWidth(popupWidth);
	   popup.setHeight(popupHeight);
	   
	   Button buttonYES = (Button) layout.findViewById(R.id.buttonYes);
	   Button buttonNO = (Button) layout.findViewById(R.id.buttonNo);
		  
		  buttonYES.setClickable(true);
		  buttonNO.setClickable(true);
			 
			buttonYES.setOnClickListener(
					new OnClickListener(){

						@Override
						public void onClick(View arg0) {
							popup.dismiss();
							context.startActivity(new Intent(context, CreateEventActivity.class));		
							 m.remove();
						}
						
					});
			
			buttonNO.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					popup.dismiss();
					 m.remove();
					
				}});
	   popup.showAtLocation(layout, Gravity.BOTTOM, 0,  0);
	   
}




}

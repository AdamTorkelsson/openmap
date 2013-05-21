package com.openmap.grupp1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

public class TutorialPopupDialog {
	private Context context;
	int i = 0;
	
	public TutorialPopupDialog(Context context){
		this.context = context;
		
		dialogHandler();
		
	}
	private void dialogHandler() {
		if(i==0){
			standardDialog(R.layout.tutorialdialogview1);
		i++;}
		else if(i==1){
			standardDialog(R.layout.tutorialdialogview2);
			i++;}
		else if(i==2){
			standardDialog(R.layout.tutorialdialogview3);
			i++;}	
	}
	
public void standardDialog(int view){
	LayoutInflater li = LayoutInflater.from(context);
	View tutorialView= li.inflate(view, null);
	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	// set prompts.xml to alertdialog builder
	//the two strings
	
	alertDialogBuilder.setView(tutorialView);
	// set dialog message

		alertDialogBuilder
	.setCancelable(false)
	.setPositiveButton("Next",
	new DialogInterface.OnClickListener() {		
			  public void onClick(DialogInterface dialog,int id) {
				  	dialogHandler();
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
}}

package com.openmap.grupp1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class TutorialPopupDialog {
	private Context context;
	int i = 1;
	
	public TutorialPopupDialog(Context context){
		this.context = context;
		
		dialogHandler();
		
	}
	
	private void dialogHandler() {
		if(i==1){
			standardDialog(R.string.tutorialdialogview1,"Yes");
			i++;}
		else if(i==2){
			standardDialog(R.string.tutorialdialogview2,"Next");
			i++;}
			else if(i==3){
			standardDialog(R.string.tutorialdialogview3,"Next");
			i++;}
		else if(i==4){
			standardDialog(R.string.tutorialdialogview4,"Next");
			i++;}
		else if(i==5){
			standardDialog(R.string.tutorialdialogview5,"Next");
			i++;}
		else if(i==6){
			standardDialog(R.string.tutorialdialogview6,"Next");
			i++;}
		else if(i==7){
			standardDialog(R.string.tutorialdialogview7,"Finish");
			i++;}

	}
	
public void standardDialog(int dialog, String rightButton){
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

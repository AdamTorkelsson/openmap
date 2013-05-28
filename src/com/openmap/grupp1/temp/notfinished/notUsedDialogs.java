package com.openmap.grupp1.temp.notfinished;

public class notUsedDialogs {

}
/*
public void showInfo(Context context,final LatLng point,
		final Resources res, 
		final GoogleMap myMap,
		String title,
		String description ){

   //POPUP som fungerar
  
	int popupWidth = 300;
	int popupHeight = 300;

   // Inflate the popup_layout.xml
LinearLayout viewGroup = (LinearLayout) ((Activity) context).findViewById(R.layout.activity_main);
   LayoutInflater layoutInflater = (LayoutInflater) context
     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

View layout = layoutInflater.inflate(R.layout.showinfopopup, viewGroup);


TextView titleView = (TextView) layout.findViewById(R.id.titleView1);
TextView descriptionView = (TextView) layout.findViewById(R.id.descriptionView1);

titleView.setText(title);
descriptionView.setText(description);

   // Creating the PopupWindow
   final PopupWindow popup = new PopupWindow(context);
   popup.setContentView(layout);
   popup.setWidth(popupWidth);
   popup.setHeight(popupHeight);
   popup.setFocusable(true);
   popup.setTouchable(true);
  
   // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.

   Projection projection =	myMap.getProjection();
   // Clear the default translucent background
 //  popup.setBackgroundDrawable(new BitmapDrawable());
 
   //Displaying the popup at the specified location, + offsets.
   popup.showAtLocation(layout, Gravity.NO_GRAVITY, 
		   projection.toScreenLocation(point).x -popupWidth/2, 
		   projection.toScreenLocation(point).y - popupHeight/2);
}

public void checkInDialog(Context context, final GoogleMap myMap) {
		
		this.context = context;
		//POPUP som fungerar
		   int popupWidth = 600;
		   int popupHeight = 200;
		  
		   // Inflate the popup_layout.xml
		LinearLayout viewGroup = (LinearLayout) ((Activity) context).findViewById(R.layout.activity_main);
		   LayoutInflater layoutInflater = (LayoutInflater) context
		     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  
		   View layout = layoutInflater.inflate(R.layout.confirmcheckinview, viewGroup);
		
		   // Creating the PopupWindow
		   final PopupWindow popup = new PopupWindow(context);
	
		   popup.setContentView(layout);
		  // popup.setWindowLayoutMode(layout.getWidth(), layout.getHeight());
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
								//*Connect to database and add the person to the event
							}
							
						});
				
				buttonNO.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						popup.dismiss();
						
					}});
		   popup.showAtLocation(layout, Gravity.BOTTOM, 0,  0);
		   
	}*/
/*package com.openmap.grupp1;

ANVÄNDS INTE ALLS, BYTTE APPROACH, BEHÅLLER SÅ LÄNGE

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CreateSearchPopup {
	
	private PopupWindow searchPopup = new PopupWindow();	

    private ListView mListView;
    private SearchView searchView;
    private TagsDbAdapter mDbHelper;
    private TextView tagText;
    private final Context mCtx;
    private final Activity mActivity;
    private View layout;

    public CreateSearchPopup(Context context, Activity activity) {
    	this.mCtx = context;
    	this.mActivity = activity;
    	
        mDbHelper = new TagsDbAdapter(mCtx);
        mDbHelper.open();
 
        //Clean all tags
        mDbHelper.deleteAllTags();
        //Add some tags as a sample
        mDbHelper.createTag("Fotbollsmatch");
        mDbHelper.createTag("KLUBBA");
        mDbHelper.createTag("Konsert");
        mDbHelper.createTag("Bög");
        mDbHelper.createTag("Plats");
        mDbHelper.createTag("Fest");
        mDbHelper.createTag("Öl");
        mDbHelper.createTag("Fotboll");
        mDbHelper.createTag("HockeyVM");
    }
    
	public boolean isShowingPopup() {
		return searchPopup.isShowing();
	}
	
	public void dismissPopup() {
		searchPopup.dismiss();
	}
	
    public void showResults(String query) {
    	 
        Cursor cursor = mDbHelper.searchTag((query != null ? query.toString() : "@@@@"));
        if (cursor == null) {
            //
        } else {
            // Specify the columns we want to display in the result
            String[] from = new String[] {
                    TagsDbAdapter.KEY_TAG};   
 
            // Specify the Corresponding layout elements where we want the columns to go
            int[] to = new int[] {     R.id.stag};
 
            // Create a simple cursor adapter for the definitions and apply them to the ListView
            SimpleCursorAdapter tags = new SimpleCursorAdapter(mCtx,R.layout.searchviewsearchresult, cursor, from, to, 0);
            mListView.setAdapter(tags);
            
            
            // Define the on-click listener for the list items

        }
    }
	
	public void createPopup(){
		//POPUP som fungerar
		   int popupWidth = 500;
		   int popupHeight = 600;

		   // Inflate the popup_layout.xml
		   LinearLayout viewGroup = (LinearLayout) ((Activity) mCtx).findViewById(R.layout.activity_main);
		   LayoutInflater layoutInflater = (LayoutInflater) mCtx
		     .getSystemService(mCtx.LAYOUT_INFLATER_SERVICE);

		   layout = layoutInflater.inflate(R.layout.searchtagview, viewGroup);
		   mListView = (ListView) layout.findViewById(R.id.list);
		   // Creating the PopupWindow
		   searchPopup = new PopupWindow(mCtx);
		   searchPopup.setContentView(layout);
		   searchPopup.setWidth(popupWidth);
		   searchPopup.setHeight(popupHeight);
		   searchPopup.setTouchable(true);
		   searchPopup.setFocusable(true);
		   

           mListView.setOnItemClickListener(new OnItemClickListener() {
           	@Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   // Get the cursor, positioned to the corresponding row in the result set
                   Cursor cursor = (Cursor) mListView.getItemAtPosition(position);


                   // Get the tag from this row in the database.
                   String tag = cursor.getString(cursor.getColumnIndexOrThrow("tag"));
                   
                   
                   //Check if the Layout already exists
                   
                   LinearLayout tagLayout = (LinearLayout)mActivity.findViewById(R.id.tagLayout);
                   if(tagLayout == null){
                       //Inflate the Tag Information View 
                       LinearLayout rightLayout = (LinearLayout)layout.findViewById(R.id.rightLayout);
                       Log.d("testar", "showresults5");
                       View tagInfo = mActivity.getLayoutInflater().inflate(R.layout.searchviewaddedtags, rightLayout, false);
                       Log.d("testar", "showresults5.6");
                       Log.d("testar", "showresults5.6" + tagInfo.toString());

                       
                       rightLayout.addView(tagInfo);
                       Log.d("testar", "showresults5.7");

                   }


                   //Get References to the TextViews
                   tagText = (TextView) layout.findViewById(R.id.tag);
                   Log.d("testar", "showresults5.8");

               
                   // Update the parent class's TextView
                   tagText.setText(tag);
                   Log.d("testar", "showresults6");

           

               }
           });

		   	        /*
		   Button buttonYES = (Button) layout.findViewById(R.id.buttonYes);
		   Button buttonNO = (Button) layout.findViewById(R.id.buttonNo);
			  
			  buttonYES.setClickable(true);
			  buttonNO.setClickable(true);
				 
				buttonYES.setOnClickListener(
						new OnClickListener(){

							@Override
							public void onClick(View arg0) {
								popup.dismiss();
								startNewActivity();
							}
							
						});
				
				buttonNO.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						popup.dismiss();
						
					}});*/
				
				/*
		   searchPopup.showAtLocation(layout, Gravity.CENTER, 0,  -170);


		   
		  
		   
	}
	
	
	

	
	
}*/

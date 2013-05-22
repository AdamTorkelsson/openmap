package com.openmap.grupp1;

import java.io.File;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;
import android.database.Cursor;

public class AddTagActivity extends Activity implements SearchView.OnQueryTextListener,
SearchView.OnCloseListener{
	
    private ListView listViewSearched;
    private ListView listViewAdded;
    private SearchView searchView;
    private TagsDbAdapter mDbHelper;
    private ArrayList<String> addedTags = new ArrayList<String>();
    private ArrayAdapter<String> addedTagsAdapter;
    private ArrayList<String> newTags = new ArrayList<String>();
    private Context mCtx = this;

	
	 public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
         setContentView(R.layout.addtagview);
	 
         listViewSearched = (ListView) findViewById(R.id.addtag_list_searched);
         listViewAdded = (ListView) findViewById(R.id.addtag_list_added);

         
         mDbHelper = new TagsDbAdapter(this);
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
         mDbHelper.createTag("Hockey-VM");

     	addedTagsAdapter =      
       	         new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, addedTags);
       	         listViewAdded.setAdapter(addedTagsAdapter);
         // Define the on-click listener for listViewSearched
         listViewSearched.setOnItemClickListener(new OnItemClickListener() {
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 // Get the cursor, positioned to the corresponding row in the result set
                 Cursor cursor = (Cursor) listViewSearched.getItemAtPosition(position);
                 
                 // Get the tag from this row in the database.
                 String tag = cursor.getString(cursor.getColumnIndexOrThrow("tag"));

                 //Check if the Layout already exists
                 LinearLayout tagLayout = (LinearLayout)findViewById(R.id.tagLayout);
                 if(tagLayout == null){
                     //Inflate the Tag Information View 
                     LinearLayout rightLayout = (LinearLayout)findViewById(R.id.rightLayout);
                     View tagInfo = getLayoutInflater().inflate(R.layout.searchviewaddedtags, rightLayout, false);
                     rightLayout.addView(tagInfo);
                 }


                 //Adds the tag to addedListview to the right if it doesn't exist
                 if(!addedTags.contains(tag)) {
                 	addedTags.add(tag);
                 	addedTagsAdapter.notifyDataSetChanged();
                 	
                 }
             }
         });
         
         
         // Define the on-click listener for listViewAdded
         listViewAdded.setOnItemClickListener(new OnItemClickListener() {
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             	String removeItem = (String) listViewAdded.getItemAtPosition(position);
             	addedTags.remove(removeItem);
             	addedTagsAdapter.notifyDataSetChanged();
             }
         });
         
         
         
  
	 
		 
		  Button buttonTag	  = (Button) findViewById(R.id.buttonTag);
		  Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
	
		
		  buttonTag.setClickable(true);
		  buttonCancel.setClickable(true);

		  
		  buttonTag.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					//skicka till databasen
					//lägg till newTags till databasen om det behövs
					final String PREFS_NAME = "MySettings";
					SharedPreferences latlng = mCtx.getSharedPreferences(PREFS_NAME,mCtx.MODE_PRIVATE);
					SharedPreferences.Editor editor = latlng.edit();
					editor.putBoolean("createMarker", true);
					editor.commit();
					stopThisActivity();
					}

				});
			
		  buttonCancel.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					stopThisActivity();
					}});
		  

		  }


	 private void stopThisActivity(){
		 	this.finish();
	 }
	 

			
		



@Override
public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.addtagmenu, menu);
    ActionBar ab = getActionBar();
    ab.setDisplayShowTitleEnabled(false);
    ab.setDisplayShowHomeEnabled(false);
    
    
    searchView = (SearchView) menu.findItem(R.id.addtagmenu_search).getActionView();
    searchView.setIconifiedByDefault(false);
    searchView.setOnQueryTextListener(this);
    searchView.setOnCloseListener(this);
    searchView.setQueryHint("Search tags");
    searchView.requestFocus();


    return true;

}
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
case R.id.addtagmenu_add:
	String newTag = searchView.getQuery().toString();
	//add an if statement here that only adds newTag to newTags if it isnt shown as a result
	newTags.add(newTag);
    if(!addedTags.contains(newTag)) {
     	addedTags.add(newTag);
     	addedTagsAdapter.notifyDataSetChanged();
    }
	return true;
default:
    return super.onOptionsItemSelected(item);
    }
    }

@Override
protected void onDestroy() {
    super.onDestroy();
    if (mDbHelper  != null) {
        mDbHelper.close();
    }
}

public boolean onQueryTextChange(String newText) {
	showResults(newText + "*");
    return false;
}

public boolean onQueryTextSubmit(String query) {
    showResults(query + "*");
    return false;
}

public boolean onClose() {
    showResults("");
    return false;
}





private void showResults(String query) {

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
        SimpleCursorAdapter searchResult = new SimpleCursorAdapter(this,R.layout.searchviewsearchresult, cursor, from, to, 0);
        listViewSearched.setAdapter(searchResult);

    }
}

}

	

      
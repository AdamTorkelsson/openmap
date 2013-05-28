package com.openmap.grupp1.helpfunctions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.openmap.grupp1.R;
import com.openmap.grupp1.TutorialPopupDialog;
import com.openmap.grupp1.R.anim;
import com.openmap.grupp1.R.id;
import com.openmap.grupp1.R.layout;
import com.openmap.grupp1.R.menu;
import com.openmap.grupp1.R.string;
import com.openmap.grupp1.database.AddLocationTask;
import com.openmap.grupp1.database.LocationMarker;
import com.openmap.grupp1.database.EventMarker;
import com.openmap.grupp1.database.RequestTagDbTask;

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
import android.view.inputmethod.InputMethodManager;
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

public class AddEventActivity extends Activity implements SearchView.OnQueryTextListener,
SearchView.OnCloseListener{

	private ListView listViewSearched;
	private ListView listViewAdded;
	private SearchView searchView;
	private RequestTagDbTask mDbHelper;
	private ArrayList<String> addedTags = new ArrayList<String>();
	private ArrayAdapter<String> addedTagsAdapter;
	private ArrayList<String> newTags = new ArrayList<String>();
	private ArrayList<String> searchedTags = new ArrayList<String>();
	private ArrayAdapter<String> searchedTagsAdapter;
	private Context context = this;
	private final String PREFS_NAME = "MySharedPrefs";
	private SharedPreferences settings;
	private LocationMarker newMarker;


	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		//Sets the view for the activity
		setContentView(R.layout.addeventview);

		//Sets the animation for changing to this activity
		overridePendingTransition(R.anim.map_out,R.anim.other_in);

		//Sets the listeners for the listviews
		setSearchedListListener();
		setAddedListListener();

		//Sets the listeners for the buttons
		setCancelListener();
		setTagListener();
	}
	
	//Sets the listener to the searched tags listview to the left
	public void setSearchedListListener() {
		listViewSearched = (ListView) findViewById(R.id.addtag_list_searched);
		// Define the on-click listener for listViewSearched
		listViewSearched.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				//Get the tag at the clicked position
				String tag = (String) listViewSearched.getItemAtPosition(position);

				//Adds the tag to listViewAdded to the right if it doesn't exist in it and removes it from listViewSearched
				if(!addedTags.contains(tag)) {
					addedTags.add(tag);
					addedTagsAdapter.notifyDataSetChanged();
					searchedTags.remove(tag);
					searchedTagsAdapter.notifyDataSetChanged();
				}
			}
		});
	}
	
	//Sets the listener to the added tags listview to the right
	public void setAddedListListener() {
		listViewAdded = (ListView) findViewById(R.id.addtag_list_added);
		//Sets the adapter between the arraylist and the listview to be able to load content from the list
		addedTagsAdapter =      
				new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, addedTags);
		listViewAdded.setAdapter(addedTagsAdapter);
		
		// Define the on-click listener for listViewAdded
		listViewAdded.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				//Get the tag at the clicked position
				String removeItem = (String) listViewAdded.getItemAtPosition(position);

				//Adds the tag to listViewSearched to the left if it doesn't exist in it and removes it from listViewAdded
				if(searchedTags != null && !searchedTags.contains(removeItem)) {

					//If the tag isnt new it will put it back in to the searchedtags listview
					if(!newTags.contains(removeItem)) {
						searchedTags.add(removeItem);
						searchedTagsAdapter.notifyDataSetChanged();
					}

					//If the tag is new and removed it will be removed from the newTags list
					else {
						newTags.remove(removeItem);
					}
				}

				else{
					//
				}
				//Removes the tag from the listview to the right
				addedTags.remove(removeItem);
				addedTagsAdapter.notifyDataSetChanged();

			}
		}
				);
		
		
	}
	
	//Sets the listener for the cancel button
	public void setCancelListener() {
		Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
		buttonCancel.setClickable(true);
		//Sets the clicklistener for the cancel button which exits this activity and goes to the map
		buttonCancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {

				//Hides the keyboard to get a smoother transition from this activity to the map
				InputMethodManager imm = (InputMethodManager)context.getSystemService( Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),      
						InputMethodManager.HIDE_NOT_ALWAYS);

				//Closes this activity
				finish();
			}
		});
	}

	//Sets the listener for the tag button
	public void setTagListener() {
		Button buttonTag	  = (Button) findViewById(R.id.buttonTag);
		buttonTag.setClickable(true);
		//Define the onClickListener for the tag button
		buttonTag.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//Creates a dialog if there are no added tags and tells you no tags have been added
				if (addedTags.isEmpty()) {
					TutorialPopupDialog TPD = new TutorialPopupDialog(context);
					TPD.standardDialog(R.string.noAddedTags,"Ok",false);
				}

				//Sends the new marker and its information to the database
				else {
					settings = context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
					String[] addedTagsArray = addedTags.toArray(new String[addedTags.size()]);

					//Sends a marker of type LocationMarker if it is a location
					if (!settings.getBoolean("isEvent", false)) {
						newMarker = new LocationMarker(settings.getString("markerTitle","System Failure"),
								Double.valueOf(settings.getString("markerLat","System failure lat")),
								Double.valueOf(settings.getString("markerLng","System Failure lng")),
								settings.getString("markerDescription","System failure desc"),
								addedTagsArray);
					}
					//Creates a marker of type EventMarker if it is an event
					else if (settings.getBoolean("isEvent", false)) {
						newMarker = new EventMarker(settings.getString("markerTitle","System Failure"),
								Double.valueOf(settings.getString("markerLat","System failure lat")),
								Double.valueOf(settings.getString("markerLng","System Failure lng")),
								settings.getString("markerDescription","System failure desc"),
								addedTagsArray,
								settings.getString("markerStartDate","System Failure startDate"),
								settings.getString("markerEndDate","System Failure endDate"),
								settings.getString("markerStartTime","System Failure startTime"),
								settings.getString("markerEndTime","System Failure endTime"));
					}

					else {
						//
					}


					//Sends the new marker to the database
					try {
						AddLocationTask addLocationTask = new AddLocationTask();
						addLocationTask.execute(newMarker.getPairsList());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//Hides the keyboard to get a smoother transition from this activity to the map
					InputMethodManager imm = (InputMethodManager)context.getSystemService( Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),      
							InputMethodManager.HIDE_NOT_ALWAYS);

					//Closes this activity
					finish();
				}
			}

		});

	}

	//Creates the option menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.addtagmenu, menu);
		ActionBar ab = getActionBar();
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);

		//Defines the searchview and its properties
		searchView = (SearchView) menu.findItem(R.id.addtagmenu_search).getActionView();
		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this);
		searchView.setQueryHint("Search tags");
		searchView.requestFocus();
		return true;

	}

	//Adds the functionality for when clicking the buttons in the options menu
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		//Adds the new tag to the added tags listview to the right in the view
		case R.id.addtagmenu_add:
			String newTag = searchView.getQuery().toString();
			if(newTag != null && !addedTags.contains(newTag) && !newTag.isEmpty() && !newTag.startsWith(" ")) {
				newTags.add(newTag);
				addedTags.add(newTag);
				addedTagsAdapter.notifyDataSetChanged();
			}
			//Show a dialog if the newTag is null, empty, starts with a blank character or is already in the added tags listview
			else {
				TutorialPopupDialog TPD = new TutorialPopupDialog(this);
				TPD.standardDialog(R.string.wrongAddedTag,"Ok",false);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//
	}

	//Calls the showResults method which shows the results when text is inserted into the searchview in the menu
	public boolean onQueryTextChange(String newText) {
		showResults(newText);
		return false;
	}

	//Calls the showResults method which shows the results when the searchbutton is pressed
	public boolean onQueryTextSubmit(String query) {
		showResults(query);
		return false;
	}

	//Empties the searched tags listview to the left if you close the searchview 
	public boolean onClose() {
		showResults("");
		return false;
	}




	//Shows the results in the searched tags listview to the left
	private void showResults(String query) {

		try {
			//Creates the object which talks to the database and sends the query to the database
			mDbHelper = new RequestTagDbTask();
			searchedTags = mDbHelper.getTagArray(query);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*If the searchedTags array is non-null, non-empty and doesnt start with a null object 
		it sets the searched tags listview content to the gotten array */
		if (searchedTags != null && !searchedTags.isEmpty() && searchedTags.get(0) != null) {
			searchedTagsAdapter =      
					new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, searchedTags);
			listViewSearched.setAdapter(searchedTagsAdapter);
		} 

		//If it doesnt go into the if statement it will set the searched tags listview to an empty list
		else {
			searchedTags = new ArrayList<String>();
			searchedTagsAdapter =      
					new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, searchedTags);
			listViewSearched.setAdapter(searchedTagsAdapter);		
		}
	}

}




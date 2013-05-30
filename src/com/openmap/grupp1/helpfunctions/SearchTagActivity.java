package com.openmap.grupp1.helpfunctions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.openmap.grupp1.R;
import com.openmap.grupp1.database.RequestTagDbTask;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Activity called when the user wants to filter the markers displayed based on his preferred tags
 */
public class SearchTagActivity extends Activity implements SearchView.OnQueryTextListener,
SearchView.OnCloseListener {

	private ListView listViewSearched;
	private ListView listViewAdded;
	private SearchView searchView;
	private RequestTagDbTask mDbHelper;
	private ArrayList<String> addedTags = new ArrayList<String>();
	private ArrayAdapter<String> addedTagsAdapter;
	private ArrayList<String> searchedTags = new ArrayList<String>();
	private ArrayAdapter<String> searchedTagsAdapter;
	private Context context = this;
	private final String PREFS_NAME = "MySharedPrefs";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Sets the the view for the activity
		setContentView(R.layout.searchtagview);

		//Sets the animation when opening this activity
		overridePendingTransition(R.anim.map_out,R.anim.other_in);

		//Sets the listener for the listviews
		setSearchedListListener();
		setAddedListListener();

	}
	
	/**
	 * Sets the searched tag listview listener
	 */
	public void setSearchedListListener() {
		//Declares the listviews for the view 
		listViewSearched = (ListView) findViewById(R.id.list_searched);
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
	
	/**
	 * Sets the listener for the added tags listview to the right
	 */
	public void setAddedListListener() {
		listViewAdded = (ListView) findViewById(R.id.list_added);
		
		//Sets the adapter between the arraylist and the listview to be able to load content from the list
		addedTagsAdapter =      
				new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, addedTags);
		listViewAdded.setAdapter(addedTagsAdapter);

		// Define the on-click listener for listViewAdded
		listViewAdded.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				//Get the tag at the clicked position
				String removeItem = (String) listViewAdded.getItemAtPosition(position);
				addedTags.remove(removeItem);
				addedTagsAdapter.notifyDataSetChanged();

				//Adds the tag to listViewSearched to the left if it doesn't exist in it and removes it from listViewAdded
				if(!searchedTags.contains(removeItem)) {
					searchedTags.add(removeItem);
					searchedTagsAdapter.notifyDataSetChanged();
				}
			}
		});

	}

	//Creates the options menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.searchtagmenu, menu);
		ActionBar ab = getActionBar();
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F39C12")));

		//Defines the searchview and its properties
		searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
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
		
		//Stores the filter and closes the activity
		case R.id.btn_filter_search:
			//Stores the filter so that it can be used when loading markers
			SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			Set<String> set = new HashSet<String>(addedTags);
			editor.putStringSet("tagSet", set);
			editor.commit();

			//Hides the keyboard to get a smoother transition from this activity to the map
			InputMethodManager imm = (InputMethodManager)context.getSystemService( Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),      
					InputMethodManager.HIDE_NOT_ALWAYS);
			
			//Closes this activity
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}}

	@Override
	protected void onDestroy() {
		super.onDestroy();
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




	/**
	 * Shows the results in the searched tags listview to the left
	 * @param query
	 */
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

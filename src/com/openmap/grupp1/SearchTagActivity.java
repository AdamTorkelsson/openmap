package com.openmap.grupp1;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchtagview);
		overridePendingTransition(R.anim.map_out,R.anim.other_in);

		listViewSearched = (ListView) findViewById(R.id.list_searched);
		listViewAdded = (ListView) findViewById(R.id.list_added);

		addedTagsAdapter =      
				new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, addedTags);
		listViewAdded.setAdapter(addedTagsAdapter);
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

		// Define the on-click listener for listViewAdded
		listViewAdded.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				//Get the tag at the clicked position
				String removeItem = (String) listViewAdded.getItemAtPosition(position);
				
				//Adds the tag to listViewSearched to the left if it doesn't exist in it and removes it from listViewAdded
				if(!searchedTags.contains(removeItem)) {
					addedTags.remove(removeItem);
					addedTagsAdapter.notifyDataSetChanged();
					searchedTags.add(removeItem);
					searchedTagsAdapter.notifyDataSetChanged();
				}
			}
		});



	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.searchtagmenu, menu);
		ActionBar ab = getActionBar();
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);

		searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this);
		searchView.setQueryHint("Search tags");
		searchView.requestFocus();

		return true;

	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.btn_filter_search:
			//add filter function here, call loadMarkers in some way
			//send the arraylist addedTags here 
			InputMethodManager imm = (InputMethodManager)context.getSystemService( Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),      
				    InputMethodManager.HIDE_NOT_ALWAYS);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public boolean onQueryTextChange(String newText) {
		showResults(newText);
		return false;
	}

	public boolean onQueryTextSubmit(String query) {
		showResults(query);
		return false;
	}

	public boolean onClose() {
		showResults("");
		return false;
	}





	private void showResults(String query) {

		try {
			mDbHelper = new RequestTagDbTask();
			searchedTags = mDbHelper.getTagArray(query);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (searchedTags != null && !searchedTags.isEmpty() && searchedTags.get(0) != null) {
			searchedTagsAdapter =      
					new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, searchedTags);
			listViewSearched.setAdapter(searchedTagsAdapter);
		} 
		else {
			//
		}
	}


}

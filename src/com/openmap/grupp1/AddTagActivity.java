package com.openmap.grupp1;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

public class AddTagActivity extends Activity implements SearchView.OnQueryTextListener,
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
	private LocationPair newMarker;


	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtagview);
		overridePendingTransition(R.anim.map_out,R.anim.other_in);

		listViewSearched = (ListView) findViewById(R.id.addtag_list_searched);
		listViewAdded = (ListView) findViewById(R.id.addtag_list_added);



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
				if(searchedTags != null && !searchedTags.contains(removeItem)) {

					if(!newTags.contains(removeItem)) {
						searchedTags.add(removeItem);
						searchedTagsAdapter.notifyDataSetChanged();
					}
					else {
						newTags.remove(removeItem);
					}
					addedTags.remove(removeItem);
					addedTagsAdapter.notifyDataSetChanged();
				}
				
				else{
					//
				}

			}
		}
	);


		Button buttonTag	  = (Button) findViewById(R.id.buttonTag);
		Button buttonCancel = (Button) findViewById(R.id.buttonCancel);


		buttonTag.setClickable(true);
		buttonCancel.setClickable(true);


		buttonTag.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//skicka till databasen
				//lägg till newTags till databasen om det behövs
				if (addedTags.isEmpty()) {
					TutorialPopupDialog TPD = new TutorialPopupDialog(context);
					TPD.standardDialog(R.string.noAddedTags,"Ok",false);
				}
				else {

					settings = context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
					Log.d("asd","asd");
					String[] addedTagsArray = addedTags.toArray(new String[addedTags.size()]);

					newMarker = new LocationPair(settings.getString("markerTitle","System Failure"),
							Double.valueOf(settings.getString("markerLat","System failure lat")),
							Double.valueOf(settings.getString("markerLng","System Failure lng")),
							settings.getString("markerDescription","System failure desc"),
							addedTagsArray);




					try {
						AddLocationTask addLocationTask = new AddLocationTask();
						addLocationTask.execute(newMarker.getPairsList());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					SharedPreferences.Editor editor = settings.edit();

					editor.putBoolean("createMarker", true);
					editor.commit();
					InputMethodManager imm = (InputMethodManager)context.getSystemService( Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),      
							InputMethodManager.HIDE_NOT_ALWAYS);
					finish();
				}
			}

		});

		buttonCancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				InputMethodManager imm = (InputMethodManager)context.getSystemService( Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),      
						InputMethodManager.HIDE_NOT_ALWAYS);
				finish();			}});


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
	//
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
		searchedTags = new ArrayList<String>();
		searchedTagsAdapter =      
				new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, searchedTags);
		listViewSearched.setAdapter(searchedTagsAdapter);		
	}
}

}




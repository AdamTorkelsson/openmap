package com.openmap.grupp1;
 
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
 
public class SearchTagActivity extends Activity implements SearchView.OnQueryTextListener,
SearchView.OnCloseListener {
 
    private ListView listViewSearched;
    private ListView listViewAdded;
    private SearchView searchView;
    private TagsDbAdapter mDbHelper;
  //  private TextView tagText;
    private ArrayList<String> addedTags = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;

 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchtagview);
 
        

 
        listViewSearched = (ListView) findViewById(R.id.list_searched);
        listViewAdded = (ListView) findViewById(R.id.list_added);

        
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
        
        
 
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchtagactivity_menu, menu);
        ActionBar ab = getActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayShowHomeEnabled(false);
        
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setQueryHint("Search tags");


        return true;
    
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
    case R.id.btn_back_search:
    	//lägg in en back funktion här
        return true;
    default:
        return super.onOptionsItemSelected(item);
        }}
 
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
            Log.d("testar", "showresults3.7");
        	arrayAdapter =      
       	         new ArrayAdapter<String>(this,R.layout.searchviewaddedtags, addedTags);
       	         listViewAdded.setAdapter(arrayAdapter);

 
            // Define the on-click listener for the list items
            listViewSearched.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the cursor, positioned to the corresponding row in the result set
                    Cursor cursor = (Cursor) listViewSearched.getItemAtPosition(position);
                    Log.d("testar", "showresults3.8");

 
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
 
                    //Get References to the TextViews
                    //tagText = (TextView) findViewById(R.id.tag);

                
                    // Update the parent class's TextView
                    //tagText.setText(tag);
                	addedTags.add(tag);
                	arrayAdapter.notifyDataSetChanged();

            
                    searchView.setQuery("",true);
                }
            });
        }
    }
 

}
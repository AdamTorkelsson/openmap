package com.openmap.grupp1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;


public class SharedPrefs {
    private static final String APP_SHARED_PREFS = "Your_preferences"; 
    private SharedPreferences appSharedPrefs;
    private Editor prefsEditor;
    

    public SharedPrefs(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS,
                Context.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }
    
    public boolean setTitle(String Title) {
        prefsEditor.putString(setValue(Title), Title);
        prefsEditor.commit();
        return true;
    }
    
    public boolean setDescription(String Description) {
        
        prefsEditor.putString(setValue(Description), Description);
        prefsEditor.commit();
        return true;
    }
    
    public String getTitle() {
        return appSharedPrefs.getString("Title", "Error");
    }
    public String getDescription() {
        return appSharedPrefs.getString("Description", "Error");
    }

  
    private String setValue(String value){
    	appSharedPrefs.getString("Description", "NotExisting");
    	if ("NotExisting" != appSharedPrefs.getString("Description", "NotExisting")){
    		return value;}
    	else {
    		return setValue(value + "1");
    	}
    	}
    }


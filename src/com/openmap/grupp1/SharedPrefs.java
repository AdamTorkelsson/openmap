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
    

    public String getTitle() {
        return appSharedPrefs.getString("Title", "Error");
    }

    public boolean setTitle(String Title) {
        prefsEditor.putString("Title", Title);
        prefsEditor.commit();
        return true;
    }
    
    public String getDescription() {

        return appSharedPrefs.getString("Description", "Error");
    }

    public boolean setDescription(String Description) {
    
        prefsEditor.putString("Description", Description);
        prefsEditor.commit();
        return true;
    }

}
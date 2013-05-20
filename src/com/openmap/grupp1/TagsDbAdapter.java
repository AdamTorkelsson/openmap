package com.openmap.grupp1;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class TagsDbAdapter {
 
    public static final String KEY_ROWID = "rowid";
    public static final String KEY_TAG = "tag";
    public static final String KEY_SEARCH = "searchData";
 
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
 
    private static final String DATABASE_NAME = "TagData";
    private static final String FTS_VIRTUAL_TABLE = "TagInfo";
    private static final int DATABASE_VERSION = 1;
 
    //Create a FTS3 Virtual Table for fast searches
    private static final String DATABASE_CREATE =
        "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE + " USING fts3(" +
        KEY_TAG + "," +
        KEY_SEARCH + "," +
        " UNIQUE (" + KEY_TAG + "));";
 
 
    private final Context mCtx;
 
    private static class DatabaseHelper extends SQLiteOpenHelper {
 
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
 
 
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }
 
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(db);
        }
    }
 
    public TagsDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }
 
    public TagsDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
 
    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }
 
 
    public long createTag(String tag) {
 
        ContentValues initialValues = new ContentValues();
        String searchValue =     tag;
        initialValues.put(KEY_TAG, tag);
        initialValues.put(KEY_SEARCH, searchValue);
 
        return mDb.insert(FTS_VIRTUAL_TABLE, null, initialValues);
    }
 
 
    public Cursor searchTag(String inputText) throws SQLException {
        String query = "SELECT docid as _id," + 
        KEY_TAG + 
        " from " + FTS_VIRTUAL_TABLE +
        " where " +  KEY_SEARCH + " MATCH '" + inputText + "';";
        Cursor mCursor = mDb.rawQuery(query,null);
 
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
 
    }
 
 
    public boolean deleteAllTags() {
 
        int doneDelete = 0;
        doneDelete = mDb.delete(FTS_VIRTUAL_TABLE, null , null);
        return doneDelete > 0;
 
    }
 
}
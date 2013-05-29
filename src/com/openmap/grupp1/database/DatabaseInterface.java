package com.openmap.grupp1.database;

import java.util.ArrayList;


public interface DatabaseInterface<T> {
	public String getFromDB();
	public void sendToDB(ArrayList<T> params);
}

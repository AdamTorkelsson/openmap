package com.openmap.grupp1.database;

import java.util.ArrayList;

/**
 * This interface defines the methods that will be used in any class
 * that will communicate with the database.
 * @param: <T>
 */
public interface DatabaseInterface<T> {

	/**This method returns the response string from the database, if any.
	 * It is in JSON-format and has to be decoded, depending on what was
	 * requested.
	 * @param void
	 * @return String
	 */
	public String getFromDB();
	
	/**This method is used to send request to the database. The parameters
	 * have to be inserted into an ArrayList to be encoded correctly.
	 * 
	 * @param params	the request as an ArrayList.
	 * @return void
	 */
	public void sendToDB(ArrayList<T> params);
}

package com.openmap.grupp1.helpfunctions;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

/**
 * Dialog fragment to be shown when the user wants to choose a date.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	public String date;


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and returns it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	//Handles the information it gets when the user presses the positive button, notifies the listener and sends the information 
	public void onDateSet(DatePicker view, int year, int month, int day) {

		//Adds a zero to the start of month if it is between 0 and 10
		if (month+1 < 10) {
			date = Integer.toString(year)+"-0"+Integer.toString(month+1);
		}
		else{
			date = Integer.toString(year)+"-"+Integer.toString(month+1);
		}

		//Adds a zero to the start of day if it is between 0 and 10
		if (day < 10) {
			date += "-0"+Integer.toString(day);
		}
		else {
			date += "-"+Integer.toString(day);
		}

		//Notifies the listener and sends the information
		DatePickerDialogListener activity = (DatePickerDialogListener) getActivity();
		activity.onFinishDatePickerDialog(date);

		//Dismisses the view that contains this fragment
		this.dismiss();
	}

	/**
	 * Interface to be implemented to be able to listen to the TimePickerFragment
	 */
	public interface DatePickerDialogListener {
		/**
		 * Called when the TimePickerFragment is dismissed
		 * @param date The date which is chosen in the DatePickerFragment
		 */
		void onFinishDatePickerDialog(String date);
	}
}

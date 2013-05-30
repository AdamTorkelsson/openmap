package com.openmap.grupp1.helpfunctions;


import java.util.Calendar;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
/**
 * Dialog fragment to be shown when the user wants to choose a time.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
	String time;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and returns it
		return new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

	//Handles the information it gets when the user presses the positive button, notifies the listener and sends the information 
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

		//Adds a zero to the start of hourOfDay if it is between 0 and 10
		if (hourOfDay<10 && hourOfDay > 0) {
			time = "0" + Integer.toString(hourOfDay);
		}
		else {
			time = Integer.toString(hourOfDay);
		}

		//Adds a zero to the start of minute if it is between 0 and 10
		if (minute<10) {
			time += ":0"+Integer.toString(minute);
		}
		else {
			time += ":"+Integer.toString(minute);
		}

		//Notifies the listener and sends the information
		TimePickerDialogListener activity = (TimePickerDialogListener) getActivity();
		activity.onFinishTimePickerDialog(time);

		//Dismisses the view that contains this fragment
		this.dismiss();

	}

	/**
	 * Interface to be implemented to be able to listen to the TimePickerFragment
	 */
	public interface TimePickerDialogListener {
		/**
		 * Called when the TimePickerFragment is dismissed
		 * @param time The time which is chosen in the DatePickerFragment
		 */
		void onFinishTimePickerDialog(String time);
	}
}

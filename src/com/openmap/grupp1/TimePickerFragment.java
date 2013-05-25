package com.openmap.grupp1;

import java.util.Calendar;

import com.openmap.grupp1.DatePickerFragment.DatePickerDialogListener;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
		String time;
		 
		 @Override
		 public Dialog onCreateDialog(Bundle savedInstanceState) {
		        // Use the current time as the default values for the picker
		        final Calendar c = Calendar.getInstance();
		        int hour = c.get(Calendar.HOUR_OF_DAY);
		        int minute = c.get(Calendar.MINUTE);

		        // Create a new instance of TimePickerDialog and return it
		        return new TimePickerDialog(getActivity(), this, hour, minute,
		                DateFormat.is24HourFormat(getActivity()));
		    }
		 
		
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			time = Integer.toString(hourOfDay)+":"+Integer.toString(minute);
			TimePickerDialogListener activity = (TimePickerDialogListener) getActivity();
			activity.onFinishTimePickerDialog(time);
			this.dismiss();
			
		}
		public interface TimePickerDialogListener {
		    void onFinishTimePickerDialog(String inputText);
		}
	 }

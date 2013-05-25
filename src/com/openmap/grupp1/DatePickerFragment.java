package com.openmap.grupp1;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

public String date;

public DatePickerFragment(){
}



@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker

final Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY_OF_MONTH);

// Create a new instance of DatePickerDialog and return it
return new DatePickerDialog(getActivity(), this, year, month, day);
}

public void onDateSet(DatePicker view, int year, int month, int day) {
	date = Integer.toString(year)+"-"+Integer.toString(month+1)+"-"+Integer.toString(day);
	DatePickerDialogListener activity = (DatePickerDialogListener) getActivity();
	activity.onFinishDatePickerDialog(date);
	this.dismiss();
}


public interface DatePickerDialogListener {
    void onFinishDatePickerDialog(String inputText);
}
}
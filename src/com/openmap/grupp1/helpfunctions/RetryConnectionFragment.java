package com.openmap.grupp1.helpfunctions;

import com.openmap.grupp1.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Dialog fragment to be shown when a connection to the database can't be established
 */
public class RetryConnectionFragment extends DialogFragment {

	/**
	 *  The activity that creates an instance of this dialog fragment must
	 * implement this interface in order to receive event callbacks.
	 * Each method passes the DialogFragment in case the host needs to query it.
	 */
	public interface RetryConnectionListener {
		/**
		 * Defines the action to be taken when the positive(right) button is pressed
		 * @param dialog The dialog to be listened to
		 */
		public void onDialogPositiveClick(DialogFragment dialog);
		/**
		 * Defines the action to be taken when the negative(left) button is pressed
		 * @param dialog The dialog to be listened to
		 */
		public void onDialogNegativeClick(DialogFragment dialog);
	}

	// Use this instance of the interface to deliver action events
	RetryConnectionListener mListener;

	// Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the host
			mListener = (RetryConnectionListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement RetryConnectionListener");
		}
	}
	//Defines the dialog
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Build the dialog and set up the button click handlers
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.noConnection)
		.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Send the positive button event back to the host activity
				mListener.onDialogPositiveClick(RetryConnectionFragment.this);
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Send the negative button event back to the host activity
				mListener.onDialogPositiveClick(RetryConnectionFragment.this);
			}
		});
		return builder.create();
	}
}
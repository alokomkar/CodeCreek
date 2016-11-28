package com.sortedqueue.programmercreek.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;


public class AuxilaryUtils {
	
	public static void showConfirmationDialog(final Activity activity){

		Builder builder = new Builder(activity);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				activity.finish();
			}
		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				//dialog.dismiss();
			}
		});

		builder.setMessage("All your progress will be lost. Are you sure you want to Quit?");
		builder.setTitle(activity.getTitle());
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.show();
	}

	
	public static void displayAlert(String title, String message, Context context) {

		Builder alertDialogBuilder = new Builder(
				context);

		// set title
		alertDialogBuilder.setTitle(title);

		// set dialog message
		alertDialogBuilder
		.setMessage(message)
		.setCancelable(false)
		.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, close
				// current activity
				dialog.cancel();
				

			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	public static void displayResultAlert(String title, String message, final Activity activity) {

		Builder alertDialogBuilder = new Builder(
				activity);

		// set title
		alertDialogBuilder.setTitle(title);

		// set dialog message
		alertDialogBuilder
		.setMessage(message)
		.setCancelable(false)
		.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, close
				// current activity
				dialog.cancel();
				activity.finish();

			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	public static String getProgramTitle(int program_Index, Context context, DatabaseHandler databaseHandler ) {
		
		if ( databaseHandler == null ) {
			databaseHandler = new DatabaseHandler( context );
		}
		return databaseHandler.getProgram_Index(program_Index).getProgram_Description();
		
	}

}

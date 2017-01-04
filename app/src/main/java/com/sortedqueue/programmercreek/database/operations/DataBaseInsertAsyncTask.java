package com.sortedqueue.programmercreek.database.operations;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;


public class DataBaseInsertAsyncTask extends AsyncTask<Void, Void, Void> {

	Context mContext = null;
	int mIndex = 0;
	UIUpdateListener mUiUpdateListener;
	ArrayList<Program_Table> program_tables;

	private String TAG = getClass().getSimpleName();

	public DataBaseInsertAsyncTask(Context mContext, int i, ArrayList<Program_Table> program_tables, UIUpdateListener uiUpdateListener) {
		this.mContext = mContext;
		this.mIndex = i;
		this.mUiUpdateListener = uiUpdateListener;
		this.program_tables = program_tables;
	}

	private void logDebugMessage( String message ) {
		Log.d(TAG, message);
	}


	public DataBaseInsertAsyncTask(Context context, int index, UIUpdateListener uiUpdateListener ) {

		this.mContext = context;
		this.mIndex = index;
		this.mUiUpdateListener = uiUpdateListener;

	}


	@Override
	protected Void doInBackground(Void... params) {

		return null;

	}


	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		CommonUtils.displayProgressDialog(mContext, "Initializing data for the first time...");
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		CommonUtils.dismissProgressDialog();
		if( mUiUpdateListener != null ) {
			mUiUpdateListener.updateUI( );
		}
	}
}

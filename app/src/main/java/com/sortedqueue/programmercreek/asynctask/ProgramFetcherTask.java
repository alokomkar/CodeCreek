package com.sortedqueue.programmercreek.asynctask;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener;


public class ProgramFetcherTask extends AsyncTask<Void, Void, Void> {

	ProgressDialog mProgressDialog = null;
	Context mContext = null;
	int mProgramIndex;
	UIProgramFetcherListener mUiProgramFetcherListener;
	DatabaseHandler mDatabaseHandler;
	List<Program_Table> mProgram_TableList;

	public ProgramFetcherTask( Context context, DatabaseHandler databaseHandler, int index ) {
		this.mContext = context;
		this.mProgramIndex = index;
		this.mUiProgramFetcherListener = (UIProgramFetcherListener) mContext;
		this.mDatabaseHandler = databaseHandler;
	}
	
	public ProgramFetcherTask( Context context, UIProgramFetcherListener uiProgramFetcherListener, DatabaseHandler databaseHandler, int index ) {
		this.mContext = context;
		this.mProgramIndex = index;
		this.mUiProgramFetcherListener = uiProgramFetcherListener;
		this.mDatabaseHandler = databaseHandler;
	}

	@Override
	protected Void doInBackground(Void... params) {
		if( mDatabaseHandler == null ) {
			mDatabaseHandler = new DatabaseHandler(mContext);
		}
		mProgram_TableList =  mDatabaseHandler.getAllProgram_Tables( mProgramIndex );
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setMessage("Initializing Program, Please Wait...");
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
		if( mUiProgramFetcherListener != null ) {
			mUiProgramFetcherListener.updateUI( mProgram_TableList );
		}
	}


}

package com.sortedqueue.programmercreek.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.List;


public class ProgramFetcherTask extends AsyncTask<Void, Void, List<Program_Table>> {

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
	protected List<Program_Table> doInBackground(Void... params) {
		if( mDatabaseHandler == null ) {
			mDatabaseHandler = new DatabaseHandler(mContext);
		}
		mProgram_TableList =  mDatabaseHandler.getAllProgram_Tables( mProgramIndex, new CreekPreferences(mContext).getProgramLanguage() );
		return mProgram_TableList;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		CommonUtils.displayProgressDialog(mContext, "Initializing Program, Please Wait...");
	}

	@Override
	protected void onPostExecute(List<Program_Table> result) {
		super.onPostExecute(result);
		CommonUtils.dismissProgressDialog();
		if( mUiProgramFetcherListener != null ) {
			mUiProgramFetcherListener.updateUI( result );
		}
	}


}

package com.sortedqueue.programmercreek.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.UIProgramListFetcherListener;

import java.util.List;

public class ProgramListFetcherTask extends AsyncTask<Void, Void, List<Program_Index>> {

	ProgressDialog mProgressDialog;
	private Context mContext;
	private UIProgramListFetcherListener mUIProgramListFetcherListener;
	List<Program_Index> mProgram_Indexs;
	private DatabaseHandler mDatabaseHandler;
	
	public ProgramListFetcherTask(Context context, DatabaseHandler databaseHandler, UIProgramListFetcherListener uiProgramListFetcherListener ) {
		this.mContext = context;
		this.mUIProgramListFetcherListener = uiProgramListFetcherListener;
		this.mDatabaseHandler = databaseHandler;
	}
	
	@Override
    protected void onPreExecute() {
    	mProgressDialog = new ProgressDialog(mContext);
    	mProgressDialog.setMessage("Fetching Program List");
    	mProgressDialog.setCancelable(false);
    	mProgressDialog.show();
    }
	
	@Override
	protected List<Program_Index> doInBackground(Void... params) {
		mProgram_Indexs = mDatabaseHandler.getAllProgram_Indexs();
		return mProgram_Indexs;
	}
	
	@Override
	protected void onPostExecute(List<Program_Index> program_Indexs) {
		if( mProgressDialog != null ) {
			mProgressDialog.dismiss();
		}
		if( mUIProgramListFetcherListener != null ) {
			mUIProgramListFetcherListener.updateUIProgramList(program_Indexs);
		}
	
	}
	

}

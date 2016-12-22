package com.sortedqueue.programmercreek.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.UIProgramListFetcherListener;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.List;

public class ProgramListFetcherTask extends AsyncTask<Void, Void, List<Program_Index>> {

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
		CommonUtils.displayAdsProgressDialog(mContext, "Fetching Program List");
    }
	
	@Override
	protected List<Program_Index> doInBackground(Void... params) {
		mProgram_Indexs = mDatabaseHandler.getAllProgram_Indexs(new CreekPreferences(mContext).getProgramLanguage());
		return mProgram_Indexs;
	}
	
	@Override
	protected void onPostExecute(List<Program_Index> program_Indexs) {
		CommonUtils.dismissProgressDialog();
		if( mUIProgramListFetcherListener != null ) {
			mUIProgramListFetcherListener.updateUIProgramList(program_Indexs);
		}
	
	}
	

}

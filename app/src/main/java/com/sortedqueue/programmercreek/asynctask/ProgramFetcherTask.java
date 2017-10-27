package com.sortedqueue.programmercreek.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;


public class ProgramFetcherTask extends AsyncTask<Void, Void, ArrayList<ProgramTable>> {

	Context mContext = null;
	int mProgramIndex;
	UIProgramFetcherListener mUiProgramFetcherListener;
	ArrayList<ProgramTable> mProgram_TableList;

	public ProgramFetcherTask( Context context, int index ) {
		this.mContext = context;
		this.mProgramIndex = index;
		this.mUiProgramFetcherListener = (UIProgramFetcherListener) mContext;
	}

	public ProgramFetcherTask( Context context, UIProgramFetcherListener uiProgramFetcherListener, int index ) {
		this.mContext = context;
		this.mProgramIndex = index;
		this.mUiProgramFetcherListener = uiProgramFetcherListener;
	}

	@Override
	protected ArrayList<ProgramTable> doInBackground(Void... params) {
		mProgram_TableList = new FirebaseDatabaseHandler(mContext).getProgramTables(mProgramIndex);
		return mProgram_TableList;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		CommonUtils.INSTANCE.displayProgressDialog(mContext, "Initializing Program, Please Wait...");
	}

	@Override
	protected void onPostExecute(ArrayList<ProgramTable> result) {
		super.onPostExecute(result);
		if( result.size() > 0 ) {
			CommonUtils.INSTANCE.dismissProgressDialog();
			if( mUiProgramFetcherListener != null ) {
				mUiProgramFetcherListener.updateUI( result );
			}
		}
		else {
			new FirebaseDatabaseHandler(mContext).getProgramTablesInBackground(mProgramIndex, new FirebaseDatabaseHandler.GetProgramTablesListener() {
				@Override
				public void onSuccess(ArrayList<ProgramTable> programTables) {
					mUiProgramFetcherListener.updateUI(programTables);
					CommonUtils.INSTANCE.dismissProgressDialog();
				}

				@Override
				public void onError(DatabaseError databaseError) {
					CommonUtils.INSTANCE.dismissProgressDialog();
				}
			});
		}

	}


}

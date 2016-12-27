package com.sortedqueue.programmercreek.database.operations;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;


public class DataBaseInsertAsyncTask extends AsyncTask<Void, Void, Void> {

	Context mContext = null;
	DatabaseHandler mDatabaseHandler;
	int mIndex = 0;
	UIUpdateListener mUiUpdateListener;
	ArrayList<Program_Table> program_tables;

	private String TAG = getClass().getSimpleName();

	public DataBaseInsertAsyncTask(Context mContext, int i, ArrayList<Program_Table> program_tables, UIUpdateListener uiUpdateListener) {
		this.mContext = mContext;
		this.mDatabaseHandler = new DatabaseHandler(mContext);
		this.mIndex = i;
		this.mUiUpdateListener = uiUpdateListener;
		this.program_tables = program_tables;
	}

	private void logDebugMessage( String message ) {
		Log.d(TAG, message);
	}


	public DataBaseInsertAsyncTask(Context context, int index, UIUpdateListener uiUpdateListener ) {

		this.mContext = context;
		this.mDatabaseHandler = new DatabaseHandler(context);
		this.mIndex = index;
		this.mUiUpdateListener = uiUpdateListener;

	}

	public DatabaseHandler insertProgramListtoDB( Context context ) {
		/**
		 * Adding data to database
		 * */
		DatabaseHandler databaseHandler = new DatabaseHandler(context);
		/**
		 * CRUD Operations
		 * */
		// Inserting Contacts
		logDebugMessage("Inserting ProgramList");
		return databaseHandler;
	}

	@Override
	protected Void doInBackground(Void... params) {

		logDebugMessage("Inserting Program Index : " + mIndex);
		switch( mIndex ) {
		case -1:
			insertProgramListtoDB(mContext);
			break;
		case -3 :
			insertProgramstoDB( mContext );
			break;
		}
		return null;

	}

	private DatabaseHandler insertProgramstoDB(Context mContext) {
		String programLanguage = new CreekPreferences(mContext).getProgramLanguage();
		for( Program_Table program_table : program_tables ) {
			program_table.setmProgram_Language(programLanguage);
			mDatabaseHandler.addProgram_Table(program_table);
		}
		return mDatabaseHandler;
	}


	/*private void insertProgram_31(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 31");
		int index = 0;

		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "#include <stdio.h>","Header Include"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "void concatenate_string(char*, char*);","Function Declaration - concatenate_string"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "int main() {","Main Definition"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " char original[100], add[100];","Variable Declaration - original, add"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " printf(\"Enter source string\\n\"); ","Print Enter source string"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " gets(original);","Read orginal"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " printf(\"Enter string to concatenate\\n\");","Print Enter string to concatenate"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " gets(add);","Read add"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " concatenate_string(original, add);","Call function concatenate_string"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " printf(\"String after concatenation is \"%s\"\\n\", original);","Print String after concatenation"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " return 0;","return 0"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "}","End"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "void concatenate_string(char *original, char *add) {","Function Definition - concatenate_string"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " while(*original)","Repeat while *original not empty"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "  original++;","increment original"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " while(*add) {","Repeat while *add not empty"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "  *original = *add;","Assign *original = *add"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "  add++; original++;","increment add, original"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " }","End"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " *original = '\0';","Assign *original = '\0'"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "}","End"));


	}*/

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

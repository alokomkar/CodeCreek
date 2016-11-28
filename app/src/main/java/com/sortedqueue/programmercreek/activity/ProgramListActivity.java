package com.sortedqueue.programmercreek.activity;


import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CustomProgramIndexAdapter;
import com.sortedqueue.programmercreek.asynctask.ProgramFetcherTask;
import com.sortedqueue.programmercreek.asynctask.ProgramListFetcherTask;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.database.operations.DataBaseInserterAsyncTask;
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener;
import com.sortedqueue.programmercreek.interfaces.UIProgramListFetcherListener;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;


public class ProgramListActivity extends Activity implements UIUpdateListener {

	DatabaseHandler mDatabaseHandler;

	int mInvokeTest = 0;

	List<Program_Index> mProgram_Indexs;

	public static int PROGRAM_LIST_SIZE = 0;

	private String TAG = getClass().getSimpleName();
	private void logDebugMessage( String message ) {
		Log.d(TAG, message);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_program_list);

		mDatabaseHandler = new DatabaseHandler(this);

		fetchProgramsList( mDatabaseHandler );


	}

	private void fetchProgramsList(final DatabaseHandler databaseHandler) {

		// Reading all contacts
		logDebugMessage("Reading All Programs...");

		new ProgramListFetcherTask(this, mDatabaseHandler, new UIProgramListFetcherListener() {

			@Override
			public void updateUIProgramList(List<Program_Index> program_Indexes) {
				mProgram_Indexs = program_Indexes;
				PROGRAM_LIST_SIZE = mProgram_Indexs.size();
				if( mProgram_Indexs == null || mProgram_Indexs.size() == 0 ) {
					new DataBaseInserterAsyncTask(ProgramListActivity.this, -1, ProgramListActivity.this).execute();
				}
				CustomProgramIndexAdapter customProgramIndexAdapter = new CustomProgramIndexAdapter(ProgramListActivity.this, android.R.layout.simple_list_item_1, R.id.txtViewProgDescription, mProgram_Indexs);
				ListView programListView = (ListView)findViewById(R.id.listView1);
				programListView.setOnItemClickListener(programListItemListener());
				programListView.setAdapter(customProgramIndexAdapter);

			}
		}).execute();

	}

	@Override
	protected void onDestroy() { 

		mDatabaseHandler.close();
		super.onDestroy();

	}

	Bundle mBundle;
	Intent mInvokeIntent = null;
	int mSelectedProgramIndex = 0;
	private List<Program_Table> mProgram_TableList;
	private OnItemClickListener programListItemListener() {
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long id) {

				mBundle = new Bundle();
				Program_Index program_Index = (Program_Index) adapter.getItemAtPosition(position);
				/*
				 * Check the module table if the selected program has modules
				 * */
				if( mDatabaseHandler == null ) {
					mDatabaseHandler = new DatabaseHandler(ProgramListActivity.this);
				}
				/*List<Module_Table> module_Tables = mDatabaseHandler.getAllModule_Tables(program_Index.getIndex());
				if( module_Tables != null ) { 
					newIntentBundle.putBoolean(DashboardActivity.KEY_MODULE_LIST, true);
				}
				else { 
					newIntentBundle.putBoolean(DashboardActivity.KEY_MODULE_LIST, false);
				}*/
				/**
				 * Pack and pass program index to 
				 * ProgramActivity so as to retrieve 
				 * corresponding program from DB.
				 * */
				mSelectedProgramIndex = program_Index.getIndex();
				mProgram_TableList = null;
				new ProgramFetcherTask(ProgramListActivity.this, new UIProgramFetcherListener() {

					@Override
					public void updateUI(List<Program_Table> program_TableList) {
						
						mProgram_TableList = program_TableList;
						if( mProgram_TableList != null ) {
							logDebugMessage("Size of Program Table : " + mProgram_TableList.size());	
						}
						if( program_TableList == null || program_TableList.size() == 0 ) {
							new DataBaseInserterAsyncTask(ProgramListActivity.this, mSelectedProgramIndex, ProgramListActivity.this).execute();

						}
					}
				}, mDatabaseHandler, mSelectedProgramIndex).execute();



				mBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mSelectedProgramIndex);
				mBundle.putBoolean(KEY_WIZARD, getIntent().getExtras().getBoolean(ProgramListActivity.KEY_WIZARD));
				mInvokeTest = getIntent().getExtras().getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST);


				switch( mInvokeTest ) {

				case ProgrammingBuddyConstants.KEY_REVISE : 
					showReviseModeBox();
					break;

				case ProgrammingBuddyConstants.KEY_WIZARD :
					mInvokeIntent = new Intent(ProgramListActivity.this, FullScreenWizardActivity.class);
					mInvokeIntent.putExtras(mBundle);
					startActivity(mInvokeIntent);	
					break;

				case ProgrammingBuddyConstants.KEY_TEST :
				case ProgrammingBuddyConstants.KEY_LIST :
				case ProgrammingBuddyConstants.KEY_MATCH :
				case ProgrammingBuddyConstants.KEY_QUIZ :
					insertProgramTables();
					break;

				}
			}
		};

	}

	private void invokeTestIntents( ) {

		switch( mInvokeTest ) {
		case ProgrammingBuddyConstants.KEY_TEST :
			mInvokeIntent = new Intent(ProgramListActivity.this, TestDragNDropActivity.class);
			mInvokeIntent.putExtras(mBundle);
			startActivity(mInvokeIntent);
			break;
		case ProgrammingBuddyConstants.KEY_LIST :
			showOptionsBox();
			break;
		case ProgrammingBuddyConstants.KEY_MATCH :
			mInvokeIntent = new Intent(ProgramListActivity.this, MatchMakerActivity.class);
			mInvokeIntent.putExtras(mBundle);
			startActivity(mInvokeIntent);
			break;
		case ProgrammingBuddyConstants.KEY_QUIZ :
			showSelectBox();
			break;

		}
	}

	private void insertProgramTables() {
		//if( mDatabaseHandler == null ) {
			mDatabaseHandler = new DatabaseHandler(this);
		//}
		if( mDatabaseHandler.getProgram_TablesCount() != ProgramListActivity.PROGRAM_LIST_SIZE ) {
			new DataBaseInserterAsyncTask(ProgramListActivity.this, -2, ProgramListActivity.this ).execute();	
		}
		else {
			invokeTestIntents();
		}
	}

	protected void showReviseModeBox() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Revise Programs"); 
		String[] boxTypes = {"Normal Mode","Line By Line Mode"};
		builder.setItems(boxTypes, reviseModeTypeListener);
		builder.setNegativeButton("Cancel", null);
		AlertDialog alertDialog  = builder.create();
		alertDialog.show();


	}

	public static final int KEY_REVISE_NORMAL = 0;
	public static final int KEY_REVISE_LINE_BY_LINE = 1;

	/**
	 * Quiz Type Listener 
	 * */
	DialogInterface.OnClickListener reviseModeTypeListener = 
			new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			Bundle newIntentBundle = new Bundle();
			Intent newIntent = null;
			newIntentBundle.putBoolean(KEY_WIZARD, getIntent().getExtras().getBoolean(ProgramListActivity.KEY_WIZARD));

			switch ( which ) {

			case KEY_REVISE_NORMAL :
				newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mSelectedProgramIndex);
				newIntent = new Intent(ProgramListActivity.this, ProgramActivity.class);
				break;

			case KEY_REVISE_LINE_BY_LINE :
				newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mSelectedProgramIndex);
				newIntent = new Intent(ProgramListActivity.this, MemorizeProgramActivity.class);
				break;

			}

			newIntent.putExtras(newIntentBundle);
			startActivity(newIntent);

		}
	};


	protected void showOptionsBox() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select Mode"); 
		String[] boxTypes = {"Revise Mode", "Memorize Mode", "Test Mode", "Quiz Mode", "Match Mode"};
		builder.setItems(boxTypes, optionTypeListener);
		builder.setNegativeButton("Cancel", null);
		AlertDialog alertDialog  = builder.create();
		alertDialog.show();

	}

	public static final int INDEX_KEY_REVISE = 0;
	public static final int INDEX_KEY_MEMORIZE = 1;
	public static final int INDEX_KEY_TEST = 2;
	public static final int INDEX_KEY_QUIZ = 3;
	public static final int INDEX_KEY_MATCH = 4;


	/**
	 * Option Type Listener 
	 * */
	DialogInterface.OnClickListener optionTypeListener = 
			new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			Intent newIntent = new Intent(ProgramListActivity.this, QuizActivity.class);
			Bundle newIntentBundle = new Bundle();
			newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mSelectedProgramIndex);

			switch ( which ) {

			case INDEX_KEY_REVISE :
				newIntent = new Intent(ProgramListActivity.this, ProgramActivity.class);
				newIntent.putExtras(newIntentBundle);
				startActivity(newIntent);	
				break;

			case INDEX_KEY_MEMORIZE :
				newIntent = new Intent(ProgramListActivity.this, MemorizeProgramActivity.class);
				newIntent.putExtras(newIntentBundle);
				startActivity(newIntent);	
				break;

			case INDEX_KEY_TEST :
				newIntent = new Intent(ProgramListActivity.this, TestDragNDropActivity.class);
				newIntent.putExtras(newIntentBundle);
				startActivity(newIntent);
				break;

			case INDEX_KEY_QUIZ : 
				showSelectBox();
				break;

			case INDEX_KEY_MATCH : 
				newIntent = new Intent(ProgramListActivity.this, MatchMakerActivity.class);
				newIntent.putExtras(newIntentBundle);
				startActivity(newIntent);
				break;

			}


		}
	};

	protected void showSelectBox() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Quiz Mode - Question Type"); 
		String[] boxTypes = {"Description","Program Code"};
		builder.setItems(boxTypes, quizTypeListener);
		builder.setNegativeButton("Cancel", null);
		AlertDialog alertDialog  = builder.create();
		alertDialog.show();

	}

	public static String KEY_QUIZ_TYPE = "Quiz_Type";

	public static final int KEY_QUIZ_DESCRIPTION_QUESTION = 1;
	public static final int KEY_QUIZ_PROGRAM_CODE_QUESTION = 0;
	public static final String KEY_WIZARD = "wizard_mode";

	/**
	 * Quiz Type Listener 
	 * */
	DialogInterface.OnClickListener quizTypeListener = 
			new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			Bundle newIntentBundle = new Bundle();
			Intent newIntent = new Intent(ProgramListActivity.this, QuizActivity.class);

			switch ( which ) {

			case KEY_QUIZ_DESCRIPTION_QUESTION :
				newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mSelectedProgramIndex);
				newIntentBundle.putInt(KEY_QUIZ_TYPE, KEY_QUIZ_DESCRIPTION_QUESTION);
				break;

			case KEY_QUIZ_PROGRAM_CODE_QUESTION :
				newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mSelectedProgramIndex);
				newIntentBundle.putInt(KEY_QUIZ_TYPE, KEY_QUIZ_PROGRAM_CODE_QUESTION);
				break;

			}

			newIntent.putExtras(newIntentBundle);
			startActivity(newIntent);


		}
	};

	@Override
	public void updateUI() {
		new ProgramFetcherTask(ProgramListActivity.this, new UIProgramFetcherListener() {

			@Override
			public void updateUI(List<Program_Table> program_TableList) {
				// TODO Auto-generated method stub
				mProgram_TableList = program_TableList;
				invokeTestIntents();
			}
		}, mDatabaseHandler, mSelectedProgramIndex).execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.program, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_refresh_database:
			new DataBaseInserterAsyncTask(this, -1, this).execute();
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}

	}




}

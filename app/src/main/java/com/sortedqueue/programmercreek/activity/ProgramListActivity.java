package com.sortedqueue.programmercreek.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.asynctask.ProgramListFetcherTask;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.UIProgramListFetcherListener;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ProgramListActivity extends Activity implements UIUpdateListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {

	DatabaseHandler mDatabaseHandler;

	int mInvokeTest = 0;

	List<Program_Index> mProgram_Indexs;

	public int PROGRAM_LIST_SIZE = 0;

	private String TAG = getClass().getSimpleName();
	private Program_Index program_Index;
	@Bind(R.id.adView)
	AdView adView;

	private void logDebugMessage( String message ) {
		Log.d(TAG, message);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_program_list);
		ButterKnife.bind(this);
		mDatabaseHandler = new DatabaseHandler(this);

		fetchProgramsList( mDatabaseHandler );
		initAds();

	}

	private void initAds() {
		MobileAds.initialize(getApplicationContext(), getString(R.string.mobile_banner_id));
		//For actual ads : AdRequest adRequest = new AdRequest.Builder().build();
		//For creating test ads
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
				.build();
		adView.loadAd(adRequest);
		adView.setVisibility(View.GONE);
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				super.onAdLoaded();
				adView.setVisibility(View.VISIBLE);
			}
		});
	}


	private void fetchProgramsList(final DatabaseHandler databaseHandler) {

		// Reading all contacts
		logDebugMessage("Reading All Programs...");

		new ProgramListFetcherTask(this, mDatabaseHandler, new UIProgramListFetcherListener() {

			@Override
			public void updateUIProgramList(List<Program_Index> program_Indexes) {
				mProgram_Indexs = program_Indexes;
				PROGRAM_LIST_SIZE = mProgram_Indexs.size();
				CustomProgramRecyclerViewAdapter customProgramRecyclerViewAdapter = new CustomProgramRecyclerViewAdapter(ProgramListActivity.this, mProgram_Indexs);
				RecyclerView programListRecyclerView = (RecyclerView) findViewById(R.id.programListRecyclerView);
				programListRecyclerView.setLayoutManager( new LinearLayoutManager(ProgramListActivity.this, LinearLayoutManager.VERTICAL, false));
				programListRecyclerView.setAdapter(customProgramRecyclerViewAdapter);

			}
		}).execute();

	}

	Bundle mBundle;
	Intent mInvokeIntent = null;
	int mSelectedProgramIndex = 0;
	String mSelectedProgramTitle = "";

	private void loadProgramExplanation(String programWiki) {
		Intent intent = new Intent(ProgramListActivity.this, ProgramWikiActivity.class);
		intent.putExtra(DatabaseHandler.KEY_WIKI, programWiki);
		startActivity(intent);
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
							newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, PROGRAM_LIST_SIZE);
							newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, mSelectedProgramTitle);
							newIntent = new Intent(ProgramListActivity.this, ProgramActivity.class);
							break;

						case KEY_REVISE_LINE_BY_LINE :
							newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mSelectedProgramIndex);
							newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, PROGRAM_LIST_SIZE);
							newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, mSelectedProgramTitle);
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
		String[] boxTypes = {"Explanation", "Revise Mode", "Memorize Mode", "Test Mode", "Quiz Mode", "Match Mode" };
		builder.setItems(boxTypes, optionTypeListener);
		builder.setNegativeButton("Cancel", null);
		AlertDialog alertDialog  = builder.create();
		alertDialog.show();

	}

	public static final int INDEX_KEY_EXPLANATION = 0;
	public static final int INDEX_KEY_REVISE = 1;
	public static final int INDEX_KEY_MEMORIZE = 2;
	public static final int INDEX_KEY_TEST = 3;
	public static final int INDEX_KEY_QUIZ = 4;
	public static final int INDEX_KEY_MATCH = 5;


	/**
	 * Option Type Listener 
	 * */
	DialogInterface.OnClickListener optionTypeListener =
			new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					Intent newIntent = null;
					Bundle newIntentBundle = new Bundle();
					newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mSelectedProgramIndex);
					newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, PROGRAM_LIST_SIZE);
					newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, mSelectedProgramTitle);

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
						case INDEX_KEY_EXPLANATION :
							String programWiki = program_Index.getWiki();
							loadProgramExplanation( programWiki );
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
							newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, mSelectedProgramTitle);
							newIntentBundle.putInt(KEY_QUIZ_TYPE, KEY_QUIZ_DESCRIPTION_QUESTION);
							break;

						case KEY_QUIZ_PROGRAM_CODE_QUESTION :
							newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mSelectedProgramIndex);
							newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, mSelectedProgramTitle);
							newIntentBundle.putInt(KEY_QUIZ_TYPE, KEY_QUIZ_PROGRAM_CODE_QUESTION);
							break;

					}

					newIntent.putExtras(newIntentBundle);
					startActivity(newIntent);


				}
			};

	@Override
	public void updateUI() {

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
				//insertProgramIndexes();
				return true;

			default:
				return super.onOptionsItemSelected(item);

		}

	}


	@Override
	public void onItemClick(int position) {
		mBundle = new Bundle();
		program_Index = mProgram_Indexs.get(position);
				/*
				 * Check the module table if the selected program has modules
				 * */
		if( mDatabaseHandler == null ) {
			mDatabaseHandler = new DatabaseHandler(ProgramListActivity.this);
		}
				/*List<Module_Table> module_Tables = mDatabaseHandler.getAllModule_Tables(program_Index.getTableIndex());
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
		mSelectedProgramTitle = program_Index.getProgram_Description();

		mBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mSelectedProgramIndex);
		mBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, PROGRAM_LIST_SIZE);
		mBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, mSelectedProgramTitle);
		mBundle.putBoolean(KEY_WIZARD, getIntent().getExtras().getBoolean(ProgramListActivity.KEY_WIZARD));
		mInvokeTest = getIntent().getExtras().getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST);


		switch( mInvokeTest ) {

			case ProgrammingBuddyConstants.KEY_REVISE :
				showReviseModeBox();
				break;

			case ProgrammingBuddyConstants.KEY_WIZARD :
				Intent programListIntent = new Intent(getApplicationContext(), ProgramListActivity.class);
				programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, mInvokeTest);
				programListIntent.putExtra(ProgramListActivity.KEY_WIZARD, true);
				startActivity(programListIntent);
				break;

			case ProgrammingBuddyConstants.KEY_TEST :
			case ProgrammingBuddyConstants.KEY_LIST :
			case ProgrammingBuddyConstants.KEY_MATCH :
			case ProgrammingBuddyConstants.KEY_QUIZ :
				invokeTestIntents();
				break;

		}
	}
}

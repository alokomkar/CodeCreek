package com.sortedqueue.programmercreek.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CustomProgramLineListAdapter;
import com.sortedqueue.programmercreek.asynctask.ProgramFetcherTask;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.database.operations.DataBaseInsertAsyncTask;
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProgramActivity extends AppCompatActivity implements UIUpdateListener {

	ArrayList<String> mProgramList = new ArrayList<String>();
	ArrayList<String> mProgramExplanationList = new ArrayList<String>();

	//ArrayAdapter<String> mAdapterProgramList;
	CustomProgramLineListAdapter mAdapterProgramList;
	CustomProgramLineListAdapter mAdapterProgramExplanationList;

	List<Program_Table> mProgram_TableList;

	ListView mProgramListView;
	ListView mProgramExplanationListView;

	int mProgram_Index;
	Button mProgDescriptionBtn;
	ImageButton mNextProgramBtn;
	ImageButton mPrevProgramBtn;

	int mListPostion = 0;
	DatabaseHandler mDatabaseHandler;
	boolean mWizard = false;
	String mProgram_Title = null;
	//gesture detector
	/**
	 * http://code.tutsplus.com/tutorials/android-sdk-detecting-gestures--mobile-21161
	 * */


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDatabaseHandler = new DatabaseHandler(this);
		Bundle newProgramActivityBundle = getIntent().getExtras();
		mProgram_Index = newProgramActivityBundle.getInt(ProgrammingBuddyConstants.KEY_PROG_ID);

		this.mWizard = newProgramActivityBundle.getBoolean(ProgramListActivity.KEY_WIZARD);
		//boolean modules = newProgramActivityBundle.getBoolean(DashboardActivity.KEY_MODULE_LIST);

		mProgram_Title = getProgramTitle(mProgram_Index); 
		if(  mProgram_Title == null ) {
			AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", this);
		}
		else {
			setTitle("Revise : "+mProgram_Title);
			Log.d("ProgramActivity", " :: Program_Index : " + mProgram_Index+"");
			getProgramTableFromDB(mProgram_Index);
		}
	}

	private void getProgramTableFromDB(int program_Index) {

		new ProgramFetcherTask(this, new UIProgramFetcherListener() {

			@Override
			public void updateUI(List<Program_Table> program_TableList) {
				mProgram_TableList = program_TableList;
				if( mProgram_TableList == null || mProgram_TableList.size() == 0 ) {
					new DataBaseInsertAsyncTask(ProgramActivity.this, mProgram_Index, ProgramActivity.this ).execute();
					mProgram_TableList = mDatabaseHandler.getAllProgram_Tables(mProgram_Index, new CreekPreferences(ProgramActivity.this).getProgramLanguage());
				}
				else {
					initUI( mProgram_TableList );
				}		
			}
		}, mDatabaseHandler, program_Index).execute();


	}

	private void initUI(List<Program_Table> program_TableList) {

		mProgramList = new ArrayList<String>();
		mProgramExplanationList = new ArrayList<String>();

		Iterator<Program_Table> iteraor = program_TableList.iterator();
		while(iteraor.hasNext()) { 

			Program_Table newProgram_Table = iteraor.next();
			mProgramList.add(newProgram_Table.getLine_No()+". "+newProgram_Table.getProgram_Line());
			mProgramExplanationList.add(newProgram_Table.getLine_No()+". "+newProgram_Table.getProgram_Line_Description());

		}

		setContentView(R.layout.activity_program);

		//FrameLayout container = (LinearLayout) findViewById(R.id.container);
		mProgramListView = (ListView) findViewById(R.id.list_program);
		mProgramExplanationListView = (ListView) findViewById(R.id.list_explanation);



		// Prepare the ListView
		/*mAdapterProgramList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mProgramList);*/
		mAdapterProgramList = new CustomProgramLineListAdapter(this, android.R.layout.simple_list_item_1,
				R.id.progamLineTxtView, mProgramList, false );

		// Prepare the ListView
		mAdapterProgramExplanationList = new CustomProgramLineListAdapter(this, android.R.layout.simple_list_item_1,
				R.id.progamLineTxtView, mProgramExplanationList, true);

		mProgramListView.setAdapter(mAdapterProgramList);
		mProgramExplanationListView.setAdapter(mAdapterProgramExplanationList);
		mProgramExplanationListView.setRotationY(-90f);
		mListPostion = mProgramListView.getFirstVisiblePosition();

		initButtons();

	}

	private void initButtons() {

		mProgDescriptionBtn = (Button) findViewById(R.id.descriptionBtn);
		mProgDescriptionBtn.setOnClickListener(mButtonClickListener);

		mNextProgramBtn = (ImageButton) findViewById(R.id.nextProgramBtn);
		mNextProgramBtn.setOnClickListener(mButtonClickListener);

		mPrevProgramBtn = (ImageButton) findViewById(R.id.prevProgramBtn);
		mPrevProgramBtn.setOnClickListener(mButtonClickListener);
		enableDisablePrevButton();
		if( mWizard == true ) {
			mPrevProgramBtn.setVisibility(View.GONE);
		}


	}

	OnClickListener mButtonClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch( v.getId() ) {
			case R.id.descriptionBtn :
				programDescriptionAction();
				break;

			case R.id.prevProgramBtn :
				prevProgramBtnAction();
				break;

			case R.id.nextProgramBtn :
				if( mWizard == true ) {
					showSelectBox();
				}
				else {
					nextProgramBtnAction();	
				}
				break;

			}

		}




	};


	private void programDescriptionAction() {

		if(mProgDescriptionBtn.getText() == "Flip") { 
			mProgDescriptionBtn.setText("Flip");	
		}
		else { 
			mProgDescriptionBtn.setText("Flip");
		}
		flipit();

	}

	protected void nextProgramBtnAction() {
		mProgram_Index = mProgram_Index + 1;
		enableDisablePrevButton();
		NextProgram(mProgram_Index);
	}

	protected void prevProgramBtnAction() {
		mProgram_Index = mProgram_Index - 1;
		enableDisablePrevButton();
		NextProgram(mProgram_Index);	
	}

	@SuppressWarnings("unused")
	private void populateProgramTable(int program_Index) {
		List<Program_Table> program_TableList = mDatabaseHandler.getAllProgram_Tables(mProgram_Index, new CreekPreferences(this).getProgramLanguage());


		Iterator<Program_Table> iteraor = program_TableList.iterator();
		while(iteraor.hasNext()) { 
			Program_Table newProgram_Table = iteraor.next();
			mProgramList.add(newProgram_Table.getLine_No()+". "+newProgram_Table.getProgram_Line());
			mProgramExplanationList.add(newProgram_Table.getLine_No()+". "+newProgram_Table.getProgram_Line_Description());

		}


	}

	public String getProgramTitle(int program_Index) {
		if( mDatabaseHandler == null ) {
			mDatabaseHandler = new DatabaseHandler(ProgramActivity.this);
		}
		return AuxilaryUtils.getProgramTitle(program_Index, ProgramActivity.this, mDatabaseHandler );
	}

	public void enableDisablePrevButton() { 
		if( mProgram_Index == 1 || mProgram_Index < 1) { 
			mPrevProgramBtn.setEnabled(false);

		}
		else {
			mPrevProgramBtn.setEnabled(true);
		}

	}

	protected void NextProgram(int program_Index) {
		/**
		 * Reset the list and reset adapter to change view
		 * */
		if( program_Index > 0 && program_Index <= ProgramListActivity.PROGRAM_LIST_SIZE ) {

			List<Program_Table> program_TableList = mDatabaseHandler.getAllProgram_Tables(program_Index, new CreekPreferences(this).getProgramLanguage());
			if( program_TableList == null || program_TableList.size() == 0 && mProgram_Index <= ProgramListActivity.PROGRAM_LIST_SIZE ) {

				new DataBaseInsertAsyncTask( this, mProgram_Index, this ).execute();
				program_TableList = mDatabaseHandler.getAllProgram_Tables(mProgram_Index, new CreekPreferences(this).getProgramLanguage());

			}
			else {
				/*if( mProgDescriptionBtn.getText().equals("Flip")) {
					flipit();
					mProgDescriptionBtn.setText("Flip");
				}*/
				mAdapterProgramExplanationList.clear();
				mAdapterProgramList.clear();
				mListPostion = 1;
			}
			mProgramList = new ArrayList<String>();
			mProgramExplanationList = new ArrayList<String>();
			mProgram_Title = getProgramTitle(mProgram_Index);

			if( mProgram_Title == null ) {
				AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", ProgramActivity.this);
			}
			else {
				setTitle("Revise : "+mProgram_Title);
				Iterator<Program_Table> iteraor = program_TableList.iterator();

				while(iteraor.hasNext()) { 
					Program_Table newProgram_Table = iteraor.next();
					mProgramList.add(newProgram_Table.getLine_No()+". "+newProgram_Table.getProgram_Line());
					mProgramExplanationList.add(newProgram_Table.getLine_No()+". "+newProgram_Table.getProgram_Line_Description());

				}


			}

			// Prepare the ListView
			mAdapterProgramList.addAll(mProgramList);
			// Prepare the ListView
			mAdapterProgramExplanationList.addAll(mProgramExplanationList);

			mAdapterProgramList.setNotifyOnChange(true);
			mAdapterProgramExplanationList.setNotifyOnChange(true);

		}
		if( program_Index > ProgramListActivity.PROGRAM_LIST_SIZE ) {
			AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", ProgramActivity.this);
			mProgram_Index--;
		}

	}

	private Interpolator accelerator = new AccelerateInterpolator();
	private Interpolator decelerator = new DecelerateInterpolator();

	private void flipit() {

		final ListView visibleList;
		final ListView invisibleList;
		if (mProgramListView.getVisibility() == View.GONE) {
			mListPostion = mProgramExplanationListView.getFirstVisiblePosition();
			visibleList = mProgramExplanationListView;
			invisibleList = mProgramListView;
		} else {
			mListPostion = mProgramListView.getFirstVisiblePosition();
			invisibleList = mProgramExplanationListView;
			visibleList = mProgramListView;
		}
		ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationY", 0f, 90f);
		visToInvis.setDuration(500);
		visToInvis.setInterpolator(accelerator);
		final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationY",
				-90f, 0f);
		invisToVis.setDuration(500);
		invisToVis.setInterpolator(decelerator);
		visToInvis.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator anim) {
				visibleList.setVisibility(View.GONE);
				invisToVis.start();
				invisibleList.setVisibility(View.VISIBLE);
			}
		});
		visToInvis.start();
		//TODO TO retain postion
		visibleList.setSelection(mListPostion);
		invisibleList.setSelection(mListPostion);
	}

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
			Intent newIntent = new Intent(ProgramActivity.this, QuizActivity.class);
			newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true);

			switch ( which ) {

			case KEY_QUIZ_DESCRIPTION_QUESTION :
				newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mProgram_Index);
				newIntentBundle.putInt(KEY_QUIZ_TYPE, KEY_QUIZ_DESCRIPTION_QUESTION);

				break;

			case KEY_QUIZ_PROGRAM_CODE_QUESTION :
				newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mProgram_Index);
				newIntentBundle.putInt(KEY_QUIZ_TYPE, KEY_QUIZ_PROGRAM_CODE_QUESTION);
				break;

			}

			newIntent.putExtras(newIntentBundle);
			startActivity(newIntent);
			ProgramActivity.this.finish();

		}
	};


	@Override
	public void updateUI() {

		mProgram_TableList = mDatabaseHandler.getAllProgram_Tables(mProgram_Index, new CreekPreferences(this).getProgramLanguage());
		if( mProgram_TableList == null || mProgram_TableList.size() == 0 ) { 
			AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", this);
			mProgram_Index--;
		}
		else {
			initUI( mProgram_TableList );	
		}

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
			new DataBaseInsertAsyncTask(this, mProgram_Index, this ).execute();
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}

	}


}

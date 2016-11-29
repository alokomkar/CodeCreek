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
import android.widget.ListView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CustomProgramLineListAdapter;
import com.sortedqueue.programmercreek.asynctask.ProgramFetcherTask;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.database.operations.DataBaseInserterAsyncTask;
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MemorizeProgramActivity extends AppCompatActivity implements UIUpdateListener {

	
	CustomProgramLineListAdapter mAdapterProgramList;
	CustomProgramLineListAdapter mAdapterProgramExplanationList;

	ListView mProgramListView;
	ListView mProgramExplanationListView;
	ArrayList<String> mProgramList;
	ArrayList<String> mProgramExplanationList;
	DatabaseHandler mDatabaseHandler;
	int mProgram_Index;
	Button mProgDescriptionBtn;
	ArrayList<String> mLinebylineprogramList = new ArrayList<String>();
	ArrayList<String> mLinebylineprogramExplanationList = new ArrayList<String>();;
	int mIndex = 0;
	int mProgramLength = 0;

	Button mPrevProgramBtn;
	Button mNextProgramBtn;
	Button mShowOrHideProgramBtn;
	boolean mWizard = false;

	public String KEY_PROG_TABLE_INSERT = "insertProgramTable";
	List<Program_Table> mProgram_TableList;
	String mProgram_Title = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revise_program);

		Bundle newProgramActivityBundle = getIntent().getExtras();
		mProgram_Index = newProgramActivityBundle.getInt(ProgrammingBuddyConstants.KEY_PROG_ID);
		mWizard = newProgramActivityBundle.getBoolean(ProgramListActivity.KEY_WIZARD);
		Log.d("Program Activity", " :: Program_Index :  " +  mProgram_Index+"");
		if( mDatabaseHandler == null ) {
			mDatabaseHandler = new DatabaseHandler(this);
		}
		getProgramTableFromDB(mProgram_Index);
		
	}

	private void getProgramTableFromDB(int program_Index) {
		
		new ProgramFetcherTask(this, new UIProgramFetcherListener() {
			
			@Override
			public void updateUI(List<Program_Table> program_TableList) {
				mProgram_TableList = program_TableList;
				if( mProgram_TableList == null || mProgram_TableList.size() == 0 ) {
					new DataBaseInserterAsyncTask(MemorizeProgramActivity.this, mProgram_Index, MemorizeProgramActivity.this ).execute();
					mProgram_TableList = mDatabaseHandler.getAllProgram_Tables(mProgram_Index);
				}
				else {
					initUI( mProgram_TableList );
				}		
			}
		}, mDatabaseHandler, program_Index).execute();
		
		
	}

	private void initUI(List<Program_Table> program_TableList) {

		if( program_TableList != null && program_TableList.size() > 0 ) {

			mProgram_Title = getProgramTitle(mProgram_Index);
			setTitle("Memorize : "+mProgram_Title);
			mProgramList = new ArrayList<String>();
			mProgramExplanationList = new ArrayList<String>();

			Iterator<Program_Table> iteraor = program_TableList.iterator();
			while(iteraor.hasNext()) { 
				Program_Table newProgram_Table = (Program_Table) iteraor.next();
				mProgramList.add(newProgram_Table.getLine_No()+". "+newProgram_Table.getProgram_Line());
				mProgramExplanationList.add(newProgram_Table.getLine_No()+". "+newProgram_Table.getProgram_Line_Description());

			}

			mProgramLength = mProgramList.size();
			Log.d("Program Length", mProgramLength+"");

			mProgramListView = (ListView) findViewById(R.id.list_program);
			mProgramExplanationListView = (ListView) findViewById(R.id.list_explanation);


			mLinebylineprogramExplanationList.add(mProgramExplanationList.get(mIndex));
			mLinebylineprogramList.add(mProgramList.get(mIndex));
			mIndex++;


			// Prepare the ListView
			mAdapterProgramList = new CustomProgramLineListAdapter(this,
					android.R.layout.simple_list_item_1, R.id.progamLineTxtView, mLinebylineprogramList, false);
			// Prepare the ListView
			mAdapterProgramExplanationList = new CustomProgramLineListAdapter(this,
					android.R.layout.simple_list_item_1, R.id.progamLineTxtView, mLinebylineprogramExplanationList, true);

			mProgramListView.setAdapter(mAdapterProgramList);
			mProgramExplanationListView.setAdapter(mAdapterProgramExplanationList);
			mProgramExplanationListView.setRotationY(-90f);

			initButtons();

		}


	}

	private void initButtons() {

		mProgDescriptionBtn = (Button) findViewById(R.id.descriptionBtn);
		mProgDescriptionBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(mProgDescriptionBtn.getText() == "Program") { 
					mProgDescriptionBtn.setText("Description");	
				}
				else { 
					mProgDescriptionBtn.setText("Program");
				}
				flipit();
			}
		});

		mShowOrHideProgramBtn = (Button) findViewById(R.id.showAllBtn_revise);
		mShowOrHideProgramBtn.setText(">|");
		mShowOrHideProgramBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mAdapterProgramExplanationList.clear();
				mAdapterProgramList.clear();
				Log.d("Index < programlength", mIndex+"<"+mProgramLength);
				if( mLinebylineprogramExplanationList != null ) { 
					mLinebylineprogramExplanationList.clear();
					mLinebylineprogramExplanationList = new ArrayList<String>();
				}
				if( mLinebylineprogramList != null ) { 
					mLinebylineprogramList.clear();
					mLinebylineprogramList = new ArrayList<String>();
				}
				if( mShowOrHideProgramBtn.getText().toString().equals(">|") == true ) {

					for( int i = 0; i < mProgramLength; i++) { 
						mLinebylineprogramExplanationList.add(mProgramExplanationList.get(i));
						mLinebylineprogramList.add(mProgramList.get(i));
					}
					mIndex = mProgramLength;
					// Prepare the ListView
					mAdapterProgramList.addAll(mLinebylineprogramList);
					// Prepare the ListView
					mAdapterProgramExplanationList.addAll(mLinebylineprogramExplanationList);
					mAdapterProgramList.notifyDataSetChanged();
					mAdapterProgramExplanationList.notifyDataSetChanged();

					//To scroll down automatically upon introduction of new line
					mProgramExplanationListView.setSelection((mAdapterProgramExplanationList.getCount() - 1));
					mProgramListView.setSelection((mAdapterProgramList.getCount() - 1));
					mShowOrHideProgramBtn.setText("|<");
				}
				else {
					mIndex = 1;
					mShowOrHideProgramBtn.setText(">|");
				}

			}

		});


		mNextProgramBtn = (Button) findViewById(R.id.nextProgramBtn_revise);
		mNextProgramBtn.setText(">");
		mPrevProgramBtn = (Button) findViewById(R.id.prevProgramBtn_revise);
		mPrevProgramBtn.setText("<");

		enableDisablePrevButton();

		mNextProgramBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mProgram_Index = mProgram_Index + 1;
				enableDisablePrevButton();	
				mLinebylineprogramExplanationList = new ArrayList<String>();
				mLinebylineprogramList = new ArrayList<String>();
				NextProgram(mProgram_Index);
			}
		});

		mPrevProgramBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mProgram_Index = mProgram_Index - 1;
				enableDisablePrevButton();	
				mLinebylineprogramExplanationList = new ArrayList<String>();
				mLinebylineprogramList = new ArrayList<String>();
				NextProgram(mProgram_Index);	
			}
		});


		Button hintProgramButton = (Button) findViewById(R.id.hintProgramBtn);
		hintProgramButton.setText("?");
		hintProgramButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mAdapterProgramExplanationList.clear();
				mAdapterProgramList.clear();
				Log.d("Index < programlength", mIndex+"<"+mProgramLength);
				if( mLinebylineprogramExplanationList != null ) { 
					mLinebylineprogramExplanationList.clear();
					mLinebylineprogramExplanationList = new ArrayList<String>();
				}
				if( mLinebylineprogramList != null ) { 
					mLinebylineprogramList.clear();
					mLinebylineprogramList = new ArrayList<String>();
				}
				mIndex++;
				if( mIndex <= mProgramLength ) { 

					for( int i = 0; i < mIndex; i++) { 
						mLinebylineprogramExplanationList.add(mProgramExplanationList.get(i));
						mLinebylineprogramList.add(mProgramList.get(i));
					}
					// Prepare the ListView
					mAdapterProgramList.addAll(mLinebylineprogramList);
					// Prepare the ListView
					mAdapterProgramExplanationList.addAll(mLinebylineprogramExplanationList);
					mAdapterProgramList.notifyDataSetChanged();
					mAdapterProgramExplanationList.notifyDataSetChanged();

					//To scroll down automatically upon introduction of new line
					mProgramExplanationListView.setSelection((mAdapterProgramExplanationList.getCount() - 1));
					mProgramListView.setSelection((mAdapterProgramList.getCount() - 1));

				}
				else {
					NextProgram(mProgram_Index);
				}
				//To navigate to next program - disable this.
				/**else { 
					NextProgram(mProgram_Index++);
					enableDisablePrevButton();
				}*/
			}
		});

		checkWizardMode();


	}

	private void checkWizardMode() {

		if( mWizard == true ) {

			mPrevProgramBtn.setVisibility(View.GONE);
			mNextProgramBtn.setVisibility(View.GONE);
			mShowOrHideProgramBtn.setText(">>");
			mShowOrHideProgramBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showSelectBox();
				}
			});

		}

	}

	public String getProgramTitle(int program_Index) { 
		if ( mDatabaseHandler == null ) {
			mDatabaseHandler = new DatabaseHandler( this );
		}
		return mDatabaseHandler.getProgram_Index(program_Index).getProgram_Description();
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
			mIndex = 0;
			List<Program_Table> program_TableList = mDatabaseHandler.getAllProgram_Tables(program_Index);
			if( program_TableList == null || program_TableList.size() == 0 ) {
				new DataBaseInserterAsyncTask(this, mProgram_Index, this).execute();
				program_TableList = mDatabaseHandler.getAllProgram_Tables(mProgram_Index);
			}
			if( program_TableList != null && program_TableList.size() > 0 ) { 
				if( mProgDescriptionBtn.getText().equals("Program")) {
					flipit();
					mProgDescriptionBtn.setText("Description");
				}

				mAdapterProgramExplanationList.clear();
				mAdapterProgramList.clear();
				mProgram_Title = getProgramTitle( program_Index );

				if( mProgram_Title == null ) {
					AuxilaryUtils.displayAlert("My Programming Buddy", "You are viewing the last program", MemorizeProgramActivity.this);
				}
				else {
					setTitle("Memorize : "+getProgramTitle(program_Index));

					mProgramList = new ArrayList<String>();
					mProgramExplanationList = new ArrayList<String>();

					Iterator<Program_Table> iteraor = program_TableList.iterator();

					while(iteraor.hasNext()) { 
						Program_Table newProgram_Table = (Program_Table) iteraor.next();
						mProgramList.add(newProgram_Table.getLine_No()+". "+newProgram_Table.getProgram_Line());
						mProgramExplanationList.add(newProgram_Table.getLine_No()+". "+newProgram_Table.getProgram_Line_Description());
					}
					mProgramLength = mProgramList.size();
					Log.d("Program Length", mProgramLength+"");
				}

				mLinebylineprogramExplanationList.add(mProgramExplanationList.get(mIndex));
				mLinebylineprogramList.add(mProgramList.get(mIndex++));

				// Prepare the ListView
				mAdapterProgramList.addAll(mLinebylineprogramList);
				// Prepare the ListView
				mAdapterProgramExplanationList.addAll(mLinebylineprogramExplanationList);

				mAdapterProgramList.setNotifyOnChange(true);
				mAdapterProgramExplanationList.setNotifyOnChange(true);	
			}
		}
		if( program_Index > ProgramListActivity.PROGRAM_LIST_SIZE ) {
			AuxilaryUtils.displayAlert("My Programming Buddy", "You are viewing the last program", MemorizeProgramActivity.this);
			mProgram_Index--;
		}

	}



	private Interpolator accelerator = new AccelerateInterpolator();
	private Interpolator decelerator = new DecelerateInterpolator();

	int mListPostion = 0;

	private void flipit() {
		final ListView visibleList;
		final ListView invisibleList;
		if (mProgramListView.getVisibility() == View.GONE) {
			visibleList = mProgramExplanationListView;
			invisibleList = mProgramListView;
		} else {
			invisibleList = mProgramExplanationListView;
			visibleList = mProgramListView;
		}
		mListPostion = visibleList.getFirstVisiblePosition();
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

	
	/**
	 * Quiz Type Listener 
	 * */
	DialogInterface.OnClickListener quizTypeListener = 
			new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			Bundle newIntentBundle = new Bundle();
			Intent newIntent = new Intent(MemorizeProgramActivity.this, QuizActivity.class);
			newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true);

			switch ( which ) {

			case ProgrammingBuddyConstants.KEY_QUIZ_DESCRIPTION_QUESTION :
				newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mProgram_Index);
				newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_QUIZ_TYPE, ProgrammingBuddyConstants.KEY_QUIZ_DESCRIPTION_QUESTION);
				break;

			case ProgrammingBuddyConstants.KEY_QUIZ_PROGRAM_CODE_QUESTION :
				newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mProgram_Index);
				newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_QUIZ_TYPE, ProgrammingBuddyConstants.KEY_QUIZ_PROGRAM_CODE_QUESTION);
				break;

			}

			newIntent.putExtras(newIntentBundle);
			startActivity(newIntent);
			MemorizeProgramActivity.this.finish();

		}
	};

	@Override
	public void updateUI() {

		mProgram_TableList = mDatabaseHandler.getAllProgram_Tables(mProgram_Index);
		if( mProgram_TableList == null || mProgram_TableList.size() == 0 ) { 
			AuxilaryUtils.displayAlert("My Programming Buddy", "You are viewing the last program", this);
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
			new DataBaseInserterAsyncTask(this, mProgram_Index, this).execute();
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}

	}




}

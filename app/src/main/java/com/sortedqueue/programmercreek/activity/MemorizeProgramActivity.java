package com.sortedqueue.programmercreek.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
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
	ProgramIndex mProgramIndex;
	int programIndex;
	Button mProgDescriptionBtn;
	ArrayList<String> mLinebylineprogramList = new ArrayList<String>();
	ArrayList<String> mLinebylineprogramExplanationList = new ArrayList<String>();
	int mIndex = 0;
	int mProgramLength = 0;

	ImageButton mPrevProgramBtn;
	ImageButton mNextProgramBtn;
	ImageButton mShowOrHideProgramBtn;
	boolean mWizard = false;

	public String KEY_PROG_TABLE_INSERT = "insertProgramTable";
	ArrayList<ProgramTable> mProgramTableList;
	String mProgram_Title = null;
	private Drawable mShowAllDrawable;
	private Drawable mHideDrawable;
	private int mTotalPrograms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revise_program);

		Bundle newProgramActivityBundle = getIntent().getExtras();
		mProgramIndex = (ProgramIndex) newProgramActivityBundle.get(ProgrammingBuddyConstants.KEY_PROG_ID);
        programIndex = mProgramIndex.getIndex();
		mTotalPrograms = newProgramActivityBundle.getInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS);
		mWizard = newProgramActivityBundle.getBoolean(ProgramListActivity.KEY_WIZARD);
		mShowAllDrawable = ContextCompat.getDrawable(this, R.drawable.ic_show_all);
		mHideDrawable = ContextCompat.getDrawable(this, R.drawable.ic_remove);
		Log.d("Program Activity", " :: ProgramIndex :  " +  mProgramIndex+"");
		getProgramTableFromDB(programIndex);
		this.overridePendingTransition(R.anim.anim_slide_in_left,
				R.anim.anim_slide_out_left);
		
	}

	private void getProgramTableFromDB(final int program_Index) {
		
		new ProgramFetcherTask(this, new UIProgramFetcherListener() {
			
			@Override
			public void updateUI(ArrayList<ProgramTable> program_TableList) {
				mProgramTableList = program_TableList;
				{
					initUI( mProgramTableList );
				}		
			}
		}, program_Index).execute();
		
		
	}

	private void initUI(List<ProgramTable> program_TableList) {

		if( program_TableList != null && program_TableList.size() > 0 ) {

			mProgram_Title = mProgramIndex.getProgram_Description();
			setTitle("Memorize : "+ mProgram_Title);
			mProgramList = new ArrayList<String>();
			mProgramExplanationList = new ArrayList<String>();

			Iterator<ProgramTable> iteraor = program_TableList.iterator();
			while(iteraor.hasNext()) { 
				ProgramTable newProgramTable = iteraor.next();
				mProgramList.add(newProgramTable.getLine_No()+". "+newProgramTable.getProgram_Line());
				mProgramExplanationList.add(newProgramTable.getLine_No()+". "+newProgramTable.getProgram_Line_Description());

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
				if(mProgDescriptionBtn.getText() == "Flip") { 
					mProgDescriptionBtn.setText("Flip");	
				}
				else { 
					mProgDescriptionBtn.setText("Flip");
				}
				flipit();
			}
		});

		mShowOrHideProgramBtn = (ImageButton) findViewById(R.id.showAllBtn_revise);
		mShowOrHideProgramBtn.setImageDrawable(mShowAllDrawable);
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
				if( mShowOrHideProgramBtn.getDrawable().equals(mShowAllDrawable) == true ) {

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
					mShowOrHideProgramBtn.setImageDrawable(mHideDrawable);
				}
				else {
					mIndex = 1;
					mShowOrHideProgramBtn.setImageDrawable(mShowAllDrawable);
				}

			}

		});


		mNextProgramBtn = (ImageButton) findViewById(R.id.nextProgramBtn_revise);
		mPrevProgramBtn = (ImageButton) findViewById(R.id.prevProgramBtn_revise);

		enableDisablePrevButton();

		mNextProgramBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				programIndex = programIndex + 1;
				enableDisablePrevButton();	
				mLinebylineprogramExplanationList = new ArrayList<String>();
				mLinebylineprogramList = new ArrayList<String>();
				NextProgram(programIndex);
			}
		});

		mPrevProgramBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				programIndex = programIndex - 1;
				enableDisablePrevButton();	
				mLinebylineprogramExplanationList = new ArrayList<String>();
				mLinebylineprogramList = new ArrayList<String>();
				NextProgram(programIndex);
			}
		});


		ImageButton hintProgramButton = (ImageButton) findViewById(R.id.hintProgramBtn);
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
					NextProgram(programIndex);
				}
				//To navigate to next program - disable this.
				/**else { 
					NextProgram(mProgramIndex++);
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
			mShowOrHideProgramBtn.setImageDrawable(mShowAllDrawable);
			mShowOrHideProgramBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showSelectBox();
				}
			});

		}

	}

	public String getProgramTitle(int program_Index) {
		return AuxilaryUtils.getProgramTitle(program_Index, MemorizeProgramActivity.this );
	}

	public void enableDisablePrevButton() { 
		if( programIndex == 1 || programIndex < 1) {
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
		if( program_Index > 0 && program_Index <= mTotalPrograms ) {
			mIndex = 0;
			ArrayList<ProgramTable> program_TableList = new FirebaseDatabaseHandler(MemorizeProgramActivity.this).getProgramTables(program_Index);

			if( program_TableList != null && program_TableList.size() > 0 ) {
				/*if( mProgDescriptionBtn.getText().equals("Flip")) {
					flipit();
					mProgDescriptionBtn.setText("Flip");
				}*/

				mAdapterProgramExplanationList.clear();
				mAdapterProgramList.clear();
				mProgram_Title = getProgramTitle( program_Index );

				if( mProgram_Title == null ) {
					setTitle("Memorize");
					AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", MemorizeProgramActivity.this);
				}
				else {
					setTitle("Memorize : "+getProgramTitle(program_Index));

					mProgramList = new ArrayList<String>();
					mProgramExplanationList = new ArrayList<String>();

					Iterator<ProgramTable> iteraor = program_TableList.iterator();

					while(iteraor.hasNext()) { 
						ProgramTable newProgramTable = iteraor.next();
						mProgramList.add(newProgramTable.getLine_No()+". "+newProgramTable.getProgram_Line());
						mProgramExplanationList.add(newProgramTable.getLine_No()+". "+newProgramTable.getProgram_Line_Description());
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
		if( program_Index > mTotalPrograms ) {
			AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", MemorizeProgramActivity.this);
			programIndex--;
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
			Intent newIntent = new Intent(MemorizeProgramActivity.this, WizardActivity.class);
            newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_QUIZ);
			newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true);

			switch ( which ) {

			case ProgrammingBuddyConstants.KEY_QUIZ_DESCRIPTION_QUESTION :
				newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, mProgramIndex);
				newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_QUIZ_TYPE, ProgrammingBuddyConstants.KEY_QUIZ_DESCRIPTION_QUESTION);
				break;

			case ProgrammingBuddyConstants.KEY_QUIZ_PROGRAM_CODE_QUESTION :
				newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, mProgramIndex);
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
		mProgramTableList = new FirebaseDatabaseHandler(MemorizeProgramActivity.this).getProgramTables(programIndex);;
		if( mProgramTableList == null || mProgramTableList.size() == 0 ) { 
			AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", this);
			programIndex--;
		}
		else {
			initUI( mProgramTableList );	
		}

	}
	
	@Override
	public void finish() {
		super.finish();
		this.overridePendingTransition(R.anim.anim_slide_in_right,
				R.anim.anim_slide_out_right);
	}

	@Override
	public void onBackPressed() {
		finish();
	}


}

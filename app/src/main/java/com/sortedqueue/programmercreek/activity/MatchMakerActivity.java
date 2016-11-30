package com.sortedqueue.programmercreek.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.database.operations.DataBaseInserterAsyncTask;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.PrettifyHighlighter;
import com.sortedqueue.programmercreek.util.ShuffleList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MatchMakerActivity extends Activity implements UIUpdateListener {

	private LinearLayout mMatchMakerLeftLinearLayout = null;
	private LinearLayout mMatchMakerRightLinearLayout = null;
	ArrayList<String> mProgramList;
	ArrayList<String> mProgramCheckList;
	ArrayList<String> mShuffleProgramList;
	ArrayList<String> mProgramExplanationList;
	Button mCheckSolutionBtn = null;
	Button mTimerButton = null;
	int mProgram_Index = 0;
	DatabaseHandler mDatabaseHandler = null;
	View mSelectedProgramLineView = null;
	PrettifyHighlighter mHighlighter = new PrettifyHighlighter();
	long remainingTime = 0;
	long time = 0;
	long interval = 0;
	TextView[] mSummaryTextViewList;
	TextView[] mProgramLineTextViewList;
	CountDownTimer mCountDownTimer;
	boolean mWizard = false;
	int mProgramSize;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_maker);

		Bundle newProgramActivityBundle = getIntent().getExtras();
		mProgram_Index = newProgramActivityBundle.getInt(ProgrammingBuddyConstants.KEY_PROG_ID);
		mWizard = newProgramActivityBundle.getBoolean(ProgramListActivity.KEY_WIZARD);
		
		if( mDatabaseHandler == null ) {
			mDatabaseHandler = new DatabaseHandler(this);
		}
		List<Program_Table> program_TableList =  mDatabaseHandler.getAllProgram_Tables( mProgram_Index );
		if( program_TableList == null || program_TableList.size() == 0 ) {
			new DataBaseInserterAsyncTask(this, mProgram_Index, this).execute();
		}
		else {
			initUI( program_TableList );	
		}
	}

	private void initUI( List<Program_Table> program_TableList ) {
		
		if( program_TableList != null && program_TableList.size() > 0 ) {

			setTitle("Match : " + AuxilaryUtils.getProgramTitle(mProgram_Index, MatchMakerActivity.this, mDatabaseHandler));

			mProgramList = new ArrayList<String>();
			mProgramExplanationList = new ArrayList<String>();
			mProgramCheckList = new ArrayList<String>();

			Program_Table newProgram_Table;
			String programLine_html;
			Iterator<Program_Table> iteraor = program_TableList.iterator();
			while(iteraor.hasNext()) { 
				newProgram_Table = iteraor.next();
				programLine_html = newProgram_Table.getmProgram_Line_Html();
				if( newProgram_Table.getProgram_Line().contains("<")) {
					programLine_html = newProgram_Table.getProgram_Line().trim();
				}
				mProgramList.add(programLine_html);
				mProgramCheckList.add(newProgram_Table.getProgram_Line());
				mProgramExplanationList.add(newProgram_Table.getProgram_Line_Description());
			}
		}

		mShuffleProgramList = new ArrayList<String>();
		mShuffleProgramList.addAll(mProgramList);
		mShuffleProgramList = ShuffleList.shuffleList(mShuffleProgramList);

		mMatchMakerLeftLinearLayout = (LinearLayout) findViewById(R.id.leftLinearLyt);
		
		if( mMatchMakerLeftLinearLayout != null ) {
			mMatchMakerLeftLinearLayout.removeAllViews();
		}
		
		mProgramSize = mShuffleProgramList.size();
		mProgramLineTextViewList = new TextView[mProgramSize];
		String programLine = null;
		for( int i = 0; i < mProgramSize; i++ ) {
			mProgramLineTextViewList[i] = new TextView(this);
			setProgramLineTextViewParms(mProgramLineTextViewList[i]);
			mMatchMakerLeftLinearLayout.addView(mProgramLineTextViewList[i]);
			programLine = mShuffleProgramList.get(i);
			if( programLine.contains("font") == false ) {
				mProgramLineTextViewList[i].setText(programLine);
				mProgramLineTextViewList[i].setTextColor(Color.parseColor("#006699"));
			}
			else {
				mProgramLineTextViewList[i].setText(Html.fromHtml(/*mHighlighter.highlight("c", programLine)*/programLine));
			}
			mProgramLineTextViewList[i].setId(i);
		}

		mMatchMakerRightLinearLayout = (LinearLayout) findViewById(R.id.rightLinearLyt);
		//mMatchMakerRightLinearLayout.setLayoutParams(layoutParams);
		if( mMatchMakerRightLinearLayout != null ) {
			mMatchMakerRightLinearLayout.removeAllViews();
		}

		mSummaryTextViewList = new TextView[mProgramSize];
		for( int i = 0; i < mProgramSize; i++ ) {
			mSummaryTextViewList[i] = new TextView(this);
			setSummaryTextViewParms(mSummaryTextViewList[i]);
			mMatchMakerRightLinearLayout.addView(mSummaryTextViewList[i]);
			mSummaryTextViewList[i].setText(mProgramExplanationList.get(i));
			mProgramLineTextViewList[i].setId(mProgramSize+i);
		}

		mCheckSolutionBtn = (Button) findViewById(R.id.checkMatchButton);
		mTimerButton = (Button) findViewById(R.id.timerMatchButton);
		mTimerButton.setText("00:00");
		mTimerButton.setEnabled(false);
		
		time = (mProgramSize / 2) * 60 * 1000;
		interval = 1000;
		
		if( mCountDownTimer != null ) {
			mCountDownTimer.cancel();
			mCheckSolutionBtn.setEnabled(true);
		}

		mCountDownTimer = new CountDownTimer( time, interval) {

			@Override
			public void onTick(long millisUntilFinished) {
				mTimerButton.setText(""+String.format("%d:%d",
						TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
						TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - 
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
				remainingTime = time - millisUntilFinished;
			}


			@Override
			public void onFinish() {

				mTimerButton.setText("Time up");
				if( mWizard == true ) {
					mTimerButton.setText("Next");
					mTimerButton.setEnabled(true);
					mTimerButton.setOnClickListener(mNextBtnClickListener);
				}
			
				checkScore(mProgramSize);
			}
		};
		
		
		mCheckSolutionBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showConfirmSubmitDialog( mProgramSize, mCountDownTimer );
			}
		});

		mCountDownTimer.start();

		
	}

	OnClickListener mNextBtnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			switch( v.getId() ) {
			case R.id.timerMatchButton : 
				navigateToTest();
				break;
			}
			
		}
	};

	private void showConfirmSubmitDialog( final int programSize, final CountDownTimer countDownTimer ) {
		Builder builder = new Builder(this);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				checkScore(programSize);
				countDownTimer.cancel();
			}

		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		builder.setMessage("Are you sure you want to submit the Match?");
		builder.setTitle(this.getTitle());
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.show();

	}


	protected void navigateToTest() {
	
		Bundle newIntentBundle = new Bundle();
		newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mProgram_Index);
		newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true);
		Intent intent = new Intent( this, TestDragNDropActivity.class);
		intent.putExtras(newIntentBundle);
		startActivity(intent);
		this.finish();
		
	}


	protected void checkScore( int programSize ) {

		int CheckSolution = 0;
		int matchScore = 0;

		for( int i = 0; i < programSize; i++ ) {
			//Log.d("SOLUTION", mSummaryTextViewList[i].getText().toString().trim());
			if( mProgramCheckList.get(i).trim().equals(mSummaryTextViewList[i].getText().toString().trim()) == true ) {
				CheckSolution++;
				matchScore++;
				mSummaryTextViewList[i].setBackgroundResource(R.drawable.answer);
			}
			else {
				mSummaryTextViewList[i].setBackgroundResource(R.drawable.error);
				String programLine = mSummaryTextViewList[i].getText().toString();
				for( int j = 0; j < programSize; j++ ) {
					if( programLine.equals(mProgramLineTextViewList[j].getText().toString()) == true ) {
						mProgramLineTextViewList[j].setBackgroundResource(R.drawable.option);
					}
				}
				mSummaryTextViewList[i].setText(mProgramExplanationList.get(i));
				CheckSolution--;
			}
		}
		if( CheckSolution == programSize ) {
			if( quizComplete == false ) {
				AuxilaryUtils.displayAlert("Match Complete", "Congratulations.. Your Score is "+ CheckSolution +"/"+programSize+" in "+String.format("%d min, %d sec", 
						TimeUnit.MILLISECONDS.toMinutes(remainingTime),
						TimeUnit.MILLISECONDS.toSeconds(remainingTime) - 
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime)))+", Fantastic Work..!!", MatchMakerActivity.this);
				mCheckSolutionBtn.setEnabled(false);
			}
			else {
				AuxilaryUtils.displayAlert("Match Complete", "Congratulations.. Your Score is "+ CheckSolution +"/"+programSize+", Fantastic Work..!!", MatchMakerActivity.this);
				mCheckSolutionBtn.setEnabled(false);
			}
			
		}
		else {
			//Toast.makeText(MatchMakerActivity.this, "Please check the program again...", Toast.LENGTH_SHORT).show();
			String message = null;

			if( quizComplete == false ) {
				switch( matchScore ) {

				case 0 :
					message = "Your Score is "+ matchScore +"/"+programSize+" in "+String.format("%d min, %d sec", 
							TimeUnit.MILLISECONDS.toMinutes(remainingTime),
							TimeUnit.MILLISECONDS.toSeconds(remainingTime) - 
							TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime)))+", Good Luck next time";
					break;

				default :
					message = "Your Score is "+ matchScore +"/"+programSize+" in "+String.format("%d min, %d sec", 
							TimeUnit.MILLISECONDS.toMinutes(remainingTime),
							TimeUnit.MILLISECONDS.toSeconds(remainingTime) - 
							TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime)))+", Keep Working..";
					break;

				}

			}
			else {
				switch( matchScore ) {

				case 0 :
					message = "Your Score is "+ matchScore +"/"+programSize+", Good Luck next time";
					break;

				default :
					message = "Your Score is "+ matchScore +"/"+programSize+", Keep Working..";
					break;

				}
			}
			AuxilaryUtils.displayAlert("Match Complete", message, MatchMakerActivity.this);
		}
		if( mWizard == true ) {
			mTimerButton.setText("Next");
			mTimerButton.setEnabled(true);
			mTimerButton.setOnClickListener(mNextBtnClickListener);
		}
		quizComplete = true;

	}

	private void setSummaryTextViewParms(TextView textView) {
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(2, 2, 2, 2);
		textView.setLayoutParams(layoutParams);
		textView.setBackgroundResource(R.drawable.choice);
		textView.setGravity(Gravity.CENTER);
		textView.setOnDragListener(new ChoiceDragListener());
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		textView.setOnClickListener(mSummaryTextViewOnClickListener);
		textView.setOnLongClickListener(mSummaryTextViewLongClickListener);
		
	}
	
	//On Long Press Clear the program line in summary text view
	OnLongClickListener mSummaryTextViewLongClickListener = new OnLongClickListener() {
		
		@Override
		public boolean onLongClick(View v) {
			
			/*TextView textView = (TextView) v;
			int lineNo = textView.getId() - mProgramSize;
			if( lineNo >= 0 && lineNo <= mProgramExplanationList.size())
			textView.setText(mProgramExplanationList.get(lineNo));*/
			// TODO Auto-generated method stub
			return false;
		}
	};

	OnClickListener mSummaryTextViewOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if( mSelectedProgramLineView != null ) {
				TextView summaryTextView = (TextView) v;
				summaryTextView.setText(((TextView) mSelectedProgramLineView).getText());
				mSelectedProgramLineView.setOnTouchListener(null);
			}
		}
	}; 

	private void setProgramLineTextViewParms(TextView textView) {
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(2, 2, 2, 2);
		textView.setLayoutParams(layoutParams);
		textView.setBackgroundResource(R.drawable.option);
		textView.setGravity(Gravity.CENTER);
		textView.setOnLongClickListener(mProgramLineLongClickListener);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		textView.setTypeface(Typeface.DEFAULT_BOLD);
	}



	OnLongClickListener mProgramLineLongClickListener = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View view) {

			view.setOnTouchListener(new ChoiceTouchListener());
			view.setBackgroundResource(R.drawable.selected);
			mSelectedProgramLineView = view;
			//Toast.makeText(MatchMakerActivity.this, "Selected", Toast.LENGTH_SHORT).show();

			//To start drag immediately after a view has been selected.
			ClipData data = ClipData.newPlainText("", "");
			DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
			view.startDrag(data, shadowBuilder, view, 0);

			return false;
		}
	};

	/**
	 * ChoiceTouchListener will handle touch events on draggable views
	 *
	 */
	private final class ChoiceTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				/*
				 * Drag details: we only need default behavior
				 * - clip data could be set to pass data as part of drag
				 * - shadow can be tailored
				 */
				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
				//start dragging the item touched
				view.startDrag(data, shadowBuilder, view, 0);
				return true;
			} else {
				return false;
			}
		}
	} 

	/**
	 * DragListener will handle dragged views being dropped on the drop area
	 * - only the drop action will have processing added to it as we are not
	 * - amending the default behavior for other parts of the drag process
	 *
	 */
	private class ChoiceDragListener implements OnDragListener {

		@Override
		public boolean onDrag(View v, DragEvent event) {
			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				//no action necessary
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				//no action necessary
				break;
			case DragEvent.ACTION_DRAG_EXITED:        
				//no action necessary
				break;
			case DragEvent.ACTION_DROP:
				//handle the dragged view being dropped over a drop view
				View view = (View) event.getLocalState();
				//stop displaying the view where it was before it was dragged
				//view.setVisibility(View.INVISIBLE);
				//view dragged item is being dropped on
				TextView dropTarget = (TextView) v;
				//view being dragged and dropped
				TextView dropped = (TextView) view;
				dropped.setOnTouchListener(null);
				mSelectedProgramLineView = null;
				//update the text in the target view to reflect the data being dropped
				dropTarget.setText(dropped.getText());
				//make it bold to highlight the fact that an item has been dropped
				dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
				//if an item has already been dropped here, there will be a tag
				Object tag = dropTarget.getTag();
				//if there is already an item here, set it back visible in its original place
				if(tag!=null)
				{
					//the tag is the view id already dropped here
					int existingID = (Integer)tag;
					//set the original view visible again
					findViewById(existingID).setVisibility(View.VISIBLE);
				}
				//set the tag in the target view being dropped on - to the ID of the view being dropped
				dropTarget.setTag(dropped.getId());
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				//no action necessary
				break;
			default:
				break;
			}
			return true;
		}
	} 

	boolean quizComplete = false;

	@Override
	public void onBackPressed(){
		if( quizComplete == false ) {
			AuxilaryUtils.showConfirmationDialog(this);	
			if( mCountDownTimer != null ) {
				mCountDownTimer.cancel();
			}
		}
		else {
			finish();
		}

	}


	@Override
	public void updateUI() {
		List<Program_Table> program_TableList =  mDatabaseHandler.getAllProgram_Tables( mProgram_Index );
		int prevProgramSize = 0;
		prevProgramSize = program_TableList.size();
		do {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			program_TableList = mDatabaseHandler.getAllProgram_Tables( mProgram_Index );
			if( prevProgramSize == program_TableList.size() ) {
				break;
			}
		} while( true );
		
		if( program_TableList != null ) {
			initUI( program_TableList );
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
			new DataBaseInserterAsyncTask(this,  mProgram_Index, this ).execute();
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}

	}


}

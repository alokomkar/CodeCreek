package com.sortedqueue.programmercreek.activity;

import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.DragNDropAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.database.operations.DataBaseInserterAsyncTask;
import com.sortedqueue.programmercreek.interfaces.DragListenerInterface;
import com.sortedqueue.programmercreek.interfaces.DropListenerInterface;
import com.sortedqueue.programmercreek.interfaces.RemoveListenerInterface;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.ShuffleList;
import com.sortedqueue.programmercreek.view.DragNDropListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestDragNDropActivity extends ListActivity implements UIUpdateListener {

	DatabaseHandler mDatabaseHandler;
	int mProgram_Index;
	ArrayList<String> mRandomTest;
	ArrayList<String> mProgramList;
	ArrayList<String> mProgramCheckList;
	ListView mListView;
	Button mCheckButton;
	Button mTimerButton;
	long remainingTime = 0;
	long time = 0;
	long interval = 0;
	CountDownTimer mCountDownTimer;
	boolean mWizard = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_drag_n_drop);

		mDatabaseHandler = new DatabaseHandler(this);
		
		mProgram_Index = getIntent().getExtras().getInt(ProgrammingBuddyConstants.KEY_PROG_ID);
		mWizard = getIntent().getExtras().getBoolean(ProgramListActivity.KEY_WIZARD);
		List<Program_Table> program_TableList = mDatabaseHandler.getAllProgram_Tables(mProgram_Index);
		if( program_TableList == null || program_TableList.size() == 0 ) {
			new DataBaseInserterAsyncTask(this, mProgram_Index, this).execute();
		}
		else {
			initUI( program_TableList );
		}
		
		
	}

	private void initUI(List<Program_Table> program_TableList) {
		
		setTitle( "Test : " + AuxilaryUtils.getProgramTitle(mProgram_Index, this, mDatabaseHandler));
		
		mProgramList = new ArrayList<String>();
		mProgramCheckList = new ArrayList<String>();
		String programLine = null;
		Iterator<Program_Table> iteraor = program_TableList.iterator();
		while(iteraor.hasNext()) { 

			Program_Table newProgram_Table = iteraor.next();
			programLine = newProgram_Table.getProgram_Line();
			mProgramCheckList.add(programLine);
			mProgramList.add(programLine);
			/*if( programLine.contains("for") ) {
				mProgramList.add(programLine);
			}
			else {
				mProgramList.add(newProgram_Table.getmProgram_Line_Html());	
			}*/
		}

		mRandomTest = new ArrayList<String>(mProgramList.size());
		for( String item : mProgramList ) { 
			mRandomTest.add(item);
		}

		mRandomTest = ShuffleList.shuffleList(mRandomTest);
		final int programSize = mRandomTest.size();
		setListAdapter(new DragNDropAdapter(this, new int[]{R.layout.dragitem}, new int[]{R.id.programLineTextView}, mRandomTest));//new DragNDropAdapter(this,content)
		mListView = getListView();
		//mListView.setBackgroundResource(R.drawable.error);

		if (mListView instanceof DragNDropListView) {
			((DragNDropListView) mListView).setDropListener(mDropListener);
			((DragNDropListView) mListView).setRemoveListener(mRemoveListener);
			((DragNDropListView) mListView).setDragListener(mDragListener);
		}

		mCheckButton = (Button) findViewById(R.id.checkBtn);
		mTimerButton = (Button) findViewById(R.id.timerTestButton);
		mTimerButton.setEnabled(false);

		time = ( programSize / 2) * 60 * 1000;
		interval = 1000;
		
		if( mCountDownTimer != null ) {
			mCountDownTimer.cancel();
			mCheckButton.setEnabled(true);
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
					mTimerButton.setText("Finish");
					mTimerButton.setEnabled(true);
					mTimerButton.setOnClickListener(mFinishBtnClickListener);
				}

				checkScore( programSize );
			}
		};

		mCheckButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showConfirmSubmitDialog( programSize, mCountDownTimer );
			}
		});

		mCountDownTimer.start();

		
		
	}

	OnClickListener mFinishBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch( v.getId() ) {
			case R.id.timerTestButton : 
				TestDragNDropActivity.this.finish();
				break;
			}

		}
	};


	int mProgramHint = 0;
	protected void checkScore(int programLength) {

		//TextView textView;
		//LinearLayout linearLayout;
		int programCheck = 0;
		for( int i = 0; i < programLength; i++ ) { 

			String item = mListView.getAdapter().getItem(i).toString().trim();
			String actualItem = mProgramList.get(i).toString().trim();
			//linearLayout = (LinearLayout) getViewByPosition(i, mListView);
			//textView = (TextView) linearLayout.getChildAt(1);

			if( actualItem.equals(item) == true )
			{ 
				//textView.setTextColor(Color.BLUE);
				//linearLayout.setBackgroundResource(R.drawable.answer);
				programCheck++;
			}
			else {

				//linearLayout.setBackgroundResource(R.drawable.error);
				programCheck--;
			}
		}
		if( programCheck < programLength ) {
			displayToast("Please check the program Again");
			mProgramHint++;
			return;
		}
		if( programCheck == programLength ) {
			String score = null;
			String resultAlert = "";
			int maxScore = programLength / 2;

			if( mProgramHint >= maxScore ) {
				score = "0 %";
				resultAlert = "Your score is "+ score +" in "+String.format("%d min, %d sec", 
						TimeUnit.MILLISECONDS.toMinutes(remainingTime),
						TimeUnit.MILLISECONDS.toSeconds(remainingTime) - 
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime)))+", Good Luck next time..";
			}
			else if( mProgramHint == 0 ) {
				score = "100 %";
				resultAlert = "Congratulations, Your score is "+ score +" in "+String.format("%d min, %d sec", 
						TimeUnit.MILLISECONDS.toMinutes(remainingTime),
						TimeUnit.MILLISECONDS.toSeconds(remainingTime) - 
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime)))+", Fantastic Work..!!";
			}
			else if( mProgramHint < maxScore ){
				score = String.format("%.2f",((float)(maxScore - mProgramHint) / maxScore * 100 ))+ " %" ;
				resultAlert = "Congratulations, Your score is "+ score +" in "+String.format("%d min, %d sec", 
						TimeUnit.MILLISECONDS.toMinutes(remainingTime),
						TimeUnit.MILLISECONDS.toSeconds(remainingTime) - 
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime)))+", Keep Working..";
			}
			AuxilaryUtils.displayAlert("Test Complete", resultAlert, TestDragNDropActivity.this);
			mCheckButton.setEnabled(false);
			
			mQuizComplete = true;
			if( mWizard == true ) {
				mTimerButton.setText("Finish");
				mTimerButton.setEnabled(true);
				mTimerButton.setOnClickListener(mFinishBtnClickListener);
			}

		}
	}

	

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


	public View getViewByPosition(int position, ListView listView) {
		final int firstListItemPosition = listView.getFirstVisiblePosition();
		final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

		if (position < firstListItemPosition || position > lastListItemPosition ) {
			return listView.getAdapter().getView(position, listView.getChildAt(position), listView);
		} else {
			final int childIndex = position - firstListItemPosition;
			return listView.getChildAt(childIndex);
		}
	}

	protected void displayToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();

	}

	private DropListenerInterface mDropListener = 
			new DropListenerInterface() {
		public void onDrop(int from, int to) {
			ListAdapter adapter = getListAdapter();
			if (adapter instanceof DragNDropAdapter) {
				((DragNDropAdapter)adapter).onDrop(from, to);
				getListView().invalidateViews();
			}
		}
	};

	private RemoveListenerInterface mRemoveListener =
			new RemoveListenerInterface() {
		public void onRemove(int which) {
			ListAdapter adapter = getListAdapter();
			if (adapter instanceof DragNDropAdapter) {
				((DragNDropAdapter)adapter).onRemove(which);
				getListView().invalidateViews();
			}
		}
	};

	private DragListenerInterface mDragListener =
			new DragListenerInterface() {

		int backgroundColor = 0xe0103010;
		int defaultBackgroundColor;

		public void onDrag(int x, int y, ListView listView) {
			
		}

		public void onStartDrag(View itemView) {
			itemView.setVisibility(View.INVISIBLE);
			defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
			//itemView.setBackgroundColor(backgroundColor);
			ImageView imageView = (ImageView)itemView.findViewById(R.id.dragItemImageView);
			if (imageView != null) imageView.setVisibility(View.INVISIBLE);
		}

		public void onStopDrag(View itemView) {
			itemView.setVisibility(View.VISIBLE);
			//itemView.setBackgroundColor(defaultBackgroundColor);
			ImageView imageView = (ImageView)itemView.findViewById(R.id.dragItemImageView);
			if (imageView != null) imageView.setVisibility(View.VISIBLE);
		}

	};

	boolean mQuizComplete = false;

	@Override
	public void onBackPressed(){
		if( mQuizComplete == false ) {
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
		
		List<Program_Table> program_TableList = mDatabaseHandler.getAllProgram_Tables(mProgram_Index);
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
		
		if( program_TableList != null && program_TableList.size() > 0 ) {
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
			new DataBaseInserterAsyncTask(this, mProgram_Index, this).execute();
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}

	}



}

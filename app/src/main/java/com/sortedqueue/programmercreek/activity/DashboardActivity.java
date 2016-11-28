package com.sortedqueue.programmercreek.activity;


import android.app.ActionBar;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.database.operations.DataBaseInserterAsyncTask;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;

public class DashboardActivity extends AppCompatActivity implements OnClickListener, UIUpdateListener {

	DatabaseHandler mDatabaseHandler;
	SharedPreferences mPreferences;
	
	private String TAG = getClass().getSimpleName();
	private void logDebugMessage( String message ) {
		Log.d(TAG, message);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initDB();
		initUI();

	}


	private void initDB() {
		//TODO Insert into database using a separate thread / AsyncTask

		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		final boolean insertProgramTable = mPreferences.getBoolean( ProgrammingBuddyConstants.KEY_PROG_TABLE_INSERT, false);
		final boolean insertProgramIndex = mPreferences.getBoolean( ProgrammingBuddyConstants.KEY_PROG_INDEX_INSERT , false);

		if( insertProgramIndex == false || insertProgramTable == false ) {
			logDebugMessage("Inserting all Programs Titles..");
			new DataBaseInserterAsyncTask(this, -1, this).execute();
		}
		
		
	}

	/**
	 * Method to initialize UI. 
	 * */
	private void initUI() {

		setContentView(R.layout.fragment_dashboard);

		Button indexBtn = (Button) findViewById(R.id.btn_program_index);
		indexBtn.setOnClickListener(this);

		Button memorizeBtn = (Button) findViewById(R.id.btn_wizard);
		memorizeBtn.setOnClickListener(this);

		Button reviseBtn = (Button) findViewById(R.id.btn_revise);
		reviseBtn.setOnClickListener(this);

		Button testBtn = (Button) findViewById(R.id.btn_test_yourself);
		testBtn.setOnClickListener(this);

		Button matchBtn = (Button) findViewById(R.id.btn_match);
		matchBtn.setOnClickListener(this);

		Button quizBtn = (Button) findViewById(R.id.btn_quiz);
		quizBtn.setOnClickListener(this);

		Button promoteBtn = (Button) findViewById(R.id.btn_tell_your_friends);
		promoteBtn.setOnClickListener(this);

		Button downloadBtn = (Button) findViewById(R.id.btn_download);
		downloadBtn.setOnClickListener(this);


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {

		case android.R.id.home:
			showConfirmationDialog();
			return true;

		case R.id.action_about:
			Intent intent = new Intent(DashboardActivity.this, AboutActivity.class);
			startActivity(intent);
			return true;

		/*case R.id.action_refresh_database:
			new DataBaseInserterAsyncTask(this, mPreferences.edit(), -1).execute();
			return true;*/

		default:
			return super.onOptionsItemSelected(item);

		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}


	public void onGroupItemClick(MenuItem item) {
		// One of the group items (using the onClick attribute) was clicked
		// The item parameter passed here indicates which item it is
		// All other menu item clicks are handled by onOptionsItemSelected()
		switch( item.getItemId() ) {
		case R.id.action_about:
			Intent intent = new Intent(DashboardActivity.this, AboutActivity.class);
			startActivity(intent);
			break;

		case R.id.action_refresh_database:
			new DataBaseInserterAsyncTask(this, -1, this).execute();
			break;
		}
	}



	@Override
	public void onBackPressed(){
		showConfirmationDialog();
	}

	private void showConfirmationDialog(){

		Builder builder = new Builder(this);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				finish();
			}
		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				//dialog.dismiss();
			}
		});

		builder.setMessage("Are you sure you want to exit?");
		builder.setTitle("Programming Buddy");
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.show();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_dashboard,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {

		switch( v.getId() ) { 
		case R.id.btn_program_index : 
			LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_LIST);
			break;

		case R.id.btn_revise :
			LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_REVISE);
			break;

		case R.id.btn_wizard :
			LaunchFullScreenWizard();
			break;

		case R.id.btn_test_yourself :
			LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_TEST);
			break;

		case R.id.btn_match :
			LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_MATCH);
			break;

		case R.id.btn_quiz :
			LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_QUIZ);
			break;

		case R.id.btn_tell_your_friends :
			tellYourFriends();
			break;
		case R.id.btn_download : 
			shareInfo();
			break;
		}

	}

	private void LaunchFullScreenWizard() {
		startActivity(new Intent(getApplicationContext(), FullScreenWizardActivity.class));
	}

	private void tellYourFriends( ) {

	}

	private void LaunchProgramListActivity(int invokeMode) {
		Intent programListIntent = new Intent(getApplicationContext(), ProgramListActivity.class);
		programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, invokeMode);
		startActivity(programListIntent);
	}




	private void shareInfo( ) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello, from Programming Buddy");
		startActivity(Intent.createChooser(shareIntent, "Share App Info"));
	}


	@Override
	public void updateUI() {
		// TODO Auto-generated method stub
		
	}

}

package com.sortedqueue.programmercreek.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.operations.DataBaseInserterAsyncTask;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, UIUpdateListener {

    SharedPreferences mPreferences;
    @Bind(R.id.indexLayout)
    FrameLayout indexLayout;
    @Bind(R.id.wizardLayout)
    FrameLayout wizardLayout;
    @Bind(R.id.reviseLayout)
    FrameLayout reviseLayout;
    @Bind(R.id.quizLayout)
    FrameLayout quizLayout;
    @Bind(R.id.matchLayout)
    FrameLayout matchLayout;
    @Bind(R.id.testLayout)
    FrameLayout testLayout;


    private String TAG = getClass().getSimpleName();

    private void logDebugMessage(String message) {
        Log.d(TAG, message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);
        ButterKnife.bind(this);
        initDB();
        initUI();

    }


    private void initDB() {
        //TODO Insert into database using a separate thread / AsyncTask

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean insertProgramTable = mPreferences.getBoolean(ProgrammingBuddyConstants.KEY_PROG_TABLE_INSERT, false);
        final boolean insertProgramIndex = mPreferences.getBoolean(ProgrammingBuddyConstants.KEY_PROG_INDEX_INSERT, false);

        if (insertProgramIndex == false || insertProgramTable == false) {
            logDebugMessage("Inserting all Programs Titles..");
            new DataBaseInserterAsyncTask(this, -1, this).execute();
        }


    }

    /**
     * Method to initialize UI.
     */
    private void initUI() {
        indexLayout.setOnClickListener(this);
        matchLayout.setOnClickListener(this);
        testLayout.setOnClickListener(this);
        reviseLayout.setOnClickListener(this);
        quizLayout.setOnClickListener(this);
        wizardLayout.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_about:
                Intent intent = new Intent(DashboardActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_invite :
                tellYourFriends();
                return true;

            case R.id.action_share :
                shareInfo();
                return true;

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
        switch (item.getItemId()) {
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
    public void onBackPressed() {
        finish();
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

        switch (v.getId()) {
            case R.id.indexLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_LIST);
                break;

            case R.id.reviseLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_REVISE);
                break;

            case R.id.wizardLayout:
                LaunchFullScreenWizard();
                break;

            case R.id.testLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_TEST);
                break;

            case R.id.matchLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_MATCH);
                break;

            case R.id.quizLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_QUIZ);
                break;
        }

    }

    private void LaunchFullScreenWizard() {
        Intent programListIntent = new Intent(getApplicationContext(), ProgramListActivity.class);
        programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_REVISE);
        programListIntent.putExtra(ProgramListActivity.KEY_WIZARD, true);
        startActivity(programListIntent);
    }

    private void tellYourFriends() {

    }

    private void LaunchProgramListActivity(int invokeMode) {
        Intent programListIntent = new Intent(getApplicationContext(), ProgramListActivity.class);
        programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, invokeMode);
        startActivity(programListIntent);
    }


    private void shareInfo() {
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

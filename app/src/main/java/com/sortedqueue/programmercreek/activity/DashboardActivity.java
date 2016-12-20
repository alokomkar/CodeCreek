package com.sortedqueue.programmercreek.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.database.operations.DataBaseInserterAsyncTask;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, UIUpdateListener {

    @Bind(R.id.wikiLayout)
    FrameLayout wikiLayout;
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
    @Bind(R.id.fillLayout)
    FrameLayout fillLayout;
    @Bind(R.id.adView)
    AdView adView;


    private String TAG = getClass().getSimpleName();
    private DatabaseHandler mDatabaseHandler;
    public static final String PROGRAMER_CREEK_WIKI = "http://programercreek.blogspot.in/2016/12/c-programming-hello-world.html";
    private FirebaseDatabaseHandler firebaseDatabaseHandler;

    private void logDebugMessage(String message) {
        Log.d(TAG, message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);
        ButterKnife.bind(this);

        initAds();
        initDB();
        initUI();

    }

    private void initAds() {
        MobileAds.initialize(getApplicationContext(), getString(R.string.mobile_banner_id));
        //For actual ads : AdRequest adRequest = new AdRequest.Builder().build();
        //For creating test ads
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                .build();
        adView.loadAd(adRequest);
    }


    private void initDB() {
        logDebugMessage("Inserting all Programs Titles..");
        firebaseDatabaseHandler = new FirebaseDatabaseHandler(DashboardActivity.this);
        firebaseDatabaseHandler.initializeProgramIndexes(new FirebaseDatabaseHandler.ProgramIndexInterface() {
            @Override
            public void getProgramIndexes(ArrayList<Program_Index> program_indices) {

            }

            @Override
            public void onError(DatabaseError error) {

            }
        });
    }

    /**
     * Method to initialize UI.
     */
    private void initUI() {
        wikiLayout.setOnClickListener(this);
        indexLayout.setOnClickListener(this);
        matchLayout.setOnClickListener(this);
        testLayout.setOnClickListener(this);
        reviseLayout.setOnClickListener(this);
        quizLayout.setOnClickListener(this);
        wizardLayout.setOnClickListener(this);
        fillLayout.setOnClickListener(this);
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

            case R.id.action_invite:
                tellYourFriends();
                return true;

            case R.id.action_share:
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.wikiLayout:
                Intent intent = new Intent(DashboardActivity.this, ProgramWikiActivity.class);
                intent.putExtra(DatabaseHandler.KEY_WIKI, PROGRAMER_CREEK_WIKI);
                startActivity(intent);
                break;
            case R.id.indexLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_LIST);
                break;

            case R.id.reviseLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_REVISE);
                break;

            case R.id.wizardLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_WIZARD);
                break;

            case R.id.testLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_TEST);
                break;

            case R.id.matchLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_MATCH);
                break;

            case R.id.fillLayout:
                LaunchFillBlanksActivity();
                break;

            case R.id.quizLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_QUIZ);
                break;
        }

    }

    private void LaunchFillBlanksActivity() {

        mDatabaseHandler = new DatabaseHandler(this);
        //}
        if (mDatabaseHandler.getProgram_TablesCount() != 31) {
            new DataBaseInserterAsyncTask(DashboardActivity.this, -2, new UIUpdateListener() {
                @Override
                public void updateUI() {
                    LaunchFillBlanksActivity();
                }
            }).execute();
        } else {
            Intent intent = new Intent(DashboardActivity.this, FillTheBlanksActivity.class);
            startActivity(intent);
        }

    }

    private void tellYourFriends() {

    }

    private void LaunchProgramListActivity(final int invokeMode) {
        if (new CreekPreferences(DashboardActivity.this).getProgramTables() == -1) {
            firebaseDatabaseHandler.initializeProgramTables(new FirebaseDatabaseHandler.ProgramTableInterface() {
                @Override
                public void getProgramTables(ArrayList<Program_Table> program_tables) {
                    LaunchProgramListActivity(invokeMode);
                }

                @Override
                public void onError(DatabaseError error) {

                }
            });
        } else {
            Intent programListIntent = new Intent(getApplicationContext(), ProgramListActivity.class);
            programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, invokeMode);
            boolean isWizard = invokeMode == ProgrammingBuddyConstants.KEY_WIZARD;
            programListIntent.putExtra(ProgramListActivity.KEY_WIZARD, isWizard);
            if (isWizard) {
                programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_REVISE);
            }
            startActivity(programListIntent);
        }

    }


    private void shareInfo() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello, from " + getString(R.string.app_name));
        startActivity(Intent.createChooser(shareIntent, "Share App Info"));
    }


    @Override
    public void updateUI() {


    }

}

package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.UserProgramDetails;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekAnalytics;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import com.sortedqueue.programmercreek.util.FileUtils;
import com.sortedqueue.programmercreek.view.UserProgramDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Alok on 25/05/17.
 */

public class CodeShareHandlerActivity extends AppCompatActivity implements FirebaseDatabaseHandler.ConfirmUserProgram {


    private CreekPreferences creekPreferences;
    private String filepath;
    private String sharedText;
    private String TAG = CodeShareHandlerActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_share);
        ButterKnife.bind(this);
        creekPreferences = new CreekPreferences(CodeShareHandlerActivity.this);
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        CreekAnalytics.logEvent(TAG, "Adding Code");
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    void handleSendText(Intent intent) {
        sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            new FirebaseDatabaseHandler(CodeShareHandlerActivity.this).compileSharedProgram( sharedText, this );
        }
    }


    @Override
    public void onSuccess(final ProgramIndex programIndex, final ArrayList<ProgramTable> programTables) {

        if (programIndex != null && programTables.size() > 0) {

            new UserProgramDialog(
                    CodeShareHandlerActivity.this,
                    programIndex, programTables,
                    new UserProgramDialog.WebUserProgramDialogListener() {
                @Override
                public void onSave(String accessSpecifier, ProgramIndex programIndex, ArrayList<ProgramTable> programTables) {

                    FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(CodeShareHandlerActivity.this);
                    firebaseDatabaseHandler.updateCodeCount();

                    UserProgramDetails userProgramDetails = new UserProgramDetails();
                    userProgramDetails.setAccessSpecifier(accessSpecifier);
                    userProgramDetails.setMd5(FileUtils.md5(sharedText));
                    userProgramDetails.setEmailId(creekPreferences.getSignInAccount());
                    userProgramDetails.setLikes(0);
                    userProgramDetails.setLikesList(new ArrayList<String>());
                    userProgramDetails.setProgramIndex(programIndex);
                    userProgramDetails.setProgramTables(programTables);
                    userProgramDetails.setViews(0);
                    userProgramDetails.setProgramLanguage(programIndex.getProgram_Language());
                    userProgramDetails.setProgramTitle(programIndex.getProgram_Description().toLowerCase());

                    if (creekPreferences.addUserFile(userProgramDetails.getMd5())) {
                        try {
                            CreekAnalytics.logEvent(TAG, new JSONObject(new Gson().toJson(programIndex)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        firebaseDatabaseHandler.writeUserProgramDetails(userProgramDetails);
                        onProgressStatsUpdate(CreekUserStats.CHAPTER_SCORE);
                    } else {
                        CommonUtils.displayToast(CodeShareHandlerActivity.this, "File already added");
                    }


                }

                @Override
                public void onCancel() {
                    finish();
                }

                @Override
                public void onPreview(ProgramIndex programIndex, ArrayList<ProgramTable> programTables) {
                    Bundle newIntentBundle = new Bundle();
                    Intent newIntent = null;
                    programIndex.setUserProgramId("trial");
                    newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true);
                    newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, programIndex);
                    newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, 1);
                    newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, programIndex.getProgram_Description());
                    newIntentBundle.putParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM, programTables);
                    newIntent = new Intent(CodeShareHandlerActivity.this, ProgramActivity.class);
                    newIntent.putExtras(newIntentBundle);
                    startActivity(newIntent);
                }
            }, 1).showDialog();

        }

    }

    private void onProgressStatsUpdate(int chapterScore) {
        CommonUtils.displayToast(CodeShareHandlerActivity.this, "You have gained " + chapterScore + "xp");
        finish();
    }

    @Override
    public void onError(String errorMessage) {
        CommonUtils.displayToast(CodeShareHandlerActivity.this, errorMessage);
    }
}

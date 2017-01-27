package com.sortedqueue.programmercreek.activity;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.DashboardPagerAdapter;
import com.sortedqueue.programmercreek.asynctask.JavaProgramInserter;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.ProgramWiki;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.fragments.DashboardFragment;
import com.sortedqueue.programmercreek.fragments.LanguageFragment;
import com.sortedqueue.programmercreek.interfaces.DashboardNavigationListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DashboardActivity extends AppCompatActivity implements DashboardNavigationListener {

    //@Bind(R.id.adView)
    //AdView adView;
    @Bind(R.id.dashboardViewPager)
    ViewPager dashboardViewPager;
    @Bind(R.id.dashboardTabLayout)
    TabLayout dashboardTabLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private String TAG = getClass().getSimpleName();
    private CreekPreferences creekPreferences;
    private GoogleApiClient mGoogleApiClient;

    private void logDebugMessage(String message) {
        Log.d(TAG, message);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);
        ButterKnife.bind(this);
        setupToolbar();
        creekPreferences = new CreekPreferences(DashboardActivity.this);
        configureGoogleSignup();
        if( !creekPreferences.getProgramLanguage().equals("") ) {
            AuxilaryUtils.scheduleNotification(DashboardActivity.this);
        }
        //adView.setVisibility(View.GONE);

        //initAds();

        dashboardViewPager.setAdapter(new DashboardPagerAdapter(getSupportFragmentManager(), DashboardActivity.this));
        dashboardTabLayout.setupWithViewPager(dashboardViewPager);
        dashboardTabLayout.getTabAt(0).setIcon(R.drawable.ic_account_box_white_24dp);
        dashboardTabLayout.getTabAt(1).setIcon(R.drawable.ic_dns_white_24dp);
        if( creekPreferences.getProgramLanguage().equals("")) {
            dashboardViewPager.setCurrentItem(0);
        }
        else {
            dashboardViewPager.setCurrentItem(1);
            getSupportActionBar().setTitle(getString(R.string.app_name) + " - " + creekPreferences.getProgramLanguage().toUpperCase());
        }

        dashboardViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if( position == 0 ) {
                    //LanguageFragment.getInstance().animateViews();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        //initJavaIndex();
        //initProgramLanguages();


    }

    /*private void initProgramLanguages() {
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(DashboardActivity.this);
        ProgramLanguage programLanguage = new ProgramLanguage("C Programming", "C", "C is a high-level and general purpose programming language that is ideal for developing firmware or portable applications.");
        firebaseDatabaseHandler.writeProgramLanguage(programLanguage);
        programLanguage = new ProgramLanguage("C++ Programming", "C++", "C++ is a general-purpose, statically typed, free-form, multi-paradigm programming language supporting procedural programming, data abstraction, and generic programming.");
        firebaseDatabaseHandler.writeProgramLanguage(programLanguage);
        programLanguage = new ProgramLanguage("Java Programming", "Java", "Java is a general-purpose computer programming language that is concurrent, class-based, object-oriented, and specifically designed to have as few implementation dependencies as possible.");
        firebaseDatabaseHandler.writeProgramLanguage(programLanguage);
        programLanguage = new ProgramLanguage("Unix Shell Programming", "USP", "Unix Shell Programming : 10CSL68 (VTU) : This course will enable students to Understand the UNIX Architecture, File systems and use of basic Commands. \\n\\nUse of editors and Networking commands. \\n\\nUnderstand Shell Programming and to write shell scripts. \\n\\nUnderstand and analyze UNIX System calls, Process Creation, Control & Relationship.");
        firebaseDatabaseHandler.writeProgramLanguage(programLanguage);
    }*/

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
    }



    private void initJavaIndex() {
        creekPreferences.setProgramLanguage("usp");
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(DashboardActivity.this);
        //http://man7.org/linux/man-pages/man3/mkfifo.3.html
        String programCode =
                "#include <stdio.h>\n" +
                        "#include <sys/wait.h>\n" +
                        "#include <errno.h>\n" +
                        "#include <stdlib.h>\n" +
                        "#include <unistd.h>\n" +
                        "int main(){\n" +
                        "pid_t pid;\n" +
                        "if ((pid = fork()) < 0){\n" +
                        "printf(\"fork error\");\n" +
                        "}\n" +
                        "else if (pid == 0){\n" +
                        "if ((pid = fork()) < 0)\n" +
                        "printf(\"fork error\");\n" +
                        "else if (pid > 0)\n" +
                        "exit(0);\n" +
                        "sleep(2);\n" +
                        "printf(\"second child, parent pid = %d\\n\", getppid());\n" +
                        "exit(0);\n" +
                        "}\n" +
                        "if (waitpid(pid, NULL, 0) != pid)\n" +
                        "printf(\"waitpid error\");\n" +
                        "exit(0);\n" +
                        "}";
        //https://gcc.gnu.org/onlinedocs/cpp/Ifdef.html
        String programExplanation =
                "include header <stdio.h>\n" +
                        "include header <sys/wait.h>\n" +
                        "include header <errno.h>\n" +
                        "include header <stdlib.h>\n" +
                        "include header <unistd.h>\n" +
                        "Main declaration\n" +
                        "variable declaration pid_t pid;\n" +
                        "check if ((pid = fork()) < 0)\n" +
                        "print \"fork error\"\n" +
                        "end if\n" +
                        "else check if (pid == 0)\n" +
                        "check if ((pid = fork()) < 0)\n" +
                        "print \"fork error\";\n" +
                        "else check if (pid > 0)\n" +
                        "normal exit with 0;\n" +
                        "sleep for 2 seconds;\n" +
                        "print \"second child, parent pid = %d\\n\", getppid()\n" +
                        "normal exit 0;\n" +
                        "end\n" +
                        "check if (waitpid(pid, NULL, 0) != pid)\n" +
                        "print \"waitpid error\"\n" +
                        "normal exit with status 0\n" +
                        "end main";
        ArrayList<String> programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        ArrayList<String> programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        int programIndex = 10;
        for( int i = 0; i < programLines.size(); i++ ) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i+1,
                            "usp",
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "#include<errno.h>\n" +
                        "#include<unistd.h>\n" +
                        "#include<stdio.h>\n" +
                        "#include<stdlib.h>\n" +
                        "int mySystem(const char *cmdstring){\n" +
                        "pid_t pid; int status;\n" +
                        "if (cmdstring == NULL)\n" +
                        "return(1);\n" +
                        "if ((pid = fork()) < 0) {\n" +
                        "status = -1;\n" +
                        "}\n" +
                        "else if (pid == 0){\n" +
                        "execl(\"/bin/sh\", \"sh\", \"-c\", cmdstring, NULL);\n" +
                        "_exit(127);\n" +
                        "}\n" +
                        "else\n" +
                        "while (waitpid(pid, &status, 0) < 0) {\n" +
                        "if (errno != EINTR)\n" +
                        "status = -1; /* error other than EINTR from waitpid()*/\n" +
                        "break;\n" +
                        "}\n" +
                        "return(status);\n" +
                        "}\n" +
                        "int main() {\n" +
                        "int status;\n" +
                        "if ((status = mySystem(\"what\")) < 0)\n" +
                        "printf(\"system() error\");\n" +
                        "if ((status = mySystem(\"who\")) < 0)\n" +
                        "printf(\"system() error\");\n" +
                        "exit(0);\n" +
                        "}";
        programExplanation =
                "include header<errno.h>\n" +
                        "include header<unistd.h>\n" +
                        "include header<stdio.h>\n" +
                        "include header<stdlib.h>\n" +
                        "define function mySystem\n" +
                        "declare variable pid_t pid; int status;\n" +
                        "check if (cmdstring == NULL)\n" +
                        "return 1;\n" +
                        "check if ((pid = fork()) < 0)\n" +
                        "Assign status = -1;\n" +
                        "end if\n" +
                        "check else if (pid == 0)\n" +
                        "call execl(\"/bin/sh\", \"sh\", \"-c\", cmdstring, NULL)\n" +
                        "exit with error code 127\n" +
                        "end else if\n" +
                        "else\n" +
                        "loop while (waitpid(pid, &status, 0) < 0) {\n" +
                        "check if (errno != EINTR)\n" +
                        "assign status = -1; /* error other than EINTR from waitpid()*/\n" +
                        "break loop\n" +
                        "end while\n" +
                        "return(status);\n" +
                        "end function\n" +
                        "Main declaration\n" +
                        "variable declaration status;\n" +
                        "check if ((status = mySystem(\"what\")) < 0)\n" +
                        "print \"system() error\"\n" +
                        "check if status = mySystem(\"who\")) < 0\n" +
                        "print \"system() error\"\n" +
                        "normal exit with status 0;\n" +
                        "end main";
        
        
        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for( int i = 0; i < programLines.size(); i++ ) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i+1,
                            "usp",
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "#include<signal.h>\n" +
                        "#include<stdio.h>\n" +
                        "#include<unistd.h>\n" +
                        "#include<errno.h>\n" +
                        "void wakeup(){\n" +
                        "printf(\"Hello\\n\");\n" +
                        "}\n" +
                        "int main(){\n" +
                        "signal(SIGALRM,&wakeup);\n" +
                        "while(1){\n" +
                        "alarm(5);\n" +
                        "pause();\n" +
                        "printf(\"Waiting For Alarm\\n\");\n" +
                        "}\n" +
                        "return 0;\n" +
                        "}";
        programExplanation =
                "include header<signal.h>\n" +
                        "include header<stdio.h>\n" +
                        "include header<unistd.h>\n" +
                        "include header<errno.h>\n" +
                        "define function wakeup\n" +
                        "print \"Hello\\n\"\n" +
                        "end function\n" +
                        "define main\n" +
                        "call signal(SIGALRM, &wakeup);\n" +
                        "loop till while(1)\n" +
                        "call alarm(5);\n" +
                        "call pause();\n" +
                        "print \"Waiting For Alarm\\n\"\n" +
                        "end while\n" +
                        "return 0;\n" +
                        "end main";
        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for( int i = 0; i < programLines.size(); i++ ) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i+1,
                            "usp",
                            programLines.get(i),
                            programExplanations.get(i)));
        }


        //new JavaProgramInserter(DashboardActivity.this).insertProgramTables();
    }

    /*private void initAds() {
        MobileAds.initialize(getApplicationContext(), getString(R.string.mobile_banner_id));
        //For actual ads : AdRequest adRequest = new AdRequest.Builder().build();
        //For creating test ads
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                .build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
                //AuxilaryUtils.displayResultAlert("Result", "Message", DashboardActivity.this);
            }
        });

    }*/


    @Override
    protected void onResume() {
        super.onResume();
        CreekApplication.getInstance().setAppRunning(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CreekApplication.getInstance().setAppRunning(false);
    }

    /**
     * Method to initialize UI.
     */



    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if(!AuxilaryUtils.isNetworkAvailable()) {
            CommonUtils.displaySnackBarIndefinite(DashboardActivity.this, R.string.internet_unavailable, R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View snackBarView) {
                    onOptionsItemSelected(item);
                }
            });
            return true;
        }

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

            case R.id.action_sync:
                LanguageFragment.getInstance().getFirebaseDBVerion();

                return true;
            case R.id.action_log_out :
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                            }
                        });
                creekPreferences.clearCacheDetails();
                logoutFromFB();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent spashIntent = new Intent(DashboardActivity.this, SplashActivity.class);
                startActivity(spashIntent);
                finish();
                return true;

            case R.id.action_feedback :
                sendEmail(DashboardActivity.this);
                return true;
            /*case R.id.action_search:
                Intent searchIntent = new Intent(DashboardActivity.this, ProgramWikiActivity.class);
                startActivity(searchIntent);
                return true;*/
            case R.id.action_rate :
                try {
                    Intent rateIntent = new Intent(Intent.ACTION_VIEW);
                    rateIntent.setData(Uri.parse("market://details?id=" + DashboardActivity.this.getPackageName() ));
                    startActivity(rateIntent);
                } catch (Exception e) { //google play app is not installed
                    Intent rateIntent = new Intent(Intent.ACTION_VIEW);
                    rateIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id="+ DashboardActivity.this.getPackageName()));
                    startActivity(rateIntent);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void logoutFromFB() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }
        if( LoginManager.getInstance() != null)
            LoginManager.getInstance().logOut();
        /*new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {



            }
        }).executeAsync();*/
    }

    public void sendEmail(Context ctx) {
        try {
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + getString(R.string.feedback_email)));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            startActivity(intent);
        } catch ( ActivityNotFoundException e ) {
            CommonUtils.displaySnackBar(DashboardActivity.this, R.string.unable_to_find_app_for_email);
        }

    }

    private void configureGoogleSignup() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();
    }


    private void tellYourFriends() {
        //https://developers.facebook.com/docs/sharing/android/
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle("Infinite Programmer")
                .setContentDescription("Check this out : ")
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#InfiniteProgrammer")
                        .build())

                .setContentUrl(Uri.parse(getString(R.string.app_url)))
                .build();
        ShareDialog shareDialog = new ShareDialog(DashboardActivity.this);
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
    }

    private void shareInfo() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this app : \n" + getString(R.string.app_url));
        startActivity(Intent.createChooser(shareIntent, "Share App Info"));
    }

    @Override
    public void navigateToDashboard() {
        dashboardViewPager.setCurrentItem(1);
        DashboardFragment.getInstance().animateViews();
        getSupportActionBar().setTitle(getString(R.string.app_name) + " - " + creekPreferences.getProgramLanguage().toUpperCase());
    }

    @Override
    public void navigateToLanguage() {
        //LanguageFragment.getInstance().animateViews();
        dashboardViewPager.setCurrentItem(0);
    }
}

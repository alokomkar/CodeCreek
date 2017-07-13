package com.sortedqueue.programmercreek.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.appinvite.AppInviteInvitation;
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
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramLanguage;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.UserProgramDetails;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.database.firebase.FirebaseStorageHandler;
import com.sortedqueue.programmercreek.fragments.DashboardFragment;
import com.sortedqueue.programmercreek.fragments.LanguageFragment;
import com.sortedqueue.programmercreek.interfaces.DashboardNavigationListener;
import com.sortedqueue.programmercreek.interfaces.retrofit.DownloadHTMLService;
import com.sortedqueue.programmercreek.network.RetrofitCreator;
import com.sortedqueue.programmercreek.util.AnimationUtils;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import com.sortedqueue.programmercreek.util.FileUtils;
import com.sortedqueue.programmercreek.util.FileUtils.DownloadFileListner;
import com.sortedqueue.programmercreek.util.PermissionUtils;
import com.sortedqueue.programmercreek.view.UserProgramDialog;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DashboardActivity extends AppCompatActivity implements DashboardNavigationListener, DownloadFileListner, View.OnClickListener, FirebaseDatabaseHandler.ConfirmUserProgram {

    //@BindView(R.id.adView)
    //AdView adView;
    @BindView(R.id.dashboardViewPager)
    ViewPager dashboardViewPager;
    @BindView(R.id.dashboardTabLayout)
    TabLayout dashboardTabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.createPresentationFAB)
    FloatingActionButton createPresentationFAB;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.addCodeTextView)
    TextView addCodeTextView;
    @BindView(R.id.addCodeFAB)
    FloatingActionButton addCodeFAB;
    @BindView(R.id.addUserCodeFAB)
    FloatingActionButton addUserCodeFAB;
    @BindView(R.id.addCodeLayout)
    LinearLayout addCodeLayout;
    @BindView(R.id.addPptTextView)
    TextView addPptTextView;
    @BindView(R.id.addPptLayout)
    LinearLayout addPptLayout;
    @BindView(R.id.fabLayout)
    LinearLayout fabLayout;
    @BindView(R.id.main_content)
    RelativeLayout mainContent;
    @BindView(R.id.reputationProgressBar)
    ProgressBar reputationProgressBar;
    @BindView(R.id.reputationTextView)
    TextView reputationTextView;
    @BindView(R.id.progressLayout)
    LinearLayout progressLayout;


    private String TAG = getClass().getSimpleName();
    private CreekPreferences creekPreferences;
    private GoogleApiClient mGoogleApiClient;
    private int REQUEST_INVITE = 9999;
    private int REQUEST_CODE_SEARCH = 1000;
    private AlertDialog alertDialog;
    private int REQUEST_DOWNLOAD_FILE = 101;
    private int REQUEST_DOWNLOAD_FILE_PERMISSION = 1234;
    private int REQUEST_IMPORT_FILE_PERMISSION = 1334;
    private String filepath;
    private Handler handler;
    private CreekUserStats creekUserStats;
    private Runnable runnable;

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
        creekPreferences = CreekApplication.getCreekPreferences();
        configureGoogleSignup();
        if (!creekPreferences.getProgramLanguage().equals("")) {
            AuxilaryUtils.scheduleNotification(DashboardActivity.this);
        }
        //adView.setVisibility(View.GONE);

        //initAds();
        createPresentationFAB.setOnClickListener(this);
        addPptTextView.setVisibility(View.GONE);
        addCodeFAB.setVisibility(View.GONE);
        addCodeTextView.setVisibility(View.GONE);
        addUserCodeFAB.setVisibility(View.GONE);
        addCodeFAB.setOnClickListener(this);
        addCodeTextView.setOnClickListener(this);
        addPptTextView.setOnClickListener(this);
        addCodeLayout.setOnClickListener(this);
        addPptLayout.setOnClickListener(this);
        addUserCodeFAB.setOnClickListener(this);

        fabLayout.setVisibility(View.GONE);
        dashboardViewPager.setAdapter(new DashboardPagerAdapter(getSupportFragmentManager(), DashboardActivity.this));
        dashboardTabLayout.setupWithViewPager(dashboardViewPager);
        dashboardTabLayout.getTabAt(0).setIcon(R.drawable.ic_account_box_white_24dp);
        dashboardTabLayout.getTabAt(1).setIcon(R.drawable.ic_dns_white_24dp);
        dashboardTabLayout.getTabAt(2).setIcon(R.drawable.ic_top_learners);
        dashboardTabLayout.getTabAt(3).setIcon(R.drawable.ic_add_to_queue_white_24dp);
        //dashboardTabLayout.getTabAt(3).setIcon(R.drawable.ic_view_carousel_white_36dp);
        if (creekPreferences.getProgramLanguage().equals("")) {
            dashboardViewPager.setCurrentItem(0);
        } else {
            dashboardViewPager.setCurrentItem(1);
            getSupportActionBar().setTitle(getString(R.string.app_name) + " - " + creekPreferences.getProgramLanguage().toUpperCase());
        }
        dashboardViewPager.setOffscreenPageLimit(dashboardTabLayout.getTabCount());
        dashboardViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3)
                    AnimationUtils.enterReveal(addUserCodeFAB);
                else
                    AnimationUtils.exitRevealGone(addUserCodeFAB);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //new FirebaseDatabaseHandler(DashboardActivity.this).searchPrograms("Swap");
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        //calculateTopLearners();
        //initJavaIndex();
        //initProgramLanguages();
        //calculateUserRankings();
        //executeProgram();
        //getOutputResponse(58011332);

    }


    @Override
    public void onProgressStatsUpdate(int points) {
        progressLayout.setVisibility(View.VISIBLE);
        animateProgress(points);
    }

    private int progressBarStatus;

    public void animateProgress(final int points) {
        try {
            if (reputationProgressBar != null) {

                if (handler == null) {
                    handler = new Handler();
                }
                if (creekPreferences == null) {
                    creekPreferences = CreekApplication.getCreekPreferences();
                }
                creekUserStats = creekPreferences.getCreekUserStats();
                if (creekUserStats == null) {
                    reputationProgressBar.setVisibility(View.GONE);
                    reputationTextView.setVisibility(View.GONE);
                    progressLayout.setVisibility(View.GONE);
                    return;
                }
                final int progress = creekUserStats.getCreekUserReputation() % 100;
                reputationProgressBar.setVisibility(View.VISIBLE);
                reputationTextView.setVisibility(View.VISIBLE);
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        for (progressBarStatus = 0; progressBarStatus <= progress; progressBarStatus++) {

                            handler.post(new Runnable() {
                                public void run() {
                                    if( reputationProgressBar != null ) {
                                        reputationProgressBar.setProgress(progressBarStatus);

                                        reputationTextView.setText("You've gained " + points + "xp\n" + progressBarStatus +"% Complete");
                                        int level = creekUserStats.getCreekUserReputation() / 100;
                                        if (level > 0) {
                                            reputationTextView.setText("You've gained " + points + "xp\n" + progressBarStatus +"% Complete : Level : " + level);
                                        }
                                    }
                                }
                            });
                            try {
                                Thread.sleep(40);
                            } catch (Exception ex) {
                            }
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressLayout.setVisibility(View.GONE);
                            }
                        }, 1500);

                    }
                };
                new Thread(runnable).start();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            if( progressLayout != null ) {
                progressLayout.setVisibility(View.GONE);
            }
        }

    }

    private void calculateUserRankings() {
        new FirebaseDatabaseHandler(DashboardActivity.this).getAllCreekUserStatsInBackground();
    }


    private void initProgramLanguages() {
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(DashboardActivity.this);
        ProgramLanguage programLanguage = new ProgramLanguage();
        programLanguage.setDescription("Analysis and design of algorithms - An alogorithm is a self contained " +
                "sequence of actions to be performed. Algorithms can perform calculations, data processing and automated" +
                " reasoning tasks.");
        programLanguage.setProgramLanguage("Analysis and Design of Algorithms");
        programLanguage.setIsProgramsOnly("true");
        programLanguage.setLanguageId("ADA");
        firebaseDatabaseHandler.writeProgramLanguage(programLanguage);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
    }


    private void initJavaIndex() {
        new JavaProgramInserter(DashboardActivity.this).insertCodeLabPrograms();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CreekApplication.getInstance().setAppRunning(true);
        calculateReputation();
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
        if (!AuxilaryUtils.isNetworkAvailable()) {
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

            case R.id.action_link:
                if (creekPreferences.getIsAnonAccount()) {
                    Intent spashIntent = new Intent(DashboardActivity.this, SplashActivity.class);
                    spashIntent.putExtra(CreekPreferences.KEY_ANON_ACCOUNT, true);
                    startActivity(spashIntent);
                    finish();
                } else {
                    CommonUtils.displaySnackBar(DashboardActivity.this, R.string.register_account_for_tour_users_only);
                }
                return true;

            case R.id.action_about:
                Intent intent = new Intent(DashboardActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_invite:
                //tellYourFriends();
                onInviteClicked();
                return true;

            case R.id.action_share:
                shareInfo();
                //onInviteClicked();
                return true;

            case R.id.action_sync:
                //downloadFile();
                LanguageFragment.getInstance().getFirebaseDBVerion();
                return true;

            case R.id.action_log_out:
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

            case R.id.action_feedback:
                sendEmail(DashboardActivity.this);
                return true;
            /*case R.id.action_search:
                Intent searchIntent = new Intent(DashboardActivity.this, ProgramWikiActivity.class);
                startActivity(searchIntent);
                return true;*/
            case R.id.action_rate:
                try {
                    Intent rateIntent = new Intent(Intent.ACTION_VIEW);
                    rateIntent.setData(Uri.parse("market://details?id=" + DashboardActivity.this.getPackageName()));
                    startActivity(rateIntent);
                } catch (Exception e) { //google play app is not installed
                    Intent rateIntent = new Intent(Intent.ACTION_VIEW);
                    rateIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + DashboardActivity.this.getPackageName()));
                    startActivity(rateIntent);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void downloadFile() {
        DownloadHTMLService downloadHTMLService = RetrofitCreator.createDownloadService(DownloadHTMLService.class);
        Call<ResponseBody> call = downloadHTMLService.downloadFileWithDynamicUrlSync("http://365programperday.blogspot.in/2016/01/android-saving-and-restoring-activity.html");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "server contacted and has file");

                    new AsyncTask<Void, Void, Void>() {

                        DownloadFileListner downloadFileListner;

                        @Override
                        protected Void doInBackground(Void... voids) {
                            boolean writtenToDisk = FileUtils.writeResponseBodyToDisk(DashboardActivity.this, response.body(), null);

                            Log.d(TAG, "file download was a success? " + writtenToDisk);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                        }
                    }.execute();
                } else {
                    Log.d(TAG, "server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void logoutFromFB() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }
        if (LoginManager.getInstance() != null)
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
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + getString(R.string.feedback_email)));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);
        if (requestCode == REQUEST_DOWNLOAD_FILE) {
            if (resultCode == RESULT_OK) {
                if (PermissionUtils.checkSelfPermission(DashboardActivity.this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_DOWNLOAD_FILE_PERMISSION)) {
                    downloadTemplateFile();
                }
            }
        } else if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d(TAG, "onActivityResult: sent invitation " + id);
                }
                new FirebaseDatabaseHandler(DashboardActivity.this).updateInviteCount(ids.length);
                creekPreferences.setShowInviteDialog(false);
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        } else if (requestCode == REQUEST_CODE_SEARCH && resultCode == AppCompatActivity.RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {

                Log.d(TAG, "File Uri : " + uri.getEncodedPath() + " Path " + uri.getPath());
                filepath = FileUtils.getPath(DashboardActivity.this, uri);
                Log.d(TAG, "File path : " + filepath);

                if (filepath != null) {
                    String fileMd5 = FileUtils.calculateMD5(new File(filepath));
                    if (creekPreferences.getCreekUserStats().getUserAddedPrograms().contains(fileMd5)) {
                        CommonUtils.displayToast(DashboardActivity.this, "File already uploaded");
                    } else
                        new FirebaseDatabaseHandler(DashboardActivity.this).readProgramFromFile(filepath, this);
                } else
                    CommonUtils.displayToast(DashboardActivity.this, "Unable to open file");
            } else {
            }
            // Rest of code that converts txt file's content into arraylist
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    private void downloadTemplateFile() {
        CommonUtils.displayProgressDialog(DashboardActivity.this, getString(R.string.downloading_file));
        FirebaseStorageHandler.downloadTemplateFile(DashboardActivity.this, new FirebaseStorageHandler.TemplateDownloadListener() {
            @Override
            public void onSuccess(String filePath) {
                CommonUtils.dismissProgressDialog();
                AuxilaryUtils.displayInformation(DashboardActivity.this,
                        getString(R.string.add_code),
                        getString(R.string.add_code_description) +
                                "\n\nTemplate file :\n\n<program_title>\n" +
                                "Hello World\n" +
                                "</program_title>\n" +
                                "<program_language>\n" +
                                "C\n" +
                                "</program_language>\n" +
                                "<program_explanation>\n" +
                                "Include header stdio\n" +
                                "Main declaration\n" +
                                "Print Hello\n" +
                                "Wait for user input\n" +
                                "End of program\n" +
                                "</program_explanation>\n" +
                                "<program>\n" +
                                "#include<stdio.h>\n" +
                                "void main() {\n" +
                                "printf(“Hello”);\n" +
                                "getch();\n" +
                                "}\n" +
                                "</program>" + "\n\nFile is saved to : " + filePath,
                        new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {

                            }
                        });
            }

            @Override
            public void onError(String error) {
                CommonUtils.dismissProgressDialog();
                CommonUtils.displayToast(DashboardActivity.this, error);
            }
        });
    }

    @Override
    public void importCodeFile() {
        if (PermissionUtils.checkSelfPermission(DashboardActivity.this,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_IMPORT_FILE_PERMISSION)) {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("text/plain");
            startActivityForResult(Intent.createChooser(intent,
                    "Load file from directory"), REQUEST_CODE_SEARCH);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    private void onInviteClicked() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                .setCustomImage(CommonUtils.getUriToDrawable(DashboardActivity.this, R.mipmap.ic_launcher))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }


    @Override
    public void onBackPressed() {
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();
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

    @Override
    public void calculateReputation() {
        if (creekPreferences == null) {
            creekPreferences = CreekApplication.getCreekPreferences();
        }
        if (creekPreferences != null && !creekPreferences.getProgramLanguage().equals("")) {
            CreekUserStats creekUserStats = creekPreferences.getCreekUserStats();
            if (creekUserStats != null && creekUserStats.getCreekUserReputation() == 0) {
                creekUserStats.calculateReputation();
                LanguageFragment.getInstance().animateProgress();
                new FirebaseDatabaseHandler(DashboardActivity.this).writeCreekUserStats(creekUserStats);
            }
        }
    }

    @Override
    public void showInviteDialog() {
        if (creekPreferences.getShowInviteDialog()) {
            AuxilaryUtils.displayAppInviteDialog(DashboardActivity.this, new AuxilaryUtils.InviteDialogListener() {
                @Override
                public void onInviteClick() {
                    onInviteClicked();
                }

                @Override
                public void onLaterClick() {
                    creekPreferences.setShowInviteDialog(false);
                }
            });
        }

    }

    @Override
    public void onSuccess(final File fileUrl) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.setVisibility(View.VISIBLE);
                Log.d(TAG, "File Url : file:///" + fileUrl.getAbsolutePath());
                webView.loadUrl("file:///" + fileUrl.getAbsolutePath());
                webView.getSettings().setJavaScriptEnabled(true);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createPresentationFAB:
                animateFab();
                break;
            case R.id.addPptTextView:
                Intent intent = new Intent(DashboardActivity.this, CreatePresentationActivity.class);
                startActivity(intent);
                break;
            case R.id.addCodeFAB:
            case R.id.addCodeTextView:
                importFromFile();
                animateFab();
                break;
            case R.id.addUserCodeFAB :
                importFromFile();
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_DOWNLOAD_FILE_PERMISSION) {
            if (PermissionUtils.checkDeniedPermissions(DashboardActivity.this, permissions).length == 0) {
                downloadTemplateFile();
            } else {
                if (permissions.length == 3) {
                    Toast.makeText(DashboardActivity.this, "Some permissions were denied", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == REQUEST_IMPORT_FILE_PERMISSION) {
            if (PermissionUtils.checkDeniedPermissions(DashboardActivity.this, permissions).length == 0) {
                importCodeFile();
            } else {
                if (permissions.length == 3) {
                    Toast.makeText(DashboardActivity.this, "Some permissions were denied", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void importFromFile() {
        Intent intent = new Intent(DashboardActivity.this, TutorialCarousalActivity.class);
        startActivityForResult(intent, REQUEST_DOWNLOAD_FILE);
    }

    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private boolean isFABOpen;

    private void animateFab() {
        if (fab_open == null) {
            fab_open = android.view.animation.AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
            fab_close = android.view.animation.AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
            rotate_forward = android.view.animation.AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
            rotate_backward = android.view.animation.AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        }
        if (isFABOpen) {
            isFABOpen = false;
            createPresentationFAB.startAnimation(rotate_backward);
            fab_close.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    addCodeTextView.setVisibility(View.GONE);
                    addPptTextView.setVisibility(View.GONE);
                    addCodeFAB.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            addCodeTextView.startAnimation(fab_close);
            addPptTextView.startAnimation(fab_close);
            addCodeFAB.startAnimation(fab_close);
        } else {
            isFABOpen = true;
            createPresentationFAB.startAnimation(rotate_forward);
            addCodeTextView.setVisibility(View.INVISIBLE);
            addCodeFAB.setVisibility(View.INVISIBLE);
            addPptTextView.setVisibility(View.INVISIBLE);
            addCodeTextView.startAnimation(fab_open);
            addPptTextView.startAnimation(fab_open);
            addCodeFAB.startAnimation(fab_open);
        }
    }

    @Override
    public void onSuccess(final ProgramIndex programIndex, final ArrayList<ProgramTable> programTables) {

        if (programIndex != null && programTables.size() > 0) {

            new UserProgramDialog(DashboardActivity.this, programIndex, programTables, new UserProgramDialog.UserProgramDialogListener() {
                @Override
                public void onSave(String accessSpecifier) {

                    FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(DashboardActivity.this);
                    firebaseDatabaseHandler.updateCodeCount();

                    UserProgramDetails userProgramDetails = new UserProgramDetails();
                    userProgramDetails.setAccessSpecifier(accessSpecifier);
                    if (filepath != null)
                        userProgramDetails.setMd5(FileUtils.calculateMD5(new File(filepath)));
                    userProgramDetails.setEmailId(creekPreferences.getSignInAccount());
                    userProgramDetails.setLikes(0);
                    userProgramDetails.setLikesList(new ArrayList<String>());
                    userProgramDetails.setProgramIndex(programIndex);
                    userProgramDetails.setProgramTables(programTables);
                    userProgramDetails.setViews(0);
                    userProgramDetails.setProgramLanguage(programIndex.getProgram_Language());
                    userProgramDetails.setProgramTitle(programIndex.getProgram_Description().toLowerCase());

                    if (creekPreferences.addUserFile(userProgramDetails.getMd5())) {
                        firebaseDatabaseHandler.writeUserProgramDetails(userProgramDetails);
                    } else {
                        CommonUtils.displayToast(DashboardActivity.this, "File already added");
                    }
                    onProgressStatsUpdate(CreekUserStats.CHAPTER_SCORE);

                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onPreview() {
                    Bundle newIntentBundle = new Bundle();
                    Intent newIntent = null;
                    programIndex.setUserProgramId("trial");
                    newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true);
                    newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, programIndex);
                    newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, 1);
                    newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, programIndex.getProgram_Description());
                    newIntentBundle.putParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM, programTables);
                    newIntent = new Intent(DashboardActivity.this, ProgramActivity.class);
                    newIntent.putExtras(newIntentBundle);
                    startActivity(newIntent);
                }
            }).showDialog();

        }

    }

    @Override
    public void onError(String errorMessage) {
        CommonUtils.displayToast(DashboardActivity.this, errorMessage);
    }
}

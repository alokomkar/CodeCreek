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
import com.sortedqueue.programmercreek.constants.LanguageConstants;
import com.sortedqueue.programmercreek.database.firebase.Code;
import com.sortedqueue.programmercreek.database.firebase.CodeOutputResponse;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.firebase.IdResponse;
import com.sortedqueue.programmercreek.database.ProgramLanguage;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.fragments.DashboardFragment;
import com.sortedqueue.programmercreek.fragments.LanguageFragment;
import com.sortedqueue.programmercreek.interfaces.DashboardNavigationListener;
import com.sortedqueue.programmercreek.interfaces.retrofit.SubmitCodeService;
import com.sortedqueue.programmercreek.network.RetrofitCreator;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    private int REQUEST_INVITE = 9999;
    private SubmitCodeService submitCodeService;

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
        dashboardTabLayout.getTabAt(2).setIcon(R.drawable.ic_top_learners);
        if( creekPreferences.getProgramLanguage().equals("")) {
            dashboardViewPager.setCurrentItem(0);
        }
        else {
            dashboardViewPager.setCurrentItem(1);
            getSupportActionBar().setTitle(getString(R.string.app_name) + " - " + creekPreferences.getProgramLanguage().toUpperCase());
        }
        dashboardViewPager.setOffscreenPageLimit(3);
        dashboardViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*if( position == 0 ) {
                    LanguageFragment.getInstance().animateProgress();
                    //LanguageFragment.getInstance().animateViews();
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        //calculateTopLearners();
        //initJavaIndex();
        //initProgramLanguages();
        //calculateUserRankings();
        //executeProgram();
        submitCodeService = RetrofitCreator.createService(SubmitCodeService.class);
        getOutputResponse(58011332);

    }

    private void getOutputResponse(int submissionId) {
        //TODO : Execute this after a delay
        Call<CodeOutputResponse> codeOutputResponseCall = submitCodeService.getOutput(
                submissionId,
                RetrofitCreator.getTokenCompilerApi(),
                true,
                true,
                true,
                true);
        codeOutputResponseCall.enqueue(new Callback<CodeOutputResponse>() {
            @Override
            public void onResponse(Call<CodeOutputResponse> call, Response<CodeOutputResponse> response) {
                Log.d(TAG, "Output Response : " + response.body().toString());
            }

            @Override
            public void onFailure(Call<CodeOutputResponse> call, Throwable t) {
                Log.e(TAG, "Output Error : " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void executeProgram() {

        Code code = new Code();
        code.setLanguage(Integer.parseInt(LanguageConstants.C_INDEX));
        code.setSourceCode("#include <stdio.h>\n" +
                "int main() {\n" +
                "\tprintf(\"Hello again!\");\n" +
                "\treturn 0;\n" +
                "}\n");
        HashMap<String, String> codeMap = new HashMap<>();
        codeMap.put("language", LanguageConstants.C_INDEX);
        codeMap.put("sourceCode", code.getSourceCode());
        /*JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("language", LanguageConstants.C_INDEX );
            jsonObject.put("sourceCode", code.getSourceCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        Call<IdResponse> idResponseCall = submitCodeService.postCode(codeMap, RetrofitCreator.getTokenCompilerApi());
        idResponseCall.enqueue(new Callback<IdResponse>() {
            @Override
            public void onResponse(Call<IdResponse> call, Response<IdResponse> response) {
                Log.d(TAG, "Execute Response : " + response.body().toString());
                getProgramOutput(response.body());
            }

            private void getProgramOutput(IdResponse body) {
                getOutputResponse(body.getId());
            }

            @Override
            public void onFailure(Call<IdResponse> call, Throwable t) {
                Log.e(TAG, "Execute Error : " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void calculateUserRankings ( ) {
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
        creekPreferences.setProgramLanguage("ada");
        new JavaProgramInserter(DashboardActivity.this).insertAlgorithmIndex();
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
                //tellYourFriends();
                onInviteClicked();
                return true;

            case R.id.action_share:
                shareInfo();
                //onInviteClicked();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
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

    @Override
    public void calculateReputation() {
        if( creekPreferences == null ) {
            creekPreferences = new CreekPreferences(DashboardActivity.this);
        }
        if( creekPreferences != null && !creekPreferences.getProgramLanguage().equals("") ) {
            CreekUserStats creekUserStats = creekPreferences.getCreekUserStats();
            if( creekUserStats != null && creekUserStats.getCreekUserReputation() == 0) {
                creekUserStats.calculateReputation();
                LanguageFragment.getInstance().animateProgress();
                new FirebaseDatabaseHandler(DashboardActivity.this).writeCreekUserStats(creekUserStats);
            }
        }
    }

    @Override
    public void showInviteDialog() {
        if( creekPreferences.getShowInviteDialog() ) {
            AuxilaryUtils.displayAppInviteDialog(DashboardActivity.this, new AuxilaryUtils.InviteDialogListener() {
                @Override
                public void onInviteClick() {
                    onInviteClicked();
                }

                @Override
                public void onLaterClick() {

                }
            });
        }

    }
}

package com.sortedqueue.programmercreek.activity;

/**
 * Created by Alok Omkar on 2016-11-26.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.CreekUser;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import com.sortedqueue.programmercreek.view.LoginSignupDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, FacebookCallback<LoginResult> {

    private static int SPLASH_TIMEOUT = 1000;
    @BindView(R.id.googleSignInButton)
    Button googleSignInButton;
    @BindView(R.id.fbLoginButton)
    LoginButton fbLoginButton;
    @BindView(R.id.signEmailButton)
    Button signEmailButton;
    @BindView(R.id.signAnonButton)
    Button signAnonButton;

    private int RC_SIGN_IN = 1000;
    private String TAG = "SplashActivity";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CreekUser creekUser;
    private CreekPreferences creekPreferences;
    private CallbackManager callbackManager;
    private Thread splashTread;
    private LoginSignupDialog loginSignupDialog;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        configureFirebaseAuth();
        ButterKnife.bind(this);

        creekPreferences = CreekApplication.getCreekPreferences();
        googleSignInButton.setVisibility(View.GONE);
        fbLoginButton.setVisibility(View.GONE);
        signEmailButton.setVisibility(View.GONE);
        signAnonButton.setVisibility(View.GONE);
        if (creekPreferences.getSignInAccount().equals("")) {

            googleSignInButton.setOnClickListener(SplashActivity.this);

            googleSignInButton.setVisibility(View.VISIBLE);
            signEmailButton.setVisibility(View.VISIBLE);
            signAnonButton.setVisibility(View.VISIBLE);

            signEmailButton.setOnClickListener(this);
            signAnonButton.setOnClickListener(this);
            configureGoogleSignup();
            //fbLoginButton.setVisibility(View.VISIBLE);
            List<String> fbPermissions = new ArrayList<>();
            fbPermissions.add("email");
            fbPermissions.add("public_profile");
            callbackManager = CallbackManager.Factory.create();
            fbLoginButton.setReadPermissions(fbPermissions);
            fbLoginButton.registerCallback(callbackManager, SplashActivity.this);
        }
        startAnimations();


    }

    private void checkAndStartApp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!creekPreferences.getSignInAccount().equals("")) {
                    startApp();
                }
            }
        });

    }

    private void startAnimations() {

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.splashLayout);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.iconImageView);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 2500) {
                        sleep(100);
                        waited += 100;
                    }
                    checkAndStartApp();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    //checkAndStartApp();
                }

            }
        };
        splashTread.start();
    }

    private void configureFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        // ...
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                storeFirebaseUserDetails(firebaseAuth);
            }
        };
    }

    private void storeFirebaseUserDetails(FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            if (!creekPreferences.getSignInAccount().equals("")) {
                Log.d(TAG, "Sign up complete");
                return;
            }
            // User is signed in
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            creekUser = new CreekUser();
            creekUser.setUserFullName(user.getDisplayName());

            if (isEmailSignup) {
                creekUser.setUserFullName(userNameEmailSignup);
            }

            if( isAnonSignup && user.getDisplayName() == null ) {
                creekUser.setUserFullName("Anonymous_" + new Date().getTime() );
                creekUser.setWasAnonUser("Yes");
            }
            if (user.getPhotoUrl() != null)
                creekUser.setUserPhotoUrl(user.getPhotoUrl().toString());
            else {
                creekUser.setUserPhotoUrl("");
            }
            if( user.getEmail() != null ) {
                creekUser.setEmailId(user.getEmail());
            }
            else {
                creekUser.setEmailId(user.getUid());
            }

            creekUser.setUserId(user.getUid());

            creekPreferences.setAccountName(creekUser.getUserFullName());
            Log.d(TAG, "Anon User name : " + creekPreferences.getAccountName());
            creekPreferences.setAccountPhoto(creekUser.getUserPhotoUrl());
            if( user.getEmail() != null && user.getEmail().trim().length() > 0 ) {
                creekPreferences.setSignInAccount(user.getEmail());
            }
            else {
                creekPreferences.setSignInAccount(user.getUid());
            }
            Log.d(TAG, "Anon User Account : " + creekPreferences.getSignInAccount());

            CommonUtils.displayProgressDialog(SplashActivity.this, "Loading");
            String email = user.getEmail();
            if( email == null ) {
                email = user.getUid();
            }
            new FirebaseDatabaseHandler(SplashActivity.this).getCreekUser(email, new FirebaseDatabaseHandler.GetCreekUserListner() {
                @Override
                public void onSuccess(CreekUser creekUser) {
                    CommonUtils.dismissProgressDialog();
                    if( creekUser.getUserId().equalsIgnoreCase("") ) {
                        creekUser.setUserId( creekPreferences.getUserId() );
                        creekUser.setWasAnonUser( creekPreferences.getIsAnonAccount() ? "Yes" : "No" );
                    }
                    startApp();
                }

                @Override
                public void onFailure(DatabaseError databaseError) {
                    //New signup
                    creekUser.save(SplashActivity.this);
                    CommonUtils.dismissProgressDialog();
                    startApp();
                }
            });
            new FirebaseDatabaseHandler(SplashActivity.this).getCreekUserStatsInBackground(new FirebaseDatabaseHandler.CreekUserStatsListener() {
                @Override
                public void onSuccess(CreekUserStats creekUserStats) {

                }

                @Override
                public void onFailure(DatabaseError databaseError) {

                }
            });

        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
            Log.d(TAG, "isAnonSignup : " + isAnonSignup);
        }
    }

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

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void configureGoogleSignup() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onClick(final View view) {
        if (!AuxilaryUtils.isNetworkAvailable()) {
            CommonUtils.displaySnackBarIndefinite(SplashActivity.this, R.string.internet_unavailable, R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View snackBarView) {
                    onClick(view);
                }
            });
            return;
        }
        switch (view.getId()) {
            case R.id.googleSignInButton:
                googleSignIn();
                break;
            case R.id.signEmailButton:
                signInEmail();
                break;
            case R.id.signAnonButton:
                signInAnonymously();
                break;
        }

    }

    boolean isAnonSignup = false;
    private void signInAnonymously() {
        isAnonSignup = true;
        CommonUtils.displayProgressDialog(SplashActivity.this, "Loading");
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInAnonymously", task.getException());
                            isAnonSignup = false;
                            Toast.makeText(SplashActivity.this, "Loading Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            isAnonSignup = true;
                        }
                        CommonUtils.dismissProgressDialog();
                        // ...
                    }
                });
    }

    private void signInEmail() {
        loginSignupDialog = new LoginSignupDialog(SplashActivity.this);
        loginSignupDialog.showDialog(new LoginSignupDialog.LoginSignupListener() {
            @Override
            public void onSuccess(String name, String email, String password) {
                if (name != null) {
                    CommonUtils.displayProgressDialog(SplashActivity.this, "Signing up...");
                    emailSignup(name, email, password);
                } else {
                    CommonUtils.displayProgressDialog(SplashActivity.this, "Logging in");
                    emailLogin(email, password);
                }
            }

            @Override
            public void onCancel() {

            }
        });
        /**/
    }

    private void emailLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(SplashActivity.this, "Authentication failed : " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            loginSignupDialog.cancelDialog();
                        }
                        CommonUtils.dismissProgressDialog();
                        // ...
                    }
                });
    }

    boolean isEmailSignup = false;
    private String userNameEmailSignup;

    private void emailSignup(String name, String email, String password) {
        isEmailSignup = true;
        this.userNameEmailSignup = name;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            isEmailSignup = false;
                            Log.w(TAG, "emailSignup:failed", task.getException());
                            Toast.makeText(SplashActivity.this, "Signup failed : " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            isEmailSignup = true;
                            loginSignupDialog.cancelDialog();
                        }
                        CommonUtils.dismissProgressDialog();

                    }
                });
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        CommonUtils.displayProgressDialog(SplashActivity.this, "Getting accounts");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            CommonUtils.dismissProgressDialog();
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                Toast.makeText(SplashActivity.this, "Sign in success.",
                        Toast.LENGTH_SHORT).show();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                Log.e(TAG, "Sign In Error : " + result.getStatus() + " : " + result.toString());
                Toast.makeText(SplashActivity.this, "Sign in failed.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        CommonUtils.displayProgressDialog(SplashActivity.this, "Authenticating account");
        creekUser = new CreekUser();
        creekUser.setUserFullName(account.getDisplayName());
        creekUser.setUserPhotoUrl(account.getPhotoUrl().toString());
        creekUser.setEmailId(account.getEmail());
        if( creekUser.getUserId().equalsIgnoreCase("") && FirebaseAuth.getInstance().getCurrentUser() != null ) {
            creekUser.setUserId(creekPreferences.getUserId());
            creekUser.setWasAnonUser("No");
        }
        creekUser.save(SplashActivity.this);
        creekPreferences.setAccountName(account.getDisplayName());
        creekPreferences.setAccountPhoto(account.getPhotoUrl().toString());
        final AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SplashActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        CommonUtils.dismissProgressDialog();
                        // ...
                    }
                });
    }

    private void startApp() {
        if (!AuxilaryUtils.isNetworkAvailable()) {
            CommonUtils.displaySnackBarIndefinite(SplashActivity.this, R.string.internet_unavailable, R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View snackBarView) {
                    startApp();
                }
            });
            return;
        }
        new FirebaseDatabaseHandler(SplashActivity.this).getCreekUser(creekPreferences.getSignInAccount(), new FirebaseDatabaseHandler.GetCreekUserListner() {
            @Override
            public void onSuccess(CreekUser creekUser) {

            }

            @Override
            public void onFailure(DatabaseError databaseError) {

            }
        });
        Intent i = new Intent(SplashActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        try {
            Toast.makeText(SplashActivity.this, "Connection failed.",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Toast.makeText(SplashActivity.this, "Login success.",
                Toast.LENGTH_SHORT).show();
        handleFBAccessToken(loginResult.getAccessToken());

    }

    private void handleFBAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SplashActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });

    }

    @Override
    public void onCancel() {
        Toast.makeText(SplashActivity.this, "Login canceled.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(FacebookException error) {
        Log.d("FBLogin", "onError : " + error.getMessage());
        error.printStackTrace();
        Toast.makeText(SplashActivity.this, "Login error.",
                Toast.LENGTH_SHORT).show();
    }
}

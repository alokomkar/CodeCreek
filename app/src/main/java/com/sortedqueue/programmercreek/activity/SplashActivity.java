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
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.CreekUser;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, FacebookCallback<LoginResult> {

    private static int SPLASH_TIMEOUT = 1000;
    @Bind(R.id.googleSignInButton)
    SignInButton googleSignInButton;
    @Bind(R.id.fbLoginButton)
    LoginButton fbLoginButton;

    private int RC_SIGN_IN = 1000;
    private String TAG = "SplashActivity";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CreekUser creekUser;
    private CreekPreferences creekPreferences;
    private CallbackManager callbackManager;
    private Thread splashTread;

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

        creekPreferences = new CreekPreferences(SplashActivity.this);
        googleSignInButton.setVisibility(View.GONE);
        fbLoginButton.setVisibility(View.GONE);
        if( creekPreferences.getSignInAccount().equals("") ) {
            googleSignInButton.setOnClickListener(SplashActivity.this);
            googleSignInButton.setVisibility(View.VISIBLE);
            configureGoogleSignup();
            fbLoginButton.setVisibility(View.VISIBLE);
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
        LinearLayout l=(LinearLayout) findViewById(R.id.splashLayout);
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
        if (user != null ) {
            if( !creekPreferences.getSignInAccount().equals("") ) {
                Log.d(TAG, "Sign up complete");
                return;
            }
            // User is signed in
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            creekUser = new CreekUser();
            creekUser.setUserFullName(user.getDisplayName());
            creekUser.setUserPhotoUrl(user.getPhotoUrl().toString());
            creekUser.setEmailId(user.getEmail());
            creekUser.save(SplashActivity.this);
            creekPreferences.setAccountName(user.getDisplayName());
            creekPreferences.setAccountPhoto(user.getPhotoUrl().toString());
            creekPreferences.setSignInAccount(user.getEmail());
            startApp();
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
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
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.googleSignInButton :
                googleSignIn();
                break;
        }

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
        }
        else {
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
                        } /*else {
                            creekPreferences.setSignInAccount(task.getResult().getUser().getEmail());
                            startApp();
                        }*/
                        CommonUtils.dismissProgressDialog();
                        // ...
                    }
                });
    }

    private void startApp() {
        Intent i = new Intent(SplashActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(SplashActivity.this, "Connection failed.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Toast.makeText(SplashActivity.this, "Login success.",
                Toast.LENGTH_SHORT).show();
        handleFBAccessToken( loginResult.getAccessToken() );

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

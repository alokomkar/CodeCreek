package com.sortedqueue.programmercreek.activity;

/**
 * Created by Alok Omkar on 2016-11-26.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.CreekUser;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static int SPLASH_TIMEOUT = 1000;
    @Bind(R.id.googleSignInButton)
    SignInButton googleSignInButton;
    private int RC_SIGN_IN = 1000;
    private String TAG = "SplashActivity";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CreekUser creekUser;
    private CreekPreferences creekPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        configureFirebaseAuth();
        ButterKnife.bind(this);
        creekPreferences = new CreekPreferences(SplashActivity.this);
        googleSignInButton.setVisibility(View.INVISIBLE);
        if (creekPreferences.getSignInAccount().equals("")) {
            googleSignInButton.setOnClickListener(this);
            googleSignInButton.setVisibility(View.VISIBLE);
            configureGoogleSignup();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startApp();
                }
            }, SPLASH_TIMEOUT);
        }

    }

    private void configureFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        // ...
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        // ...
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
        signIn();
    }

    private void signIn() {
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
                        } else {
                            creekPreferences.setSignInAccount(task.getResult().getUser().getEmail());
                            startApp();
                        }
                        CommonUtils.dismissProgressDialog();
                        // ...
                    }
                });
    }

    private void startApp() {
        Intent i = new Intent(SplashActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
        /*this.overridePendingTransition(R.anim.animation_leave,
                R.anim.animation_enter);*/
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(SplashActivity.this, "Connection failed.",
                Toast.LENGTH_SHORT).show();
    }
}

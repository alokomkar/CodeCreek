package com.sortedqueue.programmercreek.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.SplashActivity;
import com.sortedqueue.programmercreek.database.CreekUser;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import com.sortedqueue.programmercreek.view.LoginSignupDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alok Omkar on 2017-09-06.
 */

public class SignupFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.googleSignInButton)
    Button googleSignInButton;
    @BindView(R.id.signEmailButton)
    Button signEmailButton;
    Unbinder unbinder;

    private FirebaseAuth mAuth;
    private String googleIdToken;
    private LoginSignupDialog loginSignupDialog;
    private String TAG = "SignupFragment";
    private CreekPreferences creekPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_signup, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        mAuth = FirebaseAuth.getInstance();
        googleSignInButton.setOnClickListener(this);
        signEmailButton.setOnClickListener(this);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signEmailButton.callOnClick();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.googleSignInButton :
                signupGmail();
                break;
            case R.id.signEmailButton :
                signInEmail();
                break;
        }
    }

    private void signInEmail() {
        loginSignupDialog = new LoginSignupDialog(getContext(), true);
        loginSignupDialog.showDialog(new LoginSignupDialog.LoginSignupListener() {
            @Override
            public void onSuccess(String name, String email, String password) {
                emailSignup(name, email, password);
            }

            @Override
            public void onCancel() {
                loginSignupDialog.cancelDialog();
                getActivity().onBackPressed();
            }
        });
    }

    private String userFullName;
    private String userEmail;
    private void emailSignup(String name, String email, String password) {
        userFullName = name;
        userEmail = email;
        CommonUtils.displayProgressDialog(getContext(), "Signing up");
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "linkWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "linkWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            CommonUtils.dismissProgressDialog();
                            loginSignupDialog.cancelDialog();
                            getActivity().onBackPressed();
                        }

                        // ...
                    }
                });
    }

    private void updateUI(final FirebaseUser user) {
        creekPreferences = CreekApplication.Companion.getCreekPreferences();

        new FirebaseDatabaseHandler(getContext()).getCreekUser(creekPreferences.getSignInAccount(), new FirebaseDatabaseHandler.GetCreekUserListner() {
            @Override
            public void onSuccess(final CreekUser creekUser) {
                CommonUtils.dismissProgressDialog();
                creekUser.setEmailId(userEmail);
                creekUser.setUserFullName(userFullName);
                creekUser.setUserPhotoUrl("");
                creekUser.setWasAnonUser("Yes");
                creekUser.setUserId(user.getUid());
                creekUser.save(getContext());
                creekPreferences.setSignInAccount(userEmail);
                creekPreferences.setAccountName(userFullName);
                creekPreferences.setAccountPhoto("");
                creekPreferences.setUserId(creekUser.getUserId());
                creekPreferences.setAnonAccount(false);
                try {
                    new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected Void doInBackground(Void... voids) {
                            new FirebaseDatabaseHandler(getContext()).updateAnonAccountStats(creekUser);
                            return null;
                        }
                    }.execute();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }

                CommonUtils.displayToast(getContext(), "Signup Successful");
                loginSignupDialog.cancelDialog();
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                CommonUtils.dismissProgressDialog();
                CommonUtils.displayToast(getContext(), "Signup Failed, Try later");
                loginSignupDialog.cancelDialog();
                getActivity().onBackPressed();
            }
        });
    }

    private void signupGmail() {
        AuthCredential credential = GoogleAuthProvider.getCredential(getString(R.string.default_web_client_id), null);
    }
}

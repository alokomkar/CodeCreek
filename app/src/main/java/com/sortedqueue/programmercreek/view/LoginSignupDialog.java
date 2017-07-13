package com.sortedqueue.programmercreek.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.sortedqueue.programmercreek.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-01-22.
 */

public class LoginSignupDialog {

    public static final int MODE_SIGNUP = 1;
    public static final int MODE_LOGIN = 2;
    @BindView(R.id.doneButton)
    Button doneButton;
    @BindView(R.id.cancelButton)
    Button cancelButton;
    private LoginSignupListener loginSignupListener;
    @BindView(R.id.signupRadioButton)
    RadioButton signupRadioButton;
    @BindView(R.id.loginRadioButton)
    RadioButton loginRadioButton;
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_layout_name)
    TextInputLayout inputLayoutName;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.reinput_password)
    EditText reinputPassword;
    @BindView(R.id.reinput_layout_password)
    TextInputLayout reinputLayoutPassword;

    private int mode;
    private Context context;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    private String userName;
    private String userEmail;
    private String userPassword;
    private String reEnterPassword;

    public interface LoginSignupListener {
        void onSuccess(String name, String email, String password);
        void onCancel();
    }

    public LoginSignupDialog(Context context) {
        this.context = context;
        initViews();
    }

    public void cancelDialog() {
        if( alertDialog != null ) {
            alertDialog.dismiss();
        }
    }

    public void showDialog(LoginSignupListener loginSignupListener) {
        this.loginSignupListener = loginSignupListener;
        alertDialog.show();
        signupRadioButton.setChecked(true);
    }

    private void initViews() {
        builder = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.sign_in);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_signup_login, null);
        ButterKnife.bind(this, dialogView);
        builder.setView(dialogView);
        alertDialog = builder.create();

        signupRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    inputName.requestFocus();
                    inputName.setVisibility(View.VISIBLE);
                    reinputPassword.setVisibility(View.VISIBLE);
                    inputLayoutName.setVisibility(View.VISIBLE);
                    reinputLayoutPassword.setVisibility(View.VISIBLE);
                }
            }
        });

        loginRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    inputName.setVisibility(View.GONE);
                    reinputPassword.setVisibility(View.GONE);
                    inputLayoutName.setVisibility(View.GONE);
                    reinputLayoutPassword.setVisibility(View.GONE);
                }
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateAllFields()) {
                    loginSignupListener.onSuccess(userName, userEmail, userPassword);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginSignupListener.onCancel();
                alertDialog.dismiss();
            }
        });

    }

    private boolean validateAllFields() {
        userEmail = inputEmail.getText().toString();
        userPassword = inputPassword.getText().toString();
        boolean isValidated = true;
        if (signupRadioButton.isChecked()) {
            userName = inputName.getText().toString();
            reEnterPassword = reinputPassword.getText().toString();
            if (validateEditText(userName)) {
                isValidated = false;
                inputName.setError("Enter your name");
            }
            if (validateEditText(userEmail)) {
                isValidated = false;
                inputEmail.setError("Enter email");
            }
            if (validateEditText(userPassword)) {
                isValidated = false;
                inputPassword.setError("Enter password");
            }
            if (validateEditText(reEnterPassword)) {
                isValidated = false;
                reinputPassword.setError("Confirm password");
            }
            if (!isValidEmail(userEmail)) {
                isValidated = false;
                inputEmail.setError("Incorrect format for email");
            }
            if (!doPasswordsMatch()) {
                isValidated = false;
                reinputPassword.setError("Passwords don't match");
            }

        } else {

            if (validateEditText(userEmail)) {
                isValidated = false;
                inputEmail.setError("Enter email");
            }
            if (validateEditText(userPassword)) {
                isValidated = false;
                inputPassword.setError("Enter password");
            }
            if (!isValidEmail(userEmail)) {
                isValidated = false;
                inputEmail.setError("Incorrect format for email");
            }
        }
        return isValidated;
    }

    private boolean doPasswordsMatch() {
        if (!validateEditText(userPassword) && !validateEditText(reEnterPassword)) {
            return userPassword.equals(reEnterPassword);
        } else {
            return false;
        }
    }

    private boolean validateEditText(String userName) {
        return userName == null || userName.trim().length() == 0;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}

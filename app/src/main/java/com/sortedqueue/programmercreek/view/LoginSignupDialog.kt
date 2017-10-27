package com.sortedqueue.programmercreek.view

import android.content.Context
import android.content.DialogInterface
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup

import com.sortedqueue.programmercreek.R

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok Omkar on 2017-01-22.
 */

class LoginSignupDialog {
    private var signupOnly: Boolean ?= false
    @BindView(R.id.doneButton)
    internal var doneButton: Button? = null
    @BindView(R.id.cancelButton)
    internal var cancelButton: Button? = null
    private var loginSignupListener: LoginSignupListener? = null
    @BindView(R.id.signupRadioButton)
    internal var signupRadioButton: RadioButton? = null
    @BindView(R.id.loginRadioButton)
    internal var loginRadioButton: RadioButton? = null
    @BindView(R.id.input_name)
    internal var inputName: EditText? = null
    @BindView(R.id.input_layout_name)
    internal var inputLayoutName: TextInputLayout? = null
    @BindView(R.id.input_email)
    internal var inputEmail: EditText? = null
    @BindView(R.id.input_layout_email)
    internal var inputLayoutEmail: TextInputLayout? = null
    @BindView(R.id.input_password)
    internal var inputPassword: EditText? = null
    @BindView(R.id.input_layout_password)
    internal var inputLayoutPassword: TextInputLayout? = null
    @BindView(R.id.reinput_password)
    internal var reinputPassword: EditText? = null
    @BindView(R.id.reinput_layout_password)
    internal var reinputLayoutPassword: TextInputLayout? = null
    @BindView(R.id.signupRadioGroup)
    internal var signupRadioGroup: RadioGroup? = null

    private val mode: Int = 0
    private var context: Context? = null
    private var alertDialog: AlertDialog? = null
    private var builder: AlertDialog.Builder? = null

    private var userName: String? = null
    private var userEmail: String? = null
    private var userPassword: String? = null
    private var reEnterPassword: String? = null

    constructor(context: Context, signupOnly: Boolean) {
        this.context = context
        this.signupOnly = signupOnly
        initViews()
    }

    interface LoginSignupListener {
        fun onSuccess(name: String, email: String, password: String)
        fun onCancel()
    }

    constructor(context: Context) {
        this.context = context
        initViews()
    }

    fun cancelDialog() {
        if (alertDialog != null) {
            alertDialog!!.dismiss()
        }
    }

    fun showDialog(loginSignupListener: LoginSignupListener) {
        this.loginSignupListener = loginSignupListener
        alertDialog!!.show()
        signupRadioButton!!.isChecked = true
    }

    private fun initViews() {
        builder = AlertDialog.Builder(context!!)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher)
        builder!!.setTitle(R.string.sign_in)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_signup_login, null)
        ButterKnife.bind(this, dialogView)
        builder!!.setView(dialogView)
        alertDialog = builder!!.create()

        signupRadioButton!!.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                inputName!!.requestFocus()
                inputName!!.visibility = View.VISIBLE
                reinputPassword!!.visibility = View.VISIBLE
                inputLayoutName!!.visibility = View.VISIBLE
                reinputLayoutPassword!!.visibility = View.VISIBLE
            }
        }

        loginRadioButton!!.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                inputName!!.visibility = View.GONE
                reinputPassword!!.visibility = View.GONE
                inputLayoutName!!.visibility = View.GONE
                reinputLayoutPassword!!.visibility = View.GONE
            }
        }

        doneButton!!.setOnClickListener {
            if (validateAllFields()) {
                loginSignupListener!!.onSuccess(userName!!, userEmail!!, userPassword!!)
            }
        }

        cancelButton!!.setOnClickListener {
            loginSignupListener!!.onCancel()
            alertDialog!!.dismiss()
        }

        if (signupOnly!!) {
            signupRadioGroup!!.visibility = View.GONE
        }

    }

    private fun validateAllFields(): Boolean {
        userEmail = inputEmail!!.text.toString()
        userPassword = inputPassword!!.text.toString()
        var isValidated = true
        if (signupRadioButton!!.isChecked) {
            userName = inputName!!.text.toString()
            reEnterPassword = reinputPassword!!.text.toString()
            if (validateEditText(userName)) {
                isValidated = false
                inputName!!.error = "Enter your name"
            }
            if (validateEditText(userEmail)) {
                isValidated = false
                inputEmail!!.error = "Enter email"
            }
            if (validateEditText(userPassword)) {
                isValidated = false
                inputPassword!!.error = "Enter password"
            }
            if (validateEditText(reEnterPassword)) {
                isValidated = false
                reinputPassword!!.error = "Confirm password"
            }
            if (!isValidEmail(userEmail!!)) {
                isValidated = false
                inputEmail!!.error = "Incorrect format for email"
            }
            if (!doPasswordsMatch()) {
                isValidated = false
                reinputPassword!!.error = "Passwords don't match"
            }

        } else {

            if (validateEditText(userEmail)) {
                isValidated = false
                inputEmail!!.error = "Enter email"
            }
            if (validateEditText(userPassword)) {
                isValidated = false
                inputPassword!!.error = "Enter password"
            }
            if (!isValidEmail(userEmail!!)) {
                isValidated = false
                inputEmail!!.error = "Incorrect format for email"
            }
        }
        return isValidated
    }

    private fun doPasswordsMatch(): Boolean {
        if (!validateEditText(userPassword) && !validateEditText(reEnterPassword)) {
            return userPassword == reEnterPassword
        } else {
            return false
        }
    }

    private fun validateEditText(userName: String?): Boolean {
        return userName == null || userName.trim { it <= ' ' }.isEmpty()
    }

    companion object {

        val MODE_SIGNUP = 1
        val MODE_LOGIN = 2

        private fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

}

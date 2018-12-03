package com.sortedqueue.programmercreek.fragments

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.CreekUser
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekPreferences
import com.sortedqueue.programmercreek.view.LoginSignupDialog
import kotlinx.android.synthetic.main.fragment_signup.*


/**
 * Created by Alok Omkar on 2017-09-06.
 */

class SignupFragment : Fragment(), View.OnClickListener {


    private var mAuth: FirebaseAuth? = null
    private var loginSignupDialog: LoginSignupDialog? = null
    private val TAG = "SignupFragment"
    private var creekPreferences: CreekPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        googleSignInButton!!.setOnClickListener(this)
        signEmailButton!!.setOnClickListener(this)
        signEmailButton!!.callOnClick()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.googleSignInButton -> signupGmail()
            R.id.signEmailButton -> signInEmail()
        }
    }

    private fun signInEmail() {
        loginSignupDialog = LoginSignupDialog(context!!, true)
        loginSignupDialog!!.showDialog(object : LoginSignupDialog.LoginSignupListener {
            override fun onSuccess(name: String, email: String, password: String) {
                emailSignup(name, email, password)
            }

            override fun onCancel() {
                loginSignupDialog!!.cancelDialog()
                activity!!.onBackPressed()
            }
        })
    }

    private var userFullName: String? = null
    private var userEmail: String? = null
    private fun emailSignup(name: String, email: String, password: String) {
        userFullName = name
        userEmail = email
        CommonUtils.displayProgressDialog(context, "Signing up")
        val credential = EmailAuthProvider.getCredential(email, password)
        mAuth!!.currentUser!!.linkWithCredential(credential)
                .addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "linkWithCredential:success")
                        task.result?.user.apply {
                            updateUI(this)
                        }
                    } else {
                        Log.w(TAG, "linkWithCredential:failure", task.exception)
                        Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        CommonUtils.dismissProgressDialog()
                        loginSignupDialog!!.cancelDialog()
                        activity!!.onBackPressed()
                    }

                    // ...
                }
    }

    @SuppressLint("StaticFieldLeak")
    private fun updateUI(user: FirebaseUser?) {
        creekPreferences = CreekApplication.creekPreferences

        FirebaseDatabaseHandler(context!!).getCreekUser(creekPreferences!!.getSignInAccount(), object : FirebaseDatabaseHandler.GetCreekUserListner {
            override fun onSuccess(creekUser: CreekUser) {
                CommonUtils.dismissProgressDialog()
                creekUser.emailId = userEmail
                creekUser.userFullName = userFullName
                creekUser.userPhotoUrl = ""
                creekUser.wasAnonUser = "Yes"
                creekUser.userId = user?.uid
                creekUser.save(context)
                creekPreferences!!.setSignInAccount(userEmail!!)
                creekPreferences!!.setAccountName(userFullName!!)
                creekPreferences!!.setAccountPhoto("")
                creekPreferences!!.userId = creekUser.userId
                creekPreferences!!.isAnonAccount = false
                try {

                    object : AsyncTask<Void, Void, Void>() {

                        override fun doInBackground(vararg voids: Void): Void? {
                            FirebaseDatabaseHandler(context!!).updateAnonAccountStats(creekUser)
                            return null
                        }
                    }.execute()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                CommonUtils.displayToast(context!!, "Signup Successful")
                loginSignupDialog!!.cancelDialog()
                activity!!.onBackPressed()
            }

            override fun onFailure(databaseError: DatabaseError?) {
                CommonUtils.dismissProgressDialog()
                CommonUtils.displayToast(context!!, "Signup Failed, Try later")
                loginSignupDialog!!.cancelDialog()
                activity!!.onBackPressed()
            }
        })
    }

    private fun signupGmail() {
        GoogleAuthProvider.getCredential(getString(R.string.default_web_client_id), null)
    }
}

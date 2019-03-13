package com.sortedqueue.programmercreek.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.CreekUser
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekPreferences
import java.util.*

/**
 * Created by Alok on 30/10/17.
 */
class AuthPresenter( val context: Context, val authView: AuthView ) : OnCompleteListener<AuthResult> {


    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    private var creekUser: CreekUser? = null
    private var creekPreferences: CreekPreferences? = null
    internal var isAnonSignup = false

    init {
        creekPreferences = CreekApplication.creekPreferences
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth -> storeFirebaseUserDetails(firebaseAuth) }
        if (creekPreferences!!.getSignInAccount() == "") {
            authView.freshSignUp()
        }
    }


    private val TAG: String? = AuthPresenter::class.java.simpleName

    private fun storeFirebaseUserDetails(firebaseAuth: FirebaseAuth) {
        val user = firebaseAuth.currentUser
        if (user != null) {
            if (creekPreferences!!.getSignInAccount() != "") {
                Log.d(TAG, "Sign up complete")
                return
            }
            // User is signed in
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.uid)
            creekUser = CreekUser()
            creekUser!!.userFullName = user.displayName ?: ""

            if (isEmailSignup) {
                creekUser!!.userFullName = userNameEmailSignup ?: ""
            }

            if (isAnonSignup && user.displayName == null) {
                creekUser!!.userFullName = "Anonymous_" + Date().time
                creekUser!!.wasAnonUser = "Yes"
            }
            if (user.photoUrl != null)
                creekUser!!.userPhotoUrl = user.photoUrl!!.toString()
            else {
                creekUser!!.userPhotoUrl = ""
            }
            if (user.email != null) {
                creekUser!!.emailId = user.email ?: ""
            } else {
                creekUser!!.emailId = user.uid
            }

            creekUser!!.userId = user.uid

            creekPreferences!!.setAccountName( creekUser?.userFullName ?: "" )
            Log.d(TAG, "Anon User name : " + creekPreferences?.getAccountName())
            creekPreferences!!.setAccountPhoto(  creekUser?.userPhotoUrl ?: "" )
            if (user.email != null && user.email!!.trim { it <= ' ' }.isNotEmpty()) {
                creekPreferences!!.setSignInAccount( user.email!! )
            } else {
                creekPreferences!!.setSignInAccount( user.uid )
            }
            Log.d(TAG, "Anon User Account : " + creekPreferences!!.getSignInAccount())


            var email = user.email
            if (email == null) {
                email = user.uid
            }

            //Delegate getting and saving user stats to intent service
            SaveUserIntentService.startService(context, email, creekUser)
            authView.hideProgress()
            authView.startApp()




        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out")
            Log.d(TAG, "isAnonSignup : " + isAnonSignup)
        }
    }



    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.id!!)
        authView.showProgress(R.string.authenticating_account)
        creekUser = CreekUser()
        creekUser!!.userFullName = account.displayName ?: ""
        creekUser!!.userPhotoUrl = account.photoUrl!!.toString()
        creekUser!!.emailId = account.email ?: ""
        if (creekUser!!.userId.equals("", ignoreCase = true) && FirebaseAuth.getInstance().currentUser != null) {
            creekUser!!.userId = creekPreferences!!.userId
            creekUser!!.wasAnonUser = "No"
        }
        creekUser!!.save(context)
        creekPreferences!!.setAccountName( account.displayName!! )
        creekPreferences!!.setAccountPhoto(  account.photoUrl!!.toString() )
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener( this )

    }

    override fun onComplete(task: Task<AuthResult>) {
        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful)

        // If sign in fails, display a message to the user. If sign in succeeds
        // the auth state listener will be notified and logic to handle the
        // signed in user can be handled in the listener.
        if (!task.isSuccessful) {
            Log.w(TAG, "signInWithCredential", task.exception)
            authView.onError("Authentication failed.")
        }
        authView.hideProgress()
    }

    fun onStart() {
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    fun onStop() {
        if (mAuthListener != null) {
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }

    fun signInAnonymously() {
        isAnonSignup = true
        CommonUtils.displayProgressDialog(context, "Loading")
        mAuth!!.signInAnonymously()
                .addOnCompleteListener { task ->
                    Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful)

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        Log.w(TAG, "signInAnonymously", task.exception)
                        isAnonSignup = false
                        Toast.makeText(context, "Loading Failed",
                                Toast.LENGTH_SHORT).show()
                    } else {
                        isAnonSignup = true
                    }
                    authView.hideProgress()
                    // ...
                }

    }

    internal var isEmailSignup = false
    private var userNameEmailSignup: String? = null

    fun emailSignup(name: String, email: String, password: String) {
        isEmailSignup = true
        this.userNameEmailSignup = name
        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        isEmailSignup = false
                        Log.w(TAG, "emailSignup:failed", task.exception)
                        Toast.makeText(context, "Signup failed : " + task.exception!!.message,
                                Toast.LENGTH_SHORT).show()
                    } else {
                        isEmailSignup = true
                        authView.cancelLoginDialog()
                    }
                    authView.hideProgress()
                }
    }

    fun emailLogin(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful)

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        Log.w(TAG, "signInWithEmail:failed", task.exception)
                        Toast.makeText(context, "Authentication failed : " + task.exception!!.message,
                                Toast.LENGTH_SHORT).show()
                    } else {
                        authView.cancelLoginDialog()
                    }
                    authView.hideProgress()
                    // ...
                }
    }

    fun handleFBAccessToken(accessToken: AccessToken?) {
        val credential = FacebookAuthProvider.getCredential(accessToken!!.token)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener{ task ->
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful)

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        Log.w(TAG, "signInWithCredential", task.exception)
                        Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                    // ...
                }
    }
}
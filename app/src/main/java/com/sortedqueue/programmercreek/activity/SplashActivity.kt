package com.sortedqueue.programmercreek.activity

/**
 * Created by Alok Omkar on 2016-11-26.
 */

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast

import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.CreekUser
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekAnalytics
import com.sortedqueue.programmercreek.util.CreekPreferences
import com.sortedqueue.programmercreek.view.LoginSignupDialog

import java.util.ArrayList
import java.util.Date

import butterknife.BindView
import butterknife.ButterKnife
import com.sortedqueue.programmercreek.view.LoginSignupDialog.LoginSignupListener
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class SplashActivity : AppCompatActivity(), View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, FacebookCallback<LoginResult> {
    @BindView(R.id.googleSignInButton)
    internal var googleSignInButton: Button? = null
    @BindView(R.id.fbLoginButton)
    internal var fbLoginButton: LoginButton? = null
    @BindView(R.id.signEmailButton)
    internal var signEmailButton: Button? = null
    @BindView(R.id.signAnonButton)
    internal var signAnonButton: Button? = null

    private val RC_SIGN_IN = 1000
    private val TAG = "SplashActivity"
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private var creekUser: CreekUser? = null
    private var creekPreferences: CreekPreferences? = null
    private var callbackManager: CallbackManager? = null
    private var splashTread: Thread? = null
    private var loginSignupDialog: LoginSignupDialog? = null

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        configureFirebaseAuth()
        ButterKnife.bind(this)

        creekPreferences = CreekApplication.creekPreferences
        googleSignInButton!!.visibility = View.GONE
        fbLoginButton!!.visibility = View.GONE
        signEmailButton!!.visibility = View.GONE
        signAnonButton!!.visibility = View.GONE
        if (creekPreferences!!.getSignInAccount() == "") {
            CreekAnalytics.logEvent(TAG, "Fresh Signup")
            googleSignInButton!!.setOnClickListener(this@SplashActivity)

            googleSignInButton!!.visibility = View.VISIBLE
            signEmailButton!!.visibility = View.VISIBLE
            signAnonButton!!.visibility = View.VISIBLE

            signEmailButton!!.setOnClickListener(this)
            signAnonButton!!.setOnClickListener(this)
            configureGoogleSignup()
            //fbLoginButton.setVisibility(View.VISIBLE);
            val fbPermissions = ArrayList<String>()
            fbPermissions.add("email")
            fbPermissions.add("public_profile")
            callbackManager = CallbackManager.Factory.create()
            fbLoginButton!!.setReadPermissions(fbPermissions)
            fbLoginButton!!.registerCallback(callbackManager, this@SplashActivity)
        }
        startAnimations()


    }

    private fun checkAndStartApp() {
        runOnUiThread {
            if (creekPreferences!!.getSignInAccount() != "") {
                startApp()
            }
        }

    }

    private fun startAnimations() {

        var anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
        anim.reset()
        val l = findViewById(R.id.splashLayout) as LinearLayout
        l.clearAnimation()
        l.startAnimation(anim)

        anim = AnimationUtils.loadAnimation(this, R.anim.translate)
        anim.reset()
        val iv = findViewById(R.id.iconImageView) as ImageView
        iv.clearAnimation()
        iv.startAnimation(anim)

        splashTread = object : Thread() {
            override fun run() {
                try {
                    var waited = 0
                    // Splash screen pause time
                    while (waited < 2500) {
                        Thread.sleep(100)
                        waited += 100
                    }
                    checkAndStartApp()
                } catch (e: InterruptedException) {
                    // do nothing
                } finally {
                    //checkAndStartApp();
                }

            }
        }
        splashTread!!.start()
    }

    private fun configureFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance()
        // ...
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth -> storeFirebaseUserDetails(firebaseAuth) }
    }

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
            creekUser!!.userFullName = user.displayName

            if (isEmailSignup) {
                creekUser!!.userFullName = userNameEmailSignup
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
                creekUser!!.emailId = user.email
            } else {
                creekUser!!.emailId = user.uid
            }

            creekUser!!.userId = user.uid

            creekPreferences!!.setAccountName( creekUser!!.userFullName )
            Log.d(TAG, "Anon User name : " + creekPreferences!!.getAccountName())
            creekPreferences!!.setAccountPhoto(  creekUser!!.userPhotoUrl )
            if (user.email != null && user.email!!.trim { it <= ' ' }.isNotEmpty()) {
                creekPreferences!!.setSignInAccount( user.email!! )
            } else {
                creekPreferences!!.setSignInAccount( user.uid )
            }
            Log.d(TAG, "Anon User Account : " + creekPreferences!!.getSignInAccount())

            CommonUtils.displayProgressDialog(this@SplashActivity, "Loading")
            var email = user.email
            if (email == null) {
                email = user.uid
            }
            FirebaseDatabaseHandler(this@SplashActivity).getCreekUser(email, object : FirebaseDatabaseHandler.GetCreekUserListner {
                override fun onSuccess(creekUser: CreekUser) {
                    CommonUtils.dismissProgressDialog()
                    if (creekUser.userId.equals("", ignoreCase = true)) {
                        creekUser.userId = creekPreferences!!.userId
                        creekUser.wasAnonUser = if (creekPreferences!!.isAnonAccount) "Yes" else "No"
                    }
                    startApp()
                }

                override fun onFailure(databaseError: DatabaseError?) {
                    //New signup
                    creekUser!!.save(this@SplashActivity)
                    CommonUtils.dismissProgressDialog()
                    startApp()
                }
            })
            FirebaseDatabaseHandler(this@SplashActivity).getCreekUserStatsInBackground(object : FirebaseDatabaseHandler.CreekUserStatsListener {
                override fun onSuccess(creekUserStats: CreekUserStats) {

                }

                override fun onFailure(databaseError: DatabaseError?) {

                }
            })

        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out")
            Log.d(TAG, "isAnonSignup : " + isAnonSignup)
        }
    }

    override fun onResume() {
        super.onResume()
        CreekApplication.instance.isAppRunning = true
    }

    override fun onPause() {
        super.onPause()
        CreekApplication.instance.isAppRunning = false
    }

    public override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    public override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }

    private fun configureGoogleSignup() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    override fun onClick(view: View) {
        if (!AuxilaryUtils.isNetworkAvailable) {
            CommonUtils.displaySnackBarIndefinite(this@SplashActivity, R.string.internet_unavailable, R.string.retry, object : View.OnClickListener {
                override fun onClick(snackBarView: View) {
                    onClick(view)
                }
            })
            return
        }
        when (view.id) {
            R.id.googleSignInButton -> googleSignIn()
            R.id.signEmailButton -> signInEmail()
            R.id.signAnonButton -> signInAnonymously()
        }

    }

    internal var isAnonSignup = false
    private fun signInAnonymously() {
        isAnonSignup = true
        CommonUtils.displayProgressDialog(this@SplashActivity, "Loading")
        mAuth!!.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful)

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        Log.w(TAG, "signInAnonymously", task.exception)
                        isAnonSignup = false
                        Toast.makeText(this@SplashActivity, "Loading Failed",
                                Toast.LENGTH_SHORT).show()
                    } else {
                        isAnonSignup = true
                    }
                    CommonUtils.dismissProgressDialog()
                    // ...
                }
    }

    private fun signInEmail() {
        loginSignupDialog = LoginSignupDialog(this@SplashActivity)

        loginSignupDialog!!.showDialog( object : LoginSignupListener {

            override fun onSuccess(name: String, email: String, password: String) {
                if (name != null) {
                    CommonUtils.displayProgressDialog(this@SplashActivity, "Signing up...")
                    emailSignup(name, email, password)
                } else {
                    CommonUtils.displayProgressDialog(this@SplashActivity, "Logging in")
                    emailLogin(email, password)
                }
            }

            override fun onCancel() {

            }
        })
        /**/
    }

    private fun emailLogin(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful)

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        Log.w(TAG, "signInWithEmail:failed", task.exception)
                        Toast.makeText(this@SplashActivity, "Authentication failed : " + task.exception!!.message,
                                Toast.LENGTH_SHORT).show()
                    } else {
                        loginSignupDialog!!.cancelDialog()
                    }
                    CommonUtils.dismissProgressDialog()
                    // ...
                }
    }

    internal var isEmailSignup = false
    private var userNameEmailSignup: String? = null

    private fun emailSignup(name: String, email: String, password: String) {
        isEmailSignup = true
        this.userNameEmailSignup = name
        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        isEmailSignup = false
                        Log.w(TAG, "emailSignup:failed", task.exception)
                        Toast.makeText(this@SplashActivity, "Signup failed : " + task.exception!!.message,
                                Toast.LENGTH_SHORT).show()
                    } else {
                        isEmailSignup = true
                        loginSignupDialog!!.cancelDialog()
                    }
                    CommonUtils.dismissProgressDialog()
                }
    }

    private fun googleSignIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
        CommonUtils.displayProgressDialog(this@SplashActivity, "Getting accounts")
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            CommonUtils.dismissProgressDialog()
            if (result.isSuccess) {
                // Google Sign In was successful, authenticate with Firebase
                val account = result.signInAccount
                Toast.makeText(this@SplashActivity, "Sign in success.",
                        Toast.LENGTH_SHORT).show()
                firebaseAuthWithGoogle(account!!)
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                Log.e(TAG, "Sign In Error : " + result.status + " : " + result.toString())
                Toast.makeText(this@SplashActivity, "Sign in failed.",
                        Toast.LENGTH_SHORT).show()
            }
        } else {
            callbackManager!!.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.id!!)
        CommonUtils.displayProgressDialog(this@SplashActivity, "Authenticating account")
        creekUser = CreekUser()
        creekUser!!.userFullName = account.displayName
        creekUser!!.userPhotoUrl = account.photoUrl!!.toString()
        creekUser!!.emailId = account.email
        if (creekUser!!.userId.equals("", ignoreCase = true) && FirebaseAuth.getInstance().currentUser != null) {
            creekUser!!.userId = creekPreferences!!.userId
            creekUser!!.wasAnonUser = "No"
        }
        creekUser!!.save(this@SplashActivity)
        creekPreferences!!.setAccountName( account.displayName!! )
        creekPreferences!!.setAccountPhoto(  account.photoUrl!!.toString() )
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful)

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        Log.w(TAG, "signInWithCredential", task.exception)
                        Toast.makeText(this@SplashActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                    CommonUtils.dismissProgressDialog()
                    // ...
                }
    }

    private fun startApp() {
        if (!AuxilaryUtils.isNetworkAvailable) {
            CommonUtils.displaySnackBarIndefinite(this@SplashActivity, R.string.internet_unavailable, R.string.retry, object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    startApp()
                }

            } )
            return
        }
        FirebaseDatabaseHandler(this@SplashActivity).getCreekUser(creekPreferences!!.getSignInAccount(), object : FirebaseDatabaseHandler.GetCreekUserListner {
            override fun onSuccess(creekUser: CreekUser) {

            }

            override fun onFailure(databaseError: DatabaseError?) {

            }
        })
        val i = Intent(this@SplashActivity, DashboardActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        try {
            Toast.makeText(this@SplashActivity, "Connection failed.",
                    Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onSuccess(loginResult: LoginResult) {
        Toast.makeText(this@SplashActivity, "Login success.",
                Toast.LENGTH_SHORT).show()
        handleFBAccessToken(loginResult.accessToken)

    }

    private fun handleFBAccessToken(accessToken: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful)

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        Log.w(TAG, "signInWithCredential", task.exception)
                        Toast.makeText(this@SplashActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                    // ...
                }

    }

    override fun onCancel() {
        Toast.makeText(this@SplashActivity, "Login canceled.",
                Toast.LENGTH_SHORT).show()
    }

    override fun onError(error: FacebookException) {
        Log.d("FBLogin", "onError : " + error.message)
        error.printStackTrace()
        Toast.makeText(this@SplashActivity, "Login error.",
                Toast.LENGTH_SHORT).show()
    }

    companion object {

        private val SPLASH_TIMEOUT = 1000
    }
}



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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.auth.AuthPresenter
import com.sortedqueue.programmercreek.auth.AuthView
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekAnalytics
import com.sortedqueue.programmercreek.v2.data.remote.PCFirebaseHandler
import com.sortedqueue.programmercreek.v2.ui.HomeActivity
import com.sortedqueue.programmercreek.view.LoginSignupDialog
import com.sortedqueue.programmercreek.view.LoginSignupDialog.LoginSignupListener
import kotlinx.android.synthetic.main.activity_splash_screen.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*

@Suppress("PrivatePropertyName")
class SplashActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener, FacebookCallback<LoginResult>, AuthView {

    private val RC_SIGN_IN = 1000
    private val TAG = "SplashActivity"
    private var mGoogleApiClient: GoogleApiClient? = null
    private var callbackManager: CallbackManager? = null
    private var splashTread: Thread? = null
    private var loginSignupDialog: LoginSignupDialog? = null

    lateinit var mAuthPresenter : AuthPresenter

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        googleSignInButton.visibility = View.GONE
        fbLoginButton.visibility = View.GONE
        signEmailButton.visibility = View.GONE
        signAnonButton.visibility = View.GONE

        mAuthPresenter = AuthPresenter(this, this)
        startAnimations()


    }

    override fun freshSignUp() {

        CreekAnalytics.logEvent(TAG, "Fresh Signup")

        googleSignInButton.visibility = View.VISIBLE
        signEmailButton.visibility = View.VISIBLE
        signAnonButton.visibility = View.VISIBLE

        signEmailButton.setOnClickListener{ signInEmail() }
        signAnonButton.setOnClickListener{ signInAnonymously() }
        googleSignInButton.setOnClickListener{ googleSignIn() }

        configureGoogleSignup()
        //fbLoginButton.setVisibility(View.VISIBLE);

        val fbPermissions = ArrayList<String>()
        fbPermissions.add("email")
        fbPermissions.add("public_profile")
        callbackManager = CallbackManager.Factory.create()
        fbLoginButton.setReadPermissions(fbPermissions)
        fbLoginButton.registerCallback(callbackManager, this@SplashActivity)

    }

    private fun checkAndStartApp() {
        runOnUiThread {
            if (CreekApplication.creekPreferences!!.getSignInAccount() != "") {
                startApp()
            }
        }

    }

    private fun startAnimations() {

        var anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
        anim.reset()
        val l = findViewById<LinearLayout>(R.id.splashLayout)
        l.clearAnimation()
        l.startAnimation(anim)

        anim = AnimationUtils.loadAnimation(this, R.anim.translate)
        anim.reset()
        val iv = findViewById<ImageView>(R.id.iconImageView)
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
        mAuthPresenter.onStart()

    }

    public override fun onStop() {
        super.onStop()
        mAuthPresenter.onStop()
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


    private fun signInAnonymously() {
        mAuthPresenter.signInAnonymously()
    }

    private fun signInEmail() {
        loginSignupDialog = LoginSignupDialog(this@SplashActivity)

        loginSignupDialog!!.showDialog( object : LoginSignupListener {

            override fun onSuccess(name: String, email: String, password: String) {
                if (!name.isEmpty()) {
                    CommonUtils.displayProgressDialog(this@SplashActivity, "Signing up...")
                    mAuthPresenter.emailSignup(name, email, password)
                } else {
                    CommonUtils.displayProgressDialog(this@SplashActivity, "Logging in")
                    mAuthPresenter.emailLogin(email, password)
                }
            }

            override fun onCancel() {
                loginSignupDialog!!.cancelDialog()
            }
        })
    }


    private fun googleSignIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
        CommonUtils.displayProgressDialog(this@SplashActivity, "Getting accounts")
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
                mAuthPresenter.firebaseAuthWithGoogle(account!!)
            } else {
                Log.e(TAG, "Sign In Error : " + result.status + " : " + result.toString())
                Toast.makeText(this@SplashActivity, "Sign in failed.",
                        Toast.LENGTH_SHORT).show()
            }
        } else {
            callbackManager!!.onActivityResult(requestCode, resultCode, data)
        }
    }


    override fun startApp() {
        PCFirebaseHandler( this.application )
        startActivity( Intent(this@SplashActivity, HomeActivity::class.java) )
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
        mAuthPresenter.handleFBAccessToken(loginResult.accessToken)
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

    override fun showProgress(messageId: Int) {
        CommonUtils.displayProgressDialog(this@SplashActivity, R.string.loading)
    }

    override fun hideProgress() {
        CommonUtils.dismissProgressDialog()
    }

    override fun onError(error: String) {
        CommonUtils.displayToast(this@SplashActivity, error)
    }

    override fun cancelLoginDialog() {
        if( loginSignupDialog != null )
            loginSignupDialog!!.cancelDialog()
    }

}



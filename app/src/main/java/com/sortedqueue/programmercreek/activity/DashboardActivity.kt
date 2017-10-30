package com.sortedqueue.programmercreek.activity


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.appinvite.AppInviteInvitation
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.DashboardPagerAdapter
import com.sortedqueue.programmercreek.asynctask.JavaProgramInserter
import com.sortedqueue.programmercreek.billing.anjlab.AnjLabBillingPresenter
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.CreekUserDB
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramLanguage
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.UserProgramDetails
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.fragments.DashboardFragment
import com.sortedqueue.programmercreek.fragments.LanguageFragment
import com.sortedqueue.programmercreek.fragments.QuickReferenceFragment
import com.sortedqueue.programmercreek.fragments.SearchFragment
import com.sortedqueue.programmercreek.fragments.SignupFragment
import com.sortedqueue.programmercreek.interfaces.DashboardNavigationListener
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekAnalytics
import com.sortedqueue.programmercreek.util.CreekPreferences
import com.sortedqueue.programmercreek.util.FileUtils
import com.sortedqueue.programmercreek.util.FileUtils.DownloadFileListner
import com.sortedqueue.programmercreek.util.PermissionUtils
import com.sortedqueue.programmercreek.view.UserProgramDialog

import java.io.File
import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class DashboardActivity : AppCompatActivity(), DashboardNavigationListener, DownloadFileListner, View.OnClickListener, FirebaseDatabaseHandler.ConfirmUserProgram {

    //@BindView(R.id.adView)
    //AdView adView;
    @BindView(R.id.dashboardViewPager)
    internal var dashboardViewPager: ViewPager? = null
    @BindView(R.id.dashboardTabLayout)
    internal var dashboardTabLayout: TabLayout? = null
    @BindView(R.id.toolbar)
    internal var toolbar: Toolbar? = null
    @BindView(R.id.createPresentationFAB)
    internal var createPresentationFAB: FloatingActionButton? = null
    @BindView(R.id.webView)
    internal var webView: WebView? = null
    @BindView(R.id.addCodeTextView)
    internal var addCodeTextView: TextView? = null
    @BindView(R.id.addCodeFAB)
    internal var addCodeFAB: FloatingActionButton? = null
    @BindView(R.id.addUserCodeFAB)
    internal var addUserCodeFAB: FloatingActionButton? = null
    @BindView(R.id.addCodeLayout)
    internal var addCodeLayout: LinearLayout? = null
    @BindView(R.id.addPptTextView)
    internal var addPptTextView: TextView? = null
    @BindView(R.id.addPptLayout)
    internal var addPptLayout: LinearLayout? = null
    @BindView(R.id.fabLayout)
    internal var fabLayout: LinearLayout? = null
    @BindView(R.id.main_content)
    internal var mainContent: RelativeLayout? = null
    @BindView(R.id.reputationProgressBar)
    internal var reputationProgressBar: ProgressBar? = null
    @BindView(R.id.reputationTextView)
    internal var reputationTextView: TextView? = null
    @BindView(R.id.progressLayout)
    internal var progressLayout: LinearLayout? = null
    @BindView(R.id.languageSelectionTextView)
    internal var languageSelectionTextView: TextView? = null
    @BindView(R.id.selectedLanguageCardView)
    internal var selectedLanguageCardView: CardView? = null
    @BindView(R.id.container)
    internal var container: FrameLayout? = null
    @BindView(R.id.upgradeTextView)
    internal var upgradeTextView: TextView? = null
    @BindView(R.id.laterTextView)
    internal var laterTextView: TextView? = null
    @BindView(R.id.premiumLayout)
    internal var premiumLayout: RelativeLayout? = null


    private val TAG = javaClass.simpleName
    private var creekPreferences: CreekPreferences? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private val REQUEST_INVITE = 9999
    private val REQUEST_CODE_SEARCH = 1000
    private val alertDialog: AlertDialog? = null
    private val REQUEST_DOWNLOAD_FILE = 101
    private val REQUEST_DOWNLOAD_FILE_PERMISSION = 1234
    private val REQUEST_IMPORT_FILE_PERMISSION = 1334
    private var filepath: String? = null
    private var handler: Handler? = null
    private var creekUserStats: CreekUserStats? = null
    private var runnable: Runnable? = null
    //private BillingPresenter billingPresenter;
    private var anjLabBillingPresenter: AnjLabBillingPresenter? = null
    private var signUpItem: MenuItem? = null

    private fun logDebugMessage(message: String) {
        Log.d(TAG, message)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_dashboard)
        ButterKnife.bind(this)
        setupToolbar()
        creekPreferences = CreekApplication.creekPreferences
        if (!creekPreferences!!.isPremiumUser) {
            if (creekPreferences!!.showUpgradeDialog()) {
                AnimationUtils.slideInToLeft(premiumLayout)
            } else {
                creekPreferences!!.setUpgradeDialog(true)
            }
        }

        configureGoogleSignup()
        checkForDBUpdates()
        checkBillingInformation()

        if (creekPreferences!!.programLanguage != "") {
            AuxilaryUtils.scheduleNotification(this@DashboardActivity)
        }
        //adView.setVisibility(View.GONE);

        //initAds();
        createPresentationFAB!!.setOnClickListener(this)
        addPptTextView!!.visibility = View.GONE
        addCodeFAB!!.visibility = View.GONE
        addCodeTextView!!.visibility = View.GONE
        addUserCodeFAB!!.visibility = View.GONE
        addCodeFAB!!.setOnClickListener(this)
        addCodeTextView!!.setOnClickListener(this)
        addPptTextView!!.setOnClickListener(this)
        addCodeLayout!!.setOnClickListener(this)
        addPptLayout!!.setOnClickListener(this)
        addUserCodeFAB!!.setOnClickListener(this)
        selectedLanguageCardView!!.setOnClickListener(this)
        fabLayout!!.visibility = View.GONE
        dashboardViewPager!!.adapter = DashboardPagerAdapter(supportFragmentManager, this@DashboardActivity)
        dashboardTabLayout!!.setupWithViewPager(dashboardViewPager)
        //dashboardTabLayout.getTabAt(0).setIcon(R.drawable.ic_account_box_white_24dp);
        dashboardTabLayout!!.getTabAt(0)!!.setIcon(R.drawable.ic_dns_white_24dp)
        dashboardTabLayout!!.getTabAt(1)!!.setIcon(R.drawable.ic_top_learners)
        dashboardTabLayout!!.getTabAt(2)!!.setIcon(R.drawable.ic_add_to_queue_white_24dp)
        //dashboardTabLayout.getTabAt(3).setIcon(R.drawable.ic_view_carousel_white_36dp);
        if (creekPreferences!!.programLanguage == "") {
            showLanguageFragment()
            languageSelectionTextView!!.text = "Select Language"
        } else {
            languageSelectionTextView!!.text = creekPreferences!!.programLanguage.toUpperCase()
            dashboardViewPager!!.currentItem = 0
            supportActionBar!!.title = getString(R.string.app_name) + " - " + creekPreferences!!.programLanguage.toUpperCase()
        }
        dashboardViewPager!!.offscreenPageLimit = dashboardTabLayout!!.tabCount
        dashboardViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 2)
                    AnimationUtils.enterReveal(addUserCodeFAB)
                else
                    AnimationUtils.exitRevealGone(addUserCodeFAB)

                /*if( searchItem != null ) {
                    searchItem.setVisible( position == 2 );
                }*/
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        upgradeTextView!!.setOnClickListener(this)
        laterTextView!!.setOnClickListener(this)

        //new FirebaseDatabaseHandler(DashboardActivity.this).searchPrograms("Swap");
        //new FirebaseDatabaseHandler(DashboardActivity.this).updateAdSettings(1);
        FirebaseDatabaseHandler(this@DashboardActivity).getAdSettings()
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
        //calculateTopLearners();
        //initJavaIndex();
        //initProgramLanguages();
        //calculateUserRankings();
        //executeProgram();
        //getOutputResponse(58011332);

    }

    @SuppressLint("StaticFieldLeak")
    private fun checkBillingInformation() {

        object : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {
                //billingPresenter = new BillingPresenter(DashboardActivity.this);
                anjLabBillingPresenter = AnjLabBillingPresenter(this@DashboardActivity)
                return null
            }
        }.execute()

    }


    private fun checkForDBUpdates() {
        CommonUtils.displayProgressDialog(this@DashboardActivity, "Checking for updates")
        FirebaseDatabaseHandler(this@DashboardActivity).readCreekUserDB(object : FirebaseDatabaseHandler.GetCreekUserDBListener {
            override fun onSuccess(creekUserDB: CreekUserDB) {
                CommonUtils.dismissProgressDialog()
            }

            override fun onError(databaseError: DatabaseError) {
                Log.d(TAG, databaseError.message)
                CommonUtils.dismissProgressDialog()
            }
        })
        if (!creekPreferences!!.isPremiumUser) {
            FirebaseDatabaseHandler(this@DashboardActivity).verifyPurchase(object : FirebaseDatabaseHandler.AnjVerifyPurchaseListener {
                override fun onSuccess(purchase: com.sortedqueue.programmercreek.billing.anjlab.TransactionDetails) {

                }

                override fun onError(e: Exception?) {

                }
            })
        }

    }


    override fun onProgressStatsUpdate(points: Int) {
        progressLayout!!.visibility = View.VISIBLE
        animateProgress(points)
    }

    override fun hideLanguageFragment() {
        try {
            languageSelectionTextView!!.text = creekPreferences!!.programLanguage.toUpperCase()
            DashboardFragment.instance.animateViews()
            supportFragmentManager.popBackStack()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private var progressBarStatus: Int = 0

    fun animateProgress(points: Int) {
        try {
            if (reputationProgressBar != null) {

                if (handler == null) {
                    handler = Handler()
                }
                if (creekPreferences == null) {
                    creekPreferences = CreekApplication.creekPreferences
                }
                creekUserStats = creekPreferences!!.creekUserStats
                if (creekUserStats == null) {
                    reputationProgressBar!!.visibility = View.GONE
                    reputationTextView!!.visibility = View.GONE
                    progressLayout!!.visibility = View.GONE
                    return
                }
                val progress = creekUserStats!!.creekUserReputation % 100
                reputationProgressBar!!.visibility = View.VISIBLE
                reputationTextView!!.visibility = View.VISIBLE
                runnable = Runnable {
                    progressBarStatus = 0
                    while (progressBarStatus <= progress) {

                        handler!!.post {
                            if (reputationProgressBar != null) {
                                reputationProgressBar!!.progress = progressBarStatus

                                reputationTextView!!.text = "You've gained " + points + "xp\n" + progressBarStatus + "% Complete"
                                val level = creekUserStats!!.creekUserReputation / 100
                                if (level > 0) {
                                    reputationTextView!!.text = "You've gained " + points + "xp\n" + progressBarStatus + "% Complete : Level : " + level
                                }
                            }
                        }
                        try {
                            Thread.sleep(40)
                        } catch (ex: Exception) {
                        }

                        progressBarStatus++
                    }
                    handler!!.postDelayed({ progressLayout!!.visibility = View.GONE }, 1500)
                }
                Thread(runnable).start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (progressLayout != null) {
                progressLayout!!.visibility = View.GONE
            }
        }

    }

    private fun calculateUserRankings() {
        FirebaseDatabaseHandler(this@DashboardActivity).getAllCreekUserStatsInBackground()
    }


    private fun initProgramLanguages() {
        val firebaseDatabaseHandler = FirebaseDatabaseHandler(this@DashboardActivity)
        val programLanguage = ProgramLanguage()
        programLanguage.description = "Analysis and design of algorithms - An alogorithm is a self contained " +
                "sequence of actions to be performed. Algorithms can perform calculations, data processing and automated" +
                " reasoning tasks."
        programLanguage.programLanguage = "Analysis and Design of Algorithms"
        programLanguage.isProgramsOnly = "true"
        programLanguage.languageId = "ADA"
        firebaseDatabaseHandler.writeProgramLanguage(programLanguage)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(R.string.app_name)
    }


    private fun initJavaIndex() {
        JavaProgramInserter(this@DashboardActivity).insertCodeLabPrograms()
    }

    override fun onResume() {
        super.onResume()
        CreekApplication.instance.isAppRunning = true
        calculateReputation()
    }

    override fun onPause() {
        super.onPause()
        CreekApplication.instance.isAppRunning = false
    }

    /**
     * Method to initialize UI.
     */


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!AuxilaryUtils.isNetworkAvailable) {
            CommonUtils.displaySnackBarIndefinite(this@DashboardActivity, R.string.internet_unavailable, R.string.retry,
                    View.OnClickListener { onOptionsItemSelected(item) }
                     )
            return true
        }

        when (item.itemId) {

            android.R.id.home -> {
                onBackPressed()
                return true
            }

            R.id.action_signup -> {
                showSignupFragment()
                return true
            }

            R.id.action_link -> {
                if (creekPreferences!!.isAnonAccount) {
                    val spashIntent = Intent(this@DashboardActivity, SplashActivity::class.java)
                    spashIntent.putExtra(CreekPreferences.KEY_ANON_ACCOUNT, true)
                    startActivity(spashIntent)
                    finish()
                } else {
                    CommonUtils.displaySnackBar(this@DashboardActivity, R.string.register_account_for_tour_users_only)
                }
                return true
            }

            R.id.action_about -> {
                val intent = Intent(this@DashboardActivity, AboutActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.action_invite -> {
                //tellYourFriends();
                onInviteClicked()
                return true
            }

            R.id.action_share -> {
                shareInfo()
                //onInviteClicked();
                return true
            }

            R.id.action_upgrade -> {
                //billingPresenter.onUpgradeAppButtonClicked();
                anjLabBillingPresenter!!.purchasePremiumItem()
                //creekPreferences.setPremiumUser(true);
                return true
            }

        /* case R.id.action_restore_purchase :
                AuxilaryUtils.displayInputDialog(DashboardActivity.this, "Restore Purchase", "Enter unique Id you got when you purchased the item : ", new AuxilaryUtils.InputTextListener() {
                    @Override
                    public void onSuccess(String text) {
                        CommonUtils.displayProgressDialog(DashboardActivity.this, "Verifying");
                        new FirebaseDatabaseHandler(DashboardActivity.this).verifyPurchase(text, new FirebaseDatabaseHandler.VerifyPurchaseListener() {
                            @Override
                            public void onSuccess(Purchase purchase) {
                                CommonUtils.dismissProgressDialog();
                                AuxilaryUtils.displayAlert( "Verification Success", "Restart app for update to take place", DashboardActivity.this);
                            }

                            @Override
                            public void onError(Exception e) {
                                CommonUtils.displayProgressDialog(DashboardActivity.this, "Verifying");
                                if( e != null ) {
                                    AuxilaryUtils.displayAlert( "Verification Error", "Error : "+ e.getMessage() +"\n\nUnable to Verify, Try later", DashboardActivity.this);
                                    e.printStackTrace();
                                }
                                else
                                    AuxilaryUtils.displayAlert( "Verification Error", "Unable to Verify, Try later", DashboardActivity.this);
                            }
                        });
                    }

                    @Override
                    public void onDismiss() {

                    }
                });
                return true;*/

        /*case R.id.action_sync:
                //downloadFile();
                LanguageFragment.getInstance().getFirebaseDBVerion();
                return true;*/

            R.id.action_log_out -> {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
                    // ...
                }
                creekPreferences!!.clearCacheDetails()
                logoutFromFB()
                val firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.signOut()
                val spashIntent = Intent(this@DashboardActivity, SplashActivity::class.java)
                startActivity(spashIntent)
                finish()
                return true
            }

            R.id.action_feedback -> {
                sendEmail(this@DashboardActivity)
                return true
            }

        /*case R.id.action_search:
                showSearchFragment();
                return true;*/

            R.id.action_rate -> {
                try {
                    val rateIntent = Intent(Intent.ACTION_VIEW)
                    rateIntent.data = Uri.parse("market://details?id=" + this@DashboardActivity.packageName)
                    startActivity(rateIntent)
                } catch (e: Exception) { //google play app is not installed
                    val rateIntent = Intent(Intent.ACTION_VIEW)
                    rateIntent.data = Uri.parse("https://play.google.com/store/apps/details?id=" + this@DashboardActivity.packageName)
                    startActivity(rateIntent)
                }

                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }

    private fun showSignupFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_down, R.anim.slide_out_up)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(R.id.container, SignupFragment()).commit()
    }

    private fun showSearchFragment() {
        container!!.visibility = View.VISIBLE
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_down, R.anim.slide_out_up)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(R.id.container, SearchFragment()).commit()
    }


    private fun logoutFromFB() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return  // already logged out
        }
        if (LoginManager.getInstance() != null)
            LoginManager.getInstance().logOut()
        /*new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {



            }
        }).executeAsync();*/
    }

    fun sendEmail(ctx: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + getString(R.string.feedback_email)))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            CommonUtils.displaySnackBar(this@DashboardActivity, R.string.unable_to_find_app_for_email)
        }

    }

    private fun configureGoogleSignup() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this) { }
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: requestCode=$requestCode, resultCode=$resultCode")
        if (requestCode == REQUEST_INVITE) {
            if (resultCode == Activity.RESULT_OK) {
                // Get the invitation IDs of all sent messages
                val ids = AppInviteInvitation.getInvitationIds(resultCode, data)
                for (id in ids) {
                    Log.d(TAG, "onActivityResult: sent invitation " + id)
                }
                FirebaseDatabaseHandler(this@DashboardActivity).updateInviteCount(ids.size)
                creekPreferences!!.showInviteDialog = false
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        } else if (requestCode == REQUEST_CODE_SEARCH && resultCode == AppCompatActivity.RESULT_OK) {
            val uri = data.data
            if (uri != null) {

                Log.d(TAG, "File Uri : " + uri.encodedPath + " Path " + uri.path)
                filepath = FileUtils.getPath(this@DashboardActivity, uri)
                Log.d(TAG, "File path : " + filepath!!)

                if (filepath != null) {
                    val fileMd5 = FileUtils.calculateMD5(File(filepath!!))
                    if (creekPreferences!!.creekUserStats!!.userAddedPrograms.contains(fileMd5)) {
                        CommonUtils.displayToast(this@DashboardActivity, "File already uploaded")
                    } else
                        FirebaseDatabaseHandler(this@DashboardActivity).readProgramFromFile(filepath!!, this)
                } else
                    CommonUtils.displayToast(this@DashboardActivity, "Unable to open file")
            } else {
            }
            // Rest of code that converts txt file's content into arraylist
        } else {
            if (anjLabBillingPresenter!!.billingProcessor.handleActivityResult(requestCode, resultCode, data))
                super.onActivityResult(requestCode, resultCode, data)
        }/*if( requestCode == BillingPresenter.RC_REQUEST ) {
            Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
            if (billingPresenter == null) return;

            // Pass on the activity result to the helper for handling
            if (!billingPresenter.getIabHelper().handleActivityResult(requestCode, resultCode, data)) {
                // not handled, so handle it ourselves (here's where you'd
                // perform any handling of activity results not related to in-app
                // billing...
                super.onActivityResult(requestCode, resultCode, data);
            }
            else {
                Log.d(TAG, "onActivityResult handled by IABUtil.");
            }
        }*/

    }


    override fun importCodeFile() {
        if (PermissionUtils.checkSelfPermission(this@DashboardActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_IMPORT_FILE_PERMISSION)) {

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "text/plain"
            startActivityForResult(Intent.createChooser(intent,
                    "Load file from directory"), REQUEST_CODE_SEARCH)

        }

    }

    internal var searchItem: MenuItem? = null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dashboard, menu)
        searchItem = menu.findItem(R.id.action_search)
        searchItem!!.isVisible = false
        signUpItem = menu.findItem(R.id.action_signup)
        signUpItem!!.isVisible = CreekApplication.creekPreferences!!.isAnonAccount
        return true
    }

    private fun onInviteClicked() {
        val intent = AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                .setCustomImage(CommonUtils.getUriToDrawable(this@DashboardActivity, R.mipmap.ic_launcher))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build()
        startActivityForResult(intent, REQUEST_INVITE)
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            hideLanguageFragment()
            invalidateOptionsMenu()
            return
        }
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
        finish()
    }


    private fun shareInfo() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this app : \n" + getString(R.string.app_url))
        startActivity(Intent.createChooser(shareIntent, "Share App Info"))
    }

    override fun navigateToDashboard() {
        dashboardViewPager!!.currentItem = 1
        DashboardFragment.instance.animateViews()
        supportActionBar!!.title = getString(R.string.app_name) + " - " + creekPreferences!!.programLanguage.toUpperCase()
    }

    override fun navigateToLanguage() {
        //LanguageFragment.getInstance().animateViews();
        showLanguageFragment()
    }

    override fun calculateReputation() {
        if (creekPreferences == null) {
            creekPreferences = CreekApplication.creekPreferences
        }
        if (creekPreferences != null && creekPreferences!!.programLanguage != "") {
            val creekUserStats = creekPreferences!!.creekUserStats
            if (creekUserStats != null && creekUserStats.creekUserReputation == 0) {
                creekUserStats.calculateReputation()
                LanguageFragment.instance.animateProgress()
                FirebaseDatabaseHandler(this@DashboardActivity).writeCreekUserStats(creekUserStats)
            }
        }
    }

    override fun showInviteDialog() {
        if (creekPreferences!!.showInviteDialog) {
            AuxilaryUtils.displayAppInviteDialog(this@DashboardActivity, object : AuxilaryUtils.InviteDialogListener {
                override fun onInviteClick() {
                    onInviteClicked()
                }

                override fun onLaterClick() {
                    creekPreferences!!.showInviteDialog = false
                }
            })
        }

    }

    override fun onSuccess(fileUrl: File) {
        runOnUiThread {
            webView!!.visibility = View.VISIBLE
            Log.d(TAG, "File Url : file:///" + fileUrl.absolutePath)
            webView!!.loadUrl("file:///" + fileUrl.absolutePath)
            webView!!.settings.javaScriptEnabled = true
        }

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.createPresentationFAB -> animateFab()
            R.id.addPptTextView -> {
                val intent = Intent(this@DashboardActivity, CreatePresentationActivity::class.java)
                startActivity(intent)
            }
            R.id.addCodeFAB, R.id.addCodeTextView -> {
                importFromFile()
                animateFab()
            }
            R.id.addUserCodeFAB -> importFromFile()
            R.id.selectedLanguageCardView -> showLanguageFragment()
            R.id.upgradeTextView -> {
                CreekAnalytics.logEvent(TAG, "Upgrade opted")
                //billingPresenter.onUpgradeAppButtonClicked();
                anjLabBillingPresenter!!.purchasePremiumItem()
                AnimationUtils.slideOutToLeft(premiumLayout)
            }
            R.id.laterTextView -> {
                AnimationUtils.slideOutToLeft(premiumLayout)
                creekPreferences!!.setUpgradeDialog(false)
            }
        }

    }

    private fun showLanguageFragment() {
        container!!.visibility = View.VISIBLE
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_down, R.anim.slide_out_up)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(R.id.container, LanguageFragment()).commit()

    }

    override fun showQuickReferenceFragment() {
        container!!.visibility = View.VISIBLE
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_down, R.anim.slide_out_up)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(R.id.container, QuickReferenceFragment()).commit()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_IMPORT_FILE_PERMISSION) {
            if (PermissionUtils.checkDeniedPermissions(this@DashboardActivity, permissions).size == 0) {
                importCodeFile()
            } else {
                if (permissions.size == 3) {
                    Toast.makeText(this@DashboardActivity, "Some permissions were denied", Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun importFromFile() {
        val intent = Intent(this@DashboardActivity, TutorialCarousalActivity::class.java)
        startActivityForResult(intent, REQUEST_DOWNLOAD_FILE)
    }

    private var fab_open: Animation? = null
    private var fab_close: Animation? = null
    private var rotate_forward: Animation? = null
    private var rotate_backward: Animation? = null
    private var isFABOpen: Boolean = false

    private fun animateFab() {
        if (fab_open == null) {
            fab_open = android.view.animation.AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
            fab_close = android.view.animation.AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)
            rotate_forward = android.view.animation.AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_forward)
            rotate_backward = android.view.animation.AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_backward)
        }
        if (isFABOpen) {
            isFABOpen = false
            createPresentationFAB!!.startAnimation(rotate_backward)
            fab_close!!.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(animation: Animation) {
                    addCodeTextView!!.visibility = View.GONE
                    addPptTextView!!.visibility = View.GONE
                    addCodeFAB!!.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            addCodeTextView!!.startAnimation(fab_close)
            addPptTextView!!.startAnimation(fab_close)
            addCodeFAB!!.startAnimation(fab_close)
        } else {
            isFABOpen = true
            createPresentationFAB!!.startAnimation(rotate_forward)
            addCodeTextView!!.visibility = View.INVISIBLE
            addCodeFAB!!.visibility = View.INVISIBLE
            addPptTextView!!.visibility = View.INVISIBLE
            addCodeTextView!!.startAnimation(fab_open)
            addPptTextView!!.startAnimation(fab_open)
            addCodeFAB!!.startAnimation(fab_open)
        }
    }

    override fun onSuccess(programIndex: ProgramIndex, programTables: ArrayList<ProgramTable>) {

        if ( programTables.size > 0) {

            UserProgramDialog(this@DashboardActivity, programIndex, programTables, object : UserProgramDialog.UserProgramDialogListener {
                override fun onSave(accessSpecifier: String) {

                    val firebaseDatabaseHandler = FirebaseDatabaseHandler(this@DashboardActivity)
                    firebaseDatabaseHandler.updateCodeCount()

                    val userProgramDetails = UserProgramDetails()
                    userProgramDetails.accessSpecifier = accessSpecifier
                    if (filepath != null)
                        userProgramDetails.md5 = FileUtils.calculateMD5(File(filepath!!))
                    userProgramDetails.emailId = creekPreferences!!.getSignInAccount()
                    userProgramDetails.likes = 0
                    userProgramDetails.likesList = ArrayList<String>()
                    userProgramDetails.programIndex = programIndex
                    userProgramDetails.programTables = programTables
                    userProgramDetails.views = 0
                    userProgramDetails.programLanguage = programIndex.program_Language
                    userProgramDetails.programTitle = programIndex.program_Description.toLowerCase()

                    if (creekPreferences!!.addUserFile(userProgramDetails.md5)) {
                        firebaseDatabaseHandler.writeUserProgramDetails(userProgramDetails)
                    } else {
                        CommonUtils.displayToast(this@DashboardActivity, "File already added")
                    }
                    onProgressStatsUpdate(CreekUserStats.CHAPTER_SCORE)

                }

                override fun onCancel() {

                }

                override fun onPreview() {
                    val newIntentBundle = Bundle()
                    var newIntent: Intent? = null
                    programIndex.userProgramId = "trial"
                    newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true)
                    newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, programIndex)
                    newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, 1)
                    newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, programIndex.program_Description)
                    newIntentBundle.putParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM, programTables)
                    newIntent = Intent(this@DashboardActivity, ProgramActivity::class.java)
                    newIntent.putExtras(newIntentBundle)
                    startActivity(newIntent)
                }
            }).showDialog()

        }

    }

    override fun onError(errorMessage: String) {
        CommonUtils.displayToast(this@DashboardActivity, errorMessage)
    }


}

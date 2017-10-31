package com.sortedqueue.programmercreek.dashboard


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation

import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.appinvite.AppInviteInvitation
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.activity.AboutActivity
import com.sortedqueue.programmercreek.activity.CreatePresentationActivity
import com.sortedqueue.programmercreek.activity.SplashActivity
import com.sortedqueue.programmercreek.activity.TutorialCarousalActivity
import com.sortedqueue.programmercreek.asynctask.JavaProgramInserter
import com.sortedqueue.programmercreek.billing.anjlab.AnjLabBillingPresenter
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.ProgramLanguage
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.fragments.LanguageFragment
import com.sortedqueue.programmercreek.fragments.QuickReferenceFragment
import com.sortedqueue.programmercreek.fragments.SearchFragment
import com.sortedqueue.programmercreek.fragments.SignupFragment
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekAnalytics
import com.sortedqueue.programmercreek.util.CreekPreferences


import kotlinx.android.synthetic.main.fragment_dashboard.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class DashboardActivity : AppCompatActivity(), DashboardNavigationListener, DashboardView {

    override fun showProgress(messageId: Int) {
        CommonUtils.displayProgressDialog(this@DashboardActivity, getString(messageId))
    }

    override fun hideProgress() {
        CommonUtils.dismissProgressDialog()
    }

    private val TAG = javaClass.simpleName
    private var creekPreferences: CreekPreferences? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private val REQUEST_INVITE = 9999
    private var handler: Handler? = null
    private var creekUserStats: CreekUserStats? = null
    private var runnable: Runnable? = null
    private var anjLabBillingPresenter: AnjLabBillingPresenter? = null
    private var signUpItem: MenuItem? = null

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_dashboard)

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
        checkBillingInformation()

        if (creekPreferences!!.programLanguage != "") {
            AuxilaryUtils.scheduleNotification(this@DashboardActivity)
        }
        //adView.setVisibility(View.GONE);

        //initAds();
        createPresentationFAB!!.setOnClickListener{ animateFab() }
        addPptTextView!!.visibility = View.GONE
        addCodeFAB!!.visibility = View.GONE
        addCodeTextView!!.visibility = View.GONE
        addUserCodeFAB!!.visibility = View.GONE
        addCodeFAB!!.setOnClickListener{
            importFromWeb()
            animateFab()
        }
        addCodeTextView!!.setOnClickListener{
            importFromWeb()
            animateFab()
        }
        addPptTextView!!.setOnClickListener{
            val intent = Intent(this@DashboardActivity, CreatePresentationActivity::class.java)
            startActivity(intent)
        }
        addUserCodeFAB!!.setOnClickListener{ importFromWeb() }
        selectedLanguageCardView!!.setOnClickListener{ showLanguageFragment() }
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
        upgradeTextView!!.setOnClickListener{
            CreekAnalytics.logEvent(TAG, "Upgrade opted")
            anjLabBillingPresenter!!.purchasePremiumItem()
            AnimationUtils.slideOutToLeft(premiumLayout)
        }
        laterTextView!!.setOnClickListener{
            AnimationUtils.slideOutToLeft(premiumLayout)
            creekPreferences!!.setUpgradeDialog(false)
        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: requestCode=$requestCode, resultCode=$resultCode")
        if (requestCode == REQUEST_INVITE) {
            if (resultCode == Activity.RESULT_OK) {
                // Get the invitation IDs of all sent messages
                val ids = AppInviteInvitation.getInvitationIds(resultCode, data!!)
                for (id in ids) {
                    Log.d(TAG, "onActivityResult: sent invitation " + id)
                }
                FirebaseDatabaseHandler(this@DashboardActivity).updateInviteCount(ids.size)
                creekPreferences!!.showInviteDialog = false
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        } else {
            if (anjLabBillingPresenter!!.billingProcessor.handleActivityResult(requestCode, resultCode, data))
                super.onActivityResult(requestCode, resultCode, data)
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

    override fun importFromWeb() {
        val intent = Intent(this@DashboardActivity, TutorialCarousalActivity::class.java)
        startActivity(intent)
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

    override fun onError(errorMessage: String) {
        CommonUtils.displayToast(this@DashboardActivity, errorMessage)
    }


}

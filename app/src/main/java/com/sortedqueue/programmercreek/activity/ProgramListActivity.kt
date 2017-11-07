package com.sortedqueue.programmercreek.activity


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.widget.LinearLayout

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.appinvite.AppInviteInvitation
import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener
import com.sortedqueue.programmercreek.interfaces.UnlockByInviteInterface
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekPreferences
import com.startapp.android.publish.adsCommon.StartAppAd

import java.util.ArrayList



import kotlinx.android.synthetic.main.activity_program_list.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class ProgramListActivity : AppCompatActivity(), UIUpdateListener, CustomProgramRecyclerViewAdapter.AdapterClickListner, UnlockByInviteInterface {


    internal var mInvokeTest = 0

    internal var mProgram_Indexs: ArrayList<ProgramIndex> ?= null

    var PROGRAM_LIST_SIZE = 0


    private val TAG = javaClass.simpleName
    private var program_Index: ProgramIndex? = null
    private var customProgramRecyclerViewAdapter: CustomProgramRecyclerViewAdapter? = null
    private val fadeOutAnimation: Animation? = null
    private var isAdShown: Boolean = false


    private fun logDebugMessage(message: String) {
        Log.d(TAG, message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program_list)

        fetchProgramsList()
        if (!CreekApplication.creekPreferences!!.isPremiumUser) {
            initAds()
        }
        setActivityTitle()

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)

    }

    private fun startProgressAnimation() {
        AnimationUtils.enterReveal(progressLayout)
        Handler().postDelayed({ AnimationUtils.exitRevealGone(progressLayout) }, 5000)
    }


    override fun onResume() {
        super.onResume()
        if (customProgramRecyclerViewAdapter != null) {
            customProgramRecyclerViewAdapter!!.resetAdapter()
        }
        CreekApplication.instance.isAppRunning = (true)
    }

    override fun onPause() {
        super.onPause()
        CreekApplication.instance.isAppRunning = (false)
    }

    private fun setActivityTitle() {

        if (intent.extras != null) {
            mInvokeTest = intent.extras!!.getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST)
            when (mInvokeTest) {
                ProgrammingBuddyConstants.KEY_TEST -> title = "Program List : Test"
                ProgrammingBuddyConstants.KEY_MATCH -> title = "Program List : Match"
                ProgrammingBuddyConstants.KEY_QUIZ -> title = "Program List : Quiz"
                ProgrammingBuddyConstants.KEY_FILL_BLANKS -> title = "Program List : Fill"
            }
        }

    }


    private var interstitialAd: InterstitialAd? = null

    private fun requestNewInterstitial() {
        val adRequest = AdRequest.Builder()
                .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                .build()
        interstitialAd!!.loadAd(adRequest)
    }

    private fun initAds() {
        if (CreekApplication.creekPreferences!!.adsEnabled) {
            MobileAds.initialize(applicationContext, getString(R.string.mobile_banner_id))
            //For actual ads : AdRequest adRequest = new AdRequest.Builder().build();
            //For creating test ads
            val adRequest = AdRequest.Builder()
                    .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                    .build()
            adView!!.loadAd(adRequest)
            adView!!.visibility = View.GONE
            adView!!.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    adView!!.visibility = View.VISIBLE
                }
            }
        }
        interstitialAd = InterstitialAd(this)
        interstitialAd!!.adUnitId = getString(R.string.interstital_wiki_ad_id)
        interstitialAd!!.adListener = object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                finish()
            }

            override fun onAdFailedToLoad(i: Int) {
                super.onAdFailedToLoad(i)
            }

            override fun onAdLeftApplication() {
                super.onAdLeftApplication()
            }
        }
        requestNewInterstitial()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun fetchProgramsList() {

        // Reading all contacts
        logDebugMessage("Reading All Programs...")

        FirebaseDatabaseHandler(this@ProgramListActivity).initializeProgramIndexes(
                object : FirebaseDatabaseHandler.ProgramIndexInterface {
                    override fun getProgramIndexes(program_indices: ArrayList<ProgramIndex>) {
                        mProgram_Indexs = ArrayList(program_indices)
                        PROGRAM_LIST_SIZE = mProgram_Indexs!!.size
                        customProgramRecyclerViewAdapter = CustomProgramRecyclerViewAdapter(this@ProgramListActivity, mProgram_Indexs!!)

                        programListRecyclerView.layoutManager = LinearLayoutManager(this@ProgramListActivity, LinearLayoutManager.VERTICAL, false)
                        programListRecyclerView.adapter = customProgramRecyclerViewAdapter
                        startProgressAnimation()
                    }

                    override fun onError(error: DatabaseError) {

                    }
                })

    }

    internal var mBundle: Bundle ?= null
    internal var mInvokeIntent: Intent? = null
    internal var mSelectedProgramIndex = 0
    internal var mSelectedProgramTitle = ""

    private fun loadProgramExplanation(programWiki: String) {
        val intent = Intent(this@ProgramListActivity, ProgramWikiActivity::class.java)
        intent.putExtra(ProgrammingBuddyConstants.KEY_WIKI, programWiki)
        intent.putExtra(ProgrammingBuddyConstants.KEY_PROGRAM_LANGUAGE, mProgram_Indexs)
        startActivity(intent)
    }

    private fun invokeTestIntents() {

        when (mInvokeTest) {
            ProgrammingBuddyConstants.KEY_TEST -> {
                title = "Program List : Test"
                mInvokeIntent = Intent(this@ProgramListActivity, WizardActivity::class.java)
                mInvokeIntent!!.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_TEST)
                mInvokeIntent!!.putExtras(mBundle)
                startActivity(mInvokeIntent)
            }
            ProgrammingBuddyConstants.KEY_LIST -> showOptionsBox()
            ProgrammingBuddyConstants.KEY_MATCH -> {
                title = "Program List : Match"
                //mInvokeIntent = new Intent(ProgramListActivity.this, MatchMakerActivity.class);
                mInvokeIntent = Intent(this@ProgramListActivity, WizardActivity::class.java)
                mInvokeIntent!!.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_MATCH)
                mInvokeIntent!!.putExtras(mBundle)
                startActivity(mInvokeIntent)
            }
            ProgrammingBuddyConstants.KEY_FILL_BLANKS -> {
                title = "Program List : Fill"
                //mInvokeIntent = new Intent(ProgramListActivity.this, MatchMakerActivity.class);
                mInvokeIntent = Intent(this@ProgramListActivity, WizardActivity::class.java)
                mInvokeIntent!!.putExtra(ProgramListActivity.KEY_WIZARD, true)
                mInvokeIntent!!.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_FILL_BLANKS)
                mInvokeIntent!!.putExtras(mBundle)
                startActivity(mInvokeIntent)
            }
            ProgrammingBuddyConstants.KEY_QUIZ -> {
                title = "Program List : Quiz"
                showSelectBox()
            }
        }
    }

    protected fun showReviseModeBox() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Revise Programs")
        val boxTypes = arrayOf("Normal Mode", "Line By Line Mode")
        builder.setItems(boxTypes, reviseModeTypeListener)
        builder.setNegativeButton("Cancel", null)
        val alertDialog = builder.create()
        alertDialog.show()


    }

    /**
     * Quiz Type Listener
     */
    internal var reviseModeTypeListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialog, which ->
        val newIntentBundle = Bundle()
        var newIntent: Intent? = null
        newIntentBundle.putBoolean(KEY_WIZARD, intent.extras!!.getBoolean(ProgramListActivity.KEY_WIZARD))

        when (which) {

            KEY_REVISE_NORMAL -> {
                newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, program_Index)
                newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, PROGRAM_LIST_SIZE)
                newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, mSelectedProgramTitle)
                newIntent = Intent(this@ProgramListActivity, ProgramActivity::class.java)
            }

            KEY_REVISE_LINE_BY_LINE -> {
                newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, program_Index)
                newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, PROGRAM_LIST_SIZE)
                newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, mSelectedProgramTitle)
                newIntent = Intent(this@ProgramListActivity, MemorizeProgramActivity::class.java)
            }
        }

        newIntent!!.putExtras(newIntentBundle)
        startActivity(newIntent)
    }


    protected fun showOptionsBox() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Mode")
        val boxTypes = arrayOf(/*"Explanation", */"Revise Mode", "Memorize Mode", "Test Mode", "Quiz Mode", "Match Mode")
        builder.setItems(boxTypes, optionTypeListener)
        builder.setNegativeButton("Cancel", null)
        val alertDialog = builder.create()
        alertDialog.show()

    }


    /**
     * Option Type Listener
     */
    internal var optionTypeListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialog, which ->
        var newIntent: Intent? = null
        val newIntentBundle = Bundle()
        newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, program_Index)
        newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, PROGRAM_LIST_SIZE)
        newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, mSelectedProgramTitle)

        when (which) {

            INDEX_KEY_REVISE -> {
                newIntent = Intent(this@ProgramListActivity, ProgramActivity::class.java)
                newIntent.putExtras(newIntentBundle)
                startActivity(newIntent)
            }

            INDEX_KEY_MEMORIZE -> {
                newIntent = Intent(this@ProgramListActivity, MemorizeProgramActivity::class.java)
                newIntent.putExtras(newIntentBundle)
                startActivity(newIntent)
            }

            INDEX_KEY_TEST -> {
                newIntent = Intent(this@ProgramListActivity, WizardActivity::class.java)
                newIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_TEST)
                newIntent.putExtras(newIntentBundle)
                startActivity(newIntent)
            }

            INDEX_KEY_QUIZ -> showSelectBox()

            INDEX_KEY_MATCH -> {

                //newIntent = new Intent(ProgramListActivity.this, MatchMakerActivity.class);
                newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, program_Index)
                newIntent = Intent(this@ProgramListActivity, WizardActivity::class.java)
                newIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_MATCH)
                newIntent.putExtras(newIntentBundle)
                startActivity(newIntent)
            }
            INDEX_KEY_FILL -> {

                //newIntent = new Intent(ProgramListActivity.this, MatchMakerActivity.class);
                newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, program_Index)
                newIntent = Intent(this@ProgramListActivity, WizardActivity::class.java)
                newIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_FILL_BLANKS)
                newIntent.putExtras(newIntentBundle)
                startActivity(newIntent)
            }
        }/*case INDEX_KEY_EXPLANATION :
							String programWiki = program_Index.getWiki();
							loadProgramExplanation( programWiki );
							break;*/
    }

    protected fun showSelectBox() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quiz Mode - Question Type")
        val boxTypes = arrayOf("Description", "Program Code")
        builder.setItems(boxTypes, quizTypeListener)
        builder.setNegativeButton("Cancel", null)
        val alertDialog = builder.create()
        alertDialog.show()

    }

    /**
     * Quiz Type Listener
     */
    internal var quizTypeListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialog, which ->
        val newIntentBundle = Bundle()
        val newIntent = Intent(this@ProgramListActivity, WizardActivity::class.java)
        newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_QUIZ)
        when (which) {

            KEY_QUIZ_DESCRIPTION_QUESTION -> {
                newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, program_Index)
                newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, mSelectedProgramTitle)
                newIntentBundle.putInt(KEY_QUIZ_TYPE, KEY_QUIZ_DESCRIPTION_QUESTION)
            }

            KEY_QUIZ_PROGRAM_CODE_QUESTION -> {
                newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, program_Index)
                newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, mSelectedProgramTitle)
                newIntentBundle.putInt(KEY_QUIZ_TYPE, KEY_QUIZ_PROGRAM_CODE_QUESTION)
            }
        }

        newIntent.putExtras(newIntentBundle)
        startActivity(newIntent)
    }

    override fun updateUI() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.program, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_unlock_programs -> {
                AuxilaryUtils.displayAlert(getString(R.string.unlock_by_invite),
                        getString(R.string.unlock_by_invite_description),
                        this@ProgramListActivity)
                //insertProgramIndexes();
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }


    override fun onItemClick(position: Int) {
        mBundle = Bundle()
        program_Index = mProgram_Indexs!![position]
        /*
				 * Check the module table if the selected program has modules
				 * */
        /*List<Module_Table> module_Tables = mDatabaseHandler.getAllModule_Tables(program_Index.getTableIndex());
				if( module_Tables != null ) {
					newIntentBundle.putBoolean(DashboardActivity.KEY_MODULE_LIST, true);
				}
				else {
					newIntentBundle.putBoolean(DashboardActivity.KEY_MODULE_LIST, false);
				}*/
        /**
         * Pack and pass program index to
         * ProgramActivity so as to retrieve
         * corresponding program from DB.
         */
        mSelectedProgramIndex = program_Index!!.program_index
        mSelectedProgramTitle = program_Index!!.program_Description

        mBundle!!.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, program_Index)
        mBundle!!.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, PROGRAM_LIST_SIZE)
        mBundle!!.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, mSelectedProgramTitle)
        mBundle!!.putBoolean(KEY_WIZARD, intent.extras!!.getBoolean(ProgramListActivity.KEY_WIZARD))
        mInvokeTest = intent.extras!!.getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST)


        when (mInvokeTest) {

            ProgrammingBuddyConstants.KEY_REVISE -> showReviseModeBox()

            ProgrammingBuddyConstants.KEY_WIZARD -> {
                val programListIntent = Intent(applicationContext, ProgramListActivity::class.java)
                programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, mInvokeTest)
                programListIntent.putExtra(ProgramListActivity.KEY_WIZARD, true)
                startActivity(programListIntent)
            }

            ProgrammingBuddyConstants.KEY_TEST, ProgrammingBuddyConstants.KEY_LIST, ProgrammingBuddyConstants.KEY_MATCH, ProgrammingBuddyConstants.KEY_FILL_BLANKS, ProgrammingBuddyConstants.KEY_QUIZ -> invokeTestIntents()
        }
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }

    override fun onBackPressed() {
        if (!isAdShown && interstitialAd != null && interstitialAd!!.isLoaded /*&& CreekApplication.getPreferences().getAdsEnabled()*/) {
            interstitialAd!!.show()
            isAdShown = true
            return
        }
        finish()
    }

    private var creekPreferences: CreekPreferences? = null
    private val REQUEST_INVITE = 9999
    private var mToBeUnlockedIndex = -1

    override fun onUnlockClick(programIndex: Int) {
        mToBeUnlockedIndex = programIndex
        creekPreferences = CreekApplication.creekPreferences
        val intent = AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                .setCustomImage(CommonUtils.getUriToDrawable(this@ProgramListActivity, R.mipmap.ic_launcher))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build()
        startActivityForResult(intent, REQUEST_INVITE)
    }

    override fun onDismiss() {

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
                FirebaseDatabaseHandler(this@ProgramListActivity).updateInviteCount(ids.size)
                creekPreferences!!.showInviteDialog = false
                if (ids.isNotEmpty()) {
                    creekPreferences!!.setUnlockedByInviteIndex(mToBeUnlockedIndex)
                    if (customProgramRecyclerViewAdapter != null)
                        customProgramRecyclerViewAdapter!!.notifyDataSetChanged()
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        }
    }

    companion object {

        val KEY_REVISE_NORMAL = 0
        val KEY_REVISE_LINE_BY_LINE = 1

        //public static final int INDEX_KEY_EXPLANATION = 0;
        val INDEX_KEY_REVISE = 0
        val INDEX_KEY_MEMORIZE = 1
        val INDEX_KEY_TEST = 2
        val INDEX_KEY_QUIZ = 3
        val INDEX_KEY_MATCH = 4
        val INDEX_KEY_FILL = 5

        var KEY_QUIZ_TYPE = "Quiz_Type"

        val KEY_QUIZ_DESCRIPTION_QUESTION = 1
        val KEY_QUIZ_PROGRAM_CODE_QUESTION = 0
        val KEY_WIZARD = "wizard_mode"
    }
}

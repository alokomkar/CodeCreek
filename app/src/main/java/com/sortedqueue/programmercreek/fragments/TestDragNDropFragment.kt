package com.sortedqueue.programmercreek.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.DatabaseError
import com.google.gson.Gson
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.activity.ProgramListActivity
import com.sortedqueue.programmercreek.adapter.DragNDropAdapter
import com.sortedqueue.programmercreek.adapter.SubTestPagerAdapter
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.DragListenerInterface
import com.sortedqueue.programmercreek.interfaces.DropListenerInterface
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener
import com.sortedqueue.programmercreek.interfaces.RemoveListenerInterface
import com.sortedqueue.programmercreek.interfaces.SubTestCommunicationListener
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekAnalytics
import com.sortedqueue.programmercreek.view.DragNDropListView
import com.sortedqueue.programmercreek.view.ScrollableViewPager

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList
import java.util.concurrent.TimeUnit




import com.facebook.FacebookSdk.getApplicationContext
import kotlinx.android.synthetic.main.activity_test_drag_n_drop.*

/**
 * Created by Alok on 03/01/17.
 */

class TestDragNDropFragment : Fragment(), UIUpdateListener, TestCompletionListener, SubTestCommunicationListener {

    internal var mProgramIndex: ProgramIndex? = null
    internal var mRandomTest: ArrayList<String>? = null
    internal var mProgramList: ArrayList<String> ?= null
    internal var mProgramCheckList: ArrayList<String> ?= null
    internal var remainingTime: Long = 0
    internal var time: Long = 0
    internal var interval: Long = 0
    internal var mCountDownTimer: CountDownTimer? = null
    internal var mWizard = false

    private var interstitialAd: InterstitialAd? = null
    private var dragNDropListView: DragNDropListView? = null
    private val dragNDropAdapter: DragNDropAdapter? = null
    private var programSize: Int = 0

    private var bundle: Bundle? = null
    private var creekUserStats: CreekUserStats? = null
    private var programTableArray: Array<ArrayList<ProgramTable>>? = null
    private var subTestPagerAdapter: SubTestPagerAdapter? = null
    private var mProgramTableList: ArrayList<ProgramTable>? = null
    private var moduleDetailsScrollPageListener: ModuleDetailsScrollPageListener? = null
    private val TAG = TestDragNDropFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.activity_test_drag_n_drop, container, false)
        dragNDropListView = view!!.findViewById(R.id.dragNDropListView) as DragNDropListView

        handleBundle()

        MobileAds.initialize(getApplicationContext(), getString(R.string.mobile_banner_id))
        interstitialAd = InterstitialAd(context)
        interstitialAd!!.adUnitId = getString(R.string.interstital_wiki_ad_id)
        requestNewInterstitial()
        interstitialAd!!.adListener = object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                activity.finish()
            }
        }

        return view
    }

    fun getmProgramList(): ArrayList<String> {
        val programList = ArrayList<String>()
        for (programTable in mProgramTableList!!) {
            programList.add(programTable.program_Line)
        }
        return programList
    }

    private fun handleBundle() {
        mProgramTableList = bundle!!.getParcelableArrayList<ProgramTable>(ProgrammingBuddyConstants.KEY_USER_PROGRAM)

        if (mProgramTableList != null && mProgramTableList!!.size > 0) {
            mProgramIndex = bundle!!.get(ProgrammingBuddyConstants.KEY_PROG_ID) as ProgramIndex
            mWizard = bundle!!.getBoolean(ProgramListActivity.KEY_WIZARD, false)
            initUI(mProgramTableList!!)
        } else {
            if (bundle!!.getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, -1) == ProgrammingBuddyConstants.KEY_LESSON) {
                mWizard = false
                FirebaseDatabaseHandler(context).getProgramIndexInBackGround(bundle!!.getInt(ProgrammingBuddyConstants.KEY_PROG_ID),
                        object : FirebaseDatabaseHandler.GetProgramIndexListener {
                            override fun onSuccess(programIndex: ProgramIndex) {
                                mProgramIndex = programIndex
                                getProgramTablesInBackground()
                            }

                            override fun onError(databaseError: DatabaseError) {
                                CommonUtils.displayToast(context, R.string.unable_to_fetch_data)
                            }
                        })
            } else {
                mProgramIndex = bundle!!.get(ProgrammingBuddyConstants.KEY_PROG_ID) as ProgramIndex
                mWizard = bundle!!.getBoolean(ProgramListActivity.KEY_WIZARD)
                getProgramTables()
            }
        }


    }

    private fun getProgramTablesInBackground() {
        FirebaseDatabaseHandler(context)
                .getProgramTablesInBackground(mProgramIndex!!.program_index, object : FirebaseDatabaseHandler.GetProgramTablesListener {
                    override fun onSuccess(programTables: ArrayList<ProgramTable>) {
                        initUI(programTables)
                    }

                    override fun onError(databaseError: DatabaseError?) {
                        CommonUtils.displayToast(context, R.string.unable_to_fetch_data)
                    }
                })

    }

    private fun getProgramTables() {
        FirebaseDatabaseHandler(context)
                .getProgramTablesInBackground(mProgramIndex!!.program_index, object : FirebaseDatabaseHandler.GetProgramTablesListener {
                    override fun onSuccess(programTables: ArrayList<ProgramTable>) {
                        val program_TableList = programTables
                        initUI(program_TableList)
                    }

                    override fun onError(databaseError: DatabaseError?) {

                    }
                })
    }

    private fun initUI(program_TableList: ArrayList<ProgramTable>) {
        var program_TableList = program_TableList
        if (mProgramIndex != null) {
            try {
                CreekAnalytics.logEvent(TAG, JSONObject(Gson().toJson(mProgramIndex)))
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
        program_TableList = ProgramTable.getMinimalisticCode(program_TableList)

        activity.title = "Test : " + mProgramIndex!!.program_Description
        mProgramTableList = program_TableList
        //Split lengthy programs into modules
        programTableArray = ProgramTable.splitIntoModules(program_TableList)
        mProgramList = ArrayList<String>()
        mProgramCheckList = ArrayList<String>()
        var programLine: String? = null
        val iteraor = program_TableList.iterator()
        while (iteraor.hasNext()) {
            val newProgramTable = iteraor.next()
            programLine = newProgramTable.program_Line
            mProgramCheckList!!.add(programLine)
            mProgramList!!.add(programLine)
        }
        containerPager!!.setCanScroll(false)
        /*if (programTableArray.length > 1) */run {
            if (programTableArray!!.size > 1) {
                AuxilaryUtils.displayInformation(context, R.string.divide_and_conquer, R.string.divide_and_conquer_description, DialogInterface.OnDismissListener { setupSubTests(mProgramTableList!!) })

            } else {
                setupSubTests(program_TableList)
            }
        } /*else {

            containerPager.setVisibility(View.GONE);
            testTabLayout.setVisibility(View.GONE);
            dragNDropListView.setVisibility(View.VISIBLE);




            mRandomTest = new ArrayList<String>(mProgramList.size());
            for (String item : mProgramList) {
                mRandomTest.add(item);
            }

            mRandomTest = ShuffleList.shuffleList(mRandomTest);
            programSize = mRandomTest.size();
            dragNDropAdapter = new DragNDropAdapter(getContext(), new int[]{R.layout.dragitem}, new int[]{R.id.programLineTextView}, mRandomTest);
            dragNDropListView.setAdapter(dragNDropAdapter);//new DragNDropAdapter(this,content)
            //mListView.setBackgroundResource(R.drawable.error);
            dragNDropListView.setDropListener(mDropListener);
            dragNDropListView.setRemoveListener(mRemoveListener);
            dragNDropListView.setDragListener(mDragListener);

            enableTimer();
        }*/

    }

    private fun setupSubTests(program_TableList: ArrayList<ProgramTable>) {
        containerPager!!.visibility = View.VISIBLE
        testTabLayout!!.visibility = View.VISIBLE
        dragNDropListView!!.visibility = View.GONE
        subTestPagerAdapter = SubTestPagerAdapter(childFragmentManager, programTableArray!!, this)
        containerPager!!.adapter = subTestPagerAdapter
        containerPager!!.offscreenPageLimit = programTableArray!!.size + 1
        testTabLayout!!.setupWithViewPager(containerPager)
        programSize = program_TableList.size
        enableTimer()
    }

    private fun enableTimer() {
        timerButton!!.isEnabled = false
        progressLayout!!.visibility = View.GONE
        if (bundle!!.getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, -1) != ProgrammingBuddyConstants.KEY_LESSON) {
            progressLayout!!.visibility = View.VISIBLE
            time = (programSize / 2 * 60 * 1000).toLong()
            interval = 1000
            circular_progress_bar!!.max = (time / 1000).toInt()
            if (mCountDownTimer != null) {
                mCountDownTimer!!.cancel()
                checkQuizButton!!.isEnabled = true
            }

            mCountDownTimer = object : CountDownTimer(time, interval) {

                override fun onTick(millisUntilFinished: Long) {
                    timerButton!!.text = "" + String.format("%d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
                    progressTextView!!.text = timerButton!!.text
                    remainingTime = time - millisUntilFinished
                    circular_progress_bar!!.progress = (remainingTime / 1000).toInt()
                }

                override fun onFinish() {

                    timerButton!!.text = "Time up"
                    timerButton!!.visibility = View.VISIBLE
                    progressLayout!!.visibility = View.GONE
                    if (mWizard == true) {
                        timerButton!!.text = "Finish"
                        timerButton!!.visibility = View.VISIBLE
                        progressLayout!!.visibility = View.GONE
                        timerButton!!.isEnabled = true
                        timerButton!!.setOnClickListener(mFinishBtnClickListener)
                    }

                    checkScore(programSize, dragNDropListView!!, mProgramList!!)
                }
            }
            mCountDownTimer!!.start()
        }


        checkQuizButton!!.setOnClickListener { showConfirmSubmitDialog(programSize, mCountDownTimer) }
    }

    internal var mFinishBtnClickListener: View.OnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.timerButton -> showAd()
        }
    }

    private fun showAd() {

        if (interstitialAd != null && interstitialAd!!.isLoaded /*&& CreekApplication.getCreekPreferences().getAdsEnabled()*/) {
            interstitialAd!!.show()
        } else
            activity.finish()
    }


    internal var mProgramHint = 0

    protected fun checkScore(programLength: Int, dragNDropListView: DragNDropListView, mProgramList: ArrayList<String>) {
        var programCheck = 0
        /*if (programTableArray.length == 1) {

            ListAdapter listAdapter = dragNDropListView.getAdapter();
            for (int i = 0; i < programLength; i++) {
                String item = listAdapter.getItem(i).toString().trim();
                String actualItem = mProgramList.get(i).toString().trim();
                if (actualItem.equals(item) == true) {
                    programCheck++;
                } else {
                    programCheck--;
                }
            }
            checkProgramScore(programCheck, programLength);

        } else */run {
            val codeViewFragment = subTestPagerAdapter!!.codeViewFragment
            val programCode = codeViewFragment.programCode
            if (programCode != null && programCode.size == programLength) {
                for (i in 0..programLength - 1) {
                    val item = programCode[i].trim { it <= ' ' }
                    val actualItem = mProgramList[i].toString().trim { it <= ' ' }
                    if (actualItem == item == true) {
                        programCheck++
                    } else {
                        programCheck--
                    }
                }
                checkProgramScore(programCheck, programLength)
            } else {
                CommonUtils.displaySnackBar(activity, "Complete the test to proceed")
            }


        }

    }

    private fun checkProgramScore(programCheck: Int, programLength: Int) {
        if (programCheck < programLength) {
            displayToast("Please check the program again")
            mProgramHint++
            return
        }
        if (programCheck == programLength) {
            var score: String? = null
            var resultAlert = ""
            val maxScore = programLength / 2

            if (mProgramHint >= maxScore) {
                score = "0 %"
                resultAlert = "Your score is $score in " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Good Luck next time.."
            } else if (mProgramHint == 0) {
                score = "100 %"
                resultAlert = "Congratulations, Your score is $score in " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Fantastic Work..!!"
            } else if (mProgramHint < maxScore) {
                score = String.format("%.2f", (maxScore - mProgramHint).toFloat() / maxScore * 100) + " %"
                resultAlert = "Congratulations, Your score is $score in " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Keep Working.."
            }
            if (bundle!!.getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, -1) == ProgrammingBuddyConstants.KEY_LESSON) {
                resultAlert = "You have scored " + score!!
            }
            AuxilaryUtils.displayResultAlert(activity, "Test Complete", resultAlert, ((maxScore - mProgramHint).toFloat() / maxScore * 100).toInt(), 100)
            checkQuizButton!!.isEnabled = false

            mQuizComplete = true
            if (moduleDetailsScrollPageListener != null) {
                moduleDetailsScrollPageListener!!.toggleFABDrawable()
            }
            updateCreekStats()
            if (mWizard == true) {
                timerButton!!.text = "Finish"
                timerButton!!.isEnabled = true
                timerButton!!.visibility = View.VISIBLE
                progressLayout!!.visibility = View.GONE
                timerButton!!.setOnClickListener(mFinishBtnClickListener)
            }

        }
    }

    private fun updateCreekStats() {
        creekUserStats = CreekApplication.instance.creekUserStats
        if (mProgramIndex!!.userProgramId == null || mProgramIndex!!.userProgramId.trim { it <= ' ' }.length == 0) {
            when (mProgramIndex!!.program_Language.toLowerCase()) {
                "c" -> creekUserStats!!.addToUnlockedCProgramIndexList(mProgramIndex!!.program_index + 1)
                "cpp", "c++" -> creekUserStats!!.addToUnlockedCppProgramIndexList(mProgramIndex!!.program_index + 1)
                "java" -> creekUserStats!!.addToUnlockedJavaProgramIndexList(mProgramIndex!!.program_index + 1)
                "usp" -> creekUserStats!!.addToUnlockedUspProgramIndexList(mProgramIndex!!.program_index + 1)
            }
            FirebaseDatabaseHandler(context).writeCreekUserStats(creekUserStats!!)
        } else {
            if (mProgramIndex!!.userProgramId != "trial") {
                creekUserStats!!.addToUnlockedUserAddedPrograms(mProgramIndex!!.userProgramId)
                FirebaseDatabaseHandler(context).writeCreekUserStats(creekUserStats!!)
            }
        }
    }


    private fun showConfirmSubmitDialog(programSize: Int, countDownTimer: CountDownTimer?) {
        val builder = AlertDialog.Builder(activity)
        builder.setPositiveButton("Yes") { dialog, which ->
            checkScore(programSize, dragNDropListView!!, mProgramList!!)
            countDownTimer?.cancel()
        }

        builder.setNegativeButton("No") { dialog, which -> }

        builder.setMessage("Are you sure you want to submit the Match?")
        builder.setTitle(activity.title)
        builder.setIcon(android.R.drawable.ic_dialog_info)
        builder.show()

    }


    fun getViewByPosition(position: Int, listView: ListView): View {
        val firstListItemPosition = listView.firstVisiblePosition
        val lastListItemPosition = firstListItemPosition + listView.childCount - 1

        if (position < firstListItemPosition || position > lastListItemPosition) {
            return listView.adapter.getView(position, listView.getChildAt(position), listView)
        } else {
            val childIndex = position - firstListItemPosition
            return listView.getChildAt(childIndex)
        }
    }

    protected fun displayToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

    }

    private val mDropListener = DropListenerInterface { from, to ->
        dragNDropAdapter!!.onDrop(from, to)
        dragNDropListView!!.invalidateViews()
    }

    private val mRemoveListener = RemoveListenerInterface { which ->
        dragNDropAdapter!!.onRemove(which)
        dragNDropListView!!.invalidateViews()
    }

    private val mDragListener = object : DragListenerInterface {

        internal var backgroundColor = 0xe0103010.toInt()
        internal var defaultBackgroundColor: Int = 0

        override fun onDrag(x: Int, y: Int, listView: ListView) {

        }

        override fun onStartDrag(itemView: View) {
            itemView.visibility = View.INVISIBLE
            defaultBackgroundColor = itemView.drawingCacheBackgroundColor
            //itemView.setBackgroundColor(backgroundColor);
            val imageView = itemView.findViewById(R.id.dragItemImageView) as ImageView
            if (imageView != null) imageView.visibility = View.INVISIBLE
        }

        override fun onStopDrag(itemView: View) {
            itemView.visibility = View.VISIBLE
            //itemView.setBackgroundColor(defaultBackgroundColor);
            val imageView = itemView.findViewById(R.id.dragItemImageView) as ImageView
            if (imageView != null) imageView.visibility = View.VISIBLE
        }

    }

    internal var mQuizComplete = false

    fun onBackPressed() {
        if (mQuizComplete == false) {
            AuxilaryUtils.showConfirmationDialog(activity)
            if (mCountDownTimer != null) {
                mCountDownTimer!!.cancel()
            }
        } else {
            showAd()
        }
    }

    override fun onDetach() {
        super.onDetach()
        if (mCountDownTimer != null) {
            mCountDownTimer!!.cancel()
        }
    }

    override fun updateUI() {

        FirebaseDatabaseHandler(context)
                .getProgramTablesInBackground(mProgramIndex!!.program_index, object : FirebaseDatabaseHandler.GetProgramTablesListener {
                    override fun onSuccess(programTables: ArrayList<ProgramTable>) {
                        var program_TableList: ArrayList<ProgramTable>? = programTables
                        var prevProgramSize = 0
                        prevProgramSize = program_TableList!!.size
                        do {
                            try {
                                Thread.sleep(1000)
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }

                            program_TableList = FirebaseDatabaseHandler(context).getProgramTables(mProgramIndex!!.program_index)
                            if (prevProgramSize == program_TableList!!.size) {
                                break
                            }
                        } while (true)

                        if (program_TableList != null && program_TableList.size > 0) {
                            initUI(program_TableList)
                        }
                    }

                    override fun onError(databaseError: DatabaseError?) {

                    }
                })


    }

    private fun requestNewInterstitial() {
        val adRequest = AdRequest.Builder()
                .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                .build()
        interstitialAd!!.loadAd(adRequest)
    }

    fun setBundle(bundle: Bundle) {
        this.bundle = bundle
    }

    override fun isTestComplete(): Int {
        return if (mQuizComplete) ProgrammingBuddyConstants.KEY_MATCH else -1
    }

    override fun submitSubTest(index: Int, content: ArrayList<String>) {
        val codeViewFragment = subTestPagerAdapter!!.codeViewFragment
        codeViewFragment.submitSubTest(index, content)
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    fun setModuleDetailsScrollPageListener(moduleDetailsScrollPageListener: ModuleDetailsScrollPageListener) {
        this.moduleDetailsScrollPageListener = moduleDetailsScrollPageListener
    }
}

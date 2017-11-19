package com.sortedqueue.programmercreek.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.activity.AlgorithmListActivity
import com.sortedqueue.programmercreek.activity.ChaptersActivity
import com.sortedqueue.programmercreek.codelab.CodeLabActivity
import com.sortedqueue.programmercreek.activity.InterviewActivity
import com.sortedqueue.programmercreek.activity.IntroActivity
import com.sortedqueue.programmercreek.activity.LessonActivity
import com.sortedqueue.programmercreek.activity.NewProgramWikiActivity
import com.sortedqueue.programmercreek.activity.ProgramInserterActivity
import com.sortedqueue.programmercreek.activity.ProgramListActivity
import com.sortedqueue.programmercreek.activity.SyntaxLearnActivity
import com.sortedqueue.programmercreek.adapter.AlgorithmsRecyclerAdapter
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.AlgorithmsIndex
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekAnalytics
import com.sortedqueue.programmercreek.util.CreekPreferences

import java.util.ArrayList



import kotlinx.android.synthetic.main.fragment_child_dashboard.*

/**
 * Created by Alok on 02/01/17.
 */

class DashboardFragment : Fragment(), View.OnClickListener, FirebaseDatabaseHandler.GetAllAlgorithmsListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {


    private var creekPreferences: CreekPreferences? = null
    private var firebaseDatabaseHandler: FirebaseDatabaseHandler? = null
    private var algorithmsRecyclerAdapter: AlgorithmsRecyclerAdapter? = null
    private val TAG = DashboardFragment::class.java.simpleName

    private var dashboardNavigationListener: DashboardNavigationListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DashboardNavigationListener) {
            dashboardNavigationListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        dashboardNavigationListener = null
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_child_dashboard, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        creekPreferences = CreekApplication.creekPreferences
        initUI()
        animateViews()
    }

    fun animateViews() {
        if (creekPreferences == null) {
            creekPreferences = CreekApplication.creekPreferences
        }
        if (creekPreferences!!.programLanguage.toLowerCase() == "ada") {
            dashboardScrollView!!.visibility = View.GONE
            adaScrollView!!.visibility = View.VISIBLE
            FirebaseDatabaseHandler(context).getAllAlgorithmIndex(this)
        } else {
            if (null == dashboardScrollView) {
                return
            }

            dashboardScrollView!!.visibility = View.VISIBLE
            adaScrollView!!.visibility = View.GONE
            if (creekPreferences!!.isProgramsOnly) {
                introLayout!!.visibility = View.GONE
                wizardLayout!!.visibility = View.GONE
                syntaxLayout!!.visibility = View.GONE
                lessonsLayout!!.visibility = View.GONE
            } else {
                introLayout!!.visibility = View.VISIBLE
                wizardLayout!!.visibility = View.VISIBLE
                syntaxLayout!!.visibility = View.VISIBLE
                if (creekPreferences!!.programLanguage.equals("java", ignoreCase = true)) {
                    lessonsLayout!!.visibility = View.VISIBLE
                } else {
                    lessonsLayout!!.visibility = View.GONE
                }
            }

            dashboardScrollView!!.scrollTo(0, 0)
            introLayout!!.alpha = 0.0f
            wizardLayout!!.alpha = 0.0f
            if (creekPreferences!!.programLanguage.equals("java", ignoreCase = true))
                lessonsLayout!!.alpha = 0.0f
            syntaxLayout!!.alpha = 0.0f
            indexLayout!!.alpha = 0.0f
            wikiLayout!!.alpha = 0.0f
            quizLayout!!.alpha = 0.0f
            matchLayout!!.alpha = 0.0f
            testLayout!!.alpha = 0.0f
            interviewLayout!!.alpha = 0.0f
            quickReferenceLayout!!.alpha = 0.0f
            fillLayout!!.alpha = 0.0f
            codeLabLayout.alpha = 0.0f

            var delay = 0
            val standardDelay = 270
            initAnimations(introLayout!!, delay)
            delay += standardDelay
            initAnimations(wizardLayout!!, delay)
            if (creekPreferences!!.programLanguage.equals("java", ignoreCase = true)) {
                delay += standardDelay
                initAnimations(lessonsLayout!!, delay)
            }
            delay += standardDelay
            initAnimations(syntaxLayout!!, delay)
            delay += standardDelay
            initAnimations(quickReferenceLayout!!, delay)
            delay += standardDelay
            initAnimations(indexLayout!!, delay)
            delay += standardDelay
            initAnimations(wikiLayout!!, delay)
            delay += standardDelay
            initAnimations(quizLayout!!, delay)
            delay += standardDelay
            initAnimations(matchLayout!!, delay)
            delay += standardDelay
            initAnimations(fillLayout!!, delay)
            delay += standardDelay
            initAnimations(testLayout!!, delay)

            //delay += standardDelay;
            /*initAnimations(interviewLayout, delay);*/
            delay += standardDelay
            initAnimations(codeLabLayout, delay)
        }


    }

    private fun initAnimations(frameLayout: FrameLayout, delay: Int) {
        frameLayout.animate().setStartDelay(delay.toLong()).setDuration(400).alpha(1.0f)
    }

    private fun initUI() {
        wikiLayout!!.setOnClickListener(this)
        syntaxLayout!!.setOnClickListener(this)
        indexLayout!!.setOnClickListener(this)
        matchLayout!!.setOnClickListener(this)
        testLayout!!.setOnClickListener(this)
        quickReferenceLayout!!.setOnClickListener(this)
        interviewLayout!!.setOnClickListener(this)
        reviseLayout!!.setOnClickListener(this)
        quizLayout!!.setOnClickListener(this)
        wizardLayout!!.setOnClickListener(this)
        lessonsLayout!!.setOnClickListener(this)
        introLayout!!.setOnClickListener(this)
        fillLayout!!.setOnClickListener(this)
        codeLabLayout!!.setOnClickListener(this)
        downloadFileTextView!!.setOnClickListener(this)
        addCodeTextView!!.setOnClickListener(this)

    }


    override fun onClick(v: View) {

        if (creekPreferences!!.programLanguage == "") {
            CommonUtils.displaySnackBar(activity, R.string.choose_language)
            if (dashboardNavigationListener != null) {
                dashboardNavigationListener!!.navigateToLanguage()
            }
            return
        }

        val intent: Intent
        when (v.id) {
            R.id.wikiLayout -> {
                CreekAnalytics.logEvent(TAG, "Wiki")
                intent = Intent(context, NewProgramWikiActivity::class.java)
                startActivity(intent)
            }
            R.id.quickReferenceLayout -> {
                CreekAnalytics.logEvent(TAG, "Quick Reference")
                dashboardNavigationListener!!.showQuickReferenceFragment()
            }
            R.id.interviewLayout -> {
                CreekAnalytics.logEvent(TAG, "Interview")
                intent = Intent(context, InterviewActivity::class.java)
                startActivity(intent)
            }
            R.id.syntaxLayout -> {
                CreekAnalytics.logEvent(TAG, "Syntax")
                val syntaxIntent = Intent(context, SyntaxLearnActivity::class.java)
                syntaxIntent.putExtra(ProgrammingBuddyConstants.KEY_WIKI, creekPreferences!!.programWiki)
                startActivity(syntaxIntent)
            }
            R.id.indexLayout -> {
                CreekAnalytics.logEvent(TAG, "Wizard - Program Index")
                launchProgramListActivity(ProgrammingBuddyConstants.KEY_WIZARD)
            }

            R.id.introLayout -> if (creekPreferences!!.programLanguage.equals("java", ignoreCase = true)) {
                CreekAnalytics.logEvent(TAG, "Intro")
                val introIntent = Intent(context, IntroActivity::class.java)
                startActivity(introIntent)
            } else {
                CreekAnalytics.logEvent(TAG, "Intro")
                val introIntent = Intent(context, IntroActivity::class.java)
                startActivity(introIntent)
            }

            R.id.reviseLayout -> {
                val programInserterIntent = Intent(context, ProgramInserterActivity::class.java)
                startActivity(programInserterIntent)
            }

            R.id.wizardLayout -> {
                loadChapters()

            }

            R.id.lessonsLayout -> {
                CreekAnalytics.logEvent(TAG, "Bits and Bytes")
                val lessonIntent = Intent(context, LessonActivity::class.java)
                startActivity(lessonIntent)
            }

            R.id.testLayout -> {
                CreekAnalytics.logEvent(TAG, "Test")
                launchProgramListActivity(ProgrammingBuddyConstants.KEY_TEST)
            }

            R.id.matchLayout -> {
                CreekAnalytics.logEvent(TAG, "Match")
                launchProgramListActivity(ProgrammingBuddyConstants.KEY_MATCH)
            }

            R.id.fillLayout -> {
                CreekAnalytics.logEvent(TAG, "Fill Code")
                launchProgramListActivity(ProgrammingBuddyConstants.KEY_FILL_BLANKS)
            }

            R.id.quizLayout -> {
                CreekAnalytics.logEvent(TAG, "Quiz")
                launchProgramListActivity(ProgrammingBuddyConstants.KEY_QUIZ)
            }

            R.id.codeLabLayout -> {
                CreekAnalytics.logEvent(TAG, "Code Lab")
                launchProgramListActivity(ProgrammingBuddyConstants.KEY_CODE_LAB)
            }

            R.id.downloadFileTextView -> dashboardNavigationListener!!.importFromWeb()
        }//launchProgramListActivity(ProgrammingBuddyConstants.KEY_REVISE);
        //dashboardNavigationListener.onProgressStatsUpdate(50);
        /*Intent searchIntent =
                new Intent(getContext(), ProgramWikiActivity.class);
                startActivity(searchIntent);*/

    }

    private fun loadChapters() {
        if ( !AuxilaryUtils.isNetworkAvailable ) {
            CommonUtils.displaySnackBar(activity, R.string.internet_required, R.string.retry, View.OnClickListener { loadChapters() })
            return
        }
        CreekAnalytics.logEvent(TAG, "Chapters")
        val textModeIntent = Intent(context, ChaptersActivity::class.java)
        startActivity(textModeIntent)
    }


    private fun launchProgramListActivity(invokeMode: Int) {
        if (creekPreferences!!.getProgramTables() == -1) {
            CommonUtils.displayProgressDialog(activity, "Initializing data for the first time : " + creekPreferences!!.programLanguage.toUpperCase())
            firebaseDatabaseHandler = FirebaseDatabaseHandler(context)
            firebaseDatabaseHandler!!.initializeProgramTables(object : FirebaseDatabaseHandler.ProgramTableInterface {
                override fun getProgramTables(program_tables: ArrayList<ProgramTable>) {
                    CommonUtils.dismissProgressDialog()
                    launchProgramListActivity(invokeMode)
                }

                override fun onError(error: DatabaseError) {
                    CommonUtils.dismissProgressDialog()
                }
            })
        } else {
            if (invokeMode == ProgrammingBuddyConstants.KEY_CODE_LAB) {
                val intent = Intent(context, CodeLabActivity::class.java)
                startActivity(intent)
                return
            }
            val programListIntent = Intent(context, ProgramListActivity::class.java)
            programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, invokeMode)
            val isWizard = invokeMode == ProgrammingBuddyConstants.KEY_WIZARD
            programListIntent.putExtra(ProgramListActivity.KEY_WIZARD, isWizard)
            if (isWizard) {
                programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_REVISE)
            }
            startActivity(programListIntent)
            /*AuxilaryUtils.displayInformation(getContext(), R.string.unlock_programs, R.string.unlock_programs_description, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {

                }

            });*/

            /*this.overridePendingTransition(R.anim.animation_leave,
                    R.anim.animation_enter);*/
        }

    }

    override fun onSuccess(algorithmsIndexArrayList: ArrayList<AlgorithmsIndex>) {
        adaRecyclerView!!.layoutManager = LinearLayoutManager(context)
        algorithmsRecyclerAdapter = AlgorithmsRecyclerAdapter(context, this, algorithmsIndexArrayList)
        adaRecyclerView!!.adapter = algorithmsRecyclerAdapter
        CommonUtils.dismissProgressDialog()
    }

    override fun onError(databaseError: DatabaseError?) {
        CommonUtils.dismissProgressDialog()
    }

    override fun onItemClick(position: Int) {
        if( !AuxilaryUtils.isNetworkAvailable ) {
            CommonUtils.displaySnackBar(activity, R.string.internet_unavailable, R.string.retry, View.OnClickListener {
                onItemClick(position)
            })
            return
        }
        val intent = Intent(context, AlgorithmListActivity::class.java)
        intent.putExtra(ProgrammingBuddyConstants.KEY_PROG_ID, algorithmsRecyclerAdapter!!.getItemAtPosition(position))
        startActivity(intent)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var indexFragment: DashboardFragment? = null
        val instance: DashboardFragment
            get() {
                if (indexFragment == null) {
                    indexFragment = DashboardFragment()
                }
                return indexFragment!!
            }
    }
}

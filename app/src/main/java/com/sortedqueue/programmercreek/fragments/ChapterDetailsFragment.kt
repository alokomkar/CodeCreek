package com.sortedqueue.programmercreek.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.activity.WizardActivity
import com.sortedqueue.programmercreek.adapter.ChapterDetailsPagerAdapter
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.Chapter
import com.sortedqueue.programmercreek.database.ChapterDetails
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.ChapterNavigationListener
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener
import com.sortedqueue.programmercreek.interfaces.WikiNavigationListner
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.view.OneDirectionalScrollableViewPager
import com.sortedqueue.programmercreek.view.SwipeDirection
import com.startapp.android.publish.adsCommon.Ad
import com.startapp.android.publish.adsCommon.StartAppAd
import com.startapp.android.publish.adsCommon.VideoListener
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener
import kotlinx.android.synthetic.main.fragment_wizard_module.*

import java.util.ArrayList




/**
 * Created by Alok on 05/01/17.
 */

class ChapterDetailsFragment : Fragment(), WikiNavigationListner, ModuleDetailsScrollPageListener {

    private var chapter: Chapter? = null
    private var chapterDetailsPagerAdapter: ChapterDetailsPagerAdapter? = null
    private var creekUserStats: CreekUserStats? = null
    private var nextChapter: Chapter? = null
    private var onChapterNavigationListener: ChapterNavigationListener? = null
    private var fabDrawable: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_wizard_module, container, false)


        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CommonUtils.displayProgressDialog(context, "Loading modules")
        setupViews()
    }

    fun setChapter(chapter: Chapter) {
        this.chapter = chapter
    }

    private fun setupViews() {

        //syntaxLearnViewPager.setOffscreenPageLimit(chapter.getChapterDetailsArrayList().size());
        chapterDetailsPagerAdapter = ChapterDetailsPagerAdapter(context, this, childFragmentManager, chapter!!.chapterDetailsArrayList, this, nextChapter!!)
        syntaxLearnViewPager!!.adapter = chapterDetailsPagerAdapter
        syntaxLearnViewPager!!.setAllowedSwipeDirection(SwipeDirection.left)
        ProgressBar!!.max = chapter!!.chapterDetailsArrayList.size
        ProgressBar!!.progress = 1
        doneFAB!!.setOnClickListener { onScrollForward() }
        toggleFabDrawable(ProgressBar!!.progress)
        changeViewPagerBehavior(0)
        syntaxLearnViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                ProgressBar!!.progress = position + 1
                toggleFabDrawable(ProgressBar!!.progress)
                changeViewPagerBehavior(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        val chapterProgress = chapterProgress
        var currentIndex = 0
        if (chapterProgress != 0) {
            for (chapterDetails in chapter!!.chapterDetailsArrayList) {
                if (chapterProgress > chapterDetails.progressIndex) {
                    currentIndex++
                }
            }
        }
        syntaxLearnViewPager!!.currentItem = currentIndex
        CommonUtils.dismissProgressDialog()

    }

    private fun changeViewPagerBehavior(position: Int) {
        val chapterProgress = chapterProgress
        val fragment = chapterDetailsPagerAdapter!!.getItem(position)
        val chapterDetails = chapter!!.chapterDetailsArrayList[position]
        if (fragment is ProgramWikiFragment) {
            syntaxLearnViewPager!!.setAllowedSwipeDirection(SwipeDirection.none)
        } else {
            if (chapterProgress != 0 && chapterProgress > chapterDetails.progressIndex) {
                syntaxLearnViewPager!!.setAllowedSwipeDirection(SwipeDirection.all)
            } else {
                val testCompletionListener = fragment as TestCompletionListener
                syntaxLearnViewPager!!.setAllowedSwipeDirection(
                        if (testCompletionListener.isTestComplete != -1)
                            SwipeDirection.all
                        else
                            SwipeDirection.left)
            }

        }/*else if( fragment instanceof TestCompletionListener ) {
            TestCompletionListener testCompletionListener = (TestCompletionListener) fragment;
            syntaxLearnViewPager.setAllowedSwipeDirection(
                    testCompletionListener.isTestComplete() != -1 ?
                            SwipeDirection.left :
                            SwipeDirection.none);
        }*/
    }

    private val chapterProgress: Int
        get() {
            creekUserStats = CreekApplication.instance.creekUserStats
            var chapterProgress = 0
            when (chapter!!.program_Language) {
                "c" -> chapterProgress = creekUserStats!!.getcProgressIndex()
                "cpp", "c++" -> chapterProgress = creekUserStats!!.cppProgressIndex
                "java" -> chapterProgress = creekUserStats!!.javaProgressIndex
                "sql" -> chapterProgress = creekUserStats!!.sqlProgressIndex
            }
            return chapterProgress
        }

    private fun toggleFabDrawable(progress: Int) {
        val fragment = chapterDetailsPagerAdapter!!.getItem(progress - 1)
        fabDrawable = if (progress == ProgressBar!!.max) R.drawable.ic_done_all else android.R.drawable.ic_media_play
        if (fragment is NewMatchFragment) {
            fabDrawable = R.drawable.ic_help_outline_white_24dp
        } else if (fragment is NewFillBlankFragment) {
            fabDrawable = R.drawable.ic_help_outline_white_24dp
        } else if (fragment is TestDragNDropFragment) {
            fabDrawable = R.drawable.ic_help_outline_white_24dp
        } else if (fragment is QuizFragment) {
            fabDrawable = R.drawable.ic_help_outline_white_24dp
        }
        if (fragment is TestCompletionListener) {
            val testCompletionListener = fragment as TestCompletionListener
            if (testCompletionListener.isTestComplete != -1) {
                isRewardVideoShown = false
                fabDrawable = if (progress == ProgressBar!!.max) R.drawable.ic_done_all else android.R.drawable.ic_media_play
            }
        }
        doneFAB!!.setImageDrawable(ContextCompat.getDrawable(context, fabDrawable))
        onChapterNavigationListener!!.toggleFabDrawable(fabDrawable)
    }

    private var isRewardVideoShown = false
    override fun onScrollForward() {
        if (fabDrawable == R.drawable.ic_help_outline_white_24dp) {
            if (!isRewardVideoShown && !CreekApplication.creekPreferences!!.isPremiumUser) {
                showRewardedVideoDialog()
            } else {
                showSolutionFromFragment()
            }
        } else {
            //Add validations here : if answer is complete - track and allow scrolling
            //if chapter is already complete, don't restrict movement
            val chapterProgress = chapterProgress
            val fragment = chapterDetailsPagerAdapter!!.getItem(syntaxLearnViewPager!!.currentItem)
            val chapterDetails = chapter!!.chapterDetailsArrayList[syntaxLearnViewPager!!.currentItem]
            if (chapterProgress != 0 && chapterProgress > chapterDetails.progressIndex) {
                fabAction()
            } else {
                if (fragment is TestCompletionListener) {
                    val testCompletionListener = fragment as TestCompletionListener
                    when (testCompletionListener.isTestComplete) {
                        ProgrammingBuddyConstants.KEY_MATCH, ProgrammingBuddyConstants.KEY_TEST //Has same index as wiki - no changes for wiki
                            , ProgrammingBuddyConstants.KEY_QUIZ, ProgrammingBuddyConstants.KEY_FILL_BLANKS, ChapterDetails.TYPE_SYNTAX_MODULE -> {
                            fabAction()
                            updateCreekStats()
                        }
                        -1 -> CommonUtils.displaySnackBar(activity, "Complete the test to proceed")
                    }
                }
            }
        }

    }

    override fun toggleFABDrawable() {
        toggleFabDrawable(ProgressBar!!.progress)
    }

    private fun fabAction() {

        if (syntaxLearnViewPager!!.currentItem + 1 == chapter!!.chapterDetailsArrayList.size) {
            activity.onBackPressed()
        } else {
            syntaxLearnViewPager!!.currentItem = syntaxLearnViewPager!!.currentItem + 1
        }
    }

    private fun updateCreekStats() {
        val chapterDetails = chapterDetailsPagerAdapter!!.getChapterDetailsForPosition(syntaxLearnViewPager!!.currentItem)
        creekUserStats = CreekApplication.instance.creekUserStats
        when (CreekApplication.creekPreferences!!.programLanguage) {
            "c" -> if (creekUserStats!!.getcProgressIndex() < chapterDetails.progressIndex) {
                creekUserStats!!.setcProgressIndex(chapterDetails.progressIndex)
                when (chapterDetails.chapterType) {
                    ChapterDetails.TYPE_SYNTAX_MODULE -> {
                        creekUserStats!!.addToUnlockedCLanguageModuleIdList(chapterDetails.syntaxId)
                        creekUserStats!!.addToUnlockedCSyntaxModuleIdList(chapterDetails.syntaxId + "_" + chapterDetails.chapterReferenceId)
                        onChapterNavigationListener!!.onProgessStatsUpdate(CreekUserStats.MODULE_SCORE)
                    }
                    ChapterDetails.TYPE_PROGRAM_INDEX -> {
                        onChapterNavigationListener!!.onProgessStatsUpdate(CreekUserStats.PROGRAM_SCORE)
                        creekUserStats!!.addToUnlockedCProgramIndexList(Integer.parseInt(chapterDetails.chapterReferenceId))
                    }
                    ChapterDetails.TYPE_WIKI -> creekUserStats!!.addToUnlockedCWikiIdList(chapterDetails.chapterReferenceId)
                }
            }
            "c++", "cpp" -> if (creekUserStats!!.cppProgressIndex < chapterDetails.progressIndex) {
                creekUserStats!!.cppProgressIndex = chapterDetails.progressIndex
                when (chapterDetails.chapterType) {
                    ChapterDetails.TYPE_SYNTAX_MODULE -> {
                        onChapterNavigationListener!!.onProgessStatsUpdate(CreekUserStats.MODULE_SCORE)
                        creekUserStats!!.addToUnlockedCppLanguageModuleIdList(chapterDetails.syntaxId)
                        creekUserStats!!.addToUnlockedCppSyntaxModuleIdList(chapterDetails.syntaxId + "_" + chapterDetails.chapterReferenceId)
                    }
                    ChapterDetails.TYPE_PROGRAM_INDEX -> {
                        onChapterNavigationListener!!.onProgessStatsUpdate(CreekUserStats.PROGRAM_SCORE)
                        creekUserStats!!.addToUnlockedCppProgramIndexList(Integer.parseInt(chapterDetails.chapterReferenceId))
                    }
                    ChapterDetails.TYPE_WIKI -> creekUserStats!!.addToUnlockedCppWikiIdList(chapterDetails.chapterReferenceId)
                }
            }
            "java" -> if (creekUserStats!!.javaProgressIndex < chapterDetails.progressIndex) {
                creekUserStats!!.javaProgressIndex = chapterDetails.progressIndex
                when (chapterDetails.chapterType) {
                    ChapterDetails.TYPE_SYNTAX_MODULE -> {
                        onChapterNavigationListener!!.onProgessStatsUpdate(CreekUserStats.MODULE_SCORE)
                        creekUserStats!!.addToUnlockedJavaLanguageModuleIdList(chapterDetails.syntaxId)
                        creekUserStats!!.addToUnlockedJavaSyntaxModuleIdList(chapterDetails.syntaxId + "_" + chapterDetails.chapterReferenceId)
                    }
                    ChapterDetails.TYPE_PROGRAM_INDEX -> {
                        onChapterNavigationListener!!.onProgessStatsUpdate(CreekUserStats.PROGRAM_SCORE)
                        creekUserStats!!.addToUnlockedJavaProgramIndexList(Integer.parseInt(chapterDetails.chapterReferenceId))
                    }
                    ChapterDetails.TYPE_WIKI -> creekUserStats!!.addToUnlockedJavaWikiIdList(chapterDetails.chapterReferenceId)
                }
            }
            "sql" -> if (creekUserStats!!.sqlProgressIndex < chapterDetails.progressIndex) {
                creekUserStats!!.sqlProgressIndex = chapterDetails.progressIndex
                when (chapterDetails.chapterType) {
                    ChapterDetails.TYPE_SYNTAX_MODULE -> {
                        onChapterNavigationListener!!.onProgessStatsUpdate(CreekUserStats.MODULE_SCORE)
                        creekUserStats!!.addToUnlockedSqlLanguageModuleIdList(chapterDetails.syntaxId)
                        creekUserStats!!.addToUnlockedSqlSyntaxModuleIdList(chapterDetails.syntaxId + "_" + chapterDetails.chapterReferenceId)
                    }
                    ChapterDetails.TYPE_PROGRAM_INDEX -> {
                        onChapterNavigationListener!!.onProgessStatsUpdate(CreekUserStats.PROGRAM_SCORE)
                        creekUserStats!!.addToUnlockedSqlProgramIndexList(Integer.parseInt(chapterDetails.chapterReferenceId))
                    }
                    ChapterDetails.TYPE_WIKI -> creekUserStats!!.addToUnlockedSqlWikiIdList(chapterDetails.chapterReferenceId)
                }
            }
        }
        FirebaseDatabaseHandler(context).writeCreekUserStats(creekUserStats!!)
    }

    override fun onBackPressed() {
        syntaxLearnViewPager!!.currentItem = syntaxLearnViewPager!!.currentItem - 1
    }

    override fun disableViewPager() {
        if (syntaxLearnViewPager != null) {
            syntaxLearnViewPager!!.setAllowedSwipeDirection(SwipeDirection.none)
            doneFAB!!.isEnabled = false
        }
    }

    override fun enableViewPager() {
        if (syntaxLearnViewPager != null) {
            changeViewPagerBehavior(ProgressBar!!.progress - 1)
            doneFAB!!.isEnabled = true
        }

    }

    fun setNextChapter(nextChapter: Chapter) {
        this.nextChapter = nextChapter
    }

    fun setOnChapterNavigationListener(onChapterNavigationListener: ChapterNavigationListener) {
        this.onChapterNavigationListener = onChapterNavigationListener
    }

    fun showRewardedVideoDialog() {
        AuxilaryUtils.displayInformation(context, R.string.hint_video, R.string.reward_video_description,
                DialogInterface.OnClickListener { dialogInterface, i -> showRewardedClick() },

                DialogInterface.OnDismissListener { })
    }

    private fun showRewardedClick() {
        val rewardedVideo = StartAppAd(context)

        /**
         * This is very important: set the video listener to be triggered after video
         * has finished playing completely
         */
        rewardedVideo.setVideoListener { showSolutionFromFragment() }

        /**
         * Load rewarded by specifying AdMode.REWARDED
         * We are using AdEventListener to trigger ad show
         */
        rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, object : AdEventListener {

            override fun onReceiveAd(arg0: Ad) {
                rewardedVideo.showAd()
            }

            override fun onFailedToReceiveAd(arg0: Ad) {
                /**
                 * Failed to load rewarded video:
                 * 1. Check that FullScreenActivity is declared in AndroidManifest.xml:
                 * See https://github.com/StartApp-SDK/Documentation/wiki/Android-InApp-Documentation#activities
                 * 2. Is android API level above 16?
                 */
                Log.e("MainActivity", "Failed to load rewarded video with reason: " + arg0.errorMessage)
            }
        })
    }

    private fun showSolutionFromFragment() {
        val fragment = chapterDetailsPagerAdapter!!.getItem(ProgressBar!!.progress - 1)
        if (fragment is NewMatchFragment) {
            showSolutionDialog(fragment.getmProgramList())
        } else if (fragment is NewFillBlankFragment) {
            fragment.addHintsToBlanks()
        } else if (fragment is TestDragNDropFragment) {
            showSolutionDialog(fragment.getmProgramList())
        } else if (fragment is QuizFragment) {
            showSolutionDialog(fragment.getmProgramList())
        }
    }

    private fun showSolutionDialog(solutionList: ArrayList<String>) {
        var solution = ""
        for (string in solutionList) {
            solution += string + "\n"
        }
        Log.d("SolutionProgram", solution)
        AuxilaryUtils.displayAlert("Solution", solution, context)
    }
}

package com.sortedqueue.programmercreek.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.TopicDetailsAdapter
import com.sortedqueue.programmercreek.adapter.TutorialSlidesPagerAdapter
import com.sortedqueue.programmercreek.asynctask.TopicDetailsTask
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.SubTopics
import com.sortedqueue.programmercreek.database.TopicDetails
import com.sortedqueue.programmercreek.interfaces.BitModuleNavigationListener
import com.sortedqueue.programmercreek.interfaces.NewIntroNavigationListener
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekPreferences
import com.sortedqueue.programmercreek.util.ParallaxPageTransformer
import com.sortedqueue.programmercreek.view.OneDirectionalScrollableViewPager
import com.sortedqueue.programmercreek.view.SwipeDirection
import kotlinx.android.synthetic.main.app_bar_topic_details.*
import kotlinx.android.synthetic.main.content_topic_details.*
import kotlinx.android.synthetic.main.fragment_topic_details.*

import java.util.ArrayList





/**
 * Created by Alok on 15/09/17.
 */

class TopicDetailsFragment : Fragment(), TopicDetailsTask.TopicDetailsListener {

    private var mLessons: ArrayList<TopicDetails>? = null
    private var mNewIntroNavigationListener: NewIntroNavigationListener? = null
    private var topicDetailsAdapter: TopicDetailsAdapter? = null
    private var adapter: TutorialSlidesPagerAdapter? = null
    private var lesson: TopicDetails? = null
    private var creekPreferences: CreekPreferences? = null
    private var currentTopicPosition = 0
    private var handler: Handler? = null
    private var creekUserStats: CreekUserStats? = null
    private var runnable: Runnable? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_topic_details, container, false)
        creekPreferences = CreekApplication.creekPreferences
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolBar()
        CommonUtils.displayProgressDialog(context, "Loading chapters")
        TopicDetailsTask(context, "", this).execute()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is NewIntroNavigationListener)
            mNewIntroNavigationListener = context
    }

    private fun setupToolBar() {
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolbar)
        toolbar!!.title = "Program Wiki : " + CreekApplication.creekPreferences!!.programLanguage.toUpperCase()
        val toggle = ActionBarDrawerToggle(
                appCompatActivity, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout!!.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }


    private fun initLessons(position: Int) {
        lesson = mLessons!![position]
        toolbar!!.title = lesson!!.topicDescription
        val fragments = ArrayList<Fragment>()
        for (subTopics in lesson!!.subTopicsArrayList) {
            val subTopicFragment = SubTopicFragment()
            subTopicFragment.subTopics = subTopics
            subTopicFragment.setIntroNavigationListener(mNewIntroNavigationListener!!)
            subTopicFragment.setNavigationListener(object : BitModuleNavigationListener {
                override fun onMoveForward() {

                }

                override fun onMoveBackward() {

                }

                override fun onTestTriggered(testType: String?) {
                    topicDetailsViewPager!!.setAllowedSwipeDirection(if (testType != null) SwipeDirection.none else SwipeDirection.all)
                    changeScrollBehavior(currentTopicPosition)
                }

                override fun showLevelUpDialog(reputation: Int) {
                    animateProgress(reputation)
                }
            })
            fragments.add(subTopicFragment)
        }
        (fragments[0] as SubTopicFragment).setLastFirstIndicator(0)
        (fragments[fragments.size - 1] as SubTopicFragment).setLastFirstIndicator(1)
        adapter = TutorialSlidesPagerAdapter(childFragmentManager, fragments)
        topicDetailsViewPager!!.adapter = adapter
        topicDetailsViewPager!!.setPageTransformer(true, ParallaxPageTransformer())
        topicDetailsViewPager!!.offscreenPageLimit = 3
        progressBar!!.progress = 1
        progressBar!!.max = fragments.size
        topicDetailsViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                currentTopicPosition = position
                changeScrollBehavior(position)
                progressBar!!.progress = position + 1
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        CommonUtils.dismissProgressDialog()
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
                progressLayout!!.visibility = View.VISIBLE
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
                    handler!!.postDelayed({
                        progressLayout!!.visibility = View.GONE
                        val level = creekUserStats!!.creekUserReputation / 100
                        /*if (level > 0) {
                                    showShareLayout(level);
                                }*/
                    }, 1500)
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

    private fun changeScrollBehavior(position: Int) {

        val subTopics = lesson!!.subTopicsArrayList[position]
        if (subTopics.testMode != null && !subTopics.testMode.isEmpty()) {
            topicDetailsViewPager!!.setAllowedSwipeDirection(
                    if (creekPreferences!!.isUnlockedTopic(subTopics.subTopicId))
                        SwipeDirection.all
                    else
                        SwipeDirection.left)
        } else {
            topicDetailsViewPager!!.setAllowedSwipeDirection(SwipeDirection.all)
        }

    }

    override fun onSuccess(topicDetails: ArrayList<TopicDetails>) {
        mLessons = topicDetails
        CommonUtils.dismissProgressDialog()
        if (topicDetails != null && topicDetails.size > 0)
            initLessons(0)
        topicsRecyclerView!!.layoutManager = LinearLayoutManager(context)
        topicDetailsAdapter = TopicDetailsAdapter(topicDetails, object : CustomProgramRecyclerViewAdapter.AdapterClickListner {

            override fun onItemClick(position: Int) {
                initLessons(position)
                drawer_layout!!.closeDrawer(GravityCompat.START)
            }
        })
        topicsRecyclerView!!.adapter = topicDetailsAdapter
    }

    fun hideSubTopicFragment(): Boolean {
        val subTopicFragment = adapter!!.getItem(topicDetailsViewPager!!.currentItem) as SubTopicFragment
        if (subTopicFragment != null && subTopicFragment.isTestLoaded) {
            subTopicFragment.hideSubTopicQuestionFragment()
            return true
        }
        return false
    }
}

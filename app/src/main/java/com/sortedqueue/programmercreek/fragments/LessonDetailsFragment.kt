package com.sortedqueue.programmercreek.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.TutorialSlidesPagerAdapter
import com.sortedqueue.programmercreek.database.lessons.BitModule
import com.sortedqueue.programmercreek.database.lessons.Lesson
import com.sortedqueue.programmercreek.interfaces.BitModuleNavigationListener
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.ParallaxPageTransformer
import com.sortedqueue.programmercreek.view.OneDirectionalScrollableViewPager
import com.sortedqueue.programmercreek.view.SwipeDirection
import com.sortedqueue.programmercreek.view.ZoomOutPageTransformer

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by Alok on 29/08/17.
 */

class LessonDetailsFragment : Fragment(), BitModuleNavigationListener {

    @BindView(R.id.lessonDetailsViewPager)
    internal var lessonDetailsViewPager: OneDirectionalScrollableViewPager? = null
    internal var unbinder: Unbinder ?= null
    private var lesson: Lesson? = null
    private var adapter: TutorialSlidesPagerAdapter? = null

    fun setLesson(lesson: Lesson) {
        this.lesson = lesson
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_lesson_details, container, false)
        unbinder = ButterKnife.bind(this, fragmentView)
        return fragmentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CommonUtils.displayProgressDialog(context, getString(R.string.loading))
        val fragments = ArrayList<Fragment>()
        for (bitModule in lesson!!.bitModules) {
            val bitModuleFragment = BitModuleFragment()
            bitModuleFragment.bitModule = bitModule
            bitModuleFragment.setNavigationListener(this)
            fragments.add(bitModuleFragment)
        }
        (fragments[0] as BitModuleFragment).setLastFirstIndicator(0)
        (fragments[fragments.size - 1] as BitModuleFragment).setLastFirstIndicator(1)
        adapter = TutorialSlidesPagerAdapter(childFragmentManager, fragments)
        lessonDetailsViewPager!!.adapter = adapter
        lessonDetailsViewPager!!.setPageTransformer(true, ParallaxPageTransformer())
        lessonDetailsViewPager!!.offscreenPageLimit = 3
        CommonUtils.dismissProgressDialog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }

    override fun onMoveForward() {
        lessonDetailsViewPager!!.setCurrentItem(lessonDetailsViewPager!!.currentItem + 1, true)
    }

    override fun onMoveBackward() {
        lessonDetailsViewPager!!.setCurrentItem(lessonDetailsViewPager!!.currentItem - 1, true)
    }

    override fun onTestTriggered(testType: String?) {
        if (testType != null) {
            lessonDetailsViewPager!!.setAllowedSwipeDirection(SwipeDirection.none)
        } else {
            lessonDetailsViewPager!!.setAllowedSwipeDirection(SwipeDirection.all)
        }

    }

    override fun showLevelUpDialog(reputation: Int) {

    }

    val currentFragment: BitModuleFragment?
        get() {
            val bitModuleFragment = adapter!!.getItem(lessonDetailsViewPager!!.currentItem) as BitModuleFragment
            if (bitModuleFragment.bitModule!!.code != null && bitModuleFragment.bitModule!!.code.trim { it <= ' ' }.length > 0) {
                return bitModuleFragment
            } else {
                return null
            }
        }
}

package com.sortedqueue.programmercreek.activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.ScreenSlidePagerAdapter
import com.sortedqueue.programmercreek.database.PresentationModel
import com.sortedqueue.programmercreek.database.SlideModel
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.fragments.CreateSlideFragment
import com.sortedqueue.programmercreek.fragments.PresentationTitleFragment
import com.sortedqueue.programmercreek.interfaces.PresentationCommunicationsListener
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekPreferences
import com.sortedqueue.programmercreek.view.ZoomOutPageTransformer

import java.util.ArrayList
import java.util.HashMap



import kotlinx.android.synthetic.main.activity_create_presentation.*

/**
 * Created by Alok on 06/04/17.
 */

class CreatePresentationActivity : AppCompatActivity(), View.OnClickListener, PresentationCommunicationsListener {


    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private var mPagerAdapter: ScreenSlidePagerAdapter? = null

    private var fab_open: Animation? = null
    private var fab_close: Animation? = null
    private var rotate_forward: Animation? = null
    private var rotate_backward: Animation? = null
    private var isFabOpen: Boolean? = false
    private var fragmentArrayList: ArrayList<Fragment>? = null
    private val OPTION_CODE = 1
    private val OPTION_PHOTO = 2
    private var presentationModel: PresentationModel? = null
    private var slideModelArrayList: ArrayList<SlideModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_presentation)
        fragmentArrayList = ArrayList<Fragment>()
        slideModelArrayList = ArrayList<SlideModel>()
        initPagerAdapter()
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        presentationModel = PresentationModel()
        val creekPreferences = CreekApplication.creekPreferences
        presentationModel!!.presenterEmail = creekPreferences!!.getSignInAccount()
        presentationModel!!.presenterName = creekPreferences.getAccountName()

        fab_open = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)
        rotate_forward = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_forward)
        rotate_backward = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_backward)

        optionsFAB!!.setOnClickListener(this)
        addSlideFAB!!.setOnClickListener(this)
        deleteSlideFAB!!.setOnClickListener(this)
        deleteSlideTextView!!.setOnClickListener(this)
        addSlideTextView!!.setOnClickListener(this)
        addCodeFAB!!.setOnClickListener(this)
        addPhotoFAB!!.setOnClickListener(this)
        addPhotoTextView!!.setOnClickListener(this)
        addCodeTextView!!.setOnClickListener(this)
        optionsFAB!!.visibility = View.GONE
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
    }


    private fun initPagerAdapter() {
        fragmentArrayList!!.add(PresentationTitleFragment.instance)
        mPagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager, fragmentArrayList!!)
        pager!!.adapter = mPagerAdapter
        pager!!.setPageTransformer(true, ZoomOutPageTransformer())
        pager!!.offscreenPageLimit = mPagerAdapter!!.count
        pager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    if (isFabOpen!!) {
                        optionsFAB!!.callOnClick()
                    }
                    com.sortedqueue.programmercreek.util.AnimationUtils.exitRevealGone(optionsFAB)
                } else {
                    com.sortedqueue.programmercreek.util.AnimationUtils.enterReveal(optionsFAB)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.optionsFAB -> animateFAB()
            R.id.addSlideFAB, R.id.addSlideTextView -> {
                val fragment = mPagerAdapter!!.getItem(pager!!.currentItem)
                if (fragment is CreateSlideFragment) {
                    if (fragment.validateContent()) {
                        addNewSlide()
                    }
                } else {
                    addNewSlide()
                }
            }
            R.id.deleteSlideFAB, R.id.deleteSlideTextView -> if (mPagerAdapter!!.count > 2) {
                mPagerAdapter!!.removeCurrentFragment(pager!!.currentItem)
                mPagerAdapter!!.notifyDataSetChanged()
                pager!!.offscreenPageLimit = mPagerAdapter!!.count
            } else {
                CommonUtils.displaySnackBar(this@CreatePresentationActivity, R.string.presentation_needs_one_slide)
            }
            R.id.addCodeFAB, R.id.addCodeTextView -> addToSlide(OPTION_CODE)
            R.id.addPhotoTextView, R.id.addPhotoFAB -> addToSlide(OPTION_PHOTO)
        }
    }

    private fun addNewSlide() {
        mPagerAdapter!!.addNewSlideFragment(CreateSlideFragment())
        mPagerAdapter!!.notifyDataSetChanged()
        pager!!.currentItem = mPagerAdapter!!.count - 1
        pager!!.offscreenPageLimit = mPagerAdapter!!.count
    }

    private fun addToSlide(option_code: Int) {
        val currentFragment = mPagerAdapter!!.getItem(pager!!.currentItem) as CreateSlideFragment
        if (currentFragment != null) {
            if (option_code == OPTION_CODE) {
                currentFragment.insertCode()
            } else {
                currentFragment.insertPhoto()
            }
            optionsFAB!!.callOnClick()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_create_presentation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        if (item.itemId == R.id.action_finish) {
            saveAndExit()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveAndExit() {
        val fragment = mPagerAdapter!!.getItem(pager!!.currentItem)
        if (fragment is CreateSlideFragment) {
            if (fragment.validateContent()) {
                onPresentationComplete()
                finish()
            }
        }

    }

    override fun onBackPressed() {
        finish()
    }

    private fun animateFAB() {
        if (isFabOpen!!) {

            optionsFAB!!.startAnimation(rotate_backward)
            addSlideFAB!!.startAnimation(fab_close)
            deleteSlideFAB!!.startAnimation(fab_close)
            addSlideTextView!!.startAnimation(fab_close)
            deleteSlideTextView!!.startAnimation(fab_close)
            addCodeFAB!!.startAnimation(fab_close)
            addCodeTextView!!.startAnimation(fab_close)
            addPhotoFAB!!.startAnimation(fab_close)
            addPhotoTextView!!.startAnimation(fab_close)
            fab_close!!.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(animation: Animation) {
                    toggleVisibility(View.GONE)
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            addSlideFAB!!.isClickable = false
            deleteSlideFAB!!.isClickable = false
            isFabOpen = false


        } else {

            val fragment = mPagerAdapter!!.getItem(pager!!.currentItem)
            if (fragment is CreateSlideFragment) {
                val createSlideFragment = fragment
                if (createSlideFragment != null) {
                    addPhotoTextView!!.setText(if (createSlideFragment.isPhotoVisible) R.string.remove_photo else R.string.add_photo)
                    addCodeTextView!!.setText(if (createSlideFragment.isCodeVisible) R.string.remove_code else R.string.add_code)
                }
            }
            optionsFAB!!.startAnimation(rotate_forward)
            addSlideFAB!!.startAnimation(fab_open)
            deleteSlideFAB!!.startAnimation(fab_open)
            addSlideTextView!!.startAnimation(fab_open)
            deleteSlideTextView!!.startAnimation(fab_open)
            addCodeFAB!!.startAnimation(fab_open)
            addCodeTextView!!.startAnimation(fab_open)
            addPhotoFAB!!.startAnimation(fab_open)
            addPhotoTextView!!.startAnimation(fab_open)
            addSlideFAB!!.isClickable = true
            deleteSlideFAB!!.isClickable = true
            isFabOpen = true

        }
    }

    private fun toggleVisibility(visibility: Int) {
        deleteSlideFAB!!.visibility = visibility
        addSlideTextView!!.visibility = visibility
        deleteSlideFAB!!.visibility = visibility
        deleteSlideTextView!!.visibility = visibility
        addSlideFAB!!.visibility = visibility
        addSlideTextView!!.visibility = visibility
        addCodeFAB!!.visibility = visibility
        addCodeTextView!!.visibility = visibility
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }

    override fun onPresentationTitle(presentationTitle: String, presentationDescription: String, tagsList: ArrayList<String>) {
        presentationModel!!.presentationName = presentationTitle
        presentationModel!!.presentationDescription = presentationDescription
        val tagsMap = HashMap<String, Int>()
        for (tag in tagsList) {
            tagsMap.put(tag, 1)
        }
        presentationModel!!.tagList = tagsMap
        addNewSlide()
    }

    override fun onPresentationCreation(presentationId: String, slideModel: SlideModel) {

        presentationModel!!.presentationPushId = presentationId
        if (presentationModel!!.presentationImage == null && slideModel.slideImageUrl != null) {
            presentationModel!!.presentationImage = slideModel.slideImageUrl
        }
        if (!slideModelArrayList!!.contains(slideModel))
            slideModelArrayList!!.add(slideModel)
        presentationModel!!.slideModelArrayList = slideModelArrayList

    }

    override fun onPresentationComplete() {
        val firebaseDatabaseHandler = FirebaseDatabaseHandler(this@CreatePresentationActivity)
        firebaseDatabaseHandler.presentationPushId = null
        firebaseDatabaseHandler.writeNewPresentation(presentationModel!!)
    }
}

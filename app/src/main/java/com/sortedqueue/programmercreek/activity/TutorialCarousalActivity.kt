package com.sortedqueue.programmercreek.activity


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.TutorialSlidesPagerAdapter
import com.sortedqueue.programmercreek.database.TutorialModel
import com.sortedqueue.programmercreek.fragments.TutorialModelFragment
import com.sortedqueue.programmercreek.interfaces.TutorialNavigationListener
import com.sortedqueue.programmercreek.view.ZoomOutPageTransformer

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok on 12/05/17.
 */

class TutorialCarousalActivity : AppCompatActivity(), TutorialNavigationListener {


    @BindView(R.id.tutorialViewPager)
    internal var tutorialViewPager: ViewPager? = null

    private var tutorialModels: ArrayList<TutorialModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial_carousal)
        ButterKnife.bind(this)

        tutorialModels = ArrayList<TutorialModel>()
        var tutorialModel = TutorialModel("Add code and gain reputation 15xp for every code added\n\nPractice other users programs and gain 30xp",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/code_import%2Freputation_15.png?alt=media&token=9b3e0d32-5a0c-4788-83cb-28f4b02ca48d")
        tutorialModels!!.add(tutorialModel)
        tutorialModel = TutorialModel("Simply copy code from your browser, click SHARE",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/share_code%2Fshare1.png?alt=media&token=de08f979-5df0-4c50-bdf9-c5cf987da4b6")
        tutorialModels!!.add(tutorialModel)
        tutorialModel = TutorialModel("Choose Code share option",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/share_code%2Fshare2.png?alt=media&token=c6f1375d-c76f-4cf0-8630-c93815280190")
        tutorialModels!!.add(tutorialModel)
        tutorialModel = TutorialModel("Enter program name and set language",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/share_code%2Fshare3.png?alt=media&token=71d178ae-3157-4900-a74c-beae3eb13318")
        tutorialModels!!.add(tutorialModel)
        tutorialModel = TutorialModel("Preview your code",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/share_code%2Fshare4.png?alt=media&token=ad6e84fd-b9b7-43a5-bc2a-d1acf5cab1de")
        tutorialModels!!.add(tutorialModel)
        tutorialModel = TutorialModel("Click save to use program later, Gain reputaion : 25xp",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/share_code%2Fshare5.png?alt=media&token=9a673c21-9c33-4384-b5c5-e61cea305527")
        tutorialModels!!.add(tutorialModel)

        var index = 1
        val fragmentArrayList = ArrayList<Fragment>()
        for (model in tutorialModels!!) {
            val tutorialModelFragment = TutorialModelFragment()
            tutorialModelFragment.setParameter(model, index++, tutorialModels!!.size)
            fragmentArrayList.add(tutorialModelFragment)
        }

        tutorialViewPager!!.adapter = TutorialSlidesPagerAdapter(supportFragmentManager, fragmentArrayList)
        tutorialViewPager!!.setPageTransformer(true, ZoomOutPageTransformer())
        tutorialViewPager!!.offscreenPageLimit = tutorialModels!!.size

    }

    override fun onBackPressed() {
        /*Intent intent = new Intent();
        setResult(RESULT_OK, intent);*/
        finish()
    }


    override fun onNextClick() {
        tutorialViewPager!!.currentItem = tutorialViewPager!!.currentItem + 1
    }


    override fun onPreviousClick() {
        tutorialViewPager!!.currentItem = tutorialViewPager!!.currentItem - 1
    }

    override fun onCancelClick() {

        onBackPressed()
    }
}

package com.sortedqueue.programmercreek.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.ViewSlidesPagerAdapter
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.PresentationModel
import com.sortedqueue.programmercreek.database.SlideModel
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.view.ZoomOutPageTransformer

import java.util.ArrayList



import kotlinx.android.synthetic.main.activity_view_presentation.*

/**
 * Created by Alok on 11/04/17.
 */

class ViewPresentationActivity : AppCompatActivity(), FirebaseDatabaseHandler.GetAllSlidesListener {

    private val TAG = ViewPresentationActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_presentation)

        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val presentationModel = intent.extras!!.getParcelable<PresentationModel>(ProgrammingBuddyConstants.KEY_PROG_ID)
        if (presentationModel != null) {
            fetchSlides(presentationModel)
        }
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
    }

    private fun fetchSlides(presentationModel: PresentationModel) {
        CommonUtils.displayProgressDialog(this@ViewPresentationActivity, "Loading")
        onSuccess(presentationModel.slideModelArrayList)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_create_presentation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        } else if (item.itemId == R.id.action_finish) {
            saveAndExit()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveAndExit() {
        onBackPressed()
    }

    override fun onBackPressed() {
        finish()
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }

    override fun onSuccess(slideModelArrayList: ArrayList<SlideModel>) {
        CommonUtils.dismissProgressDialog()
        Log.d(TAG, "Slides size : " + slideModelArrayList.size)
        pager!!.adapter = ViewSlidesPagerAdapter(supportFragmentManager, slideModelArrayList)
        pager!!.setPageTransformer(true, ZoomOutPageTransformer())
        pager!!.offscreenPageLimit = slideModelArrayList.size
    }

    override fun onFailure(databaseError: DatabaseError?) {
        CommonUtils.dismissProgressDialog()
    }
}

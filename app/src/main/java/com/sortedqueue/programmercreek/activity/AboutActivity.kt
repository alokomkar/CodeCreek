package com.sortedqueue.programmercreek.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R

import butterknife.BindView
import butterknife.ButterKnife
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class AboutActivity : AppCompatActivity() {

    @BindView(R.id.descriptionTextView)
    internal var descriptionTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        ButterKnife.bind(this)

        val viewProfileBtn = findViewById(R.id.btn_view_profile) as Button
        viewProfileBtn.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/pub/alok-omkar/b/917/333"))
            startActivity(browserIntent)
        }
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)

    }

    override fun onResume() {
        super.onResume()
        CreekApplication.instance.isAppRunning = true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.about, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                descriptionTextView!!.text = getString(R.string.about_string)
                return true
            }
            R.id.action_terms_and_conditions -> {
                descriptionTextView!!.text = getString(R.string.terms_and_conditions)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        CreekApplication.instance.isAppRunning = false
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }

    override fun onBackPressed() {
        finish()
    }


}

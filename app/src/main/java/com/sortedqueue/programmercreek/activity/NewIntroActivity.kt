package com.sortedqueue.programmercreek.activity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.fragments.IntroTopicsFragment
import com.sortedqueue.programmercreek.fragments.TopicDetailsFragment
import com.sortedqueue.programmercreek.interfaces.NewIntroNavigationListener




import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by Alok on 15/09/17.
 */

class NewIntroActivity : AppCompatActivity(), NewIntroNavigationListener {

    private var mFragmentTransaction: FragmentTransaction? = null
    private var topicDetailsFragment: TopicDetailsFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_intro)

        loadIntroTopicsFragment()
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun loadIntroTopicsFragment() {
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        mFragmentTransaction!!.replace(R.id.container, IntroTopicsFragment(), IntroTopicsFragment::class.java.simpleName)
        mFragmentTransaction!!.addToBackStack(null)
        mFragmentTransaction!!.commit()
    }

    override fun loadTopicDetailsFragment(topic: String) {
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        topicDetailsFragment = TopicDetailsFragment()
        val bundle = Bundle()
        bundle.putString("topic", topic)
        topicDetailsFragment!!.arguments = bundle
        mFragmentTransaction!!.replace(R.id.container, topicDetailsFragment!!, TopicDetailsFragment::class.java.simpleName)
        mFragmentTransaction!!.addToBackStack(null)
        mFragmentTransaction!!.commit()
    }

    override fun onBackPressFromFragment(): Boolean {
        return topicDetailsFragment != null && topicDetailsFragment!!.hideSubTopicFragment()
    }

    override fun onBackPressed() {
        if (!onBackPressFromFragment()) {
            if (supportFragmentManager.backStackEntryCount > 1) {
                supportFragmentManager.popBackStack()
            } else
                finish()
        }

    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }
}

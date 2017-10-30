package com.sortedqueue.programmercreek.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.interfaces.NewIntroNavigationListener
import kotlinx.android.synthetic.main.fragment_intro_topics.*


class IntroTopicsFragment : Fragment(), View.OnClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_intro_topics, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        javaIntroCardView!!.setOnClickListener(this)
        javaClassCardView!!.setOnClickListener(this)
        javaMainCardView!!.setOnClickListener(this)
        javaKeywordsCardView!!.setOnClickListener(this)
        javaLoopsCardView!!.setOnClickListener(this)
        javaStatementsCardView!!.setOnClickListener(this)
        javaOperatorsCardView!!.setOnClickListener(this)
    }

    private fun setupToolbar() {

        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolbar)
        toolbar!!.title = getString(R.string.intro) + " to " + CreekApplication.creekPreferences!!.programLanguage.toUpperCase()
        appCompatActivity.supportActionBar!!.setHomeButtonEnabled(true)
        appCompatActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            activity.finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private var mNewIntroNavigationListener: NewIntroNavigationListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is NewIntroNavigationListener) {
            mNewIntroNavigationListener = context
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.javaIntroCardView -> {
            }
            R.id.javaClassCardView -> {
            }
            R.id.javaMainCardView -> {
            }
            R.id.javaLoopsCardView -> {
            }
            R.id.javaKeywordsCardView -> {
            }
            R.id.javaStatementsCardView -> {
            }
            R.id.javaOperatorsCardView -> {
            }
        }
        mNewIntroNavigationListener!!.loadTopicDetailsFragment("Any topic")
    }
}// Required empty public constructor

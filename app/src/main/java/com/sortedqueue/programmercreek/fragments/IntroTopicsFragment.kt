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

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder

class IntroTopicsFragment : Fragment(), View.OnClickListener {


    @BindView(R.id.javaIntroCardView)
    internal var javaIntroCardView: CardView? = null
    @BindView(R.id.javaClassCardView)
    internal var javaClassCardView: CardView? = null
    @BindView(R.id.javaMainCardView)
    internal var javaMainCardView: CardView? = null
    @BindView(R.id.javaKeywordsCardView)
    internal var javaKeywordsCardView: CardView? = null
    @BindView(R.id.javaLoopsCardView)
    internal var javaLoopsCardView: CardView? = null
    @BindView(R.id.javaStatementsCardView)
    internal var javaStatementsCardView: CardView? = null
    @BindView(R.id.javaOperatorsCardView)
    internal var javaOperatorsCardView: CardView? = null
    internal var unbinder: Unbinder ?= null
    @BindView(R.id.toolbar)
    internal var toolbar: Toolbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_intro_topics, container, false)
        unbinder = ButterKnife.bind(this, view)
        setupToolbar()
        javaIntroCardView!!.setOnClickListener(this)
        javaClassCardView!!.setOnClickListener(this)
        javaMainCardView!!.setOnClickListener(this)
        javaKeywordsCardView!!.setOnClickListener(this)
        javaLoopsCardView!!.setOnClickListener(this)
        javaStatementsCardView!!.setOnClickListener(this)
        javaOperatorsCardView!!.setOnClickListener(this)
        return view
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
        unbinder!!.unbind()
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

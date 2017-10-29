package com.sortedqueue.programmercreek.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.ProgramWikiRecyclerAdapter
import com.sortedqueue.programmercreek.database.ChapterDetails
import com.sortedqueue.programmercreek.database.WikiModel
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener
import com.sortedqueue.programmercreek.interfaces.WikiNavigationListner
import com.sortedqueue.programmercreek.util.CommonUtils

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok Omkar on 2016-12-31.
 */

class ProgramWikiFragment : Fragment(), TestCompletionListener {

    @BindView(R.id.headerTextView)
    internal var headerTextView: TextView? = null
    @BindView(R.id.programWikiRecyclerView)
    internal var programWikiRecyclerView: RecyclerView? = null
    @BindView(R.id.backImageView)
    internal var backImageView: ImageView? = null
    @BindView(R.id.progressBar)
    internal var progressBar: ContentLoadingProgressBar? = null
    private var wikiNavigationListener: WikiNavigationListner? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_program_wiki, null)
        ButterKnife.bind(this, fragmentView)
        setupViews(programWiki!!)
        if (wizardUrl == null) {
            progressBar!!.visibility = View.VISIBLE
            headerTextView!!.text = programWiki!!.wikiHeader
            setupRecyclerView(programWiki!!)
        } else {
            progressBar!!.visibility = View.VISIBLE
            if (wikiNavigationListener != null)
                wikiNavigationListener!!.disableViewPager()
            FirebaseDatabaseHandler(context).getWikiModel(wizardUrl, object : FirebaseDatabaseHandler.GetWikiModelListener {
                override fun onSuccess(wikiModel: WikiModel) {
                    this@ProgramWikiFragment.programWiki = wikiModel
                    headerTextView!!.text = programWiki!!.wikiHeader
                    setupRecyclerView(programWiki!!)
                }

                override fun onError(databaseError: DatabaseError) {
                    CommonUtils.displayToast(context, R.string.unable_to_fetch_data)
                    progressBar!!.visibility = View.GONE
                    if (wikiNavigationListener != null) {
                        wikiNavigationListener!!.enableViewPager()
                    }
                }
            })
        }

        return fragmentView
    }

    private fun setupViews(programWiki: WikiModel) {
        this.programWiki = programWiki
    }

    private fun setupRecyclerView(wikiModel: WikiModel) {
        programWikiRecyclerView!!.adapter = ProgramWikiRecyclerAdapter(context, wikiModel.programWikis)
        programWikiRecyclerView!!.layoutManager = LinearLayoutManager(context)
        backImageView!!.setOnClickListener {
            if (wikiNavigationListener != null) {
                wikiNavigationListener!!.onBackPressed()
            }
        }
        backImageView!!.visibility = if (wikiNavigationListener != null) View.VISIBLE else View.GONE
        progressBar!!.visibility = View.GONE
        if (wikiNavigationListener != null) {
            wikiNavigationListener!!.enableViewPager()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    private var programWiki: WikiModel? = null
    fun setParams(programWiki: WikiModel) {
        this.programWiki = programWiki
    }

    private var wizardUrl: String? = null
    fun setParams(wizardUrl: String) {
        this.wizardUrl = wizardUrl
    }

    override fun isTestComplete(): Int {
        return ChapterDetails.TYPE_WIKI
    }

    fun setWikiNavigationListener(wikiNavigationListener: WikiNavigationListner) {
        this.wikiNavigationListener = wikiNavigationListener
    }
}

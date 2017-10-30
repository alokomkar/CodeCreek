package com.sortedqueue.programmercreek.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.ArticleShareAdaper
import com.sortedqueue.programmercreek.adapter.TagsRecyclerAdapter
import com.sortedqueue.programmercreek.database.NotesModel
import com.sortedqueue.programmercreek.database.TagModel
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.fragments.NotesPreviewFragment

import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekPreferences
import com.sortedqueue.programmercreek.util.NotesUtils

import java.util.ArrayList



import kotlinx.android.synthetic.main.activity_article_share.*

/**
 * Created by Alok on 24/08/17.
 */

class ArticleShareActivity : AppCompatActivity(), View.OnClickListener {

    private var creekPreferences: CreekPreferences? = null
    private var tagsRecyclerAdapter: TagsRecyclerAdapter? = null
    private var sharedText: String? = null
    private var notesShareRecyclerAdapter: ArticleShareAdaper? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_share)

        creekPreferences = CreekPreferences(this@ArticleShareActivity)
        // Get intent, action and MIME type
        val intent = intent
        val action = intent.action
        val type = intent.type

        if (Intent.ACTION_SEND == action && type != null) {
            if ("text/plain" == type) {
                handleSendText(intent) // Handle text being sent
            }
        }
        fetchAllTags()
        previewButton!!.setOnClickListener(this)
        saveButton!!.setOnClickListener(this)
        discardButton!!.setOnClickListener(this)
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)

    }

    private fun fetchAllTags() {

        FirebaseDatabaseHandler(this@ArticleShareActivity).getAllTags(object : FirebaseDatabaseHandler.GetAllTagsListener {
            override fun onError(databaseError: DatabaseError) {
                CommonUtils.dismissProgressDialog()
            }

            override fun onSuccess(tagModel: TagModel) {
                setupRecyclerView(tagModel)
            }
        })
    }

    private fun setupRecyclerView(tagModel: TagModel) {
        languageRecyclerView!!.layoutManager = LinearLayoutManager(this@ArticleShareActivity, LinearLayoutManager.HORIZONTAL, false)
        tagsRecyclerAdapter = TagsRecyclerAdapter(tagModel.tagArrayList, 1)
        languageRecyclerView!!.adapter = tagsRecyclerAdapter
    }

    @SuppressLint("StaticFieldLeak")
    private fun handleSendText(intent: Intent) {
        sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (sharedText != null) {

            object : AsyncTask<Void, Void, ArrayList<NotesModel>>() {

                override fun onPreExecute() {
                    super.onPreExecute()
                    CommonUtils.displayProgressDialog(this@ArticleShareActivity, "Loading")
                }

                override fun onPostExecute(notesModelArrayList: ArrayList<NotesModel>) {
                    super.onPostExecute(notesModelArrayList)
                    CommonUtils.dismissProgressDialog()
                    setupRecyclerView(notesModelArrayList)
                }

                override fun doInBackground(vararg voids: Void): ArrayList<NotesModel> {
                    return NotesUtils.splitParaIntoNotes(sharedText!!)
                }

            }.execute()
        }
    }

    private fun setupRecyclerView(notesModelArrayList: ArrayList<NotesModel>) {
        notesRecyclerView!!.layoutManager = LinearLayoutManager(this@ArticleShareActivity)
        notesShareRecyclerAdapter = ArticleShareAdaper(notesModelArrayList)
        notesRecyclerView!!.adapter = notesShareRecyclerAdapter
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.saveButton -> if (tagsRecyclerAdapter!!.getSelectedTag() == "") {
                CommonUtils.displayToast(this@ArticleShareActivity, "Select language")
                return
            }
            R.id.previewButton -> {
                if (tagsRecyclerAdapter!!.getSelectedTag() == "") {
                    CommonUtils.displayToast(this@ArticleShareActivity, "Select language")
                    return
                }
                container!!.visibility = View.VISIBLE
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_down, R.anim.slide_out_up)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.replace(R.id.container, NotesPreviewFragment.newInstance(notesShareRecyclerAdapter!!.notesModelArrayList, tagsRecyclerAdapter!!.getSelectedTag())).commit()
            }
            R.id.discardButton -> {
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            return
        }
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
        finish()
    }
}

package com.sortedqueue.programmercreek.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.NotesShareRecyclerAdapter
import com.sortedqueue.programmercreek.adapter.TagsRecyclerAdapter
import com.sortedqueue.programmercreek.database.NotesModel
import com.sortedqueue.programmercreek.database.TagModel
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.fragments.NotesPreviewFragment
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekPreferences
import com.sortedqueue.programmercreek.util.NotesUtils

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok Omkar on 2017-07-28.
 */

class NoteShareActivity : AppCompatActivity(), View.OnClickListener {

    @BindView(R.id.notesRecyclerView)
    internal var notesRecyclerView: RecyclerView? = null
    @BindView(R.id.previewButton)
    internal var previewButton: Button? = null
    @BindView(R.id.discardButton)
    internal var discardButton: Button? = null
    @BindView(R.id.saveButton)
    internal var saveButton: Button? = null
    @BindView(R.id.buttonLayout)
    internal var buttonLayout: LinearLayout? = null
    @BindView(R.id.container)
    internal var container: FrameLayout? = null
    @BindView(R.id.languageRecyclerView)
    internal var languageRecyclerView: RecyclerView? = null

    private var creekPreferences: CreekPreferences? = null
    private var sharedText: String? = null
    private var notesShareRecyclerAdapter: NotesShareRecyclerAdapter? = null
    private var tagsRecyclerAdapter: TagsRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_share)
        ButterKnife.bind(this)
        creekPreferences = CreekPreferences(this@NoteShareActivity)
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

        FirebaseDatabaseHandler(this@NoteShareActivity).getAllTags(object : FirebaseDatabaseHandler.GetAllTagsListener {
            override fun onError(databaseError: DatabaseError) {
                CommonUtils.dismissProgressDialog()
            }

            override fun onSuccess(tagModel: TagModel) {
                setupRecyclerView(tagModel)
            }
        })
    }

    private fun setupRecyclerView(tagModel: TagModel) {
        languageRecyclerView!!.layoutManager = LinearLayoutManager(this@NoteShareActivity, LinearLayoutManager.HORIZONTAL, false)
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
                    CommonUtils.displayProgressDialog(this@NoteShareActivity, "Loading")
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
        notesRecyclerView!!.layoutManager = LinearLayoutManager(this@NoteShareActivity)
        notesShareRecyclerAdapter = NotesShareRecyclerAdapter(notesModelArrayList)
        notesRecyclerView!!.adapter = notesShareRecyclerAdapter
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.saveButton -> if (tagsRecyclerAdapter!!.getSelectedTag() == "") {
                CommonUtils.displayToast(this@NoteShareActivity, "Select language")
                return
            }
            R.id.previewButton -> {
                if (tagsRecyclerAdapter!!.getSelectedTag() == "") {
                    CommonUtils.displayToast(this@NoteShareActivity, "Select language")
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

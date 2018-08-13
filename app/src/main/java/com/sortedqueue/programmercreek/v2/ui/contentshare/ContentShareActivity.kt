package com.sortedqueue.programmercreek.v2.ui.contentshare

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.util.GravitySnapHelper
import com.sortedqueue.programmercreek.v2.base.BaseActivity
import com.sortedqueue.programmercreek.v2.base.PCPreferences
import com.sortedqueue.programmercreek.v2.base.hide
import com.sortedqueue.programmercreek.v2.base.show
import com.sortedqueue.programmercreek.v2.data.helper.Content
import com.sortedqueue.programmercreek.v2.data.helper.ContentType
import com.sortedqueue.programmercreek.v2.ui.ContentShareDialog
import kotlinx.android.synthetic.main.activity_content_share.*
import java.util.ArrayList
import java.util.regex.Pattern


@Suppress("PrivatePropertyName")
class ContentShareActivity : BaseActivity() {

    private val TAG = "AddedNotes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_share)
        mBasePreferencesAPI = PCPreferences( this )

        tvStart.setOnClickListener { splitIntoParas() }
        tvHeader.setOnClickListener {  }
        tvBullets.setOnClickListener {  }
        tvContent.setOnClickListener {  }
        tvBullets.setOnClickListener {  }
        tvCode.setOnClickListener {  }
        tvDone.setOnClickListener {  }
        saveFAB.setOnClickListener {
            sharedText = etContent.text.toString()
            if( sharedText != "" ) {
                mBasePreferencesAPI.setSavedNotes( sharedText!! )
            }
        }
        handleSendText()
    }

    private fun splitIntoParas() {
        sharedText = etContent.text.toString()
        if( sharedText != "" ) {
            etContent.hide()
            val p = Pattern.compile("\\n[\\n]+")
            /*if your text file has \r\n as the
            newline character then use
            Pattern p = Pattern.compile("\\r\\n[\\r\\n]+");*/
            val result = p.split(sharedText)

            if (result != null) {
                val contentList = ArrayList<Content>()
                for( line in result ) {
                    if( line.trim().isNotEmpty() )
                        contentList.add(Content(line))
                }
                contentRecyclerView.show()
                contentRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                val contentAdapter = ContentShareDialog.ContentRVAdapter(contentList)
                contentRecyclerView.adapter = contentAdapter
                GravitySnapHelper(Gravity.START).attachToRecyclerView(contentRecyclerView)
            }
        }
    }

    private var sharedText: String? = ""

    private fun handleSendText() {

        val intent = intent
        val action = intent.action
        val type = intent.type

        if (Intent.ACTION_SEND == action && type != null) {
            if ("text/plain" == type) {
                sharedText = intent!!.getStringExtra(Intent.EXTRA_TEXT)
                etContent.setText( sharedText )
                mBasePreferencesAPI.setSavedNotes( sharedText!! )
            }
        }
        else {
            sharedText = mBasePreferencesAPI.getSavedNotes()
            etContent.setText( sharedText )
        }


    }
}
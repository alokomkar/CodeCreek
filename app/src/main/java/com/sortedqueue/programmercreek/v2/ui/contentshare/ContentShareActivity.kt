package com.sortedqueue.programmercreek.v2.ui.contentshare

import android.content.Intent
import android.os.Bundle
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.BaseActivity
import com.sortedqueue.programmercreek.v2.base.PCPreferences
import com.sortedqueue.programmercreek.v2.base.hide
import com.sortedqueue.programmercreek.v2.data.helper.Content
import com.sortedqueue.programmercreek.v2.data.helper.ContentType
import kotlinx.android.synthetic.main.activity_content_share.*
import java.util.*
import java.util.regex.Pattern


@Suppress("PrivatePropertyName")
class ContentShareActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_share)

        tvStart.setOnClickListener { splitIntoParas() }
        tvHeader.setOnClickListener { setContentType( 0, "Header" ) }
        tvBullets.setOnClickListener { setContentType( 1, "Bullets" )   }
        tvContent.setOnClickListener { setContentType( 2, "Content" )   }
        tvCode.setOnClickListener {  setContentType( 3, "Code" )  }
        tvDone.setOnClickListener {  }
        saveFAB.setOnClickListener {
            sharedText = etContent.text.toString()
            if( sharedText != "" ) {
                mBasePreferencesAPI.setSavedNotes( sharedText!! )
            }
        }
        handleSendText()
    }

    private fun setContentType( contentType: Int, contentTag: String) {
        if( sharePagerAdapter != null ) {
            val content = sharePagerAdapter!!.getItem(sharePager.currentItem).setContentType(ContentType(contentType, contentTag ))
            if( contentList != null ) {
                contentList!![sharePager.currentItem] = content
                mBasePreferencesAPI.setContentList( contentList!! )
            }
        }
    }

    private var contentList: ArrayList<Content>?= null

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
                contentList = mBasePreferencesAPI.getContentList()
                //if( contentList!!.isEmpty() )
                    for( line in result ) {
                        val content = Content(line)
                        if( line.trim().isNotEmpty() && !contentList!!.contains(content) )
                            contentList!!.add(content)
                    }

                sharePager.setCanScroll(true)
                sharePagerAdapter = SharePagerAdapter( supportFragmentManager, contentList!! )
                sharePager.adapter = sharePagerAdapter

            }
        }
    }

    private var sharedText: String? = ""
    private var sharePagerAdapter : SharePagerAdapter ?= null

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
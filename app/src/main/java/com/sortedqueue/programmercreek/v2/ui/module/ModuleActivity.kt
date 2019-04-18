package com.sortedqueue.programmercreek.v2.ui.module

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.BaseActivity
import com.sortedqueue.programmercreek.v2.base.PCPreferences
import com.sortedqueue.programmercreek.v2.data.model.Chapter
import java.util.*

@SuppressLint("CommitTransaction")
class ModuleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module)
        loadContent()
    }

    private fun loadContent() {
        when( mode ) {
            modeChapter -> {
                loadModule()
            }
        }
    }

    private var moduleFragment: ModuleFragment ?= null

    private fun loadModule() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.apply {
            moduleFragment = ModuleFragment().apply {
                arguments = intent.extras
                addToBackStack(null)
                replace(R.id.moduleContainer, this, ModuleFragment::class.java.simpleName).commit()
            }
        }
    }

    override fun onBackPressed() {
        if( moduleFragment?.onBackPressed() == true )
            finish()
    }

    companion object {

        private var mode : Int = 0
        private const val modeChapter = 1
        const val modulePosition = "position"
        const val chaptersListExtra = "chaptersListExtra"

        fun loadChapter(context: Context, chapter: Chapter, position: Int, chaptersList: ArrayList<Chapter>?) {
            context.apply {
                mode = modeChapter
                startActivity( Intent( this, ModuleActivity::class.java ).apply {
                    putExtra( Chapter::class.java.simpleName, chapter )
                    putParcelableArrayListExtra( chaptersListExtra, chaptersList )
                    putExtra( modulePosition, position )
                } )
            }
        }
    }
}
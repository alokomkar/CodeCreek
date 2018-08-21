package com.sortedqueue.programmercreek.v2.ui.module

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.BaseActivity
import com.sortedqueue.programmercreek.v2.base.PCPreferences
import com.sortedqueue.programmercreek.v2.data.model.Chapter

@SuppressLint("CommitTransaction")
class ModuleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module)
        mBasePreferencesAPI = PCPreferences( this )
        loadContent()
    }

    private fun loadContent() {
        when( mode ) {
            modeChapter -> {
                loadModule()
            }
        }
    }

    private fun loadModule() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.apply {
            val moduleFragment = ModuleFragment()
            moduleFragment.arguments = intent.extras
            addToBackStack(null)
            replace(R.id.moduleContainer, moduleFragment, ModuleFragment::class.java.simpleName).commit()
        }
    }

    override fun onBackPressed() = finish()

    companion object {

        private var mode : Int = 0
        private const val modeChapter = 1
        const val modulePosition = "position"
        const val chaptersListExtra = "chaptersListExtra"

        fun loadChapter(context: Context, chapter: Chapter, position: Int, chaptersList: ArrayList<Chapter>?) {
            context.apply {
                mode = modeChapter
                val intent = Intent( context, ModuleActivity::class.java )
                intent.putExtra( Chapter::class.java.simpleName, chapter )
                intent.putParcelableArrayListExtra( chaptersListExtra, chaptersList )
                intent.putExtra( modulePosition, position )
                startActivity( intent )
            }
        }
    }
}
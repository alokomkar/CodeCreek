package com.sortedqueue.programmercreek.interfaces

import com.sortedqueue.programmercreek.database.LanguageModule

/**
 * Created by Alok Omkar on 2016-12-25.
 */

interface SyntaxNavigationListener {
    fun onModuleLoad(module: LanguageModule, nextModule: LanguageModule?)
    fun setImageDrawable(drawable: Int)
}

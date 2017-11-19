package com.sortedqueue.programmercreek.codelab

import com.sortedqueue.programmercreek.base.BaseView
import com.sortedqueue.programmercreek.database.CodeShortCuts
import java.util.*

/**
 * Created by Alok Omkar on 2017-11-19.
 */
interface CodeLabView : BaseView {
    fun getCodeShortCuts(codeShortCuts : ArrayList<CodeShortCuts>)
}
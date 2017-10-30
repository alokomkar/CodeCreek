package com.sortedqueue.programmercreek.auth

import com.sortedqueue.programmercreek.base.BaseView

/**
 * Created by Alok on 30/10/17.
 */
interface AuthView : BaseView {
    fun startApp()
    fun freshSignUp()
    fun cancelLoginDialog()
}
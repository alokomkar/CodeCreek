package com.sortedqueue.programmercreek.base

/**
 * Created by Alok on 30/10/17.
 */
interface BaseView {
    fun showProgress( messageId : Int )
    fun hideProgress( )
    fun onError( error : String )
}
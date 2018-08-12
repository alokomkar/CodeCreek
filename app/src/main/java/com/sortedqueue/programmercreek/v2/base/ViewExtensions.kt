package com.sortedqueue.programmercreek.v2.base

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.isVisible() : Boolean =
    this.visibility == View.VISIBLE

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.toggleVisibility() {
    if( this.isVisible() ) this.hide() else this.hide()
}

fun View.showSnackBar( message : Int ) = Snackbar.make( this, message, Snackbar.LENGTH_SHORT ).show()



package com.sortedqueue.programmercreek.util

/**
 * Created by Alok Omkar on 2017-03-09.
 */
interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}

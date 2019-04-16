package com.sortedqueue.programmercreek.v2.base

import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseAdapter<T, VH : BaseViewHolder> : RecyclerView.Adapter<VH>(){

    var baseAdapterClickListener: ((view : View, position : Int, item : T) -> Unit)? = null
    var onEmptyOrNot: ((isEmpty: Boolean) -> Unit)? = null
    var onReadyToLoadMore: (() -> Unit)? = null

    protected var itemsList: MutableList<T> = mutableListOf()

    fun addAll(list: List<T>) {
        val startPosition = itemsList.size
        itemsList.addAll(startPosition, list)
        notifyItemRangeInserted(startPosition, list.size)
        onEmptyOrNot?.invoke(itemsList.isEmpty())
    }

    fun addAllUnique(list: List<T>) {
        if( itemsList.isEmpty() ) {
            addAll(list)
        }
        else {
            //val startPosition = itemsList.size
            list.iterator().forEach { t ->
                if( !itemsList.contains(t) ) {
                    itemsList.add(t)
                }
            }
            notifyDataSetChanged()
            onEmptyOrNot?.invoke(itemsList.isEmpty())
        }
    }

    fun removeAll() {
        itemsList.clear()
        notifyDataSetChanged()
        onEmptyOrNot?.invoke(itemsList.isEmpty())
    }

    fun remove(item: T) {
        val position = itemsList.indexOf(item)
        itemsList.removeAt(position)
        notifyItemRemoved(position)
        onEmptyOrNot?.invoke(itemsList.isEmpty())
    }

    @CallSuper
    override fun onBindViewHolder(holder: VH, position: Int) {
        onReadyToLoadMore?.let {
            if (position == itemCount - 2)
                it.invoke()
        }
    }

    override fun getItemCount(): Int = itemsList.size
}
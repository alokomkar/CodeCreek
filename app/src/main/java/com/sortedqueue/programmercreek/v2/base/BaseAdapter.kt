package com.sortedqueue.programmercreek.v2.base

import android.support.v7.widget.RecyclerView

abstract class BaseAdapter<T, VH : BaseViewHolder> : RecyclerView.Adapter<VH>(){}
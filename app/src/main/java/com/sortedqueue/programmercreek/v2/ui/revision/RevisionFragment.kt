package com.sortedqueue.programmercreek.v2.ui.revision

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.BaseAdapterClickListener
import com.sortedqueue.programmercreek.v2.base.BaseDialogFragment
import com.sortedqueue.programmercreek.v2.base.CenterZoomLayoutManager
import com.sortedqueue.programmercreek.v2.base.getFirstContent
import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import com.sortedqueue.programmercreek.v2.ui.module.SimpleContentAdapter
import kotlinx.android.synthetic.main.fragment_revision.*

class RevisionFragment : BaseDialogFragment(), BaseAdapterClickListener<SimpleContent> {
    override fun onItemClick(position: Int, item: SimpleContent) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_revision, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivClose?.setOnClickListener { dismiss() }
        rvRevision?.apply {

            layoutManager = CenterZoomLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }

            adapter = SimpleContentAdapter( getFirstContent(), this@RevisionFragment )

            PagerSnapHelper().attachToRecyclerView(this)

        }
    }

    companion object {
        fun getInstance() = RevisionFragment()
    }
}
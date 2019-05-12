package com.sortedqueue.programmercreek.v2.ui.revision

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.v2.base.BaseViewHolder
import com.sortedqueue.programmercreek.v2.base.hide
import com.sortedqueue.programmercreek.v2.base.show
import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import kotlinx.android.synthetic.main.item_revision_content.view.*

class RevisionContentViewHolder( viewGroup : ViewGroup )  : BaseViewHolder<SimpleContent>( viewGroup, R.layout.item_revision_content ), View.OnClickListener {

    override fun onClick( view: View? ) {
        val position = adapterPosition
        if( position != RecyclerView.NO_POSITION ) {
            view?.apply {
                onItemClickListener?.invoke(this)
            }
        }
    }

    override fun bindData(item : SimpleContent) {

        itemView.tvContent.text = item.getFormattedContent()
        itemView.tvHeader.text = item.contentString
        itemView.tvBullets.text = item.contentString
        itemView.editor.setText( item.contentString )
        itemView.editor.isEnabled = false

        if( item.contentType == SimpleContent.image ) {
            Glide.with(itemView.ivContent)
                    .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.splash_logo))
                    .asBitmap()
                    .load(item.contentString)
                    .into(itemView.ivContent)
        }

        itemView.tvContent.hide()
        itemView.tvHeader.hide()
        itemView.tvBullets.hide()
        itemView.editor.hide()
        itemView.tvPracticeNow.hide()
        itemView.ivContent.hide()

        when( item.contentType ) {
            SimpleContent.header -> itemView.tvHeader.show()
            SimpleContent.bullets -> itemView.tvBullets.show()
            SimpleContent.content -> itemView.tvContent.show()
            SimpleContent.code -> {
                itemView.editor.show()
                if( AuxilaryUtils.splitProgramIntolines(item.contentString).size >= 3 )
                    itemView.tvPracticeNow.show()
                else
                    itemView.tvPracticeNow.hide()
            }
            SimpleContent.image -> itemView.ivContent.show()
        }
    }

    init {
        itemView.setOnClickListener( this )
        itemView.tvPracticeNow.setOnClickListener(this)
    }

}
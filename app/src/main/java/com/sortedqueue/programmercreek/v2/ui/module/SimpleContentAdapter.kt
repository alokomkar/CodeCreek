package com.sortedqueue.programmercreek.v2.ui.module

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.BaseAdapterClickListener
import com.sortedqueue.programmercreek.v2.base.hide
import com.sortedqueue.programmercreek.v2.base.show
import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import com.sortedqueue.programmercreek.view.CodeEditor
import java.util.*

@SuppressLint("InflateParams")
class SimpleContentAdapter(private val contentList : ArrayList<SimpleContent>,
                           private val adapterClickListener: BaseAdapterClickListener<SimpleContent> )
    : RecyclerView.Adapter<SimpleContentAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder( LayoutInflater.from( parent.context ).inflate( R.layout.item_simple_content, null ) )

    override fun getItemCount(): Int = contentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData( contentList[position] )
    }

    inner class ViewHolder( itemView : View) : RecyclerView.ViewHolder( itemView ), View.OnClickListener {

        internal val tvHeader = itemView.findViewById<TextView>(R.id.tvHeader)
        internal val tvContent = itemView.findViewById<TextView>(R.id.tvContent)
        internal val tvBullets = itemView.findViewById<TextView>(R.id.tvBullets)
        internal val editor = itemView.findViewById<CodeEditor>(R.id.editor)
        private val ivContent = itemView.findViewById<ImageView>(R.id.ivContent)

        override fun onClick( view: View? ) {
            val position = adapterPosition
            if( position != RecyclerView.NO_POSITION ) {
                adapterClickListener.onItemClick( position, contentList[position] )
            }
        }

        fun bindData( content : SimpleContent ) {

            tvContent.text = content.getFormattedContent()
            tvHeader.text = content.contentString
            tvBullets.text = content.contentString
            editor.setText( content.contentString )
            editor.isEnabled = false

            if( content.contentType == SimpleContent.image ) {
                Glide.with(ivContent)
                        .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.splash_logo))
                        .asBitmap()
                        .load(content.contentString)
                        .into(ivContent)
            }

            tvContent.hide()
            tvHeader.hide()
            tvBullets.hide()
            editor.hide()
            ivContent.hide()

            when( content.contentType ) {
                SimpleContent.header -> tvHeader.show()
                SimpleContent.bullets -> tvBullets.show()
                SimpleContent.content -> tvContent.show()
                SimpleContent.code -> editor.show()
                SimpleContent.image -> ivContent.show()
            }
        }

        init {
            itemView.setOnClickListener( this )
        }

    }

    fun addItem(simpleContent: SimpleContent) {
        contentList.add(simpleContent)
        notifyItemInserted(contentList.size - 1 )
    }
}
package com.sortedqueue.programmercreek.v2.ui


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.v2.data.helper.Content
import com.sortedqueue.programmercreek.v2.data.helper.ContentType
import com.sortedqueue.programmercreek.v2.ui.ContentShareDialog.TypeRVAdapter.ViewHolder
import java.util.*

@Suppress("DEPRECATION")
class ContentShareDialog( context : Context, sharedText : String ) {

    init {

        val dialog = Dialog(context, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_content_share)

        val window = dialog.window
        val wlp = window.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.show()

        dialog.findViewById<Button>(R.id.saveButton).setOnClickListener {  }
        dialog.findViewById<Button>(R.id.cancelButton).setOnClickListener {  }

        val typeRecyclerView = dialog.findViewById<RecyclerView>(R.id.typeRecyclerView)
        val contentRecyclerView = dialog.findViewById<RecyclerView>(R.id.contentRecyclerView)

        typeRecyclerView.layoutManager = LinearLayoutManager( context, LinearLayout.HORIZONTAL, false )
        contentRecyclerView.layoutManager = LinearLayoutManager( context, LinearLayout.VERTICAL, false )

        val contentList = ArrayList<Content>()
        val programLines = AuxilaryUtils.splitProgramIntolines(sharedText)

        for( line in programLines ) {
            contentList.add(Content(line))
        }
        val contentAdapter = ContentRVAdapter(contentList)
        contentRecyclerView.adapter = contentAdapter

        val contentTypeList = ArrayList<ContentType>()
        contentTypeList.add(ContentType(0, "header"))
        contentTypeList.add(ContentType(1, "subHeader"))
        typeRecyclerView.adapter = TypeRVAdapter( contentTypeList, object : TypeRVInterface {
            override fun onItemSelected(position: Int) {
                contentAdapter.setContentTag(contentTypeList[position])
            }
        })



    }

    interface TypeRVInterface {
        fun onItemSelected( position: Int )
    }

    inner class TypeRVAdapter( private val contentTypeList: ArrayList<ContentType>,
                               private val typeRVInterface: TypeRVInterface
    ) : RecyclerView.Adapter<ViewHolder>() {

        private var selectedPosition = 0
        @SuppressLint("InflateParams")
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_selected_tag, null))
        }

        override fun getItemCount(): Int {
            return contentTypeList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tagTextView.text = contentTypeList[position].contentTag
            holder.tagLayout.isSelected = selectedPosition ==position
        }

        inner class ViewHolder( view : View ) : RecyclerView.ViewHolder(view), View.OnClickListener {
            override fun onClick(p0: View?) {
                val position = adapterPosition
                if( position != RecyclerView.NO_POSITION ) {
                    selectedPosition = position
                    typeRVInterface.onItemSelected( position )
                }
            }

            val tagTextView : TextView = view.findViewById(R.id.tagTextView)
            val tagLayout : LinearLayout = view.findViewById(R.id.tagLayout)

            init {
                view.setOnClickListener(this)
            }
        }

    }

    class ContentRVAdapter( private val contentTypeList: ArrayList<Content> ) : RecyclerView.Adapter<ContentRVAdapter.ViewHolder>() {

        private var selectedPosition = 0
        private var contentType : ContentType ?= null

        @SuppressLint("InflateParams")
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_content, null))
        }

        override fun getItemCount(): Int {
            return contentTypeList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.contentTextView.text = contentTypeList[position].contentString
            holder.typeTextView.text = contentTypeList[position].contentType.contentTag
        }

        fun setContentTag( contentType: ContentType ) {
            if( selectedPosition != -1 ) contentTypeList[selectedPosition].contentType = contentType
            this.contentType = contentType
            notifyItemChanged( selectedPosition )
        }

        inner class ViewHolder( view : View ) : RecyclerView.ViewHolder(view), View.OnClickListener {

            override fun onClick(p0: View?) {
                val position = adapterPosition
                if( position != RecyclerView.NO_POSITION ) {
                    selectedPosition = position
                    if( contentType != null )
                        setContentTag(contentType!!)
                }
            }

            val contentTextView : TextView = view.findViewById(R.id.contentTextView)
            val typeTextView : TextView = view.findViewById(R.id.typeTextView)

            init {
                view.setOnClickListener( this )
            }
        }

    }
}
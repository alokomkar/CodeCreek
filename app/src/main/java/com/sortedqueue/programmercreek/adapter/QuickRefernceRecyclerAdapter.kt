package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.QuickReference
import com.sortedqueue.programmercreek.util.PrettifyHighlighter

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok on 04/08/17.
 */

class QuickRefernceRecyclerAdapter(private var quickReferences: ArrayList<QuickReference>?) : RecyclerView.Adapter<QuickRefernceRecyclerAdapter.ViewHolder>() {
    private var inflater: LayoutInflater? = null
    private val prettifyHighlighter: PrettifyHighlighter
    private var context: Context? = null

    init {
        this.prettifyHighlighter = PrettifyHighlighter.getInstance()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater!!.inflate(R.layout.item_quick_reference, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val quickReference = quickReferences!![position]
        holder.headerTextView!!.text = quickReference.header
        for (content in quickReference.contentArray) {
            val contentView = inflater!!.inflate(R.layout.item_edit_code, null)
            val codeEditText = contentView.findViewById(R.id.codeEditText) as EditText
            codeEditText.isEnabled = false
            if (content.contains("<") || content.contains(">")) {
                codeEditText.setText(content)
                codeEditText.setTextColor(Color.parseColor("#006699"))
            } else {
                if (Build.VERSION.SDK_INT >= 24) {
                    codeEditText.setText(Html.fromHtml(prettifyHighlighter.highlight("c", content), Html.FROM_HTML_MODE_LEGACY))
                } else {
                    codeEditText.setText(Html.fromHtml(prettifyHighlighter.highlight("c", content)))
                }
            }
            holder.explanationLayout!!.addView(contentView)
        }

        //holder.explanationLayout.setVisibility( quickReference.isExpanded ? View.GONE : View.VISIBLE );

        //holder.indicatorImageview.setImageDrawable(ContextCompat.getDrawable(context, quickReference.isExpanded ? android.R.drawable.arrow_down_float : android.R.drawable.arrow_up_float ));

    }

    fun expandItem(position: Int) {
        val quickReference = quickReferences!![position]
        quickReference.isExpanded = true
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return quickReferences!!.size
    }

    fun setItems(quickReferences: ArrayList<QuickReference>) {
        this.quickReferences = quickReferences
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        @BindView(R.id.headerTextView)
        internal var headerTextView: TextView? = null
        @BindView(R.id.explanationLayout)
        internal var explanationLayout: LinearLayout? = null
        @BindView(R.id.indicatorImageview)
        internal var indicatorImageview: ImageView? = null


        init {
            ButterKnife.bind(this, itemView)
            headerTextView!!.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            /*int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                QuickReference quickReference = quickReferences.get(position);
                quickReference.isExpanded = !quickReference.isExpanded;
                explanationLayout.setVisibility( quickReference.isExpanded ? View.GONE : View.VISIBLE );
                if( explanationLayout.getVisibility() == View.GONE ) {
                    explanationLayout.removeAllViews();
                }
                notifyItemChanged(position);
            }*/
        }
    }


}

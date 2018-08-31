package com.sortedqueue.programmercreek.v2.ui.codelanguage

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.BaseAdapterClickListener
import com.sortedqueue.programmercreek.v2.data.model.CodeLanguage
import java.util.*

class CodeLanguageAdapter(private val codeLanguageList : ArrayList<CodeLanguage>,
                          private val adapterClickListener: BaseAdapterClickListener<CodeLanguage> )
    : RecyclerView.Adapter<CodeLanguageAdapter.CodeLanguageViewHolder>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeLanguageViewHolder
            = CodeLanguageViewHolder( LayoutInflater.from( parent.context ).inflate( R.layout.item_language, null ) )

    override fun getItemCount(): Int = codeLanguageList.size

    override fun onBindViewHolder(holder: CodeLanguageViewHolder, position: Int) {
        holder.bindData( codeLanguageList[position] )
    }

    inner class CodeLanguageViewHolder( itemView : View )
        : RecyclerView.ViewHolder( itemView ),
            View.OnClickListener {

        override fun onClick(view: View?) {
            if( adapterPosition != RecyclerView.NO_POSITION )
                adapterClickListener.onItemClick( adapterPosition, codeLanguageList[adapterPosition] )
        }

        fun bindData(codeLanguage: CodeLanguage) {
            languageIdTextView.text = codeLanguage.languageExtension
            programLanguageDescriptionTextView.text = codeLanguage.description
            programmingTextView.text = codeLanguage.language
        }

        internal val languageIdTextView : TextView = itemView.findViewById(R.id.languageIdTextView)
        internal val programmingTextView: TextView = itemView.findViewById(R.id.programmingTextView)
        internal val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        internal val programLanguageDescriptionTextView: TextView = itemView.findViewById(R.id.programLanguageDescriptionTextView)

        init {
            itemView.setOnClickListener( this )
        }

    }
}
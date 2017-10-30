package com.sortedqueue.programmercreek.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.ProgramWiki
import com.sortedqueue.programmercreek.util.AuxilaryUtils




/**
 * Created by Alok Omkar on 2017-01-11.
 */
class ProgramInserterWikiAdapter(private val programWikis: List<ProgramWiki>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        this.context = parent.context
        val adapterView = LayoutInflater.from(parent.context).inflate(R.layout.item_wiki_inserter, parent, false)
        when (viewType) {
            ProgramWiki.CONTENT_HEADER -> return HeaderViewHolder(adapterView)
            ProgramWiki.CONTENT_PROGRAM -> return ExampleViewHolder(adapterView)
            ProgramWiki.CONTENT_PROGRAM_EXPLANATION -> return ExplanationViewHolder(adapterView)
        }
        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val programWiki = programWikis[position]
        when (getItemViewType(position)) {
            ProgramWiki.CONTENT_HEADER -> initHeader(holder, programWiki)
            ProgramWiki.CONTENT_PROGRAM -> initProgram(holder, programWiki)
            ProgramWiki.CONTENT_PROGRAM_EXPLANATION -> initExplanation(holder, programWiki)
        }
    }

    private fun initExplanation(holder: RecyclerView.ViewHolder, programWiki: ProgramWiki) {
        val explanationViewHolder = holder as ExplanationViewHolder
        explanationViewHolder.wikiExplanationTextView!!.text = programWiki.programExplanation
    }

    private fun initProgram(holder: RecyclerView.ViewHolder, programWiki: ProgramWiki) {
        val exampleViewHolder = holder as ExampleViewHolder
        exampleViewHolder.wikiExampleTextView!!.text = programWiki.programExample
        exampleViewHolder.wikiOuptputTextView!!.text = programWiki.output
    }

    private fun initHeader(holder: RecyclerView.ViewHolder, programWiki: ProgramWiki) {
        val headerViewHolder = holder as HeaderViewHolder
        headerViewHolder.wikiHeaderTextView!!.text = programWiki.header
    }


    override fun getItemViewType(position: Int): Int {
        return programWikis[position].contentType
    }

    override fun getItemCount(): Int {
        return programWikis.size
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        @BindView(R.id.wikiHeaderTextView)
        internal var wikiHeaderTextView: TextView? = null
        @BindView(R.id.wikiExplanationTextView)
        internal var wikiExplanationTextView: TextView? = null
        @BindView(R.id.wikiExampleTextView)
        internal var wikiExampleTextView: TextView? = null
        @BindView(R.id.wikiOuptputTextView)
        internal var wikiOuptputTextView: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
            wikiExplanationTextView!!.visibility = View.GONE
            wikiExampleTextView!!.visibility = View.GONE
            wikiOuptputTextView!!.visibility = View.GONE
            wikiHeaderTextView!!.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val programWiki = programWikis[position]
                showInputDialog("Wiki Header", programWiki.header, object : AuxilaryUtils.InputTextListener {
                    override fun onSuccess(text: String) {
                        programWiki.header = text
                        wikiHeaderTextView!!.text = programWiki.header
                    }

                    override fun onDismiss() {

                    }
                })
            }
        }
    }

    inner class ExplanationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        @BindView(R.id.wikiHeaderTextView)
        internal var wikiHeaderTextView: TextView? = null
        @BindView(R.id.wikiExplanationTextView)
        internal var wikiExplanationTextView: TextView? = null
        @BindView(R.id.wikiExampleTextView)
        internal var wikiExampleTextView: TextView? = null
        @BindView(R.id.wikiOuptputTextView)
        internal var wikiOuptputTextView: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
            wikiHeaderTextView!!.visibility = View.GONE
            wikiExampleTextView!!.visibility = View.GONE
            wikiOuptputTextView!!.visibility = View.GONE
            wikiExplanationTextView!!.setOnClickListener(this)
        }


        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val programWiki = programWikis[position]
                showInputDialog("Explanation", programWiki.programExplanation, object : AuxilaryUtils.InputTextListener {
                    override fun onSuccess(text: String) {
                        programWiki.programExplanation = text
                        wikiExplanationTextView!!.text = programWiki.programExplanation
                    }

                    override fun onDismiss() {

                    }
                })
            }
        }
    }

    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        @BindView(R.id.wikiHeaderTextView)
        internal var wikiHeaderTextView: TextView? = null
        @BindView(R.id.wikiExplanationTextView)
        internal var wikiExplanationTextView: TextView? = null
        @BindView(R.id.wikiExampleTextView)
        internal var wikiExampleTextView: TextView? = null
        @BindView(R.id.wikiOuptputTextView)
        internal var wikiOuptputTextView: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
            wikiExplanationTextView!!.visibility = View.GONE
            wikiHeaderTextView!!.visibility = View.GONE
            wikiOuptputTextView!!.setOnClickListener(this)
            wikiExampleTextView!!.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val programWiki = programWikis[position]
                when (v.id) {
                    R.id.wikiOuptputTextView -> showInputDialog("Output", programWiki.output, object : AuxilaryUtils.InputTextListener {
                        override fun onSuccess(text: String) {
                            programWiki.output = text
                            wikiOuptputTextView!!.text = programWiki.output
                        }

                        override fun onDismiss() {

                        }
                    })
                    R.id.wikiExampleTextView -> showInputDialog("Example", programWiki.programExample, object : AuxilaryUtils.InputTextListener {
                        override fun onSuccess(text: String) {
                            programWiki.programExample = text
                            wikiExampleTextView!!.text = programWiki.programExample
                        }

                        override fun onDismiss() {

                        }
                    })
                }

            }
        }
    }

    fun showInputDialog(title: String, content: String, inputTextListener: AuxilaryUtils.InputTextListener) {
        AuxilaryUtils.displayInputDialog(context!!, title, content, inputTextListener)
    }

}

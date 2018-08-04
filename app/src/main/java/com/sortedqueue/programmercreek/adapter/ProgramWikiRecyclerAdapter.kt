package com.sortedqueue.programmercreek.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.codelab.CodeLabActivity
import com.sortedqueue.programmercreek.constants.LanguageConstants
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.ProgramWiki
import com.sortedqueue.programmercreek.database.firebase.Code



import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme

/**
 * Created by Alok Omkar on 2016-12-31.
 */
class ProgramWikiRecyclerAdapter(private val context: Context, private val programWikis: List<ProgramWiki>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var programLanguage: String? = null

    init {
        this.programLanguage = CreekApplication.creekPreferences!!.programLanguage
        if (programLanguage == "c++") {
            programLanguage = "cpp"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        when (viewType) {
            ProgramWiki.CONTENT_HEADER -> {
                view = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false)
                return HeaderViewHolder(view)
            }
            ProgramWiki.CONTENT_PROGRAM -> {
                view = LayoutInflater.from(context).inflate(R.layout.item_program, parent, false)
                return ProgramViewHolder(view)
            }
            ProgramWiki.CONTENT_PROGRAM_EXPLANATION -> {
                view = LayoutInflater.from(context).inflate(R.layout.item_program_explanation, parent, false)
                return ProgramExplanationViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false)
                return HeaderViewHolder(view)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return programWikis[position].contentType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val programWiki = programWikis[position]
        when (getItemViewType(position)) {
            ProgramWiki.CONTENT_HEADER -> initHeaderView(holder, programWiki, position)
            ProgramWiki.CONTENT_PROGRAM -> initProgramView(holder, programWiki, position)
            ProgramWiki.CONTENT_PROGRAM_EXPLANATION -> initProgramExplanationView(holder, programWiki, position)
        }

    }

    private fun initProgramExplanationView(holder: RecyclerView.ViewHolder, programWiki: ProgramWiki, position: Int) {
        val programExplanationViewHolder = holder as ProgramExplanationViewHolder
        programExplanationViewHolder.syntaxDescriptionTextView!!.text = programWiki.programExplanation
    }


    @SuppressLint("SetTextI18n")
    private fun initProgramView(holder: RecyclerView.ViewHolder, programWiki: ProgramWiki, position: Int) {
        val programViewHolder = holder as ProgramViewHolder
        programViewHolder.syntaxDescriptionTextView!!.text = "Example : \n" + programWiki.programExample
        //programViewHolder.syntaxDescriptionTextView.setText( "Example : \n" + programWiki.getProgramExample());
        programViewHolder.syntaxSolutionTextView!!.text = programWiki.output
        programViewHolder.programCodeView!!.setOptions(Options.get(context)
                .withLanguage(programLanguage!!)
                .withCode(programWiki.programExample)
                .withTheme(ColorTheme.MONOKAI))
        /*if( programLanguage.equals("cpp") || programLanguage.equals("c") || programLanguage.equals("java") ) {
            programViewHolder.codeLabTextView.setVisibility(View.VISIBLE);
        }
        else */run { programViewHolder.codeLabTextView!!.visibility = View.GONE }
    }

    private fun initHeaderView(holder: RecyclerView.ViewHolder, programWiki: ProgramWiki, position: Int) {
        val headerViewHolder = holder as HeaderViewHolder
        headerViewHolder.syntaxNameTextView!!.text = programWiki.header
    }

    override fun getItemCount(): Int {
        return programWikis.size
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var syntaxNameTextView: TextView? = view.findViewById(R.id.syntaxNameTextView)

        init {

        }
    }

    inner class ProgramViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        internal var syntaxDescriptionTextView: TextView? = view.findViewById(R.id.syntaxDescriptionTextView)


        internal var syntaxSolutionTextView: TextView? = view.findViewById(R.id.syntaxSolutionTextView)


        internal var programCodeView: CodeView? = view.findViewById(R.id.programCodeView)


        internal var codeLabTextView: TextView? = view.findViewById(R.id.codeLabTextView)

        init {

            codeLabTextView!!.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val code = Code()
                when (programLanguage) {
                    "c" -> code.language = LanguageConstants.C_INDEX
                    "cpp" -> code.language = LanguageConstants.CPP_INDEX
                    "java" -> code.language = LanguageConstants.JAVA_INDEX
                }
                val sourceCode = programWikis[position].programExample
                code.sourceCode = sourceCode
                val intent = Intent(context, CodeLabActivity::class.java)
                intent.putExtra(ProgrammingBuddyConstants.KEY_PROG_ID, code)
                context.startActivity(intent)
            }
        }
    }

    inner class ProgramExplanationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var syntaxDescriptionTextView: TextView? = view.findViewById(R.id.syntaxDescriptionTextView)

        init {

        }
    }
}

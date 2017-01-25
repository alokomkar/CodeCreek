package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.ProgramWiki;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;

/**
 * Created by Alok Omkar on 2016-12-31.
 */
public class ProgramWikiRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ProgramWiki> programWikis;
    private String programLanguage;

    public ProgramWikiRecyclerAdapter(Context context, List<ProgramWiki> programWikis) {
        this.context = context;
        this.programWikis = programWikis;
        this.programLanguage = new CreekPreferences(context).getProgramLanguage();
        if( programLanguage.equals("c++") ) {
            programLanguage = "cpp";
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ProgramWiki.CONTENT_HEADER:
                view = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
                return new HeaderViewHolder(view);
            case ProgramWiki.CONTENT_PROGRAM:
                view = LayoutInflater.from(context).inflate(R.layout.item_program, parent, false);
                return new ProgramViewHolder(view);
            case ProgramWiki.CONTENT_PROGRAM_EXPLANATION:
                view = LayoutInflater.from(context).inflate(R.layout.item_program_explanation, parent, false);
                return new ProgramExplanationViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return programWikis.get(position).getContentType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ProgramWiki programWiki = programWikis.get(position);
        switch (getItemViewType(position)) {
            case ProgramWiki.CONTENT_HEADER:
                initHeaderView(holder, programWiki, position);
                break;
            case ProgramWiki.CONTENT_PROGRAM:
                initProgramView(holder, programWiki, position);
                break;
            case ProgramWiki.CONTENT_PROGRAM_EXPLANATION:
                initProgramExplanationView(holder, programWiki, position);
                break;
        }

    }

    private void initProgramExplanationView(RecyclerView.ViewHolder holder, ProgramWiki programWiki, int position) {
        ProgramExplanationViewHolder programExplanationViewHolder = (ProgramExplanationViewHolder) holder;
        programExplanationViewHolder.syntaxDescriptionTextView.setText(programWiki.getProgramExplanation());
    }


    private void initProgramView(RecyclerView.ViewHolder holder, ProgramWiki programWiki, int position) {
        ProgramViewHolder programViewHolder = (ProgramViewHolder) holder;
        programViewHolder.syntaxDescriptionTextView.setText("Example : \n" + programWiki.getProgramExample());
        //programViewHolder.syntaxDescriptionTextView.setText( "Example : \n" + programWiki.getProgramExample());
        programViewHolder.syntaxSolutionTextView.setText(programWiki.getOutput());
        programViewHolder.programCodeView.setOptions(Options.Default.get(context)
                .withLanguage(programLanguage)
                .withCode(programWiki.getProgramExample())
                .withTheme(ColorTheme.MONOKAI))
                ;
    }

    private void initHeaderView(RecyclerView.ViewHolder holder, ProgramWiki programWiki, int position) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.syntaxNameTextView.setText(programWiki.getHeader());
    }

    @Override
    public int getItemCount() {
        return programWikis.size();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.syntaxNameTextView)
        TextView syntaxNameTextView;

        public HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ProgramViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.syntaxDescriptionTextView)
        TextView syntaxDescriptionTextView;

        @Bind(R.id.syntaxSolutionTextView)
        TextView syntaxSolutionTextView;

        @Bind(R.id.programCodeView)
        CodeView programCodeView;

        public ProgramViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ProgramExplanationViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.syntaxDescriptionTextView)
        TextView syntaxDescriptionTextView;

        public ProgramExplanationViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

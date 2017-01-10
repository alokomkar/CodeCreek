package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.protectsoft.webviewcode.Codeview;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.ProgramWiki;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2016-12-31.
 */
public class ProgramWikiRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ProgramWiki> programWikis;

    public ProgramWikiRecyclerAdapter(Context context, List<ProgramWiki> programWikis) {
        this.context = context;
        this.programWikis = programWikis;
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
        Codeview.with(context)
                .withCode("Example : \n" + programWiki.getProgramExample())
                .into(programViewHolder.syntaxDescriptionTextView);
        //programViewHolder.syntaxDescriptionTextView.setText( "Example : \n" + programWiki.getProgramExample());
        programViewHolder.syntaxSolutionTextView.setText(programWiki.getOutput());
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
        WebView syntaxDescriptionTextView;

        @Bind(R.id.syntaxSolutionTextView)
        TextView syntaxSolutionTextView;

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

package com.sortedqueue.programmercreek.adapter;

import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.ProgramWiki;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-01-11.
 */
public class ProgramInserterWikiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProgramWiki> programWikis;

    public ProgramInserterWikiAdapter(List<ProgramWiki> programWikis) {
        this.programWikis = programWikis;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View adapterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wiki_inserter, parent, false);
        switch (viewType) {
            case ProgramWiki.CONTENT_HEADER :
                return new HeaderViewHolder(adapterView);
            case ProgramWiki.CONTENT_PROGRAM:
                return new ExampleViewHolder(adapterView);
            case ProgramWiki.CONTENT_PROGRAM_EXPLANATION:
                return new ExplanationViewHolder(adapterView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProgramWiki programWiki = programWikis.get(position);
        switch (getItemViewType(position)) {
            case ProgramWiki.CONTENT_HEADER :
                initHeader(holder, programWiki);
                break;
            case ProgramWiki.CONTENT_PROGRAM:
                initProgram(holder, programWiki);
                break;
            case ProgramWiki.CONTENT_PROGRAM_EXPLANATION:
                initExplanation(holder, programWiki);
                break;
        }
    }

    private void initExplanation(RecyclerView.ViewHolder holder, ProgramWiki programWiki) {
        ExplanationViewHolder explanationViewHolder = (ExplanationViewHolder) holder;
        explanationViewHolder.wikiExplanationEditText.setText(programWiki.getProgramExplanation());
    }

    private void initProgram(RecyclerView.ViewHolder holder, ProgramWiki programWiki) {
        ExampleViewHolder exampleViewHolder = (ExampleViewHolder) holder;
        exampleViewHolder.wikiExampleEditText.setText(programWiki.getProgramExample());
        exampleViewHolder.wikiOuptputEditText.setText(programWiki.getOutput());
    }

    private void initHeader(RecyclerView.ViewHolder holder, ProgramWiki programWiki) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.wikiHeaderEditText.setText(programWiki.getHeader());
    }



    @Override
    public int getItemViewType(int position) {
        return programWikis.get(position).getContentType();
    }

    @Override
    public int getItemCount() {
        return programWikis.size();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder implements TextWatcher {
        @Bind(R.id.wikiHeaderEditText)
        AppCompatEditText wikiHeaderEditText;
        @Bind(R.id.wikiExplanationEditText)
        AppCompatEditText wikiExplanationEditText;
        @Bind(R.id.wikiExampleEditText)
        AppCompatEditText wikiExampleEditText;
        @Bind(R.id.wikiOuptputEditText)
        AppCompatEditText wikiOuptputEditText;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            wikiExplanationEditText.setVisibility(View.GONE);
            wikiExampleEditText.setVisibility(View.GONE);
            wikiOuptputEditText.setVisibility(View.GONE);
            wikiHeaderEditText.addTextChangedListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                String wikiHeader = editable.toString();
                ProgramWiki programWiki = programWikis.get(position);
                programWiki.setHeader(wikiHeader);
            }
        }
    }

    public class ExplanationViewHolder extends RecyclerView.ViewHolder implements TextWatcher {
        @Bind(R.id.wikiHeaderEditText)
        AppCompatEditText wikiHeaderEditText;
        @Bind(R.id.wikiExplanationEditText)
        AppCompatEditText wikiExplanationEditText;
        @Bind(R.id.wikiExampleEditText)
        AppCompatEditText wikiExampleEditText;
        @Bind(R.id.wikiOuptputEditText)
        AppCompatEditText wikiOuptputEditText;
        public ExplanationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            wikiHeaderEditText.setVisibility(View.GONE);
            wikiExampleEditText.setVisibility(View.GONE);
            wikiOuptputEditText.setVisibility(View.GONE);
            wikiExplanationEditText.addTextChangedListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                String wikiExplanation = editable.toString();
                ProgramWiki programWiki = programWikis.get(position);
                programWiki.setProgramExplanation(wikiExplanation);
            }
        }
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder implements TextWatcher {
        @Bind(R.id.wikiHeaderEditText)
        AppCompatEditText wikiHeaderEditText;
        @Bind(R.id.wikiExplanationEditText)
        AppCompatEditText wikiExplanationEditText;
        @Bind(R.id.wikiExampleEditText)
        AppCompatEditText wikiExampleEditText;
        @Bind(R.id.wikiOuptputEditText)
        AppCompatEditText wikiOuptputEditText;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            wikiExplanationEditText.setVisibility(View.GONE);
            wikiHeaderEditText.setVisibility(View.GONE);
            wikiOuptputEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int position = getAdapterPosition();
                    if( position != RecyclerView.NO_POSITION ) {
                        String output = editable.toString();
                        ProgramWiki programWiki = programWikis.get(position);
                        programWiki.setOutput(output);
                    }
                }
            });
            wikiExampleEditText.addTextChangedListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                String example = editable.toString();
                ProgramWiki programWiki = programWikis.get(position);
                programWiki.setProgramExample(example);
            }
        }
    }

}

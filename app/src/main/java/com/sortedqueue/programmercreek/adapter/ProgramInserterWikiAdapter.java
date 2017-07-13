package com.sortedqueue.programmercreek.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.ProgramWiki;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-01-11.
 */
public class ProgramInserterWikiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProgramWiki> programWikis;
    private Context context;
    public ProgramInserterWikiAdapter(List<ProgramWiki> programWikis) {
        this.programWikis = programWikis;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
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
        explanationViewHolder.wikiExplanationTextView.setText(programWiki.getProgramExplanation());
    }

    private void initProgram(RecyclerView.ViewHolder holder, ProgramWiki programWiki) {
        ExampleViewHolder exampleViewHolder = (ExampleViewHolder) holder;
        exampleViewHolder.wikiExampleTextView.setText(programWiki.getProgramExample());
        exampleViewHolder.wikiOuptputTextView.setText(programWiki.getOutput());
    }

    private void initHeader(RecyclerView.ViewHolder holder, ProgramWiki programWiki) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.wikiHeaderTextView.setText(programWiki.getHeader());
    }



    @Override
    public int getItemViewType(int position) {
        return programWikis.get(position).getContentType();
    }

    @Override
    public int getItemCount() {
        return programWikis.size();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        @BindView(R.id.wikiHeaderTextView)
        TextView wikiHeaderTextView;
        @BindView(R.id.wikiExplanationTextView)
        TextView wikiExplanationTextView;
        @BindView(R.id.wikiExampleTextView)
        TextView wikiExampleTextView;
        @BindView(R.id.wikiOuptputTextView)
        TextView wikiOuptputTextView;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            wikiExplanationTextView.setVisibility(View.GONE);
            wikiExampleTextView.setVisibility(View.GONE);
            wikiOuptputTextView.setVisibility(View.GONE);
            wikiHeaderTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                final ProgramWiki programWiki = programWikis.get(position);
                showInputDialog("Wiki Header", programWiki.getHeader(), new AuxilaryUtils.InputTextListener() {
                    @Override
                    public void onSuccess(String text) {
                        programWiki.setHeader(text);
                        wikiHeaderTextView.setText(programWiki.getHeader());
                    }

                    @Override
                    public void onDismiss() {

                    }
                });
            }
        }
    }

    public class ExplanationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.wikiHeaderTextView)
        TextView wikiHeaderTextView;
        @BindView(R.id.wikiExplanationTextView)
        TextView wikiExplanationTextView;
        @BindView(R.id.wikiExampleTextView)
        TextView wikiExampleTextView;
        @BindView(R.id.wikiOuptputTextView)
        TextView wikiOuptputTextView;
        public ExplanationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            wikiHeaderTextView.setVisibility(View.GONE);
            wikiExampleTextView.setVisibility(View.GONE);
            wikiOuptputTextView.setVisibility(View.GONE);
            wikiExplanationTextView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                final ProgramWiki programWiki = programWikis.get(position);
                showInputDialog("Explanation", programWiki.getProgramExplanation(), new AuxilaryUtils.InputTextListener() {
                    @Override
                    public void onSuccess(String text) {
                        programWiki.setProgramExplanation(text);
                        wikiExplanationTextView.setText(programWiki.getProgramExplanation());
                    }

                    @Override
                    public void onDismiss() {

                    }
                });
            }
        }
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        @BindView(R.id.wikiHeaderTextView)
        TextView wikiHeaderTextView;
        @BindView(R.id.wikiExplanationTextView)
        TextView wikiExplanationTextView;
        @BindView(R.id.wikiExampleTextView)
        TextView wikiExampleTextView;
        @BindView(R.id.wikiOuptputTextView)
        TextView wikiOuptputTextView;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            wikiExplanationTextView.setVisibility(View.GONE);
            wikiHeaderTextView.setVisibility(View.GONE);
            wikiOuptputTextView.setOnClickListener(this);
            wikiExampleTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                final ProgramWiki programWiki = programWikis.get(position);
                switch ( v.getId() ) {
                    case R.id.wikiOuptputTextView :
                        showInputDialog("Output", programWiki.getOutput(), new AuxilaryUtils.InputTextListener() {
                            @Override
                            public void onSuccess(String text) {
                                programWiki.setOutput(text);
                                wikiOuptputTextView.setText(programWiki.getOutput());
                            }

                            @Override
                            public void onDismiss() {

                            }
                        });
                        break;
                    case R.id.wikiExampleTextView :
                        showInputDialog("Example", programWiki.getProgramExample(), new AuxilaryUtils.InputTextListener() {
                            @Override
                            public void onSuccess(String text) {
                                programWiki.setProgramExample(text);
                                wikiExampleTextView.setText(programWiki.getProgramExample());
                            }

                            @Override
                            public void onDismiss() {

                            }
                        });
                        break;
                }

            }
        }
    }
    
    public void showInputDialog(String title, String content, AuxilaryUtils.InputTextListener inputTextListener) {
        AuxilaryUtils.displayInputDialog(context, title, content, inputTextListener );
    }

}

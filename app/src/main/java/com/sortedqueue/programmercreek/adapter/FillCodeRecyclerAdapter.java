package com.sortedqueue.programmercreek.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-09-01.
 */

public class FillCodeRecyclerAdapter extends RecyclerView.Adapter<FillCodeRecyclerAdapter.ViewHolder> {
    private String[] codeWords;
    private int randomIndex;

    public FillCodeRecyclerAdapter(String[] codeWords, int randomIndex) {
        this.codeWords = codeWords;
        this.randomIndex = randomIndex;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_code_text, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.codeTextView.setText(codeWords[position]);
        holder.codeTextView.setTextColor(position == randomIndex ? (Color.parseColor("#006699")) : (Color.parseColor("#000000")) );
    }

    public void setCode( String option ) {
        codeWords[randomIndex] = option;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return codeWords.length;
    }

    public String getBlankSpace() {
        return codeWords[randomIndex];
    }

    public String getCodeLine() {
        String codeLine = "";
        for( String programLine : codeWords ) {
            codeLine += programLine;
        }
        return codeLine;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.codeTextView)
        TextView codeTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            codeTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION && position == randomIndex ) {
                codeTextView.setText("________");
            }
        }
    }
}

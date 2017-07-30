package com.sortedqueue.programmercreek.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.NotesModel;
import com.sortedqueue.programmercreek.util.PrettifyHighlighter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-07-29.
 */
public class NotesPreviewRecyclerAdapter extends RecyclerView.Adapter<NotesPreviewRecyclerAdapter.ViewHolder> {


    private ArrayList<NotesModel> notesModelArrayList;
    private PrettifyHighlighter prettifyHighlighter;

    public NotesPreviewRecyclerAdapter(ArrayList<NotesModel> notesModelArrayList) {
        this.notesModelArrayList = notesModelArrayList;
        this.prettifyHighlighter = PrettifyHighlighter.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes_preview, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotesModel notesModel = notesModelArrayList.get(position);
        holder.headerTextView.setVisibility(View.GONE);
        holder.contentTextView.setVisibility(View.GONE);
        holder.codeTextView.setVisibility(View.GONE);
        switch (notesModel.getNoteType()) {
            case NotesModel.TYPE_CODE :
                if( notesModel.getNoteLine().contains("<") || notesModel.getNoteLine().contains(">")) {
                    holder.codeTextView.setText(notesModel.getNoteLine());
                    holder.codeTextView.setTextColor(Color.parseColor("#006699"));
                    holder.codeTextView.setVisibility(View.VISIBLE);

                }
                else {
                    holder.codeTextView.setText(Html.fromHtml(prettifyHighlighter.highlight("c", notesModel.getNoteLine())));
                    holder.codeTextView.setVisibility(View.VISIBLE);
                }
                break;
            case NotesModel.TYPE_HEADER:
                holder.headerTextView.setText(notesModel.getNoteLine());
                holder.headerTextView.setVisibility(View.VISIBLE);
                break;
            case NotesModel.TYPE_NOTES:
                holder.contentTextView.setText(notesModel.getNoteLine());
                holder.contentTextView.setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return notesModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.headerTextView)
        TextView headerTextView;
        @BindView(R.id.contentTextView)
        TextView contentTextView;
        @BindView(R.id.codeTextView)
        TextView codeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

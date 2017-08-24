package com.sortedqueue.programmercreek.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.NotesModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sortedqueue.programmercreek.database.NotesModel.TYPE_CODE;
import static com.sortedqueue.programmercreek.database.NotesModel.TYPE_HEADER;
import static com.sortedqueue.programmercreek.database.NotesModel.TYPE_NOTES;

/**
 * Created by Alok on 24/08/17.
 */

public class ArticleShareAdaper extends RecyclerView.Adapter<ArticleShareAdaper.ViewHolder> {

    private ArrayList<NotesModel> notesModelArrayList;

    public ArticleShareAdaper(ArrayList<NotesModel> notesModelArrayList) {
        this.notesModelArrayList = notesModelArrayList;
    }

    @Override
    public ArticleShareAdaper.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_article, parent, false));
    }

    @Override
    public void onBindViewHolder(ArticleShareAdaper.ViewHolder holder, int position) {
        NotesModel notesModel = getItemAtPosition(position);
        holder.notesTextView.setText(notesModel.getNoteLine());
        int noteType = notesModel.getNoteType();
        switch (noteType) {
            case TYPE_CODE :
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.md_black_1000));
                holder.notesTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
                break;
            case NotesModel.TYPE_HEADER:
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.md_blue_grey_300));
                holder.notesTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
                break;
            case NotesModel.TYPE_NOTES:
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
                holder.notesTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary));
                break;
        }
    }

    private NotesModel getItemAtPosition(int position) {
        return notesModelArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return notesModelArrayList.size();
    }

    public ArrayList<NotesModel> getNotesModelArrayList() {
        return notesModelArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.notesTextView)
        TextView notesTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                NotesModel notesModel = getItemAtPosition(position);
                int noteType = notesModel.getNoteType();
                switch ( noteType ) {
                    case TYPE_CODE :
                        notesModel.setNoteType(TYPE_HEADER);
                        break;
                    case TYPE_NOTES :
                        notesModel.setNoteType(TYPE_CODE);
                        break;
                    case TYPE_HEADER :
                        notesModel.setNoteType(TYPE_NOTES);
                        break;
                }
                notifyItemChanged(position);
            }
        }
    }

}

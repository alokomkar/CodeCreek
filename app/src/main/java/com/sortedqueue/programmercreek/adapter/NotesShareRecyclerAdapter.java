package com.sortedqueue.programmercreek.adapter;

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

/**
 * Created by Alok Omkar on 2017-07-28.
 */
public class NotesShareRecyclerAdapter extends RecyclerView.Adapter<NotesShareRecyclerAdapter.ViewHolder> {

    private ArrayList<NotesModel> notesModelArrayList;

    public NotesShareRecyclerAdapter(ArrayList<NotesModel> notesModelArrayList) {
        this.notesModelArrayList = notesModelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotesModel notesModel = getItemAtPosition(position);
        holder.notesTextView.setText(notesModel.getNoteLine());
        int noteType = notesModel.getNoteType();
        holder.codeRadioButton.setChecked(noteType == NotesModel.TYPE_CODE);
        holder.headerRadioButton.setChecked(noteType == NotesModel.TYPE_HEADER);
        holder.notesRadioButton.setChecked(noteType == NotesModel.TYPE_NOTES);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        @BindView(R.id.notesTextView)
        TextView notesTextView;
        @BindView(R.id.codeRadioButton)
        RadioButton codeRadioButton;
        @BindView(R.id.notesRadioButton)
        RadioButton notesRadioButton;
        @BindView(R.id.headerRadioButton)
        RadioButton headerRadioButton;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            codeRadioButton.setOnCheckedChangeListener(this);
            notesRadioButton.setOnCheckedChangeListener(this);
            headerRadioButton.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isSelected) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION && isSelected ) {
                NotesModel notesModel = getItemAtPosition(position);
                switch (compoundButton.getId()) {
                    case R.id.codeRadioButton :
                        notesModel.setNoteType(NotesModel.TYPE_CODE);
                        break;
                    case R.id.notesRadioButton :
                        notesModel.setNoteType(NotesModel.TYPE_NOTES);
                        break;
                    case R.id.headerRadioButton :
                        notesModel.setNoteType(NotesModel.TYPE_HEADER);
                        break;
                }

            }

        }
    }

}

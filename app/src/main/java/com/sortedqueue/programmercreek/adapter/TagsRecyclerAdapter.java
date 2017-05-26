package com.sortedqueue.programmercreek.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 13/04/17.
 */
public class TagsRecyclerAdapter extends RecyclerView.Adapter<TagsRecyclerAdapter.ViewHolder> {


    private ArrayList<String> tagArrayList;
    private ArrayList<String> selectedTags;

    private String selectedTag = "";

    private int mode = -1;

    public TagsRecyclerAdapter(ArrayList<String> tagArrayList) {
        this.tagArrayList = tagArrayList;
        this.selectedTags = new ArrayList<>();
    }

    public TagsRecyclerAdapter(ArrayList<String> tagArrayList, int mode) {
        this.tagArrayList = tagArrayList;
        this.selectedTags = new ArrayList<>();
        this.mode = mode;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String tag = tagArrayList.get(position);
        holder.tagTextView.setText(tag);

        if( mode == -1 ) {
            boolean isSelected = selectedTags.contains(tag);
            holder.tagLayout.setSelected(isSelected);
        }
        else {
            boolean isSelected = tag.equals(selectedTag);
            holder.tagLayout.setSelected(isSelected);
        }

    }

    public ArrayList<String> getSelectedTags() {
        return selectedTags;
    }

    public void addTag( String tag ) {
        if( tagArrayList.contains(tag) ) {
            selectedTags.add(tag);
            notifyDataSetChanged();
        }
        else {
            tagArrayList.add(tag);
            selectedTags.add(tag);
            notifyDataSetChanged();
        }
    }

    public String getSelectedTag() {
        return selectedTag;
    }

    @Override
    public int getItemCount() {
        return tagArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.tagTextView)
        TextView tagTextView;
        @Bind(R.id.tagLayout)
        LinearLayout tagLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                String tag = tagArrayList.get(position);
                if( mode == -1 ) {
                    if( selectedTags.contains(tag) ) {
                        selectedTags.remove(tag);
                    }
                    else {
                        selectedTags.add(tag);
                    }
                }
                else {
                    selectedTag = tag;
                }
                notifyDataSetChanged();
            }
        }
    }


}

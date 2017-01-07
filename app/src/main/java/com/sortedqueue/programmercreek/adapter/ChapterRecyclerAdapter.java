package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.Chapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 06/01/17.
 */

public class ChapterRecyclerAdapter extends RecyclerView.Adapter<ChapterRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Chapter> chapters;
    private CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner;

    public ChapterRecyclerAdapter(Context context, ArrayList<Chapter> chapters, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        this.context = context;
        this.chapters = chapters;
        this.adapterClickListner = adapterClickListner;
    }

    @Override
    public ChapterRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_module, parent, false);
        return new ChapterRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChapterRecyclerAdapter.ViewHolder holder, int position) {
        Chapter languageModule = chapters.get(position);
        holder.moduleNameTextView.setText(languageModule.getChapterName());
        holder.moduleDescriptionTextView.setText(languageModule.getChapteBrief());
        holder.itemView.setEnabled(position == 0);
        holder.lockedImageView.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.moduleNameTextView)
        TextView moduleNameTextView;
        @Bind(R.id.moduleDescriptionTextView)
        TextView moduleDescriptionTextView;
        @Bind(R.id.headerTextView)
        TextView headerTextView;
        @Bind(R.id.lockedImageView)
        ImageView lockedImageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            headerTextView.setText("Chapter");
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION )
                adapterClickListner.onItemClick(position);
        }
    }
}

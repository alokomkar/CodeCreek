package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.database.CreekUserStats;

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
    private CreekUserStats creekUserStats;
    public ChapterRecyclerAdapter(Context context, ArrayList<Chapter> chapters, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        this.context = context;
        this.chapters = chapters;
        this.adapterClickListner = adapterClickListner;
        this.creekUserStats = CreekApplication.getInstance().getCreekUserStats();
    }

    @Override
    public ChapterRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_module, parent, false);
        return new ChapterRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChapterRecyclerAdapter.ViewHolder holder, int position) {
        Chapter chapter = chapters.get(position);
        holder.moduleNameTextView.setText(chapter.getChapterName());
        holder.moduleDescriptionTextView.setText(chapter.getChapteBrief());
        boolean isChapterEnabled;
        int chapterProgress = 0;
        switch ( chapter.getProgram_Language() ) {
            case "c":
                isChapterEnabled = creekUserStats.getcProgramIndex() >= chapter.getMinStats();
                chapterProgress = creekUserStats.getcProgramIndex();
                holder.itemView.setEnabled(isChapterEnabled);
                holder.lockedImageView.setVisibility( isChapterEnabled ? View.INVISIBLE : View.VISIBLE);
                break;
            case "cpp":
            case "c++":
                isChapterEnabled = creekUserStats.getCppProgramIndex() >= chapter.getMinStats();
                chapterProgress = creekUserStats.getCppProgramIndex();
                holder.itemView.setEnabled(isChapterEnabled);
                holder.lockedImageView.setVisibility( isChapterEnabled ? View.INVISIBLE : View.VISIBLE);
                break;
            case "java":
                isChapterEnabled = creekUserStats.getJavaProgressIndex() >= chapter.getMinStats();
                chapterProgress = creekUserStats.getJavaProgressIndex();
                holder.itemView.setEnabled(isChapterEnabled);
                holder.lockedImageView.setVisibility( isChapterEnabled ? View.INVISIBLE : View.VISIBLE);
                break;
        }
        int maxProgress = chapter.getChapterDetailsArrayList().size();
        holder.progressBar.setMax(maxProgress);
        chapterProgress = chapterProgress <= maxProgress ? chapterProgress : maxProgress;
        holder.progressBar.setProgress( chapterProgress );
        holder.progressBar.setVisibility( chapterProgress == 0 ? View.GONE : View.VISIBLE );

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
        @Bind(R.id.progressBar)
        ProgressBar progressBar;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            headerTextView.setText("Chapter");
            progressBar.setVisibility(View.VISIBLE);
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

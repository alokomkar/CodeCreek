package com.sortedqueue.programmercreek.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.util.CommonUtils;

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
                isChapterEnabled = creekUserStats.getcProgressIndex() >= chapter.getMinStats();
                chapterProgress = creekUserStats.getcProgressIndex();
                holder.lockedImageView.setVisibility( isChapterEnabled ? View.INVISIBLE : View.VISIBLE);
                break;
            case "cpp":
            case "c++":
                isChapterEnabled = creekUserStats.getCppProgressIndex() >= chapter.getMinStats();
                chapterProgress = creekUserStats.getCppProgressIndex();
                holder.lockedImageView.setVisibility( isChapterEnabled ? View.INVISIBLE : View.VISIBLE);
                break;
            case "java":
                isChapterEnabled = creekUserStats.getJavaProgressIndex() >= chapter.getMinStats();
                chapterProgress = creekUserStats.getJavaProgressIndex();
                holder.lockedImageView.setVisibility( isChapterEnabled ? View.INVISIBLE : View.VISIBLE);
                break;
            case "sql":
                isChapterEnabled = creekUserStats.getSqlProgressIndex() >= chapter.getMinStats();
                chapterProgress = creekUserStats.getSqlProgressIndex();
                holder.lockedImageView.setVisibility( isChapterEnabled ? View.INVISIBLE : View.VISIBLE);
                break;
        }
        int maxProgress = chapter.getChapterDetailsArrayList().size();
        holder.appCompatSeekBar.setMax( maxProgress);
        chapterProgress = chapterProgress - chapter.getMinStats();
        if( chapterProgress < 0 ) {
            chapterProgress = 0;
        }
        chapterProgress = chapterProgress <= maxProgress ? chapterProgress : maxProgress;
        holder.appCompatSeekBar.setProgress( chapterProgress );
        holder.appCompatSeekBar.setVisibility( chapterProgress == 0 ? View.GONE : View.VISIBLE );
        startAnimation(holder.itemView, position * 250 );

    }

    private void startAnimation(View itemView, int delay) {
        itemView.setAlpha(0.0f);
        itemView.animate().setStartDelay(delay).setDuration(300).alpha(1.0f);
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
        @Bind(R.id.appCompatSeekBar)
        SeekBar appCompatSeekBar;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            headerTextView.setText("Chapter");
            appCompatSeekBar.setVisibility(View.VISIBLE);
            appCompatSeekBar.setActivated(false);
            appCompatSeekBar.setEnabled(false);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                if( lockedImageView.getVisibility() == View.VISIBLE ) {
                    CommonUtils.displaySnackBar((Activity) context, R.string.chapter_locked);
                    return;
                }
                adapterClickListner.onItemClick(position);
            }

        }
    }
}

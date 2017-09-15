package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.lessons.Lesson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 15/09/17.
 */

public class TopicDetailsAdapter extends RecyclerView.Adapter<TopicDetailsAdapter.ViewHolder> {
    private final ArrayList<Lesson> lessons;
    private final CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListener;

    public TopicDetailsAdapter( ArrayList<Lesson> lessons, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        this.lessons = lessons;
        this.adapterClickListener = adapterClickListner;
    }

    @Override
    public TopicDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TopicDetailsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topics, parent, false));
    }

    @Override
    public void onBindViewHolder(TopicDetailsAdapter.ViewHolder holder, int position) {
        Lesson lesson = getItem(position);
        holder.topicsTextView.setText(lesson.getTitle());
        holder.dividerView.setVisibility( position == (lessons.size() - 1) ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public Lesson getItem(int position) {
        return lessons.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.topicsTextView)
        TextView topicsTextView;
        @BindView(R.id.dividerView)
        View dividerView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if( position != -1 )
                adapterClickListener.onItemClick(position);
        }
    }
}

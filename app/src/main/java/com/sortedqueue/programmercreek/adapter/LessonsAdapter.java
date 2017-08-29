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
 * Created by Alok on 29/08/17.
 */

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.ViewHolder> {
    private final ArrayList<Lesson> lessons;
    private final CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListener;
    private Context context;

    public LessonsAdapter(Context context, ArrayList<Lesson> lessons, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        this.context = context;
        this.lessons = lessons;
        this.adapterClickListener = adapterClickListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lesson, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lesson lesson = getItem(position);
        holder.lessonTitleTextView.setText(lesson.getTitle());
        holder.programLanguageTextView.setText(lesson.getProgramLanguage().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public Lesson getItem(int position) {
        return lessons.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.programLanguageTextView)
        TextView programLanguageTextView;
        @BindView(R.id.lessonTitleTextView)
        TextView lessonTitleTextView;

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

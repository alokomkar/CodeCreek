package com.sortedqueue.programmercreek.adapter;

import android.content.ClipData;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.fragments.MatchMakerFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-08-14.
 */
public class MatchOptionsDragAdapter extends RecyclerView.Adapter<MatchOptionsDragAdapter.ViewHolder> {
    private final ArrayList<String> mProgramList;
    private View mSelectedProgramLineView;

    public MatchOptionsDragAdapter(ArrayList<String> mProgramList) {
        this.mProgramList = mProgramList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_option_question, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnTouchListener {
        @BindView(R.id.questionTextView)
        TextView questionTextView;
        @BindView(R.id.matchQuestionLayout)
        LinearLayout matchQuestionLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            view.setOnTouchListener(this);
            view.setBackgroundResource(R.drawable.selected);
            mSelectedProgramLineView = view;
            //Toast.makeText(getContext(), "Selected", Toast.LENGTH_SHORT).show();

            //To start drag immediately after a view has been selected.
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);

            return false;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				/*
				 * Drag details: we only need default behavior
				 * - clip data could be set to pass data as part of drag
				 * - shadow can be tailored
				 */
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }
}

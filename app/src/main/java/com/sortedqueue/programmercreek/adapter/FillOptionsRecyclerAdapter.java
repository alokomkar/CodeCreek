package com.sortedqueue.programmercreek.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.fragments.BitModuleFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-09-01.
 */

public class FillOptionsRecyclerAdapter extends RecyclerView.Adapter<FillOptionsRecyclerAdapter.ViewHolder> {
    private ArrayList<String> fillBlankOptions;
    private CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner;
    public FillOptionsRecyclerAdapter(ArrayList<String> fillBlankOptions, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        this.adapterClickListner = adapterClickListner;
        this.fillBlankOptions = fillBlankOptions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_code_text, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.codeTextView.setText(fillBlankOptions.get(position));
        holder.codeTextView.setTextColor((Color.parseColor("#006699")) );
    }

    @Override
    public int getItemCount() {
        return fillBlankOptions.size();
    }

    public String getItem(int position) {
        return fillBlankOptions.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.codeTextView)
        TextView codeTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            codeTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                adapterClickListner.onItemClick(position);
            }
        }
    }
}

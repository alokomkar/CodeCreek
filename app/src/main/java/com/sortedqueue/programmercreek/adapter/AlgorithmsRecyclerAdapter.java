package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.AlgorithmsIndex;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-03-17.
 */
public class AlgorithmsRecyclerAdapter extends RecyclerView.Adapter<AlgorithmsRecyclerAdapter.ViewHolder> {

    private CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner;
    private ArrayList<AlgorithmsIndex> algorithmsIndexArrayList;

    public AlgorithmsRecyclerAdapter(Context context, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner, ArrayList<AlgorithmsIndex> algorithmsIndexArrayList) {
        this.adapterClickListner = adapterClickListner;
        this.algorithmsIndexArrayList = algorithmsIndexArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_algorithm_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AlgorithmsIndex algorithmsIndex = getItemAtPosition(position);
        holder.descriptionTextView.setText(algorithmsIndex.getProgramDescription());
        holder.titleTextView.setText(algorithmsIndex.getProgramTitle());
    }

    @Override
    public int getItemCount() {
        return algorithmsIndexArrayList.size();
    }

    public AlgorithmsIndex getItemAtPosition(int position) {
       return algorithmsIndexArrayList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.titleTextView)
        TextView titleTextView;
        @Bind(R.id.descriptionTextView)
        TextView descriptionTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
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

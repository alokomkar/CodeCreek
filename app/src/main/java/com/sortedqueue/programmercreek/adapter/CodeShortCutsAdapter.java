package com.sortedqueue.programmercreek.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.CodeShortCuts;

import java.util.ArrayList;

/**
 * Created by Alok on 14/09/17.
 */

public class CodeShortCutsAdapter extends RecyclerView.Adapter<CodeShortCutsAdapter.ViewHolder> {

    private ArrayList<CodeShortCuts> mCodeShortCuts;
    private CustomProgramRecyclerViewAdapter.AdapterClickListner mAdapterClickListner;
    public CodeShortCutsAdapter(ArrayList<CodeShortCuts> codeShortCuts, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        mCodeShortCuts = codeShortCuts;
        mAdapterClickListner = adapterClickListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_code_shortcut, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mCodeShortCuts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION )
                mAdapterClickListner.onItemClick(position);
        }
    }
}

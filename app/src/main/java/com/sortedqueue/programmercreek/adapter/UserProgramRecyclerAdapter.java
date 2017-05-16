package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.UserProgramDetails;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 16/05/17.
 */

public class UserProgramRecyclerAdapter extends RecyclerView.Adapter<UserProgramRecyclerAdapter.ViewHolder> {

    private CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner;
    private ArrayList<UserProgramDetails> userProgramDetailsArrayList;
    private Context context;

    public UserProgramRecyclerAdapter(Context context, ArrayList<UserProgramDetails> modelArrayList, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        this.userProgramDetailsArrayList = modelArrayList;
        this.context = context;
        this.adapterClickListner = adapterClickListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_program, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserProgramDetails presentationModel = userProgramDetailsArrayList.get(position);
        ProgramIndex programIndex = presentationModel.getProgramIndex();
        holder.titleTextView.setText( programIndex.getProgram_Language().toUpperCase() + " : " + programIndex.getProgram_Description());
        holder.subTitleTextView.setText(presentationModel.getPreview());
    }

    @Override
    public int getItemCount() {
        return userProgramDetailsArrayList.size();
    }

    public UserProgramDetails getItemAtPosition( int position ) {
        return userProgramDetailsArrayList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.titleTextView)
        TextView titleTextView;
        @Bind(R.id.subTitleTextView)
        TextView subTitleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                adapterClickListner.onItemClick(position);
            }
        }
    }

}
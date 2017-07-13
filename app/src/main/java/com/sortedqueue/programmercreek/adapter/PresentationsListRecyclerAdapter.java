package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.PresentationModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 11/04/17.
 */
public class PresentationsListRecyclerAdapter extends RecyclerView.Adapter<PresentationsListRecyclerAdapter.ViewHolder> {

    private CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner;
    private ArrayList<PresentationModel> presentationModels;
    private Context context;

    public PresentationsListRecyclerAdapter(Context context, ArrayList<PresentationModel> presentationModelArrayList, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        this.presentationModels = presentationModelArrayList;
        this.context = context;
        this.adapterClickListner = adapterClickListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_presentation_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PresentationModel presentationModel = presentationModels.get(position);
        holder.titleTextView.setText(presentationModel.getPresentationName());
        holder.subTitleTextView.setText(presentationModel.getPresenterName());
        Glide.with(context)
                .load(presentationModel.getPresentationImage())
                .asBitmap()
                .centerCrop()
                .error(R.color.md_blue_600)
                .placeholder(R.color.md_blue_600)
                .into(holder.slideImageView);
    }

    @Override
    public int getItemCount() {
        return presentationModels.size();
    }

    public PresentationModel getItemAtPosition( int position ) {
        return presentationModels.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.titleTextView)
        TextView titleTextView;
        @BindView(R.id.subTitleTextView)
        TextView subTitleTextView;
        @BindView(R.id.slideImageView)
        ImageView slideImageView;

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

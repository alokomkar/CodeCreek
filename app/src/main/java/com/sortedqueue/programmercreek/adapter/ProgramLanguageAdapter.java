package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.ProgramLanguage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 27/01/17.
 */

public class ProgramLanguageAdapter extends RecyclerView.Adapter<ProgramLanguageAdapter.ViewHolder> {


    private Context context;
    private ArrayList<ProgramLanguage> programLanguages;
    private CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner;

    public ProgramLanguageAdapter(Context context, ArrayList<ProgramLanguage> programLanguages, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        this.context = context;
        this.programLanguages = programLanguages;
        this.adapterClickListner = adapterClickListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProgramLanguage programLanguage = programLanguages.get(position);
        holder.programmingTextView.setText(programLanguage.getProgramLanguage());
        holder.programLanguageDescriptionTextView.setText(programLanguage.getDescription());
        holder.languageIdTextView.setText(programLanguage.getLanguageId());
        //startAnimation(holder.itemView, position * 250 );
    }

    private void startAnimation(View itemView, int delay) {
        itemView.setAlpha(0.0f);
        itemView.animate().setStartDelay(delay).setDuration(250).alpha(1.0f);
    }


    @Override
    public int getItemCount() {
        return programLanguages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.programmingTextView)
        TextView programmingTextView;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.programLanguageDescriptionTextView)
        TextView programLanguageDescriptionTextView;
        @BindView(R.id.languageIdTextView)
        TextView languageIdTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                adapterClickListner.onItemClick( position );
            }
        }
    }
}

package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.WikiModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-01-19.
 */
public class ProgramWikiNavRecyclerAdapter extends RecyclerView.Adapter<ProgramWikiNavRecyclerAdapter.ViewHolder> {
    private final CustomProgramRecyclerViewAdapter.AdapterClickListner mAdapterClickListner;
    private final String mProgramType;
    private Context context;
    private ArrayList<WikiModel> programWikis;
    private String programLanguage;

    public ProgramWikiNavRecyclerAdapter(Context context, ArrayList<WikiModel> programWikis) {
        this.context = context;
        this.programWikis = programWikis;
        this.programLanguage = CreekApplication.getCreekPreferences().getProgramLanguage();
        this.mAdapterClickListner = (CustomProgramRecyclerViewAdapter.AdapterClickListner) context;
        if( programLanguage.equals("c++") ) {
            programLanguage = "cpp";
        }
        mProgramType = programLanguage.substring(0, 1).toUpperCase();
    }

    @Override
    public ProgramWikiNavRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View adapterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_nav_list, parent, false);
        return new ProgramWikiNavRecyclerAdapter.ViewHolder(adapterView);
    }

    @Override
    public void onBindViewHolder(ProgramWikiNavRecyclerAdapter.ViewHolder holder, int position) {

        WikiModel programWiki = programWikis.get(position);
        holder.programTypeTextView.setText(mProgramType);
        holder.txtViewProgDescription.setText(programWiki.getWikiHeader());

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.programTypeTextView)
        TextView programTypeTextView;
        @BindView(R.id.txtViewProgDescription)
        TextView txtViewProgDescription;
        @BindView(R.id.lockedImageView)
        ImageView lockedImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            lockedImageView.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                mAdapterClickListner.onItemClick(position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return programWikis.size();
    }


}

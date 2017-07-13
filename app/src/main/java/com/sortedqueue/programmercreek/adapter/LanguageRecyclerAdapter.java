package com.sortedqueue.programmercreek.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-04-16.
 */

public class LanguageRecyclerAdapter extends RecyclerView.Adapter<LanguageRecyclerAdapter.ViewHolder> {

    private ArrayList<String> languages;
    private CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner;
    private String selectedLanguage = "";

    public LanguageRecyclerAdapter(ArrayList<String> languages,
                                   CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        this.languages = languages;
        this.adapterClickListner = adapterClickListner;
    }

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String language = languages.get(position);
        holder.tagTextView.setText(language);
        holder.tagLayout.setSelected( selectedLanguage.equals(language));
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tagTextView)
        TextView tagTextView;
        @BindView(R.id.tagLayout)
        LinearLayout tagLayout;

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

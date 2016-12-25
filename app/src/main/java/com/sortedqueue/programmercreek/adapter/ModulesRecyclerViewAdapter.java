package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.LanguageModule;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2016-12-25.
 */
public class ModulesRecyclerViewAdapter extends RecyclerView.Adapter<ModulesRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LanguageModule> languageModules;
    private CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner;

    public ModulesRecyclerViewAdapter(Context context, ArrayList<LanguageModule> languageModules, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        this.context = context;
        this.languageModules = languageModules;
        this.adapterClickListner = adapterClickListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_module, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LanguageModule languageModule = languageModules.get(position);
        holder.moduleNameTextView.setText(languageModule.getModuleName());
        holder.moduleDescriptionTextView.setText(languageModule.getModuleDescription());
    }

    @Override
    public int getItemCount() {
        return languageModules.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.moduleNameTextView)
        TextView moduleNameTextView;
        @Bind(R.id.moduleDescriptionTextView)
        TextView moduleDescriptionTextView;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION )
                adapterClickListner.onItemClick(position);
        }
    }
}

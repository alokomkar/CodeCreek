package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.ModuleOption;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2016-12-25.
 */
public class OptionsRecyclerViewAdapter extends RecyclerView.Adapter<OptionsRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ModuleOption> moduleOptions;
    private CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner;
    public OptionsRecyclerViewAdapter(Context context, List<ModuleOption> syntaxOptions, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        this.adapterClickListner = adapterClickListner;
        this.context = context;
        this.moduleOptions = syntaxOptions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModuleOption moduleOption = moduleOptions.get(position);
        holder.optionTextView.setText(moduleOption.getOption());
    }

    @Override
    public int getItemCount() {
        return moduleOptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.optionTextView)
        TextView optionTextView;

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

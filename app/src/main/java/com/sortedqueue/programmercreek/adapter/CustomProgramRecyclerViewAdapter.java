package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2016-12-24.
 */
public class CustomProgramRecyclerViewAdapter extends RecyclerView.Adapter<CustomProgramRecyclerViewAdapter.ViewHolder> {

    private String mProgramType;
    private Context mContext;
    private List<Program_Index> mProgram_Indexs;
    private AdapterClickListner mAdapterClickListner;

    public interface AdapterClickListner {
        void onItemClick( int position );
    }

    public CustomProgramRecyclerViewAdapter(Context context, List<Program_Index> mProgram_indexs) {
        this.mContext = context;
        this.mProgram_Indexs = mProgram_indexs;
        this.mAdapterClickListner = (AdapterClickListner) context;
        mProgramType = new CreekPreferences(mContext).getProgramLanguage().substring(0, 1).toUpperCase();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View adapterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_list, parent, false);
        return new ViewHolder(adapterView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.programTypeTextView.setText(mProgramType);
        holder.txtViewProgDescription.setText(mProgram_Indexs.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mProgram_Indexs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.programTypeTextView)
        TextView programTypeTextView;
        @Bind(R.id.txtViewProgDescription)
        TextView txtViewProgDescription;
        @Bind(R.id.programIndexLayout)
        RelativeLayout programIndexLayout;
        @Bind(R.id.wikiTextView)
        TextView wikiTextView;
        @Bind(R.id.RelativeLayout1)
        CardView RelativeLayout1;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                mAdapterClickListner.onItemClick(position);
            }
        }
    }

}

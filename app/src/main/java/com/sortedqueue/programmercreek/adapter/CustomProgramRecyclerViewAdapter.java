package com.sortedqueue.programmercreek.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2016-12-24.
 */
public class CustomProgramRecyclerViewAdapter extends RecyclerView.Adapter<CustomProgramRecyclerViewAdapter.ViewHolder> {

    private String mProgramType;
    private String programLanguage;
    private Context mContext;
    private ArrayList<ProgramIndex> mProgram_Indexs;
    private AdapterClickListner mAdapterClickListner;
    private CreekUserStats creekUserStats;

    private int lastPosition = -1;
    private Animation bottomUpAnimation;
    private Animation topDownAnimation;

    public interface AdapterClickListner {
        void onItemClick( int position );
    }

    public CustomProgramRecyclerViewAdapter(Context context, ArrayList<ProgramIndex> mProgram_indexs) {
        this.mContext = context;
        this.mProgram_Indexs = mProgram_indexs;
        this.mAdapterClickListner = (AdapterClickListner) context;
        this.programLanguage =new CreekPreferences(mContext).getProgramLanguage();
        this.creekUserStats = CreekApplication.getInstance().getCreekUserStats();
        mProgramType = programLanguage.substring(0, 1).toUpperCase();
        bottomUpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.item_up_from_bottom);
        topDownAnimation = AnimationUtils.loadAnimation(mContext, R.anim.item_up_from_bottom);
    }

    public void resetAdapter() {
        this.creekUserStats = CreekApplication.getInstance().getCreekUserStats();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View adapterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_list, parent, false);
        return new ViewHolder(adapterView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.programTypeTextView.setText(mProgramType);
        ProgramIndex programIndex = mProgram_Indexs.get(position);
        int program_Index = programIndex.getProgram_index();
        boolean isAvailable = true;

        switch (programLanguage) {
            case "c" :
                isAvailable = (creekUserStats.getUnlockedCProgramIndexList().contains(program_Index));
                break;
            case "c++" :
            case "cpp" :
                isAvailable = (creekUserStats.getUnlockedCppProgramIndexList().contains(program_Index));
                break;
            case "java" :
                isAvailable = (creekUserStats.getUnlockedJavaProgramIndexList().contains(program_Index));
                break;
        }
        holder.lockedImageView.setVisibility( isAvailable ? View.GONE : View.VISIBLE );
        holder.txtViewProgDescription.setText(programIndex.getProgram_Description());
        //Remove this later
        //holder.lockedImageView.setVisibility(View.GONE);
        //holder.itemView.startAnimation((position > lastPosition) ? bottomUpAnimation : topDownAnimation );
       /* if( position > lastPosition ) {
            startAnimation(holder.itemView, position * 250 );
        }
        lastPosition = position;*/
    }

    private void startAnimation(View itemView, int delay) {

        itemView.setAlpha(0.0f);
        itemView.animate().setStartDelay(delay).setDuration(300).alpha(1.0f);
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
        @Bind(R.id.lockedImageView)
        ImageView lockedImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                if( lockedImageView.getVisibility() == View.VISIBLE ) {
                    CommonUtils.displaySnackBar((Activity) mContext, R.string.program_locked);
                    return;
                }
                mAdapterClickListner.onItemClick(position);
            }
        }
    }

}

package com.sortedqueue.programmercreek.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.interfaces.UnlockByInviteInterface;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
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
    private UnlockByInviteInterface mUnlockByInviteInterface;
    private CreekUserStats creekUserStats;
    private CreekPreferences creekPreferences;

    private int lastPosition = -1;
    private Animation bottomUpAnimation;
    private Animation topDownAnimation;
    private Drawable unlockByInviteDrawable;
    private Drawable lockedDrawable;

    public interface AdapterClickListner {
        void onItemClick(int position);
    }

    public CustomProgramRecyclerViewAdapter(Context context, ArrayList<ProgramIndex> mProgram_indexs) {
        this.mContext = context;
        this.mProgram_Indexs = mProgram_indexs;
        this.mAdapterClickListner = (AdapterClickListner) context;
        this.mUnlockByInviteInterface = (UnlockByInviteInterface) context;
        this.programLanguage = CreekApplication.getCreekPreferences().getProgramLanguage();
        this.creekUserStats = CreekApplication.getInstance().getCreekUserStats();
        this.creekPreferences = CreekApplication.getCreekPreferences();
        this.unlockByInviteDrawable = ContextCompat.getDrawable(context, android.R.drawable.ic_media_ff);
        this.lockedDrawable = ContextCompat.getDrawable(context, android.R.drawable.ic_lock_lock);
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
        holder.txtViewProgDescription.setText(programIndex.getProgram_Description());
        int program_Index = programIndex.getProgram_index();
        boolean isAvailable = true;

        switch (programLanguage) {
            case "c":
                isAvailable = (creekUserStats.getUnlockedCProgramIndexList().contains(program_Index));
                break;
            case "c++":
            case "cpp":
                isAvailable = (creekUserStats.getUnlockedCppProgramIndexList().contains(program_Index));
                break;
            case "java":
                isAvailable = (creekUserStats.getUnlockedJavaProgramIndexList().contains(program_Index));
                break;
            case "usp":
                isAvailable = creekUserStats.getUnlockedUspProgramIndexList().contains(program_Index);
                break;
            case "sql":
                isAvailable = creekUserStats.getUnlockedSqlProgramIndexList().contains(program_Index);
                break;
        }
        holder.quizTextView.setSelected(true);
        /*holder.lockedImageView.setVisibility( isAvailable ? View.GONE : View.VISIBLE );

        if( !isAvailable ) {
            if( creekPreferences.isUnlockedByInvite(program_Index) ) {
                holder.unlockedByInviteImageView.setVisibility(View.VISIBLE);
                holder.lockedImageView.setVisibility(View.GONE);
            }
            else {
                holder.unlockedByInviteImageView.setVisibility(View.GONE);
                holder.lockedImageView.setVisibility(View.VISIBLE);
            }
        }*/

        //Remove this later
        holder.lockedImageView.setVisibility(View.GONE);
        /*if( position > lastPosition ) {
            startAnimation(holder.itemView, program_Index * 25);
        }
        lastPosition = position;*/
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    private void startAnimation(View itemView, int delay) {

        itemView.setAlpha(0.0f);
        itemView.animate().setStartDelay(delay).setDuration(300).alpha(1.0f);
    }

    @Override
    public int getItemCount() {
        return mProgram_Indexs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @Bind(R.id.programTypeTextView)
        TextView programTypeTextView;
        @Bind(R.id.txtViewProgDescription)
        TextView txtViewProgDescription;
        @Bind(R.id.lockedImageView)
        ImageView lockedImageView;
        @Bind(R.id.unlockedByInviteImageView)
        ImageView unlockedByInviteImageView;
        @Bind(R.id.quizTextView)
        TextView quizTextView;
        @Bind(R.id.matchTextView)
        TextView matchTextView;
        @Bind(R.id.testTextView)
        TextView testTextView;
        @Bind(R.id.completionLayout)
        LinearLayout completionLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                final ProgramIndex programIndex = mProgram_Indexs.get(position);
                if (lockedImageView.getVisibility() == View.VISIBLE) {
                    if (unlockedByInviteImageView.getVisibility() == View.VISIBLE) {
                        mAdapterClickListner.onItemClick(position);
                        return;
                    }
                    AuxilaryUtils.displayInviteDialog(mContext, R.string.unlock_by_invite, R.string.unlock_by_invite_description, new UnlockByInviteInterface() {
                        @Override
                        public void onUnlockClick(int index) {
                            if (mUnlockByInviteInterface != null) {
                                mUnlockByInviteInterface.onUnlockClick(programIndex.getProgram_index());
                            }
                        }

                        @Override
                        public void onDismiss() {
                            mUnlockByInviteInterface.onDismiss();
                        }
                    });
                    CommonUtils.displaySnackBar((Activity) mContext, R.string.program_locked);
                    return;
                }
                if (unlockedByInviteImageView.getVisibility() == View.VISIBLE) {
                    mAdapterClickListner.onItemClick(position);
                    return;
                }
                mAdapterClickListner.onItemClick(position);
            }


        }

        @Override
        public boolean onLongClick(View view) {
            final int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                if (mUnlockByInviteInterface != null) {
                    final ProgramIndex programIndex = mProgram_Indexs.get(position);
                    mUnlockByInviteInterface.onUnlockClick(programIndex.getProgram_index());
                }
            }
            return true;
        }
    }

}

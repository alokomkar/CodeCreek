package com.sortedqueue.programmercreek.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2016-12-25.
 */
public class ModulesRecyclerViewAdapter extends RecyclerView.Adapter<ModulesRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LanguageModule> languageModules;
    private CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner;
    private CreekUserStats creekUserStats;
    private String programLanguage;
    private String TAG = ModulesRecyclerViewAdapter.class.getSimpleName();

    public ModulesRecyclerViewAdapter(Context context, ArrayList<LanguageModule> languageModules, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        this.context = context;
        this.languageModules = languageModules;
        this.adapterClickListner = adapterClickListner;
        this.creekUserStats = CreekApplication.getInstance().getCreekUserStats();
        this.programLanguage = CreekApplication.getCreekPreferences().getProgramLanguage();
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
        /*boolean isLocked = true;
        switch ( programLanguage ) {
            case "c" :
                isLocked = !(creekUserStats.getUnlockedCLanguageModuleIdList().contains(languageModule.getModuleId()));
                break;
            case "c++" :
            case "cpp" :
                isLocked = !(creekUserStats.getUnlockedCppLanguageModuleIdList().contains(languageModule.getModuleId()));
                break;
            case "java" :
                isLocked = !(creekUserStats.getUnlockedJavaLanguageModuleIdList().contains(languageModule.getModuleId()));
                break;
            case "sql" :
                isLocked = !(creekUserStats.getUnlockedSqlLanguageModuleIdList().contains(languageModule.getModuleId()));
                break;
        }*/

        //holder.lockedImageView.setVisibility(isLocked ? View.VISIBLE : View.INVISIBLE);
        holder.lockedImageView.setVisibility(View.INVISIBLE);
        //startAnimation(holder.itemView, position * 250 );
    }

    private void startAnimation(View itemView, int delay) {
        itemView.setAlpha(0.0f);
        itemView.animate().setStartDelay(delay).setDuration(300).alpha(1.0f);
    }

    @Override
    public int getItemCount() {
        return languageModules.size();
    }

    public void resetAdapter() {
        this.creekUserStats = CreekApplication.getInstance().getCreekUserStats();
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.moduleNameTextView)
        TextView moduleNameTextView;
        @BindView(R.id.moduleDescriptionTextView)
        TextView moduleDescriptionTextView;
        @BindView(R.id.lockedImageView)
        ImageView lockedImageView;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION )
                if( lockedImageView.getVisibility() == View.VISIBLE ) {
                    CommonUtils.displaySnackBar((Activity) context, R.string.module_locked);
                    return;
                }
                adapterClickListner.onItemClick(position);
        }
    }
}

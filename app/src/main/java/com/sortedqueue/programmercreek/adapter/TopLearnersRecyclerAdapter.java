package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.UserRanking;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-02-17.
 */
public class TopLearnersRecyclerAdapter extends RecyclerView.Adapter<TopLearnersRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UserRanking> userRankings;

    public TopLearnersRecyclerAdapter(Context context, ArrayList<UserRanking> userRankings) {
        this.context = context;
        this.userRankings = userRankings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View adapterView = LayoutInflater.from(context).inflate(R.layout.item_top_learner, parent, false);
        return new ViewHolder(adapterView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        UserRanking userRanking = userRankings.get(position);
        holder.levelTextView.setText("Level " + (int)(userRanking.getReputation() / 100) );
        holder.userNameTextView.setText(userRanking.getUserFullName());
        Glide.with(context).load(userRanking.getUserPhotoUrl()).asBitmap().centerCrop().into(holder.movieGridItemImageView);

    }

    @Override
    public int getItemCount() {
        return userRankings.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.movieGridItemImageView)
        ImageView movieGridItemImageView;
        @Bind(R.id.userNameTextView)
        TextView userNameTextView;
        @Bind(R.id.levelTextView)
        TextView levelTextView;
        @Bind(R.id.movieGridCardView)
        CardView movieGridCardView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

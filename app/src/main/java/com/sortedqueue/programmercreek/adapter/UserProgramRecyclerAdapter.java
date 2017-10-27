package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.UserProgramDetails;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 16/05/17.
 */

public class UserProgramRecyclerAdapter extends RecyclerView.Adapter<UserProgramRecyclerAdapter.ViewHolder> {


    private String accessSpecifier;
    private UserProgramClickListener adapterClickListner;
    private ArrayList<UserProgramDetails> userProgramDetailsArrayList;
    private Context context;

    private String userEmail;

    private Drawable likeDrawable;
    private Drawable unlikeDrawable;

    public interface UserProgramClickListener extends CustomProgramRecyclerViewAdapter.AdapterClickListner {
        void onLikeClicked( boolean isLiked, int position );
        void onShareClicked( int position );
    }

    public UserProgramRecyclerAdapter(Context context, String accessSpecifier, ArrayList<UserProgramDetails> modelArrayList, UserProgramClickListener adapterClickListner) {
        this.userProgramDetailsArrayList = modelArrayList;
        this.accessSpecifier = accessSpecifier;
        this.context = context;
        this.adapterClickListner = adapterClickListner;
        this.userEmail = CreekApplication.Companion.getCreekPreferences().getSignInAccount();
        this.likeDrawable = ContextCompat.getDrawable(context, R.drawable.ic_star_on);
        this.unlikeDrawable = ContextCompat.getDrawable(context, R.drawable.ic_star_off);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_program, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserProgramDetails userProgramDetails = userProgramDetailsArrayList.get(position);
        ProgramIndex programIndex = userProgramDetails.getProgramIndex();
        holder.titleTextView.setText(programIndex.getProgram_Language().toUpperCase() + " : " + programIndex.getProgram_Description());
        holder.subTitleTextView.setText(userProgramDetails.getPreview());
        holder.viewsTextView.setText(userProgramDetails.getViews() + " Views");
        holder.likesTextView.setText(userProgramDetails.getLikes() + " Likes");

        //holder.likesTextView.setCompoundDrawables( userProgramDetails.isLiked( userEmail ) ? likeDrawable : unlikeDrawable, null, null, null);
        holder.likesTextView.setCompoundDrawablesWithIntrinsicBounds( userProgramDetails.isLiked( userEmail ) ? likeDrawable : unlikeDrawable, null, null, null);
        holder.extrasLayout.setVisibility( accessSpecifier.equals("Favorites") ? View.GONE : View.VISIBLE );

    }

    @Override
    public int getItemCount() {
        return userProgramDetailsArrayList.size();
    }

    public UserProgramDetails getItemAtPosition(int position) {
        return userProgramDetailsArrayList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.titleTextView)
        TextView titleTextView;
        @BindView(R.id.subTitleTextView)
        TextView subTitleTextView;
        @BindView(R.id.viewsTextView)
        TextView viewsTextView;
        @BindView(R.id.likesTextView)
        TextView likesTextView;
        @BindView(R.id.shareImageView)
        ImageView shareImageView;
        @BindView(R.id.extrasLayout)
        LinearLayout extrasLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            titleTextView.setOnClickListener(this);
            subTitleTextView.setOnClickListener(this);
            viewsTextView.setOnClickListener(this);
            likesTextView.setOnClickListener(this);
            shareImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                UserProgramDetails userProgramDetails = userProgramDetailsArrayList.get(position);
                if( v.getId() == R.id.likesTextView ) {
                    if( userProgramDetails.isLiked(userEmail) ) {
                        userProgramDetails.setLiked(false, userEmail);
                        adapterClickListner.onLikeClicked(false, position);
                    }
                    else {
                        userProgramDetails.setLiked(true, userEmail);
                        adapterClickListner.onLikeClicked(true, position);
                    }

                }
                else if( v.getId() == R.id.shareImageView ) {
                    adapterClickListner.onShareClicked(position);
                }
                else {
                    adapterClickListner.onItemClick(position);
                }
            }
        }
    }

}
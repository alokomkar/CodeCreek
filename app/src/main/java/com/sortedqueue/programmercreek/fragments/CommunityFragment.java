package com.sortedqueue.programmercreek.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sortedqueue.programmercreek.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-04-04.
 */
public class CommunityFragment extends Fragment implements View.OnClickListener {

    private static CommunityFragment instance;
    @Bind(R.id.facebookCardView)
    CardView facebookCardView;
    @Bind(R.id.twitterCardView)
    CardView twitterCardView;
    @Bind(R.id.googleCardView)
    CardView googleCardView;

    private final String TWITTER_LINK = "https://twitter.com/Programmercreek";
    private final String FACEBOOK_LINK = "https://www.facebook.com/Infinite-Programmer-1819430981602209/?fref=ts";
    private final String GOOGLE_PLUS_LINK = "https://plus.google.com/u/1/communities/117275222080442676688";


    public static CommunityFragment getInstance() {
        if (instance == null) {
            instance = new CommunityFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        ButterKnife.bind(this, view);
        initListeners();
        return view;
    }

    private void initListeners() {
        facebookCardView.setOnClickListener(this);
        googleCardView.setOnClickListener(this);
        twitterCardView.setOnClickListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.googleCardView :
                startIntent(GOOGLE_PLUS_LINK);
                break;
            case R.id.facebookCardView :
                startIntent(FACEBOOK_LINK);
                break;
            case R.id.twitterCardView :
                startIntent(TWITTER_LINK);
                break;
        }
    }

    private void startIntent( String url ) {
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

}

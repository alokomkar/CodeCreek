package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.SlideModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;

/**
 * Created by Alok on 11/04/17.
 */

public class ViewSlideFragment extends Fragment {

    @Bind(R.id.titleTextView)
    TextView titleTextView;
    @Bind(R.id.subTitleTextView)
    TextView subTitleTextView;
    @Bind(R.id.slideImageView)
    ImageView slideImageView;
    @Bind(R.id.slideImageLayout)
    FrameLayout slideImageLayout;
    @Bind(R.id.codeView)
    CodeView codeView;
    private SlideModel slideModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_slide, container, false);
        ButterKnife.bind(this, view);
        setupViews();
        return view;
    }

    private void setupViews() {
        titleTextView.setText(slideModel.getTitle());
        subTitleTextView.setText(slideModel.getSubTitle());
        Glide.with(getContext())
                .load(slideModel.getSlideImageUrl())
                .asBitmap()
                .centerCrop()
                .error(R.color.md_blue_600)
                .placeholder(R.color.md_blue_600)
                .into(slideImageView);
    }

    public void setParameter(SlideModel parameter) {
        this.slideModel = parameter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

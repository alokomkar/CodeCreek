package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.TutorialModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 15/05/17.
 */

public class TutorialModelFragment extends Fragment {

    @Bind(R.id.titleTextView)
    TextView titleTextView;
    @Bind(R.id.subTitleTextView)
    TextView subTitleTextView;
    @Bind(R.id.slideImageView)
    ImageView slideImageView;
    @Bind(R.id.cancelButton)
    Button cancelButton;
    @Bind(R.id.nextButton)
    Button nextButton;
    private TutorialModel tutorialModel;
    private int index = 0;

    public void setParameter(TutorialModel parameter, int index) {
        this.tutorialModel = parameter;
        this.index = index;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);
        ButterKnife.bind(this, view);
        titleTextView.setText("Step : " + index);
        subTitleTextView.setText(tutorialModel.getStepDescription());
        slideImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.splash_logo));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

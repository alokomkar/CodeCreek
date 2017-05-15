package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
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
import com.sortedqueue.programmercreek.interfaces.TutorialNavigationListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 15/05/17.
 */

public class TutorialModelFragment extends Fragment implements View.OnClickListener {

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
    private int size;

    private TutorialNavigationListener tutorialNavigationListener;

    public void setParameter(TutorialModel parameter, int index, int size) {
        this.tutorialModel = parameter;
        this.index = index;
        this.size = size;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof TutorialNavigationListener ) {
            tutorialNavigationListener = (TutorialNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        tutorialNavigationListener = null;
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
        if( index == 1 ) {
            cancelButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setText("Next");
            cancelButton.setText("Cancel");
        }
        else if( index > 1 && index < size ) {
            cancelButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setText("Next");
            cancelButton.setText("Prev");
        }
        else if( index == size ) {
            cancelButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setText("Done");
            cancelButton.setText("Prev");
        }
        nextButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();
        switch ( buttonText ) {
            case "Done" :
                tutorialNavigationListener.onCancelClick();
                break;
            case "Prev" :
                tutorialNavigationListener.onPreviousClick();
                break;
            case "Next" :
                tutorialNavigationListener.onNextClick();
                break;
        }
    }
}

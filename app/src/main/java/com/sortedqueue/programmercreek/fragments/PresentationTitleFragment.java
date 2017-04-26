package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-04-26.
 */

public class PresentationTitleFragment extends Fragment {

    @Bind(R.id.presentationTitleEditText)
    EditText presentationTitleEditText;
    @Bind(R.id.presentationTitleLayout)
    TextInputLayout presentationTitleLayout;
    @Bind(R.id.presentationDescriptionEditText)
    EditText presentationDescriptionEditText;
    @Bind(R.id.presentationDescriptionLayout)
    TextInputLayout presentationDescriptionLayout;
    @Bind(R.id.tagsHeaderTextView)
    TextView tagsHeaderTextView;
    @Bind(R.id.tagsRecyclerView)
    RecyclerView tagsRecyclerView;
    @Bind(R.id.addTagEditText)
    EditText addTagEditText;
    @Bind(R.id.addTagTextView)
    TextView addTagTextView;
    @Bind(R.id.tagsLayout)
    LinearLayout tagsLayout;
    @Bind(R.id.titleLayout)
    LinearLayout titleLayout;
    @Bind(R.id.doneButton)
    Button doneButton;
    @Bind(R.id.cancelButton)
    Button cancelButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_presentation_title, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

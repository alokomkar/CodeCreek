package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.LanguageRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.TagsRecyclerAdapter;
import com.sortedqueue.programmercreek.database.TagModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-04-26.
 */

public class PresentationTitleFragment extends Fragment implements View.OnClickListener {

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
    private ArrayList<String> languages;
    private LanguageRecyclerAdapter languageRecyclerAdapter;
    private TagsRecyclerAdapter tagsRecyclerAdapter;

    private static PresentationTitleFragment presentationTitleFragment;

    public static PresentationTitleFragment getInstance() {
        if( presentationTitleFragment == null ) {
            presentationTitleFragment = new PresentationTitleFragment();
        }
        return presentationTitleFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_presentation_title, container, false);
        ButterKnife.bind(this, view);
        fetchAllTags();
        setupListeners();
        return view;
    }

    private void setupListeners() {
        doneButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    private void fetchAllTags() {

        CommonUtils.displayProgressDialog(getContext(), getString(R.string.loading));
        new FirebaseDatabaseHandler(getContext()).getAllTags(new FirebaseDatabaseHandler.GetAllTagsListener() {
            @Override
            public void onError(DatabaseError databaseError) {
                CommonUtils.dismissProgressDialog();
            }

            @Override
            public void onSuccess(TagModel tagModel) {
                setupRecyclerView(tagModel);
            }
        });
    }

    private void setupRecyclerView(TagModel tagModel) {
        tagsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tagsRecyclerAdapter = new TagsRecyclerAdapter(tagModel.getTagArrayList());
        tagsRecyclerView.setAdapter(tagsRecyclerAdapter);
        CommonUtils.dismissProgressDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.doneButton :
                validateAndSavePresentation();
                break;
            case R.id.cancelButton :
                getActivity().finish();
                break;
        }
    }

    private void validateAndSavePresentation() {
        String presentationTitle = presentationTitleEditText.getText().toString();
        if( presentationTitle == null || presentationTitle.trim().length() == 0 ) {
            presentationTitleLayout.setError(getString(R.string.required_field));
            return;
        }
        String presentationDescription = presentationDescriptionEditText.getText().toString();
        if( presentationDescription == null || presentationDescription.trim().length() == 0 ) {
            presentationDescriptionLayout.setError(getString(R.string.required_field));
            return;
        }
        tagsRecyclerAdapter.getSelectedTags();
    }
}

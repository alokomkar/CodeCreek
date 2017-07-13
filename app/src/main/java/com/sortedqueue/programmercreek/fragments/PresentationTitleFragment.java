package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
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
import com.sortedqueue.programmercreek.adapter.TagsRecyclerAdapter;
import com.sortedqueue.programmercreek.database.TagModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.PresentationCommunicationsListener;
import com.sortedqueue.programmercreek.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-04-26.
 */

public class PresentationTitleFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.presentationTitleEditText)
    EditText presentationTitleEditText;
    @BindView(R.id.presentationTitleLayout)
    TextInputLayout presentationTitleLayout;
    @BindView(R.id.presentationDescriptionEditText)
    EditText presentationDescriptionEditText;
    @BindView(R.id.presentationDescriptionLayout)
    TextInputLayout presentationDescriptionLayout;
    @BindView(R.id.tagsHeaderTextView)
    TextView tagsHeaderTextView;
    @BindView(R.id.tagsRecyclerView)
    RecyclerView tagsRecyclerView;
    @BindView(R.id.addTagEditText)
    EditText addTagEditText;
    @BindView(R.id.addTagTextView)
    TextView addTagTextView;
    @BindView(R.id.tagsLayout)
    LinearLayout tagsLayout;
    @BindView(R.id.titleLayout)
    LinearLayout titleLayout;
    @BindView(R.id.doneButton)
    Button doneButton;
    @BindView(R.id.cancelButton)
    Button cancelButton;
    private TagsRecyclerAdapter tagsRecyclerAdapter;

    private PresentationCommunicationsListener presentationCommunicationsListener;

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
        addTagTextView.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof PresentationCommunicationsListener )
        presentationCommunicationsListener = (PresentationCommunicationsListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presentationCommunicationsListener = null;
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
            case R.id.addTagTextView :
                String newTag = addTagEditText.getText().toString();
                if( newTag != null && newTag.trim().length() > 0 ) {
                    addTagEditText.clearComposingText();
                    tagsRecyclerAdapter.addTag(newTag);
                }
                break;
        }
    }

    private void validateAndSavePresentation() {
        String presentationTitle = presentationTitleEditText.getText().toString();
        if( presentationTitle == null || presentationTitle.trim().length() == 0 ) {
            presentationTitleLayout.setErrorEnabled(true);
            presentationTitleLayout.setError(getString(R.string.required_field));
            return;
        }

        presentationTitleLayout.setError(null);
        presentationTitleLayout.setErrorEnabled(false);

        String presentationDescription = presentationDescriptionEditText.getText().toString();
        if( presentationDescription == null || presentationDescription.trim().length() == 0 ) {
            presentationDescriptionLayout.setErrorEnabled(false);
            presentationDescriptionLayout.setError(getString(R.string.required_field));
            return;
        }
        presentationDescriptionLayout.setError(null);
        presentationDescriptionLayout.setErrorEnabled(false);

        if( presentationCommunicationsListener != null ) {
            presentationCommunicationsListener.onPresentationTitle(presentationTitle, presentationDescription, tagsRecyclerAdapter.getSelectedTags());
        }
    }
}

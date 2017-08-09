package com.sortedqueue.programmercreek.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.QuickRefernceRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.TagsRecyclerAdapter;
import com.sortedqueue.programmercreek.database.QuickReference;
import com.sortedqueue.programmercreek.database.TagModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alok on 04/08/17.
 */

public class QuickReferenceFragment extends Fragment implements CustomProgramRecyclerViewAdapter.AdapterClickListner {

    @BindView(R.id.quickReferenceRecyclerView)
    RecyclerView quickReferenceRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;
    @BindView(R.id.languageRecyclerView)
    RecyclerView languageRecyclerView;
    private TagsRecyclerAdapter tagsRecyclerAdapter;
    private String selectedTag;
    private QuickRefernceRecyclerAdapter quickRefernceRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_quick_reference, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchAllTags();
    }

    private void fetchAllTags() {
        quickReferenceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        quickRefernceRecyclerAdapter = new QuickRefernceRecyclerAdapter(new ArrayList<QuickReference>());
        quickReferenceRecyclerView.setAdapter(quickRefernceRecyclerAdapter);
        CommonUtils.displayProgressDialog(getContext(), getContext().getString(R.string.loading));
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
        languageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tagsRecyclerAdapter = new TagsRecyclerAdapter(tagModel.getTagArrayList(), 1, this);
        languageRecyclerView.setAdapter(tagsRecyclerAdapter);
        tagsRecyclerAdapter.setSelectedTag("C");
        onItemClick(0);
        CommonUtils.dismissProgressDialog();
    }

    private void getAllReference() {

        new AsyncTask<Void, Void, ArrayList<QuickReference>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressLayout.setVisibility(View.VISIBLE);

            }

            @Override
            protected void onPostExecute(ArrayList<QuickReference> quickReferences) {
                super.onPostExecute(quickReferences);
                quickRefernceRecyclerAdapter.setItems(quickReferences);
                progressLayout.setVisibility(View.GONE);
            }

            @Override
            protected ArrayList<QuickReference> doInBackground(Void... voids) {
                switch (selectedTag) {
                    case "C" :
                        return QuickReference.getCQuickReference();
                    case "C++" :
                        return QuickReference.getCPPQuickReference();
                    case "Java" :
                        return QuickReference.getJavaQuickReference();
                    case "SQL" :
                        return QuickReference.getSQLQuickReference();
                    default:
                        return QuickReference.getCQuickReference();
                }
            }
        }.execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(int position) {
        selectedTag = tagsRecyclerAdapter.getSelectedTag();
        getAllReference();
    }
}

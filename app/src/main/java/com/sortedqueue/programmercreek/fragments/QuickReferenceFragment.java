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

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.QuickRefernceRecyclerAdapter;
import com.sortedqueue.programmercreek.database.QuickReference;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alok on 04/08/17.
 */

public class QuickReferenceFragment extends Fragment {

    @BindView(R.id.quickReferenceRecyclerView)
    RecyclerView quickReferenceRecyclerView;
    Unbinder unbinder;

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
        getAllReference();
    }

    private void getAllReference() {
        new AsyncTask<Void, Void, ArrayList<QuickReference>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                CommonUtils.displayProgressDialog(getContext(), "Loading");

            }

            @Override
            protected void onPostExecute(ArrayList<QuickReference> quickReferences) {
                super.onPostExecute(quickReferences);
                CommonUtils.dismissProgressDialog();
                quickReferenceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                QuickRefernceRecyclerAdapter quickRefernceRecyclerAdapter = new QuickRefernceRecyclerAdapter(quickReferences);
                quickReferenceRecyclerView.setAdapter(quickRefernceRecyclerAdapter);
            }

            @Override
            protected ArrayList<QuickReference> doInBackground(Void... voids) {
                return QuickReference.getCQuickReference();
            }
        }.execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

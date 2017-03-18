package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.AlgorithmListActivity;
import com.sortedqueue.programmercreek.adapter.AlgorithmsRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.database.AlgorithmsIndex;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.AlgorithmNavigationListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-03-17.
 */

public class AlgorithmIndexFragment extends Fragment implements FirebaseDatabaseHandler.GetAllAlgorithmsListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {

    @Bind(R.id.modulesRecyclerView)
    RecyclerView programListRecyclerView;
    private AlgorithmsRecyclerAdapter algorithmsRecyclerAdapter;

    private static AlgorithmIndexFragment indexFragment;
    public static AlgorithmIndexFragment getInstance() {
        if( indexFragment == null ) {
            indexFragment = new AlgorithmIndexFragment();
        }
        return indexFragment;
    }

    private AlgorithmNavigationListener algorithmNavigationListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof AlgorithmNavigationListener ) {
            algorithmNavigationListener = (AlgorithmNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        algorithmNavigationListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_module, container, false);
        ButterKnife.bind(this, view);
        fetchAlgorithmsList();
        return view;
    }

    private void fetchAlgorithmsList() {
        new FirebaseDatabaseHandler(getContext()).getAllAlgorithmIndex( this );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSuccess(ArrayList<AlgorithmsIndex> algorithmsIndexArrayList) {
        programListRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()) );
        algorithmsRecyclerAdapter = new AlgorithmsRecyclerAdapter( getContext(), this, algorithmsIndexArrayList );
        programListRecyclerView.setAdapter( algorithmsRecyclerAdapter );
    }

    @Override
    public void onError(DatabaseError databaseError) {

    }

    @Override
    public void onItemClick(int position) {
        algorithmNavigationListener.loadAlgorithmFragment(algorithmsRecyclerAdapter.getItemAtPosition( position ));
    }


}

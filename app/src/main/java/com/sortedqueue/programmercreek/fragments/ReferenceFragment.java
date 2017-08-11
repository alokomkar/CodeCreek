package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter;
import com.sortedqueue.programmercreek.database.QuickReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alok on 11/08/17.
 */

public class ReferenceFragment extends Fragment {

    @BindView(R.id.headerView)
    View headerView;
    @BindView(R.id.headerTextView)
    TextView headerTextView;
    @BindView(R.id.dividerView)
    View dividerView;
    @BindView(R.id.indicatorImageview)
    ImageView indicatorImageview;
    @BindView(R.id.contentRecyclerView)
    RecyclerView contentRecyclerView;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_reference, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    private QuickReference quickReference;
    private String language;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        quickReference = getArguments().getParcelable("quickReference");
        language = getArguments().getString("language");
        headerTextView.setText(quickReference.getHeader());
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        contentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentRecyclerView.setAdapter(new CodeEditorRecyclerAdapter(getContext(), quickReference.getContentArray(), language));
    }

    public static ReferenceFragment newInstance(QuickReference quickReference, String language) {
        ReferenceFragment referenceFragment = new ReferenceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("language", language);
        bundle.putParcelable("quickReference", quickReference);
        referenceFragment.setArguments(bundle);
        return referenceFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

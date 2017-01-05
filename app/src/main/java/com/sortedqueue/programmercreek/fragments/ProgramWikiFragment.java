package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.ProgramWikiRecyclerAdapter;
import com.sortedqueue.programmercreek.database.ProgramWiki;
import com.sortedqueue.programmercreek.database.WikiModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2016-12-31.
 */

public class ProgramWikiFragment extends Fragment {

    @Bind(R.id.headerTextView)
    TextView headerTextView;
    @Bind(R.id.programWikiRecyclerView)
    RecyclerView programWikiRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_program_wiki, null);
        ButterKnife.bind(this, fragmentView);
        setupViews( programWiki );
        if( wizardUrl == null ) {
            headerTextView.setText(programWiki.getWikiHeader());
            setupRecyclerView( programWiki );
        }
        else {
            new FirebaseDatabaseHandler(getContext()).getWikiModel(wizardUrl, new FirebaseDatabaseHandler.GetWikiModelListener() {
                @Override
                public void onSuccess(WikiModel wikiModel) {
                    ProgramWikiFragment.this.programWiki = wikiModel;
                    headerTextView.setText(programWiki.getWikiHeader());
                    setupRecyclerView( programWiki );
                }
            });
        }

        return fragmentView;
    }

    private void setupViews(WikiModel programWiki) {
        this.programWiki = programWiki;
    }

    private void setupRecyclerView(WikiModel wikiModel) {
        programWikiRecyclerView.setAdapter( new ProgramWikiRecyclerAdapter( getContext(), wikiModel.getProgramWikis() ));
        programWikiRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()) );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private WikiModel programWiki;
    public void setParams(WikiModel programWiki) {
        this.programWiki = programWiki;
    }

    private String wizardUrl;
    public void setParams(String wizardUrl) {
        this.wizardUrl = wizardUrl;
    }
}

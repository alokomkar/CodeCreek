package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.ProgramWikiRecyclerAdapter;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.database.ChapterDetails;
import com.sortedqueue.programmercreek.database.WikiModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener;
import com.sortedqueue.programmercreek.interfaces.WikiNavigationListner;
import com.sortedqueue.programmercreek.util.CommonUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2016-12-31.
 */

public class ProgramWikiFragment extends Fragment implements TestCompletionListener {

    @Bind(R.id.headerTextView)
    TextView headerTextView;
    @Bind(R.id.programWikiRecyclerView)
    RecyclerView programWikiRecyclerView;
    @Bind(R.id.backImageView)
    ImageView backImageView;
    @Bind(R.id.progressBar)
    ContentLoadingProgressBar progressBar;
    private WikiNavigationListner wikiNavigationListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_program_wiki, null);
        ButterKnife.bind(this, fragmentView);
        setupViews( programWiki );
        if( wizardUrl == null ) {
            progressBar.setVisibility(View.VISIBLE);
            headerTextView.setText(programWiki.getWikiHeader());
            setupRecyclerView( programWiki );
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            if( wikiNavigationListener != null )
                wikiNavigationListener.disableViewPager();
            new FirebaseDatabaseHandler(getContext()).getWikiModel(wizardUrl, new FirebaseDatabaseHandler.GetWikiModelListener() {
                @Override
                public void onSuccess(WikiModel wikiModel) {
                    ProgramWikiFragment.this.programWiki = wikiModel;
                    headerTextView.setText(programWiki.getWikiHeader());
                    setupRecyclerView( programWiki );
                }

                @Override
                public void onError(DatabaseError databaseError) {
                    CommonUtils.displayToast(getContext(), R.string.unable_to_fetch_data);
                    progressBar.setVisibility(View.GONE);
                    if( wikiNavigationListener != null ) {
                        wikiNavigationListener.enableViewPager();
                    }
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
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( wikiNavigationListener != null ) {
                    wikiNavigationListener.onBackPressed();
                }
            }
        });
        backImageView.setVisibility( wikiNavigationListener != null ? View.VISIBLE : View.GONE );
        progressBar.setVisibility(View.GONE);
        if( wikiNavigationListener != null ) {
            wikiNavigationListener.enableViewPager();
        }
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

    @Override
    public int isTestComplete() {
        return ChapterDetails.TYPE_WIKI;
    }

    public void setWikiNavigationListener(WikiNavigationListner wikiNavigationListener) {
        this.wikiNavigationListener = wikiNavigationListener;
    }
}

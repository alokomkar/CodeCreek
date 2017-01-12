package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.ProgramInserterWikiAdapter;
import com.sortedqueue.programmercreek.database.ProgramWiki;
import com.sortedqueue.programmercreek.database.WikiModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-01-11.
 */

public class ProgramInserterFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.wikiHeaderEditText)
    AppCompatEditText wikiHeaderEditText;
    @Bind(R.id.wikiIdEditText)
    AppCompatEditText wikiIdEditText;
    @Bind(R.id.syntaxLanguageTextView)
    TextView syntaxLanguageTextView;
    @Bind(R.id.insertButton)
    Button insertButton;
    @Bind(R.id.deleteButton)
    Button deleteButton;
    @Bind(R.id.programWikiRecyclerView)
    RecyclerView programWikiRecyclerView;
    @Bind(R.id.saveButton)
    Button saveButton;

    private WikiModel wikiModel = new WikiModel();
    private List<ProgramWiki> programWikis = new ArrayList<>();
    private ProgramInserterWikiAdapter adapter;
    private ProgramWiki programWiki;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_program_inserter, null);
        ButterKnife.bind(this, fragmentView);
        setupViews();
        return fragmentView;
    }

    private void setupViews() {
        String language = new CreekPreferences(getContext()).getProgramLanguage();
        if( language.equals("c++") ) {
            language = "cpp";
        }
        syntaxLanguageTextView.setText(language);
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_HEADER);
        programWikis.add( programWiki );
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM_EXPLANATION);
        programWikis.add( programWiki );
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM);
        programWikis.add( programWiki );
        wikiModel.setSyntaxLanguage(syntaxLanguageTextView.getText().toString());
        deleteButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        insertButton.setOnClickListener(this);
        setupRecyelerView();
    }

    private void setupRecyelerView() {
        adapter = new ProgramInserterWikiAdapter(programWikis);
        programWikiRecyclerView.setAdapter( adapter );
        programWikiRecyclerView.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false ));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.deleteButton :
                deleteWiki();
                break;
            case R.id.insertButton :
                insertWiki();
                break;
            case R.id.saveButton :
                saveAllDetails();
                break;
        }
    }

    private void insertWiki() {
        ProgramWiki programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_HEADER);
        programWikis.add( programWiki );
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM_EXPLANATION);
        programWikis.add( programWiki );
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM);
        programWikis.add( programWiki );
        adapter.notifyDataSetChanged();
    }

    private void deleteWiki() {
        if( programWikis.size() == 3 ) {
            return;
        }
        int size = programWikis.size();
        programWikis.remove(size - 1);
        programWikis.remove(size - 2);
        programWikis.remove(size - 3);
        adapter.notifyDataSetChanged();
    }

    private void saveAllDetails() {
        if( wikiHeaderEditText.getText().toString().trim().length() == 0 ) {
            return;
        }
        if( wikiIdEditText.getText().toString().trim().length() == 0 ) {
            return;
        }

        wikiModel.setWikiHeader(wikiHeaderEditText.getText().toString());
        wikiModel.setWikiId(wikiIdEditText.getText().toString());
        wikiModel.setProgramWikis(programWikis);
        new FirebaseDatabaseHandler(getContext()).writeProgramWiki(wikiModel);
    }
}

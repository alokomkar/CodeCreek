package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.NewProgramWikiActivity;
import com.sortedqueue.programmercreek.adapter.NotesPreviewRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.ProgramWikiNavRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.ProgramWikiRecyclerAdapter;
import com.sortedqueue.programmercreek.database.NotesModel;
import com.sortedqueue.programmercreek.database.ProgramWiki;
import com.sortedqueue.programmercreek.database.WikiModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-07-28.
 */
public class NotesPreviewFragment extends Fragment {

    @BindView(R.id.programRecyclerView)
    RecyclerView programRecyclerView;
    private WikiModel wikiModel = new WikiModel();
    private ArrayList<ProgramWiki> programWikis = new ArrayList<>();
    private ArrayList<NotesModel> notesModelArrayList;
    private String selectedTag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_notes, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notesModelArrayList = getArguments().getParcelableArrayList("notes");
        selectedTag = getArguments().getString("selectedTag");

        wikiModel = new WikiModel();
        programWikis = new ArrayList<>();
        wikiModel.setSyntaxLanguage(selectedTag);

        for( NotesModel notesModel : notesModelArrayList ) {
            switch ( notesModel.getNoteType() ) {
                case NotesModel.TYPE_CODE :
                    break;
                case NotesModel.TYPE_LINK :
                    break;
                case NotesModel.TYPE_HEADER :
                    break;
                case NotesModel.TYPE_NOTES :
                    break;
            }
        }

        programRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()) );
        programRecyclerView.setAdapter( new NotesPreviewRecyclerAdapter(notesModelArrayList) );



    }

    public static NotesPreviewFragment newInstance(ArrayList<NotesModel> notesModelArrayList, String selectedTag) {
        NotesPreviewFragment notesPreviewFragment = new NotesPreviewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("notes", notesModelArrayList);
        bundle.putString("selectedTag", selectedTag);
        notesPreviewFragment.setArguments(bundle);
        notesPreviewFragment.setArguments(bundle);
        return notesPreviewFragment;
    }


}

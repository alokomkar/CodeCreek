package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.ChapterRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.database.ChapterDetails;
import com.sortedqueue.programmercreek.interfaces.ChapterNavigationListener;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 06/01/17.
 */

public class ChaptersFragment extends Fragment {


    @Bind(R.id.modulesRecyclerView)
    RecyclerView chaptersRecyclerView;

    private ChapterNavigationListener chapterNavigationListener;
    private ArrayList<Chapter> chapters;
    private String TAG = ChaptersFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_module, container, false);
        ButterKnife.bind(this, view);
        getModules();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof ChapterNavigationListener ) {
            chapterNavigationListener = (ChapterNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        chapterNavigationListener = null;
        super.onDetach();
    }

    private void getModules() {
        CommonUtils.displayProgressDialog(getContext(), "Loading chapters");
        /*
        new FirebaseDatabaseHandler(getContext()).initializeModules(new FirebaseDatabaseHandler.ModuleInterface() {
            @Override
            public void getModules(ArrayList<LanguageModule> chapters) {
                setupRecyclerView( chapters );
            }

            @Override
            public void onError(DatabaseError error) {
                Log.e(TAG, "Error : " + error.getMessage() + " : Details : " + error.getDetails() );
                CommonUtils.dismissProgressDialog();
            }
        });*/
        Chapter chapter = new Chapter();
        chapter.setMinStats(0);
        chapter.setProgram_Language("c");
        chapter.setChapterId("c_w_1");
        chapter.setChapterName("Simple I/O : Hello world");
        chapter.setChapteBrief("Learn about simple input and output commands, write your very first program");
        String syntaxId = "c_1";
        ArrayList<ChapterDetails> chapterDetailsArrayList = new ArrayList<>();
        int index = 1;
        int progressIndex = 1;
        ChapterDetails chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("c1");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("1");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters = new ArrayList<>();
        chapters.add(chapter);

        //Second Chapter
        chapter = new Chapter();
        chapter.setMinStats(chapterDetailsArrayList.size());
        chapter.setProgram_Language("c");
        chapter.setChapterId("c_w_2");
        chapter.setChapterName("Variables and Constants : Sum of two numbers");
        chapter.setChapteBrief("How to use variables and constants, practice program to find sum of two numbers ");

        syntaxId = "c_2";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("c2");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("2");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        Log.d("Chapter", "Chapter details : " + chapter.toString());
        chapters.add(chapter);
        setupRecyclerView(chapters);
    }

    private void setupRecyclerView(ArrayList<Chapter> chapters) {
        this.chapters = chapters;
        chaptersRecyclerView.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        chaptersRecyclerView.setAdapter( new ChapterRecyclerAdapter(getContext(), this.chapters, new CustomProgramRecyclerViewAdapter.AdapterClickListner() {
            @Override
            public void onItemClick(int position) {
                chapterNavigationListener.onChapterSelected(ChaptersFragment.this.chapters.get(position));
            }
        }));
        CommonUtils.dismissProgressDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

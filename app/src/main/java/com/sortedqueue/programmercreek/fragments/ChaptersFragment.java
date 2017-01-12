package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        chapters = new ArrayList<>();
        int prevChapterMinStats = 0;
        Chapter chapter = new Chapter();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("c");
        chapter.setChapterId("c_w_1");
        chapter.setChapterName("Simple I/O : Hello world");
        chapter.setChapteBrief("Learn about simple input and output commands,\nWrite your very first program : Hello world");
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

        chapters.add(chapter);

        //Second Chapter
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("c");
        chapter.setChapterId("c_w_2");
        chapter.setChapterName("Variables and Constants : Sum of two numbers");
        chapter.setChapteBrief("How to use variables and constants,\nPractice program to find sum of two numbers ");

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
        chapters.add(chapter);

        //Third chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("c");
        chapter.setChapterId("c_w_3");
        chapter.setChapterName("Operators : Sum of two numbers");
        chapter.setChapteBrief("How to perform mathematical and logical operations using operators,\nPractice program : Pythagoras theorem, Odd and Even using ? Operator ");

        syntaxId = "c_4";
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
        chapterDetails.setChapterReferenceId("c3");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("3");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("c8");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("14");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        //4th chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("c");
        chapter.setChapterId("c_w_4");
        chapter.setChapterName("Decision Making : use of if condition");
        chapter.setChapteBrief("Explore decision making C language,\nPractice program : Read a file - use of if keyword");

        syntaxId = "c_5";
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
        chapterDetails.setChapterReferenceId("c22");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("29");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        //5th chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("c");
        chapter.setChapterId("c_w_5");
        chapter.setChapterName("Looping : while, for, do...while");
        chapter.setChapteBrief("Often times, we come across a scenario requiring us to execute a statement multiple times, how do we do it?,\nPractice program : Palindrome number, Bubble sort");

        syntaxId = "c_6";
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
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("c12");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("19");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_2");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("c19");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("25");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("c");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_3");

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        setupRecyclerView(chapters);
    }

    private void setupRecyclerView(ArrayList<Chapter> chapters) {
        this.chapters = chapters;
        chaptersRecyclerView.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        chaptersRecyclerView.setAdapter( new ChapterRecyclerAdapter(getContext(), this.chapters, new CustomProgramRecyclerViewAdapter.AdapterClickListner() {
            @Override
            public void onItemClick(int position) {
                Chapter nextChapter = null;
                if( position + 1 < ChaptersFragment.this.chapters.size() ) {
                    nextChapter = ChaptersFragment.this.chapters.get( position + 1 );
                }
                chapterNavigationListener.onChapterSelected(ChaptersFragment.this.chapters.get(position), nextChapter);
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

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

        chapters = new ArrayList<>();
        int prevChapterMinStats = 0;
        Chapter chapter = new Chapter();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("java");
        chapter.setChapterId("java_w_1");
        chapter.setChapterName("Basic datatypes : Usage in programs");
        chapter.setChapteBrief("Basic datatypes covering primitive, non primitive and other datatypes\n" +
                "1. Prime numbers\n" +
                "2. Command line argument\n" +
                "3. String concatenation");
        String syntaxId = "java_1";
        ArrayList<ChapterDetails> chapterDetailsArrayList = new ArrayList<>();
        int index = 1;
        int progressIndex = 1;
        ChapterDetails chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("java1");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_QUIZ);
        chapterDetails.setChapterReferenceId("1" );

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("java2");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);
        chapterDetails.setChapterReferenceId("2" );

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("java3");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("3" );
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);

        chapters.add(chapter);

        //Second Chapter
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("java");
        chapter.setChapterId("java_w_2");
        chapter.setChapterName("Variable types");
        chapter.setChapteBrief("A variable provides us with named storage that our programs can manipulate.\n" +
                "1. Quadratic formula\n" +
                "2. Exploring while loop\n" +
                "3. Nested loops");

        syntaxId = "java_2";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("java4");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("4");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("java5");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("5");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_MATCH);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("java6");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("6");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        //Third chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("java");
        chapter.setChapterId("java_w_3");
        chapter.setChapterName("Objects and classes");
        chapter.setChapteBrief("How to use objects and classes\n" +
                "1. Interactive input\n" +
                "2. Factorial recursion"
                );

        syntaxId = "java_3";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("java7");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("7");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_QUIZ);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("java8");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("8");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        //4th chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("java");
        chapter.setChapterId("java_w_4");
        chapter.setChapterName("Modifier types in Java");
        chapter.setChapteBrief("Java provides a number of access modifiers to set access levels for classes, variables, methods, and constructors. \n" +
                "1. Binary search"
                );

        syntaxId = "java_4";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("java9");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("9");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_QUIZ);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        //5th chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("java");
        chapter.setChapterId("java_w_5");
        chapter.setChapterName("Enhanced for loop");
        chapter.setChapteBrief("As of Java 5, the enhanced for loop was introduced. This is mainly used to traverse collection of elements including arrays.\n"
                );

        syntaxId = "java_5";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("java");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        setupRecyclerView(chapters);
        /*FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(getContext());
        for( Chapter chapter1 : chapters ) {
            firebaseDatabaseHandler.writeChapter(chapter1);
        }
        */
        /*CommonUtils.displayProgressDialog(getContext(), "Loading chapters");
        new FirebaseDatabaseHandler(getContext()).getChaptersInBackground(new FirebaseDatabaseHandler.GetChapterListener() {
            @Override
            public void onSuccess(ArrayList<Chapter> chaptersList) {
                setupRecyclerView(chaptersList);
            }

            @Override
            public void onErrror(DatabaseError error) {
                CommonUtils.displaySnackBar(getActivity(), R.string.unable_to_fetch_data);
                CommonUtils.dismissProgressDialog();
            }
        });*/

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

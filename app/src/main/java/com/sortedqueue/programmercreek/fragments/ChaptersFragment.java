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
import com.sortedqueue.programmercreek.adapter.ChapterRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.database.ChapterDetails;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
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
        chapter.setProgram_Language("cpp");
        chapter.setChapterId("cpp_w_1");
        chapter.setChapterName("Basic I/O : Usage in programs");
        chapter.setChapteBrief("Learn about simple input and output commands\n" +
                "1. Largest of 3 numbers\n" +
                "2. Calculate discount\n" +
                "3. To find the case of the character");
        String syntaxId = "cpp_1";
        ArrayList<ChapterDetails> chapterDetailsArrayList = new ArrayList<>();
        int index = 1;
        int progressIndex = 1;
        ChapterDetails chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp1");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_QUIZ);
        chapterDetails.setChapterReferenceId("1" );

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp2");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);
        chapterDetails.setChapterReferenceId("2" );

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp3");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
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
        chapter.setProgram_Language("cpp");
        chapter.setChapterId("cpp_w_2");
        chapter.setChapterName("C++ Strings");
        chapter.setChapteBrief("String representations in C++\n" +
                "1. Leap year\n" +
                "2. Total days, years, months\n" +
                "3. Area of an isoceles triangle\n" +
                "4. Area and Circumference of Circle");

        syntaxId = "cpp_2";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp4");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("4");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp5");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("5");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_MATCH);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp6");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("6");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp7");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("7");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        //Third chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("cpp");
        chapter.setChapterId("cpp_w_3");
        chapter.setChapterName("Modifier types");
        chapter.setChapteBrief("Explore modifiers and type qualifiers C++\n" +
                "1. Swapping two values\n" +
                "2. Generate electricity bill\n" +
                "3. Factorial using loop\n" +
                "4. Grade generator");

        syntaxId = "cpp_3";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp8");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("8");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_QUIZ);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp9");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("9");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp10");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("10");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp11");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("11");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        //4th chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("cpp");
        chapter.setChapterId("cpp_w_4");
        chapter.setChapterName("Loop control statements");
        chapter.setChapteBrief("Loop control statements C++\n" +
                "1. Sum of all digits of a number\n" +
                "2. Fibonacci Series\n" +
                "3. Sum and average\n" +
                "4. Bubble sort");

        syntaxId = "cpp_5";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp12");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("12");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_QUIZ);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp13");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("13");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp14");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("14");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp15");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("15");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        //5th chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("cpp");
        chapter.setChapterId("cpp_w_5");
        chapter.setChapterName("Storage classes in c++");
        chapter.setChapteBrief("Storage classes in c++\n" +
                "The storage classs defines the scope and life time of variables and functions\n" +
                "1. Binary search\n" +
                "2. Sum of two matrix\n" +
                "3. Sum of rows and columns\n" +
                "4. Vowels and consonants count\n" +
                "5. Largest and second largest in an array");

        syntaxId = "cpp_4";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp16");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("16");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_QUIZ);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp17");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("17");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp18");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("18");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp19");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("19");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("cpp20");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("20");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        /*//6th chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("cpp");
        chapter.setChapterId("cpp_w_6");
        chapter.setChapterName("Storage Classes");
        chapter.setChapteBrief("A storage class defines the scope and life-time of variables/functions");

        syntaxId = "cpp_3";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("cpp");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_"+index);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(getContext());
        for( Chapter chapter1 : chapters ) {
            firebaseDatabaseHandler.writeChapter(chapter1);
        }*/
        setupRecyclerView(chapters);
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

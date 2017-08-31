package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.ProgramListActivity;
import com.sortedqueue.programmercreek.adapter.ChapterRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.ChapterNavigationListener;
import com.sortedqueue.programmercreek.util.AnimationUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 06/01/17.
 */

public class ChaptersFragment extends Fragment {

    //TODO https://github.com/AdColony/AdColony-Android-SDK-3/wiki/Showing-Interstitial-Ads
    @BindView(R.id.modulesRecyclerView)
    RecyclerView chaptersRecyclerView;
    @BindView(R.id.adView)
    AdView adView;

    private ChapterNavigationListener chapterNavigationListener;
    private ArrayList<Chapter> chapters;
    private String TAG = ChaptersFragment.class.getSimpleName();
    private ChapterRecyclerAdapter chapterRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapters, container, false);
        ButterKnife.bind(this, view);
        getModules();
        if( !CreekApplication.getCreekPreferences().isPremiumUser() ) {
            initAds();
        }
        return view;
    }

    private void initAds() {
        /*if( CreekApplication.getCreekPreferences().getAdsEnabled() )*/ {
            MobileAds.initialize(getContext(), getString(R.string.mobile_banner_id));
            //For actual ads : AdRequest adRequest = new AdRequest.Builder().build();
            //For creating test ads
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                    .build();
            adView.loadAd(adRequest);
            adView.setVisibility(View.GONE);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    adView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChapterNavigationListener) {
            chapterNavigationListener = (ChapterNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        chapterNavigationListener = null;
        super.onDetach();
    }

    private void getModules() {
        chaptersRecyclerView.setVisibility(View.INVISIBLE);
        chapters = new ArrayList<>();
        /*int prevChapterMinStats = 0;
        Chapter chapter = new Chapter();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("sql");
        chapter.setChapterId("sql_w_1");
        chapter.setChapterName("SQL - Syntax");
        chapter.setChapteBrief("SQL is followed by unique set of rules and guidelines called Syntax. " +
                "This tutorial gives you a quick start with SQL by listing all the basic SQL Syntax\n" +
                "1. Create Connection with database\n" +
                "2. Create Database");
        String syntaxId = "sql_1";
        ArrayList<ChapterDetails> chapterDetailsArrayList = new ArrayList<>();
        int index = 1;
        int progressIndex = 1;
        ChapterDetails chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("sql1");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);
        chapterDetails.setChapterReferenceId("1");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("sql2");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_MATCH);
        chapterDetails.setChapterReferenceId("2" );

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);

        chapters.add(chapter);

        //Second Chapter
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("sql");
        chapter.setChapterId("sql_w_2");
        chapter.setChapterName("SQL - Data Types");
        chapter.setChapteBrief("SQL data type is an attribute that specifies type of data of any object. Each column, variable and expression has related data type in SQL.\n" +
                "1. Selecting a database\n" +
                "2. Drop database");

        syntaxId = "sql_2";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("sql3");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("3");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("sql4");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("4");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_MATCH);

        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        //Third chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("sql");
        chapter.setChapterId("sql_w_3");
        chapter.setChapterName("SQL - Expressions");
        chapter.setChapteBrief("An expression is a combination of one or more values, operators, and " +
                "SQL functions that evaluate to a value.\n" +
                        "\n" +
                "SQL EXPRESSIONs are like formulas and they are written " +
                "in query language. You can also use them " +
                "to query the database for specific set of data.\n" +
                "1. Create Table \n" +
                "2. Drop Table"
                );

        syntaxId = "sql_3";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("sql5");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("5");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("sql6");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("6");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);



        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        //4th chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("sql");
        chapter.setChapterId("sql_w_4");
        chapter.setChapterName("SQL Database Commands");
        chapter.setChapteBrief("In this module, we will take a closer look at " +
                "commands involved in creation, selection and deletion of database\n\n" +
                "1. Insert records\n" +
                "2. Select records"
                );

        syntaxId = "sql_4";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("sql7");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("7");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("sql8");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("8");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);



        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        //5th chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("sql");
        chapter.setChapterId("sql_w_5");
        chapter.setChapterName("SQL - Table commands");
        chapter.setChapteBrief("In this module, we will take a closer " +
                "look at commands involved in creation and deletion of table\n\n" +
                "1. Update Query\n" +
                "2. Delete Row\n" +
                "3. Where clause"
                );

        syntaxId = "sql_5";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("sql9");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("9");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_QUIZ);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("sql10");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("10");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("sql11");
        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("11");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);
        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        //6th chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("sql");
        chapter.setChapterId("sql_w_6");
        chapter.setChapterName("SQL - CRUD Operations");
        chapter.setChapteBrief("In this module, we will take a closer look at " +
                "commands involved in insertion, selection, " +
                "update and deletion of rows of a table\n\n" +
                "1. Like clause"
        );

        syntaxId = "sql_6";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);
        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);
        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);
        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);
        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);
        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);
        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);
        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("sql12");
        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("12");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_FILL_BLANKS);
        chapterDetailsArrayList.add(chapterDetails);



        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        //7th chapter :
        chapter = new Chapter();
        prevChapterMinStats = prevChapterMinStats + chapterDetailsArrayList.size();
        chapter.setMinStats(prevChapterMinStats);
        chapter.setProgram_Language("sql");
        chapter.setChapterId("sql_w_7");
        chapter.setChapterName("SQL - Extras");
        chapter.setChapteBrief("In this module, we will explore sorting, " +
                "ordering and filtering of rows of a table\n\n" +
                "1. Sorting data : ASC, DESC"
        );

        syntaxId = "sql_7";
        chapterDetailsArrayList = new ArrayList<>();
        index = 1;
        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);
        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);
        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);
        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setSyntaxId(syntaxId);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);
        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("sql13");
        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setProgramLanguage("sql");
        chapterDetails.setProgressIndex(progressIndex++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("13");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);
        chapterDetailsArrayList.add(chapterDetails);

        chapter.setChapterDetailsArrayList(chapterDetailsArrayList);
        chapters.add(chapter);

        setupRecyclerView(chapters);
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(getContext());
        for( Chapter chapter1 : chapters ) {
            firebaseDatabaseHandler.writeChapter(chapter1);
        }*/
        CommonUtils.displayProgressDialog(getContext(), "Loading chapters");
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
        });

    }

    private void setupRecyclerView(final ArrayList<Chapter> chapters) {
        this.chapters = chapters;
        chaptersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        chapterRecyclerAdapter = new ChapterRecyclerAdapter(getContext(), this.chapters, new CustomProgramRecyclerViewAdapter.AdapterClickListner() {
            @Override
            public void onItemClick(int position) {
                if (position + 1 > ChaptersFragment.this.chapters.size()) {
                    Intent programListIntent = new Intent(getContext(), ProgramListActivity.class);
                    programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_WIZARD);
                    programListIntent.putExtra(ProgramListActivity.KEY_WIZARD, true);
                    programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_REVISE);
                    startActivity(programListIntent);
                    return;
                }
                Chapter nextChapter = null;
                if (position + 1 < ChaptersFragment.this.chapters.size()) {
                    nextChapter = ChaptersFragment.this.chapters.get(position + 1);
                }
                chapterNavigationListener.onChapterSelected(ChaptersFragment.this.chapters.get(position), nextChapter);
            }
        });
        chaptersRecyclerView.setAdapter(chapterRecyclerAdapter);
        CommonUtils.dismissProgressDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationUtils.slideInToLeft(chaptersRecyclerView);
            }
        }, 450);
    }

    @Override
    public void onResume() {
        super.onResume();
        if( chapterRecyclerAdapter != null ) {
            chapterRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

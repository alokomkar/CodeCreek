package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.database.ChapterDetails;
import com.sortedqueue.programmercreek.fragments.FillBlankFragment;
import com.sortedqueue.programmercreek.fragments.NewFillBlankFragment;
import com.sortedqueue.programmercreek.fragments.NewMatchFragment;
import com.sortedqueue.programmercreek.fragments.ProgramWikiFragment;
import com.sortedqueue.programmercreek.fragments.QuizFragment;
import com.sortedqueue.programmercreek.fragments.SyntaxLearnActivityFragment;
import com.sortedqueue.programmercreek.fragments.TestDragNDropFragment;
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener;
import com.sortedqueue.programmercreek.interfaces.WikiNavigationListner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alok on 05/01/17.
 */

public class ChapterDetailsPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> chapterFragments;
    private List<ChapterDetails> chapterDetailsArrayList;
    private ModuleDetailsScrollPageListener moduleDetailsScrollPageListener;

    public ChapterDetailsPagerAdapter(Context context,
                                      ModuleDetailsScrollPageListener moduleDetailsScrollPageListener,
                                      FragmentManager childFragmentManager,
                                      List<ChapterDetails> chapterDetailsArrayList,
                                      WikiNavigationListner wikiNavigationListner,
                                      Chapter nextChapter) {
        super(childFragmentManager);
        chapterFragments = new ArrayList<>();
        this.chapterDetailsArrayList = chapterDetailsArrayList;
        this.moduleDetailsScrollPageListener = moduleDetailsScrollPageListener;
        SyntaxLearnActivityFragment lastSyntaxLearnActivityFragment = null;
        for( ChapterDetails chapterDetails : chapterDetailsArrayList ) {
            switch ( chapterDetails.getChapterType() ) {
                case ChapterDetails.TYPE_PROGRAM_INDEX:
                    Bundle bundle = new Bundle();
                    bundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, Integer.parseInt(chapterDetails.getChapterReferenceId()));
                    bundle.putInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_LESSON);
                    switch ( chapterDetails.getChapterTestType() ) {
                        case ProgrammingBuddyConstants.KEY_MATCH :
                            NewMatchFragment matchMakerFragment = new NewMatchFragment();
                            matchMakerFragment.setBundle(bundle);
                            chapterFragments.add(matchMakerFragment);
                            break;
                        case ProgrammingBuddyConstants.KEY_TEST :
                            TestDragNDropFragment testDragNDropFragment = new TestDragNDropFragment();
                            testDragNDropFragment.setBundle(bundle);
                            chapterFragments.add(testDragNDropFragment);
                            break;
                        case ProgrammingBuddyConstants.KEY_QUIZ :
                            QuizFragment quizFragment = new QuizFragment();
                            quizFragment.setBundle(bundle);
                            chapterFragments.add(quizFragment);
                            break;
                        case ProgrammingBuddyConstants.KEY_FILL_BLANKS:
                            NewFillBlankFragment fillBlankFragment = new NewFillBlankFragment();
                            fillBlankFragment.setBundle(bundle);
                            fillBlankFragment.setmProgram_Index(Integer.parseInt(chapterDetails.getChapterReferenceId()));
                            fillBlankFragment.setModulteDetailsScrollPageListener(this.moduleDetailsScrollPageListener);
                            chapterFragments.add(fillBlankFragment);
                    }
                    break;
                case ChapterDetails.TYPE_SYNTAX_MODULE:
                    SyntaxLearnActivityFragment syntaxLearnActivityFragment = new SyntaxLearnActivityFragment();
                    syntaxLearnActivityFragment.setSyntaxModule(chapterDetails.getSyntaxId(), chapterDetails.getChapterReferenceId());
                    syntaxLearnActivityFragment.setModulteDetailsScrollPageListener(this.moduleDetailsScrollPageListener);
                    lastSyntaxLearnActivityFragment = syntaxLearnActivityFragment;
                    chapterFragments.add(syntaxLearnActivityFragment);
                    break;
                case ChapterDetails.TYPE_WIKI:
                    ProgramWikiFragment ProgramWikiFragment = new ProgramWikiFragment();
                    ProgramWikiFragment.setParams( chapterDetails.getChapterReferenceId() );
                    ProgramWikiFragment.setWikiNavigationListener( wikiNavigationListner );
                    chapterFragments.add(ProgramWikiFragment);
                    break;
            }
        }
        if( lastSyntaxLearnActivityFragment != null ) {
            lastSyntaxLearnActivityFragment.setIsLastFragment(true, nextChapter);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return chapterFragments.get(position);
    }

    public ChapterDetails getChapterDetailsForPosition( int position ) {
        return chapterDetailsArrayList.get(position);
    }

    @Override
    public int getCount() {
        return chapterFragments.size();
    }
}
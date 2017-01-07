package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.ChapterDetails;
import com.sortedqueue.programmercreek.fragments.MatchMakerFragment;
import com.sortedqueue.programmercreek.fragments.ProgramWikiFragment;
import com.sortedqueue.programmercreek.fragments.QuizFragment;
import com.sortedqueue.programmercreek.fragments.SyntaxLearnActivityFragment;
import com.sortedqueue.programmercreek.fragments.TestDragNDropFragment;
import com.sortedqueue.programmercreek.interfaces.WikiNavigationListner;

import java.util.ArrayList;

/**
 * Created by Alok on 05/01/17.
 */

public class ChapterDetailsPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> chaterFragments;


    public ChapterDetailsPagerAdapter(Context context, FragmentManager childFragmentManager, ArrayList<ChapterDetails> wizardModules, WikiNavigationListner wikiNavigationListner) {
        super(childFragmentManager);
        chaterFragments = new ArrayList<>();

        for( ChapterDetails chapterDetails : wizardModules ) {
            switch ( chapterDetails.getChapterType() ) {
                case ChapterDetails.TYPE_PROGRAM_INDEX:
                    Bundle bundle = new Bundle();
                    bundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, Integer.parseInt(chapterDetails.getChapterReferenceId()));
                    bundle.putInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_LESSON);
                    switch ( chapterDetails.getChapterTestType() ) {
                        case ProgrammingBuddyConstants.KEY_MATCH :
                            MatchMakerFragment matchMakerFragment = new MatchMakerFragment();
                            matchMakerFragment.setBundle(bundle);
                            chaterFragments.add(matchMakerFragment);
                            break;
                        case ProgrammingBuddyConstants.KEY_TEST :
                            TestDragNDropFragment testDragNDropFragment = new TestDragNDropFragment();
                            testDragNDropFragment.setBundle(bundle);
                            chaterFragments.add(testDragNDropFragment);
                            break;
                        case ProgrammingBuddyConstants.KEY_QUIZ :
                            QuizFragment quizFragment = new QuizFragment();
                            quizFragment.setBundle(bundle);
                            chaterFragments.add(quizFragment);
                            break;
                    }
                    break;
                case ChapterDetails.TYPE_SYNTAX_MODULE:
                    SyntaxLearnActivityFragment syntaxLearnActivityFragment = new SyntaxLearnActivityFragment();
                    syntaxLearnActivityFragment.setSyntaxModule(chapterDetails.getSyntaxId(), chapterDetails.getChapterReferenceId());
                    chaterFragments.add(syntaxLearnActivityFragment);
                    break;
                case ChapterDetails.TYPE_WIKI:
                    ProgramWikiFragment ProgramWikiFragment = new ProgramWikiFragment();
                    ProgramWikiFragment.setParams( chapterDetails.getChapterReferenceId() );
                    ProgramWikiFragment.setWikiNavigationListener( wikiNavigationListner );
                    chaterFragments.add(ProgramWikiFragment);
                    break;
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        return chaterFragments.get(position);
    }

    @Override
    public int getCount() {
        return chaterFragments.size();
    }
}
package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.Chapter
import com.sortedqueue.programmercreek.database.ChapterDetails
import com.sortedqueue.programmercreek.fragments.FillBlankFragment
import com.sortedqueue.programmercreek.fragments.NewFillBlankFragment
import com.sortedqueue.programmercreek.fragments.NewMatchFragment
import com.sortedqueue.programmercreek.fragments.ProgramWikiFragment
import com.sortedqueue.programmercreek.fragments.QuizFragment
import com.sortedqueue.programmercreek.fragments.SyntaxLearnActivityFragment
import com.sortedqueue.programmercreek.fragments.TestDragNDropFragment
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener
import com.sortedqueue.programmercreek.interfaces.WikiNavigationListner

import java.util.ArrayList

/**
 * Created by Alok on 05/01/17.
 */

class ChapterDetailsPagerAdapter(context: Context,
                                 private val moduleDetailsScrollPageListener: ModuleDetailsScrollPageListener,
                                 childFragmentManager: FragmentManager,
                                 private val chapterDetailsArrayList: List<ChapterDetails>,
                                 wikiNavigationListner: WikiNavigationListner,
                                 nextChapter: Chapter) : FragmentPagerAdapter(childFragmentManager) {

    private val chapterFragments: ArrayList<Fragment>

    init {
        chapterFragments = ArrayList<Fragment>()
        var lastSyntaxLearnActivityFragment: SyntaxLearnActivityFragment? = null
        for (chapterDetails in chapterDetailsArrayList) {
            when (chapterDetails.chapterType) {
                ChapterDetails.TYPE_PROGRAM_INDEX -> {
                    val bundle = Bundle()
                    bundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, Integer.parseInt(chapterDetails.chapterReferenceId))
                    bundle.putInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_LESSON)
                    when (chapterDetails.chapterTestType) {
                        ProgrammingBuddyConstants.KEY_MATCH -> {
                            val matchMakerFragment = NewMatchFragment()
                            matchMakerFragment.setBundle(bundle)
                            matchMakerFragment.setModuleDetailsScrollPageListener(this.moduleDetailsScrollPageListener)
                            chapterFragments.add(matchMakerFragment)
                        }
                        ProgrammingBuddyConstants.KEY_TEST -> {
                            val testDragNDropFragment = TestDragNDropFragment()
                            testDragNDropFragment.setBundle(bundle)
                            testDragNDropFragment.setModuleDetailsScrollPageListener(this.moduleDetailsScrollPageListener)
                            chapterFragments.add(testDragNDropFragment)
                        }
                        ProgrammingBuddyConstants.KEY_QUIZ -> {
                            val quizFragment = QuizFragment()
                            quizFragment.setBundle(bundle)
                            quizFragment.setModuleDetailsScrollPageListener(this.moduleDetailsScrollPageListener)
                            chapterFragments.add(quizFragment)
                        }
                        ProgrammingBuddyConstants.KEY_FILL_BLANKS -> {
                            val fillBlankFragment = NewFillBlankFragment()
                            fillBlankFragment.setBundle(bundle)
                            fillBlankFragment.setmProgram_Index(Integer.parseInt(chapterDetails.chapterReferenceId))
                            fillBlankFragment.setModulteDetailsScrollPageListener(this.moduleDetailsScrollPageListener)
                            chapterFragments.add(fillBlankFragment)
                        }
                    }
                }
                ChapterDetails.TYPE_SYNTAX_MODULE -> {
                    val syntaxLearnActivityFragment = SyntaxLearnActivityFragment()
                    syntaxLearnActivityFragment.setSyntaxModule(chapterDetails.syntaxId, chapterDetails.chapterReferenceId)
                    syntaxLearnActivityFragment.setModulteDetailsScrollPageListener(this.moduleDetailsScrollPageListener)
                    lastSyntaxLearnActivityFragment = syntaxLearnActivityFragment
                    chapterFragments.add(syntaxLearnActivityFragment)
                }
                ChapterDetails.TYPE_WIKI -> {
                    val ProgramWikiFragment = ProgramWikiFragment()
                    ProgramWikiFragment.setParams(chapterDetails.chapterReferenceId)
                    ProgramWikiFragment.setWikiNavigationListener(wikiNavigationListner)
                    chapterFragments.add(ProgramWikiFragment)
                }
            }
        }
        if (lastSyntaxLearnActivityFragment != null) {
            lastSyntaxLearnActivityFragment.setIsLastFragment(true, nextChapter)
        }
    }

    override fun getItem(position: Int): Fragment {
        return chapterFragments[position]
    }

    fun getChapterDetailsForPosition(position: Int): ChapterDetails {
        return chapterDetailsArrayList[position]
    }

    override fun getCount(): Int {
        return chapterFragments.size
    }
}
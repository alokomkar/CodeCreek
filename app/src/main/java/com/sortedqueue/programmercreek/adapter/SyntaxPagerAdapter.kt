package com.sortedqueue.programmercreek.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.sortedqueue.programmercreek.database.LanguageModule
import com.sortedqueue.programmercreek.database.SyntaxModule
import com.sortedqueue.programmercreek.fragments.SyntaxLearnActivityFragment
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener

import java.util.ArrayList

/**
 * Created by cognitive on 11/30/16.
 */
class SyntaxPagerAdapter(fm: FragmentManager, module: LanguageModule, nextModule: LanguageModule?, syntaxModules: ArrayList<SyntaxModule>, private val moduleDetailsScrollPageListener: ModuleDetailsScrollPageListener) : FragmentPagerAdapter(fm) {

    private val syntaxLearnActivityFragments: ArrayList<SyntaxLearnActivityFragment>

    init {
        syntaxLearnActivityFragments = ArrayList<SyntaxLearnActivityFragment>()
        var position = 0
        for (syntaxModule in syntaxModules) {
            position++
            val syntaxLearnActivityFragment = SyntaxLearnActivityFragment()
            syntaxLearnActivityFragment.setSyntaxModule(syntaxModule)
            syntaxLearnActivityFragment.setModulteDetailsScrollPageListener(moduleDetailsScrollPageListener)
            syntaxLearnActivityFragment.setIsLastFragment(position == syntaxModules.size, nextModule)
            syntaxLearnActivityFragments.add(syntaxLearnActivityFragment)
        }

    }

    override fun getItem(position: Int): Fragment {
        return syntaxLearnActivityFragments[position]
    }

    override fun getCount(): Int {
        return syntaxLearnActivityFragments.size
    }
}

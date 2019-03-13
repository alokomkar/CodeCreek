package com.sortedqueue.programmercreek.v2.ui.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.BaseFragment
import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import kotlinx.android.synthetic.main.fragment_pager.*

class PagerFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle? ): View?
            = inflater.inflate(R.layout.fragment_pager, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contentList = arguments?.getParcelableArrayList<SimpleContent>(SimpleContent::class.java.simpleName) ?: ArrayList()
        questionsProgressBar.max = contentList.size
        questionsProgressBar.progress = 1
        vpQuestions.adapter = QuestionsPagerAdapter( contentList, childFragmentManager )
        vpQuestions.setOnScrollChangeListener { _, _, _, _, _ -> questionsProgressBar.progress = vpQuestions.currentItem + 1 }

    }
}
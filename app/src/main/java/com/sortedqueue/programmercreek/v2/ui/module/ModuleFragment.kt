package com.sortedqueue.programmercreek.v2.ui.module

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.*
import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import com.sortedqueue.programmercreek.v2.data.model.Chapter
import com.sortedqueue.programmercreek.v2.ui.chapters.SubModulesAdapter
import kotlinx.android.synthetic.main.fragment_new_module.*


class ModuleFragment : BaseModuleFragment(), BaseAdapterClickListener<SimpleContent> {

    private lateinit var contentAdapter: SimpleContentAdapter
    private lateinit var chapter: Chapter
    private var currentContentList = ArrayList<SimpleContent>()
    private var chaptersList: ArrayList<Chapter> = ArrayList()
    override fun onItemClick(position: Int, item: SimpleContent) {

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle? ): View?
            = inflater.inflate(R.layout.fragment_new_module, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Chapter>(Chapter::class.java.simpleName)?.apply {
            chapter = this
        }
        tvHeader.text = chapter.moduleTitle

        rvModuleContent.layoutManager = LinearLayoutManager(context)
        rvTracker.layoutManager = LinearLayoutManager(context)

        navigateToContent( arguments!!.getInt(ModuleActivity.modulePosition) )

        ivNavigation.setOnClickListener { drawer_layout.openDrawer(nav_view) }

        arguments?.getParcelableArrayList<Chapter>(ModuleActivity.chaptersListExtra)?.apply {
            chaptersList = this
        }
        rvTracker.adapter = SubModulesAdapter( chaptersList, -1, object : BaseAdapterClickListener<Chapter> {
            override fun onItemClick(position: Int, item: Chapter) {
                tvHeader.text = item.moduleTitle
                navigateToContent( position )
                drawer_layout.closeDrawer(nav_view)
            }
        })

        moduleProgressBar.max = chaptersList.size

    }

    private fun navigateToContent(moduleId: Int) {
        questionContainer.hide()
        nextFAB.show()
        when ( chapter.chapterTitle ){
             "Introduction to Java" -> {
                when(  moduleId ) {
                    0 -> setAdapterContent(getFirstContent())
                    1 -> setAdapterContent(getSecondContent())
                    2 -> setAdapterContent(getThirdContent())
                    3 -> setAdapterContent(getFourthContent())
                    4 -> setAdapterContent(getFifthContent())
                    5 -> {
                        questionContainer.show()
                        val fragmentTransaction = childFragmentManager.beginTransaction()
                        var pagerFragment = childFragmentManager.findFragmentByTag(PagerFragment::class.java.simpleName) as PagerFragment?
                        if (pagerFragment == null) {
                            pagerFragment = PagerFragment()
                        }
                        val bundle = Bundle()
                        bundle.putParcelableArrayList(SimpleContent::class.java.simpleName, getSixthContent())
                        pagerFragment.arguments = bundle
                        //AnimationUtils.enterReveal(checkFAB);
                        fragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                        fragmentTransaction.replace(R.id.questionContainer, pagerFragment, PagerFragment::class.java.simpleName)
                        fragmentTransaction.commit()
                        nextFAB.hide()
                    }
                }
            }
            "Object-Oriented Programming Concepts" ->{
                when(  moduleId ) {
                    0 -> setAdapterContent(getOOFirstContent())
                    1 -> setAdapterContent(getOOSecondContent())
                    2 -> setAdapterContent(getOOThirdContent())
                    3 -> setAdapterContent(getOOFourthContent())
                    4 -> setAdapterContent(getOOFifthContent())
                    5 -> setAdapterContent(getOOFifthContent())
                    6 -> {
                        questionContainer.show()
                        val fragmentTransaction = childFragmentManager.beginTransaction()
                        var pagerFragment = childFragmentManager.findFragmentByTag(PagerFragment::class.java.simpleName) as PagerFragment?
                        if (pagerFragment == null) {
                            pagerFragment = PagerFragment()
                        }
                        val bundle = Bundle()
                        bundle.putParcelableArrayList(SimpleContent::class.java.simpleName, getSixthContent())
                        pagerFragment.arguments = bundle
                        //AnimationUtils.enterReveal(checkFAB);
                        fragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                        fragmentTransaction.replace(R.id.questionContainer, pagerFragment, PagerFragment::class.java.simpleName)
                        fragmentTransaction.commit()
                        nextFAB.hide()
                    }
                }
            }

        }

        moduleProgressBar.progress = moduleId + 1

    }



    private fun setAdapterContent( simpleContentList : ArrayList<SimpleContent> ) {
        val breakIntoPoints = breakIntoPoints(simpleContentList)
        var index = 0
        currentContentList.clear()
        currentContentList.add(breakIntoPoints[index])
        contentAdapter = SimpleContentAdapter( currentContentList, this )
        rvModuleContent.adapter = contentAdapter
        nextFAB.setOnClickListener {
            if( index < breakIntoPoints.size - 1 ) {
                contentAdapter.addItem( breakIntoPoints[++index] )
                rvModuleContent.smoothScrollToPosition(currentContentList.size)
            }
            else {
                drawer_layout.openDrawer(nav_view)
            }
        }
        onProgressStatsUpdate(25)
    }

    private fun breakIntoPoints(simpleContentList: ArrayList<SimpleContent>): ArrayList<SimpleContent> {

        val breakPoints = ArrayList<SimpleContent>()
        for( content in simpleContentList ) {
            when( content.contentType ) {
                SimpleContent.content -> {
                    breakPoints.addAll( content.getFormattedContentList() )
                }
                else -> {
                    breakPoints.add( content )
                }
            }
        }
        return breakPoints
    }

}
package com.sortedqueue.programmercreek.v2.ui.module

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.fragments.NewFillBlankFragment
import com.sortedqueue.programmercreek.fragments.TestDragNDropFragment
import com.sortedqueue.programmercreek.v2.base.*
import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import com.sortedqueue.programmercreek.v2.data.model.Chapter
import com.sortedqueue.programmercreek.v2.ui.chapters.SubModulesAdapter
import kotlinx.android.synthetic.main.fragment_new_module.*


class ModuleFragment : BaseModuleFragment(), BaseAdapterClickListener<SimpleContent>, FirebaseDatabaseHandler.ConfirmUserProgram {

    override fun onSuccess(programIndex: ProgramIndex, programTables: java.util.ArrayList<ProgramTable>) {

        childFragmentManager.beginTransaction().apply {

            val bundle = Bundle().apply {
                putParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM, programTables)
                putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, programIndex)
            }

            val fragment : Fragment = if( programTables.size > 6 ) {
                NewFillBlankFragment().apply {
                    arguments = bundle
                    setBundle(bundle)
                }
            }
            else {
                TestDragNDropFragment().apply {
                    arguments = bundle
                    setBundle(bundle)
                }
            }

            setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_up, R.anim.slide_out_down)
            replace(R.id.questionContainer, fragment, NewFillBlankFragment::class.java.simpleName)
            addToBackStack(NewFillBlankFragment::class.java.simpleName)
            commit()
        }
    }

    override fun onError(errorMessage: String) {

    }

    private lateinit var contentAdapter: SimpleContentAdapter
    private lateinit var chapter: Chapter
    private var chaptersList: ArrayList<Chapter> = ArrayList()

    override fun onItemClick(position: Int, item: SimpleContent) {
        if( item.contentType >= SimpleContent.code ) {
            questionContainer.show()
            if( item.contentType == SimpleContent.code ) {
                context?.apply {
                    FirebaseDatabaseHandler(this).compileSharedProgram(item.contentString, this@ModuleFragment)
                }
            }
            else {

                //AnimationUtils.enterReveal(checkFAB);
                childFragmentManager.beginTransaction().apply {
                    val pagerFragment = ModuleQuestionsFragment().apply {
                        arguments = Bundle().apply {
                            putParcelable(SimpleContent::class.java.simpleName, item)
                        }
                    }
                    setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_up, R.anim.slide_out_down)
                    replace(R.id.questionContainer, pagerFragment, ModuleQuestionsFragment::class.java.simpleName)
                    addToBackStack(ModuleQuestionsFragment::class.java.simpleName)
                    commit()
                }
            }


            nextFAB.hide()
        }
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
        rvTracker.adapter = SubModulesAdapter( chaptersList, 0, object : BaseAdapterClickListener<Chapter> {
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
                        val pagerFragment = PagerFragment().apply {
                            arguments = Bundle().apply {
                                putParcelableArrayList(SimpleContent::class.java.simpleName, getSixthContent())
                            }
                        }
                        //AnimationUtils.enterReveal(checkFAB);
                        childFragmentManager.beginTransaction().apply {
                            setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                            replace(R.id.questionContainer, pagerFragment, PagerFragment::class.java.simpleName)
                            commit()
                        }

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
                        val pagerFragment = PagerFragment().apply {
                            arguments = Bundle().apply {
                                putParcelableArrayList(SimpleContent::class.java.simpleName, getSixthContent())
                            }
                        }
                        //AnimationUtils.enterReveal(checkFAB);
                        childFragmentManager.beginTransaction().apply {
                            setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                            replace(R.id.questionContainer, pagerFragment, PagerFragment::class.java.simpleName)
                            commit()
                        }

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

        rvModuleContent.adapter = SimpleContentAdapter( this ).apply {
            removeAll()
            contentAdapter = this
            add(breakIntoPoints[index])
        }
        nextFAB.setOnClickListener {
            if( index < breakIntoPoints.size - 1 ) {
                contentAdapter.add( breakIntoPoints[++index] )
                rvModuleContent.smoothScrollToPosition(contentAdapter.itemCount)
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

    fun onBackPressed(): Boolean {
        return if( childFragmentManager.backStackEntryCount > 0 ) {
            childFragmentManager.popBackStack()
            rvModuleContent.smoothScrollToPosition(contentAdapter.itemCount)
            questionContainer.hide()
            nextFAB.show()
            false
        }
        else true
    }

}
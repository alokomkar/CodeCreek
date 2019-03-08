package com.sortedqueue.programmercreek.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.FillCodeRecyclerAdapter
import com.sortedqueue.programmercreek.adapter.FillOptionsRecyclerAdapter
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.lessons.BitModule
import com.sortedqueue.programmercreek.interfaces.BitModuleNavigationListener
import com.sortedqueue.programmercreek.interfaces.OnBackPressListener
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekAnalytics
import kotlinx.android.synthetic.main.fragment_bit_module.*

import java.util.ArrayList




/**
 * Created by Alok on 29/08/17.
 */

class BitModuleFragment : Fragment(), View.OnClickListener, OnBackPressListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {


    var bitModule: BitModule? = null
    private var lastFirstIndicator = -1 //first - 0, last = 1
    private var navigationListener: BitModuleNavigationListener? = null
    private var codeEditorRecyclerAdapter: CodeEditorRecyclerAdapter? = null
    private var blankString: String? = null
    private var fillCodeAdapter: FillCodeRecyclerAdapter? = null
    private var fillOptionsAdapter: FillOptionsRecyclerAdapter? = null
    private var fillCodeRecyclerAdapters: ArrayList<FillCodeRecyclerAdapter>? = null
    private val TAG = BitModuleFragment::class.java.simpleName

    fun setNavigationListener(navigationListener: BitModuleNavigationListener) {
        this.navigationListener = navigationListener
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bit_module, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CreekAnalytics.logEvent(TAG, bitModule!!.title)
        titleTextView!!.text = bitModule!!.title
        checkFAB!!.visibility = View.GONE
        checkFAB!!.setOnClickListener(this)
        descriptionTextView!!.visibility = View.GONE
        if (bitModule!!.description != null) {
            descriptionTextView!!.visibility = View.VISIBLE
            descriptionTextView!!.text = bitModule!!.description
        }

        slideImageView!!.visibility = View.GONE
        if (bitModule!!.imageUrl != null && bitModule!!.imageUrl.trim { it <= ' ' }.length > 0) {
            val requestOptions = RequestOptions()
            requestOptions.optionalFitCenter()
            requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA)
            requestOptions.placeholder(R.color.colorPrimary)
            requestOptions.fallback(R.color.md_grey_500)
            Glide.with(context!!)
                    .setDefaultRequestOptions(requestOptions)
                    .load(bitModule!!.imageUrl)
                    .into(slideImageView!!)
            slideImageView!!.visibility = View.VISIBLE
        }

        codeRecyclerView!!.visibility = View.GONE
        if (bitModule!!.code != null) {
            codeRecyclerView!!.layoutManager = LinearLayoutManager(context)
            codeEditorRecyclerAdapter = CodeEditorRecyclerAdapter(context!!, AuxilaryUtils.splitProgramIntolines(bitModule!!.code), bitModule!!.programLanguage, true)
            codeRecyclerView!!.adapter = codeEditorRecyclerAdapter
            codeRecyclerView!!.visibility = View.VISIBLE
        }

        outputTextView!!.visibility = View.GONE
        if (bitModule!!.output != null) {
            outputTextView!!.text = bitModule!!.output
            outputTextView!!.visibility = View.VISIBLE
        }

        backImageView!!.setOnClickListener(this)
        nextImageView!!.setOnClickListener(this)
        backImageView!!.visibility = if (lastFirstIndicator == 0) View.GONE else View.VISIBLE
        nextImageView!!.visibility = if (lastFirstIndicator == 1) View.GONE else View.VISIBLE
        fillOptionsRecyclerView!!.visibility = View.GONE
        /*if (bitModule.getTestMode() != null && bitModule.getTestMode().equalsIgnoreCase("random")) {
            constructFillBlanks(codeEditorRecyclerAdapter.getProgramLines());
        }*/
        checkFAB!!.visibility = if (bitModule!!.testMode == null || bitModule!!.testMode.equals("random", ignoreCase = true)) View.GONE else View.VISIBLE
        if (lastFirstIndicator == 1) {
            checkFAB!!.visibility = View.VISIBLE
            checkFAB!!.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_done_all))
        }

    }

    private fun constructFillBlanks(programLines: ArrayList<String>) {
        val fillBlankOptions = ArrayList<String>()
        val fillBlankQuestions = ArrayList<Array<String>>()
        fillCodeRecyclerAdapters = ArrayList<FillCodeRecyclerAdapter>()
        blankString = "________"
        for (programLine in programLines) {
            var modifiedLine = programLine.replace("  ".toRegex(), " ")
            val codeWords = modifiedLine.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (codeWords.size > 1) {
                val randomIndex = ProgramTable.getRandomNumberInRange(0, codeWords.size - 1)
                fillBlankOptions.add(codeWords[randomIndex])
                codeWords[randomIndex] = blankString!!
                fillBlankQuestions.add(codeWords)
                val recyclerView = LayoutInflater.from(context).inflate(R.layout.recyclerview_fill_code, null).findViewById<RecyclerView>(R.id.codeBlanksRecyclerView)
                recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                fillCodeAdapter = FillCodeRecyclerAdapter(codeWords, randomIndex)
                fillCodeRecyclerAdapters!!.add(fillCodeAdapter!!)
                recyclerView.adapter = fillCodeAdapter
                fillCodeLayout!!.addView(recyclerView)
            }

        }

        fillOptionsRecyclerView!!.visibility = View.VISIBLE
        fillOptionsRecyclerView!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
        fillOptionsAdapter = FillOptionsRecyclerAdapter(fillBlankOptions, this)
        fillOptionsRecyclerView!!.adapter = fillOptionsAdapter

    }

    fun setLastFirstIndicator(lastFirstIndicator: Int) {
        this.lastFirstIndicator = lastFirstIndicator
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.checkFAB -> if (lastFirstIndicator == 1) {
                activity!!.onBackPressed()
            } else {
                if (bitModule!!.testMode != null) {
                    when (bitModule!!.testMode.toLowerCase()) {
                        "fill" -> {
                            navigationListener!!.onTestTriggered(bitModule!!.testMode)
                            val fragmentTransaction = childFragmentManager.beginTransaction()
                            fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_down, R.anim.slide_out_up)
                            fragmentTransaction.addToBackStack(null)
                            val bitFillBlankFragment = BitFillBlankFragment()
                            bitFillBlankFragment.setOnBackPressListener(this@BitModuleFragment)
                            val bundle = Bundle()
                            bundle.putParcelable("BitModule", bitModule)
                            bitFillBlankFragment.arguments = bundle
                            fragmentTransaction.replace(R.id.testContainer, bitFillBlankFragment).commit()
                        }
                        "random" -> checkSolution()
                    }
                }
            }
            R.id.nextImageView -> navigationListener!!.onMoveForward()
            R.id.backImageView -> navigationListener!!.onMoveBackward()
        }
    }

    private fun checkSolution() {
        var position = 0
        var correctAnswers = 0
        for (programLine in codeEditorRecyclerAdapter!!.programLines!!) {
            val fillCodeRecyclerAdapter = fillCodeRecyclerAdapters!![position++]
            if (fillCodeRecyclerAdapter.codeLine.trim { it <= ' ' }.replace("\\s+".toRegex(), "") == programLine.trim { it <= ' ' }.replace("\\s+".toRegex(), "")) {
                correctAnswers++
            }
            CommonUtils.displayToast(context!!, "You've got " + correctAnswers + " / " + codeEditorRecyclerAdapter!!.programLines!!.size + " correct")
        }
    }

    override fun onBackPressed() {
        if (childFragmentManager.backStackEntryCount > 0) {
            navigationListener!!.onTestTriggered(null)
            childFragmentManager.popBackStack()
        }
    }

    val isTestLoaded: Boolean
        get() = childFragmentManager.backStackEntryCount > 0

    override fun onItemClick(position: Int) {
        //Check the adapter with blank position and insert the selected text
        val option = fillOptionsAdapter!!.getItem(position)
        for (fillCodeRecyclerAdapter in fillCodeRecyclerAdapters!!) {
            if (fillCodeRecyclerAdapter.blankSpace == blankString) {
                fillCodeRecyclerAdapter.setCode(option)
                break
            }
        }
    }
}

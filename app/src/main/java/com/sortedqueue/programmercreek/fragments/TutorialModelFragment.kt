package com.sortedqueue.programmercreek.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.TutorialModel
import com.sortedqueue.programmercreek.interfaces.TutorialNavigationListener

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok on 15/05/17.
 */

class TutorialModelFragment : Fragment(), View.OnClickListener {

    @BindView(R.id.titleTextView)
    internal var titleTextView: TextView? = null
    @BindView(R.id.subTitleTextView)
    internal var subTitleTextView: TextView? = null
    @BindView(R.id.slideImageView)
    internal var slideImageView: ImageView? = null
    @BindView(R.id.cancelButton)
    internal var cancelButton: Button? = null
    @BindView(R.id.nextButton)
    internal var nextButton: Button? = null
    @BindView(R.id.slideImageLayout)
    internal var slideImageLayout: FrameLayout? = null
    @BindView(R.id.buttonLayout)
    internal var buttonLayout: LinearLayout? = null
    private var tutorialModel: TutorialModel? = null
    private var index = 0
    private var size: Int = 0

    private var tutorialNavigationListener: TutorialNavigationListener? = null

    fun setParameter(parameter: TutorialModel, index: Int, size: Int) {
        this.tutorialModel = parameter
        this.index = index
        this.size = size
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is TutorialNavigationListener) {
            tutorialNavigationListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        tutorialNavigationListener = null
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_tutorial, container, false)
        ButterKnife.bind(this, view)
        titleTextView!!.text = "Step : " + index
        subTitleTextView!!.text = tutorialModel!!.stepDescription
        slideImageView!!.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.splash_logo))
        if (index == 1) {
            cancelButton!!.visibility = View.GONE
            nextButton!!.visibility = View.VISIBLE
            nextButton!!.text = "Next"
            cancelButton!!.text = "Cancel"
        } else if (index > 1 && index < size) {
            cancelButton!!.visibility = View.VISIBLE
            nextButton!!.visibility = View.VISIBLE
            nextButton!!.text = "Next"
            cancelButton!!.text = "Prev"
        } else if (index == size) {
            cancelButton!!.visibility = View.VISIBLE
            nextButton!!.visibility = View.VISIBLE
            nextButton!!.text = "Done"
            cancelButton!!.text = "Prev"
        }
        nextButton!!.setOnClickListener(this)
        cancelButton!!.setOnClickListener(this)
        if (tutorialModel!!.stepImageUrl.trim { it <= ' ' }.length == 0) {
            slideImageLayout!!.visibility = View.GONE
        } else {
            slideImageLayout!!.visibility = View.VISIBLE
            Glide.with(context)
                    .load(tutorialModel!!.stepImageUrl)
                    .asBitmap()
                    .fitCenter()
                    .error(R.color.md_blue_600)
                    .placeholder(R.color.md_blue_600)
                    .into(slideImageView!!)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onClick(v: View) {
        val button = v as Button
        val buttonText = button.text.toString()
        when (buttonText) {
            "Done" -> tutorialNavigationListener!!.onCancelClick()
            "Prev" -> tutorialNavigationListener!!.onPreviousClick()
            "Next" -> tutorialNavigationListener!!.onNextClick()
        }
    }
}

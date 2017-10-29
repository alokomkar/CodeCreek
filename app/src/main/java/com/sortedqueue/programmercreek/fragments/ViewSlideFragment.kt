package com.sortedqueue.programmercreek.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.SlideModel

import butterknife.BindView
import butterknife.ButterKnife
import io.github.kbiakov.codeview.CodeView

/**
 * Created by Alok on 11/04/17.
 */

class ViewSlideFragment : Fragment() {

    @BindView(R.id.titleTextView)
    internal var titleTextView: TextView? = null
    @BindView(R.id.subTitleTextView)
    internal var subTitleTextView: TextView? = null
    @BindView(R.id.slideImageView)
    internal var slideImageView: ImageView? = null
    @BindView(R.id.slideImageLayout)
    internal var slideImageLayout: FrameLayout? = null
    @BindView(R.id.codeView)
    internal var codeView: CodeView? = null
    private var slideModel: SlideModel? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_view_slide, container, false)
        ButterKnife.bind(this, view)
        setupViews()
        return view
    }

    private fun setupViews() {
        titleTextView!!.text = slideModel!!.title
        subTitleTextView!!.text = slideModel!!.subTitle
        Glide.with(context)
                .load(slideModel!!.slideImageUrl)
                .asBitmap()
                .centerCrop()
                .error(R.color.md_blue_600)
                .placeholder(R.color.md_blue_600)
                .into(slideImageView!!)
        if (slideModel!!.code != null && slideModel!!.code.trim { it <= ' ' }.isNotEmpty()) {
            codeView!!.visibility = View.VISIBLE
            codeView!!.setCode(slideModel!!.code)
        } else {
            codeView!!.visibility = View.GONE
        }

    }

    fun setParameter(parameter: SlideModel) {
        this.slideModel = parameter
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}

package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.UserProgramDetails

import java.util.ArrayList




/**
 * Created by Alok on 16/05/17.
 */

class UserProgramRecyclerAdapter(private val context: Context, private val accessSpecifier: String, private val userProgramDetailsArrayList: ArrayList<UserProgramDetails>, private val adapterClickListner: UserProgramClickListener) : RecyclerView.Adapter<UserProgramRecyclerAdapter.ViewHolder>() {

    private val userEmail: String

    private val likeDrawable: Drawable
    private val unlikeDrawable: Drawable

    interface UserProgramClickListener : CustomProgramRecyclerViewAdapter.AdapterClickListner {
        fun onLikeClicked(isLiked: Boolean, position: Int)
        fun onShareClicked(position: Int)
    }

    init {
        this.userEmail = CreekApplication.creekPreferences!!.getSignInAccount()
        this.likeDrawable = ContextCompat.getDrawable(context, R.drawable.ic_star_on)
        this.unlikeDrawable = ContextCompat.getDrawable(context, R.drawable.ic_star_off)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_user_program, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userProgramDetails = userProgramDetailsArrayList[position]
        val programIndex = userProgramDetails.programIndex
        holder.titleTextView!!.text = programIndex.program_Language.toUpperCase() + " : " + programIndex.program_Description
        holder.subTitleTextView!!.text = userProgramDetails.preview
        holder.viewsTextView!!.text = userProgramDetails.views.toString() + " Views"
        holder.likesTextView!!.text = userProgramDetails.likes.toString() + " Likes"

        //holder.likesTextView.setCompoundDrawables( userProgramDetails.isLiked( userEmail ) ? likeDrawable : unlikeDrawable, null, null, null);
        holder.likesTextView!!.setCompoundDrawablesWithIntrinsicBounds(if (userProgramDetails.isLiked(userEmail)) likeDrawable else unlikeDrawable, null, null, null)
        holder.extrasLayout!!.visibility = if (accessSpecifier == "Favorites") View.GONE else View.VISIBLE

    }

    override fun getItemCount(): Int {
        return userProgramDetailsArrayList.size
    }

    fun getItemAtPosition(position: Int): UserProgramDetails {
        return userProgramDetailsArrayList[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        @BindView(R.id.titleTextView)
        internal var titleTextView: TextView? = null
        @BindView(R.id.subTitleTextView)
        internal var subTitleTextView: TextView? = null
        @BindView(R.id.viewsTextView)
        internal var viewsTextView: TextView? = null
        @BindView(R.id.likesTextView)
        internal var likesTextView: TextView? = null
        @BindView(R.id.shareImageView)
        internal var shareImageView: ImageView? = null
        @BindView(R.id.extrasLayout)
        internal var extrasLayout: LinearLayout? = null


        init {
            ButterKnife.bind(this, itemView)
            titleTextView!!.setOnClickListener(this)
            subTitleTextView!!.setOnClickListener(this)
            viewsTextView!!.setOnClickListener(this)
            likesTextView!!.setOnClickListener(this)
            shareImageView!!.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val userProgramDetails = userProgramDetailsArrayList[position]
                if (v.id == R.id.likesTextView) {
                    if (userProgramDetails.isLiked(userEmail)) {
                        userProgramDetails.setLiked(false, userEmail)
                        adapterClickListner.onLikeClicked(false, position)
                    } else {
                        userProgramDetails.setLiked(true, userEmail)
                        adapterClickListner.onLikeClicked(true, position)
                    }

                } else if (v.id == R.id.shareImageView) {
                    adapterClickListner.onShareClicked(position)
                } else {
                    adapterClickListner.onItemClick(position)
                }
            }
        }
    }

}
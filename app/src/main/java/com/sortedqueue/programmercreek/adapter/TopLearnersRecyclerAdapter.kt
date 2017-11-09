package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.Option
import com.bumptech.glide.request.RequestOptions
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.UserRanking

import java.util.ArrayList




/**
 * Created by Alok Omkar on 2017-02-17.
 */
class TopLearnersRecyclerAdapter(private val context: Context, private val userRankings: ArrayList<UserRanking>) : RecyclerView.Adapter<TopLearnersRecyclerAdapter.ViewHolder>() {
    private lateinit var requestOptions: RequestOptions
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        requestOptions = RequestOptions()
        requestOptions.optionalFitCenter()
        requestOptions.placeholder(R.color.md_grey_500)
        requestOptions.fallback(R.color.md_grey_500)

        val adapterView = LayoutInflater.from(context).inflate(R.layout.item_top_learner, parent, false)
        return ViewHolder(adapterView)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val userRanking = userRankings[position]
        holder.levelTextView!!.text = "Level " + (userRanking.reputation / 100).toInt()
        if (userRanking.userFullName != null && userRanking.userFullName.trim { it <= ' ' } != "")
            holder.userNameTextView!!.text = userRanking.userFullName
        else
            holder.userNameTextView!!.text = "Anonymous"
        Glide.with(context).setDefaultRequestOptions(requestOptions).load(userRanking.userPhotoUrl).into(holder.movieGridItemImageView!!)

    }

    override fun getItemCount(): Int {
        return userRankings.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var movieGridItemImageView: ImageView? = view.findViewById(R.id.movieGridItemImageView)

        internal var userNameTextView: TextView? = view.findViewById(R.id.userNameTextView)

        internal var levelTextView: TextView? = view.findViewById(R.id.levelTextView)

        init {

        }
    }
}

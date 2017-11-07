package com.sortedqueue.programmercreek.adapter

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.interfaces.UnlockByInviteInterface
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekPreferences

import java.util.ArrayList




/**
 * Created by Alok Omkar on 2016-12-24.
 */
class CustomProgramRecyclerViewAdapter : RecyclerView.Adapter<CustomProgramRecyclerViewAdapter.ViewHolder> {


    private var mProgramType: String? = null
    private var programLanguage: String? = null
    private var mContext: Context? = null
    private var mProgram_Indexs: ArrayList<ProgramIndex>? = null
    private var mAdapterClickListner: AdapterClickListner? = null
    private var mUnlockByInviteInterface: UnlockByInviteInterface? = null
    private var creekUserStats: CreekUserStats? = null
    private var creekPreferences: CreekPreferences? = null

    private val lastPosition = -1
    private var bottomUpAnimation: Animation? = null
    private var topDownAnimation: Animation? = null
    private var unlockByInviteDrawable: Drawable? = null
    private var lockedDrawable: Drawable? = null

    fun clearAll() {
        if (mProgram_Indexs != null) {
            mProgram_Indexs!!.clear()
        }
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): ProgramIndex {
        return mProgram_Indexs!![position]
    }

    interface AdapterClickListner {
        fun onItemClick(position: Int)
    }

    constructor(context: Context, mProgram_indexs: ArrayList<ProgramIndex>) {
        this.mContext = context
        this.mProgram_Indexs = mProgram_indexs
        this.mAdapterClickListner = context as AdapterClickListner
        this.mUnlockByInviteInterface = context as UnlockByInviteInterface
        this.programLanguage = CreekApplication.creekPreferences!!.programLanguage
        this.creekUserStats = CreekApplication.instance.creekUserStats
        this.creekPreferences = CreekApplication.creekPreferences
        this.unlockByInviteDrawable = ContextCompat.getDrawable(context, android.R.drawable.ic_media_ff)
        this.lockedDrawable = ContextCompat.getDrawable(context, android.R.drawable.ic_lock_lock)
        mProgramType = programLanguage!!.substring(0, 1).toUpperCase()
        bottomUpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.item_up_from_bottom)
        topDownAnimation = AnimationUtils.loadAnimation(mContext, R.anim.item_up_from_bottom)
    }

    constructor(context: Context, mProgram_indexs: ArrayList<ProgramIndex>, adapterClickListner: AdapterClickListner) {
        this.mContext = context
        this.mProgram_Indexs = mProgram_indexs
        this.mAdapterClickListner = adapterClickListner
        this.programLanguage = CreekApplication.creekPreferences!!.programLanguage
        this.creekUserStats = CreekApplication.instance.creekUserStats
        this.creekPreferences = CreekApplication.creekPreferences
        this.unlockByInviteDrawable = ContextCompat.getDrawable(context, android.R.drawable.ic_media_ff)
        this.lockedDrawable = ContextCompat.getDrawable(context, android.R.drawable.ic_lock_lock)
        mProgramType = programLanguage!!.substring(0, 1).toUpperCase()
        bottomUpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.item_up_from_bottom)
        topDownAnimation = AnimationUtils.loadAnimation(mContext, R.anim.item_up_from_bottom)
    }

    fun resetAdapter() {
        this.creekUserStats = CreekApplication.instance.creekUserStats
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adapterView = LayoutInflater.from(parent.context).inflate(R.layout.index_list, parent, false)
        return ViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.programTypeTextView!!.text = mProgramType
        val programIndex = mProgram_Indexs!![position]
        holder.txtViewProgDescription!!.text = programIndex.program_Description
        val program_Index = programIndex.program_index
        var isAvailable = true

        when (programLanguage) {
            "c" -> isAvailable = creekUserStats!!.unlockedCProgramIndexList.contains(program_Index)
            "c++", "cpp" -> isAvailable = creekUserStats!!.unlockedCppProgramIndexList.contains(program_Index)
            "java" -> isAvailable = creekUserStats!!.unlockedJavaProgramIndexList.contains(program_Index)
            "usp" -> isAvailable = creekUserStats!!.unlockedUspProgramIndexList.contains(program_Index)
            "sql" -> isAvailable = creekUserStats!!.unlockedSqlProgramIndexList.contains(program_Index)
        }
        //holder.doneImageView.setVisibility( isAvailable ? View.VISIBLE : View.GONE );
        //holder.quizTextView.setSelected(true);
        /*holder.lockedImageView.setVisibility( isAvailable ? View.GONE : View.VISIBLE );

        if( !isAvailable ) {
            if( creekPreferences.isUnlockedByInvite(program_Index) ) {
                holder.unlockedByInviteImageView.setVisibility(View.VISIBLE);
                holder.lockedImageView.setVisibility(View.GONE);
            }
            else {
                holder.unlockedByInviteImageView.setVisibility(View.GONE);
                holder.lockedImageView.setVisibility(View.VISIBLE);
            }
        }*/

        //Remove this later
        holder.lockedImageView!!.visibility = View.GONE
        /*if( position > lastPosition ) {
            startAnimation(holder.itemView, program_Index * 25);
        }
        lastPosition = position;*/
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder?) {
        super.onViewDetachedFromWindow(holder)
        holder!!.itemView.clearAnimation()
    }

    private fun startAnimation(itemView: View, delay: Int) {

        itemView.alpha = 0.0f
        itemView.animate().setStartDelay(delay.toLong()).setDuration(300).alpha(1.0f)
    }

    override fun getItemCount(): Int {
        return mProgram_Indexs!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        internal var programTypeTextView: TextView= itemView.findViewById(R.id.programTypeTextView)
        internal var txtViewProgDescription: TextView= itemView.findViewById(R.id.txtViewProgDescription)
        internal var lockedImageView: ImageView= itemView.findViewById(R.id.lockedImageView)
        internal var unlockedByInviteImageView: ImageView= itemView.findViewById(R.id.unlockedByInviteImageView)


        init {

            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                val programIndex = mProgram_Indexs!![position]
                if (lockedImageView!!.visibility == View.VISIBLE) {
                    if (unlockedByInviteImageView!!.visibility == View.VISIBLE) {
                        mAdapterClickListner!!.onItemClick(position)
                        return
                    }
                    AuxilaryUtils.displayInviteDialog(mContext!!, R.string.unlock_by_invite, R.string.unlock_by_invite_description, object : UnlockByInviteInterface {
                        override fun onUnlockClick(index: Int) {
                            mUnlockByInviteInterface?.onUnlockClick(programIndex.program_index)
                        }

                        override fun onDismiss() {
                            mUnlockByInviteInterface!!.onDismiss()
                        }
                    })
                    CommonUtils.displaySnackBar(mContext as Activity?, R.string.program_locked)
                    return
                }
                if (unlockedByInviteImageView!!.visibility == View.VISIBLE) {
                    mAdapterClickListner!!.onItemClick(position)
                    return
                }
                mAdapterClickListner!!.onItemClick(position)
            }


        }

        override fun onLongClick(view: View): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                if (mUnlockByInviteInterface != null) {
                    val programIndex = mProgram_Indexs!![position]
                    mUnlockByInviteInterface!!.onUnlockClick(programIndex.program_index)
                }
            }
            return true
        }
    }

}

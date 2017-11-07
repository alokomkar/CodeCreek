package com.sortedqueue.programmercreek.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.LanguageModule
import com.sortedqueue.programmercreek.util.CommonUtils

import java.util.ArrayList




/**
 * Created by Alok Omkar on 2016-12-25.
 */
class ModulesRecyclerViewAdapter(private val context: Context, private val languageModules: ArrayList<LanguageModule>, private val adapterClickListner: CustomProgramRecyclerViewAdapter.AdapterClickListner) : RecyclerView.Adapter<ModulesRecyclerViewAdapter.ViewHolder>() {
    private var creekUserStats: CreekUserStats? = null
    private val programLanguage: String
    private val TAG = ModulesRecyclerViewAdapter::class.java.simpleName

    init {
        this.creekUserStats = CreekApplication.instance.creekUserStats
        this.programLanguage = CreekApplication.creekPreferences!!.programLanguage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_module, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val languageModule = languageModules[position]
        holder.moduleNameTextView!!.text = languageModule.moduleName
        holder.moduleDescriptionTextView!!.text = languageModule.moduleDescription
        /*boolean isLocked = true;
        switch ( programLanguage ) {
            case "c" :
                isLocked = !(creekUserStats.getUnlockedCLanguageModuleIdList().contains(languageModule.getModuleId()));
                break;
            case "c++" :
            case "cpp" :
                isLocked = !(creekUserStats.getUnlockedCppLanguageModuleIdList().contains(languageModule.getModuleId()));
                break;
            case "java" :
                isLocked = !(creekUserStats.getUnlockedJavaLanguageModuleIdList().contains(languageModule.getModuleId()));
                break;
            case "sql" :
                isLocked = !(creekUserStats.getUnlockedSqlLanguageModuleIdList().contains(languageModule.getModuleId()));
                break;
        }*/

        //holder.lockedImageView.setVisibility(isLocked ? View.VISIBLE : View.INVISIBLE);
        holder.lockedImageView!!.visibility = View.INVISIBLE
        //startAnimation(holder.itemView, position * 250 );
    }

    private fun startAnimation(itemView: View, delay: Int) {
        itemView.alpha = 0.0f
        itemView.animate().setStartDelay(delay.toLong()).setDuration(300).alpha(1.0f)
    }

    override fun getItemCount(): Int {
        return languageModules.size
    }

    fun resetAdapter() {
        this.creekUserStats = CreekApplication.instance.creekUserStats
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var moduleNameTextView: TextView = itemView.findViewById(R.id.moduleNameTextView)
        var moduleDescriptionTextView: TextView = itemView.findViewById(R.id.moduleDescriptionTextView)
        var lockedImageView: ImageView = itemView.findViewById(R.id.lockedImageView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                if (lockedImageView!!.visibility == View.VISIBLE) {
                    CommonUtils.displaySnackBar(context as Activity, R.string.module_locked)
                    return
                }
            adapterClickListner.onItemClick(position)
        }
    }
}

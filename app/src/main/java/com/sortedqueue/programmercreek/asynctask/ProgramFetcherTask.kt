package com.sortedqueue.programmercreek.asynctask

import android.content.Context
import android.os.AsyncTask

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener
import com.sortedqueue.programmercreek.util.CommonUtils

import java.util.ArrayList


class ProgramFetcherTask : AsyncTask<Void, Void, ArrayList<ProgramTable>> {

    internal var mContext: Context? = null
    internal var mProgramIndex: Int = 0
    internal var mUiProgramFetcherListener: UIProgramFetcherListener? = null
    internal var mProgram_TableList: ArrayList<ProgramTable> ?= null

    constructor(context: Context, index: Int) {
        this.mContext = context
        this.mProgramIndex = index
        this.mUiProgramFetcherListener = mContext as UIProgramFetcherListener?
    }

    constructor(context: Context, uiProgramFetcherListener: UIProgramFetcherListener, index: Int) {
        this.mContext = context
        this.mProgramIndex = index
        this.mUiProgramFetcherListener = uiProgramFetcherListener
    }

    override fun doInBackground(vararg params: Void): ArrayList<ProgramTable> {
        mProgram_TableList = FirebaseDatabaseHandler(mContext!!).getProgramTables(mProgramIndex)
        return mProgram_TableList!!
    }

    override fun onPreExecute() {
        super.onPreExecute()
        CommonUtils.displayProgressDialog(mContext, "Initializing Program, Please Wait...")
    }

    override fun onPostExecute(result: ArrayList<ProgramTable>) {
        super.onPostExecute(result)
        if (result.size > 0) {
            CommonUtils.dismissProgressDialog()
            if (mUiProgramFetcherListener != null) {
                mUiProgramFetcherListener!!.updateUI(result)
            }
        } else {
            FirebaseDatabaseHandler(mContext!!).getProgramTablesInBackground(mProgramIndex, object : FirebaseDatabaseHandler.GetProgramTablesListener {
                override fun onSuccess(programTables: ArrayList<ProgramTable>) {
                    mUiProgramFetcherListener!!.updateUI(programTables)
                    CommonUtils.dismissProgressDialog()
                }

                override fun onError(databaseError: DatabaseError?) {
                    CommonUtils.dismissProgressDialog()
                }
            })
        }

    }


}

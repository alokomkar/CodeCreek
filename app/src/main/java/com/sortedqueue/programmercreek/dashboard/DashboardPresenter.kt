package com.sortedqueue.programmercreek.dashboard

import android.content.Context
import android.util.Log
import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.database.CreekUserDB
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekPreferences

/**
 * Created by Alok on 30/10/17.
 */
class DashboardPresenter( val context: Context, val dashboardView: DashboardView ) {

    private var creekPreferences : CreekPreferences = CreekApplication.getPreferences()

    init {
        checkForDBUpdates()
    }

    private val TAG: String? = DashboardPresenter::class.java.simpleName

    private fun checkForDBUpdates() {
        CommonUtils.displayProgressDialog(context, "Checking for updates")
        FirebaseDatabaseHandler(context).readCreekUserDB(object : FirebaseDatabaseHandler.GetCreekUserDBListener {
            override fun onSuccess(creekUserDB: CreekUserDB) {
                CommonUtils.dismissProgressDialog()
            }

            override fun onError(databaseError: DatabaseError) {
                Log.d(TAG, databaseError.message)
                CommonUtils.dismissProgressDialog()
            }
        })
        if (!creekPreferences.isPremiumUser) {
            FirebaseDatabaseHandler(context).verifyPurchase(object : FirebaseDatabaseHandler.AnjVerifyPurchaseListener {
                override fun onSuccess(purchase: com.sortedqueue.programmercreek.billing.anjlab.TransactionDetails) {

                }

                override fun onError(e: Exception?) {

                }
            })
        }

    }
}
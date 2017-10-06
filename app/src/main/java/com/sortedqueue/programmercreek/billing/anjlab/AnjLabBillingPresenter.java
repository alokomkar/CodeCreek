package com.sortedqueue.programmercreek.billing.anjlab;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alok on 13/09/17.
 */

public class AnjLabBillingPresenter {

    private Activity activity;
    private BillingProcessor billingProcessor;

    // SKUs for our products: the premium upgrade (non-consumable) and gas (consumable)
    private static final String SKU_PREMIUM = "com.sortedqueue.programmercreek.adfreepremium";

    public AnjLabBillingPresenter(Activity activity) {
        this.activity = activity;
        setupInAppBilling();
    }

    public void purchasePremiumItem() {
        if (!readyToPurchase) {
            complain("Billing not initialized.");
            return;
        }
        String payload = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CreekApplication.getCreekPreferences().setUserId(payload);
        billingProcessor.purchase(activity, SKU_PREMIUM, payload);
    }

    public BillingProcessor getBillingProcessor() {
        return billingProcessor;
    }

    private boolean readyToPurchase;
    private String TAG = AnjLabBillingPresenter.class.getSimpleName();
    
    private BillingProcessor.IBillingHandler billingHandler = new BillingProcessor.IBillingHandler() {
        @Override
        public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
            Log.d(TAG, "onProductPurchased: " + productId);
            try {
                CreekAnalytics.logEvent(TAG, "onProductPurchased");
                CreekAnalytics.logEvent(TAG, new JSONObject(new Gson().toJson(details)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new FirebaseDatabaseHandler(activity).updatePurchasePayload(details);
            alert("Thank you for upgrading to premium, restart app for effects to take place." );
            updateTextViews();
        }
        @Override
        public void onBillingError(int errorCode, @Nullable Throwable error) {
            CreekAnalytics.logEvent(TAG, "onBillingError");
            if( error != null && error.getMessage() != null ) {
                CreekAnalytics.logEvent(TAG, error.getMessage());
                complain("Error occurred while purchase : " + error.getMessage());
                error.printStackTrace();
            }
        }
        @Override
        public void onBillingInitialized() {
            readyToPurchase = true;
            CreekAnalytics.logEvent(TAG, "onBillingInitialized");
            updateTextViews();
        }
        @Override
        public void onPurchaseHistoryRestored() {
            for(String sku : billingProcessor.listOwnedProducts())
                Log.d(TAG, "Owned Managed Product: " + sku);
            for(String sku : billingProcessor.listOwnedSubscriptions())
                Log.d(TAG, "Owned Subscription: " + sku);
            updateTextViews();
        }
    };

    private void updateTextViews() {

    }

    private void setupInAppBilling() {

        if(!BillingProcessor.isIabServiceAvailable(activity)) {
            CommonUtils.displayToast(activity, R.string.upgrade_google_play);
            return;
        }
        String LICENSE_KEY = activity.getString(R.string.public_key);
        String MERCHANT_ID = activity.getString(R.string.merchant_id);
        billingProcessor = new BillingProcessor(activity, LICENSE_KEY, MERCHANT_ID, billingHandler);
    }

    private void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        CreekAnalytics.logEvent(TAG, "complain : " + message);
        alert("Error: " + message);
    }

    private void alert(String message) {
        CreekAnalytics.logEvent(TAG, "alert : " + message);
        AlertDialog.Builder bld = new AlertDialog.Builder(activity);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }
}

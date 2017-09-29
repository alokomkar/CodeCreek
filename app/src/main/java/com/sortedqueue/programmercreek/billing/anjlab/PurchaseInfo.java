package com.sortedqueue.programmercreek.billing.anjlab;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.anjlab.android.iab.v3.Constants;
import com.anjlab.android.iab.v3.PurchaseState;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Alok on 29/09/17.
 */

public class PurchaseInfo implements Parcelable {
    private static final String LOG_TAG = "iabv3.purchaseInfo";

    public  String responseData;
    public  String signature;
    public  PurchaseData purchaseData;

    public PurchaseInfo() {

    }

    public PurchaseInfo(String responseData, String signature)
    {
        this.responseData = responseData;
        this.signature = signature;
        this.purchaseData = parseResponseDataImpl();
    }

    /**
     * @deprecated don't call it directly, use {@see purchaseData} instead.
     */
    @Deprecated
    public PurchaseData parseResponseData()
    {
        return parseResponseDataImpl();
    }

    PurchaseData parseResponseDataImpl()
    {
        try
        {
            JSONObject json = new JSONObject(responseData);
            PurchaseData data = new PurchaseData();
            data.orderId = json.optString(Constants.RESPONSE_ORDER_ID);
            data.packageName = json.optString(Constants.RESPONSE_PACKAGE_NAME);
            data.productId = json.optString(Constants.RESPONSE_PRODUCT_ID);
            long purchaseTimeMillis = json.optLong(Constants.RESPONSE_PURCHASE_TIME, 0);
            data.purchaseTime = purchaseTimeMillis != 0 ? new Date(purchaseTimeMillis) : null;
            data.purchaseState = PurchaseState.values()[json.optInt(Constants.RESPONSE_PURCHASE_STATE, 1)];
            data.developerPayload = json.optString(Constants.RESPONSE_DEVELOPER_PAYLOAD);
            data.purchaseToken = json.getString(Constants.RESPONSE_PURCHASE_TOKEN);
            data.autoRenewing = json.optBoolean(Constants.RESPONSE_AUTO_RENEWING);
            return data;
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "Failed to parse response data", e);
            return null;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.responseData);
        dest.writeString(this.signature);
        dest.writeParcelable(this.purchaseData, flags);
    }

    protected PurchaseInfo(Parcel in) {
        this.responseData = in.readString();
        this.signature = in.readString();
        this.purchaseData = in.readParcelable(PurchaseData.class.getClassLoader());
    }

    public static final Parcelable.Creator<PurchaseInfo> CREATOR = new Parcelable.Creator<PurchaseInfo>() {
        @Override
        public PurchaseInfo createFromParcel(Parcel source) {
            return new PurchaseInfo(source);
        }

        @Override
        public PurchaseInfo[] newArray(int size) {
            return new PurchaseInfo[size];
        }
    };
}

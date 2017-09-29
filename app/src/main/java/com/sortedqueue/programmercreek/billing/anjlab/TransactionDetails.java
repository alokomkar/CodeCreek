package com.sortedqueue.programmercreek.billing.anjlab;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.Date;
import java.util.Locale;

/**
 * Created by Alok on 29/09/17.
 */

public class TransactionDetails implements Parcelable {

    /**
     * @deprecated use {@see purchaseInfo.purchaseData.productId}} instead.
     */
    @Deprecated
    public String productId;

    /**
     * @deprecated use {@see purchaseInfo.purchaseData.orderId}} instead.
     */
    @Deprecated
    public String orderId;

    /**
     * @deprecated use {@see purchaseInfo.purchaseData.purchaseToken}} instead.
     */
    @Deprecated
    public String purchaseToken;

    /**
     * @deprecated use {@see purchaseInfo.purchaseData.purchaseTime}} instead.
     */
    @Deprecated
    public Date purchaseTime;

    public PurchaseInfo purchaseInfo;

    public TransactionDetails() {

    }

    public TransactionDetails(PurchaseInfo info)
    {
        purchaseInfo = info;
        productId = purchaseInfo.purchaseData.productId;
        orderId = purchaseInfo.purchaseData.orderId;
        purchaseToken = purchaseInfo.purchaseData.purchaseToken;
        purchaseTime = purchaseInfo.purchaseData.purchaseTime;
    }

    @Override
    public String toString()
    {
        return String.format(Locale.US, "%s purchased at %s(%s). Token: %s, Signature: %s",
                productId,
                purchaseTime,
                orderId,
                purchaseToken,
                purchaseInfo.signature);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        com.anjlab.android.iab.v3.TransactionDetails details = (com.anjlab.android.iab.v3.TransactionDetails) o;

        return !(orderId != null ? !orderId.equals(details.orderId) : details.orderId != null);
    }

    @Override
    public int hashCode()
    {
        return orderId != null ? orderId.hashCode() : 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.orderId);
        dest.writeString(this.purchaseToken);
        dest.writeLong(this.purchaseTime != null ? this.purchaseTime.getTime() : -1);
        dest.writeParcelable(this.purchaseInfo, flags);
    }

    protected TransactionDetails(Parcel in) {
        this.productId = in.readString();
        this.orderId = in.readString();
        this.purchaseToken = in.readString();
        long tmpPurchaseTime = in.readLong();
        this.purchaseTime = tmpPurchaseTime == -1 ? null : new Date(tmpPurchaseTime);
        this.purchaseInfo = in.readParcelable(PurchaseInfo.class.getClassLoader());
    }

    public static final Creator<TransactionDetails> CREATOR = new Creator<TransactionDetails>() {
        @Override
        public TransactionDetails createFromParcel(Parcel source) {
            return new TransactionDetails(source);
        }

        @Override
        public TransactionDetails[] newArray(int size) {
            return new TransactionDetails[size];
        }
    };
}

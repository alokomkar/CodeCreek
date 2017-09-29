package com.sortedqueue.programmercreek.billing.anjlab;

import android.os.Parcel;
import android.os.Parcelable;

import com.anjlab.android.iab.v3.PurchaseState;

import java.util.Date;

/**
 * Created by Alok on 29/09/17.
 */

public class PurchaseData implements Parcelable {
    public String orderId;
    public String packageName;
    public String productId;
    public Date purchaseTime;
    public PurchaseState purchaseState;
    public String developerPayload;
    public String purchaseToken;
    public boolean autoRenewing;





    public PurchaseData()
    {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.packageName);
        dest.writeString(this.productId);
        dest.writeLong(this.purchaseTime != null ? this.purchaseTime.getTime() : -1);
        dest.writeInt(this.purchaseState == null ? -1 : this.purchaseState.ordinal());
        dest.writeString(this.developerPayload);
        dest.writeString(this.purchaseToken);
        dest.writeByte(this.autoRenewing ? (byte) 1 : (byte) 0);
    }

    protected PurchaseData(Parcel in) {
        this.orderId = in.readString();
        this.packageName = in.readString();
        this.productId = in.readString();
        long tmpPurchaseTime = in.readLong();
        this.purchaseTime = tmpPurchaseTime == -1 ? null : new Date(tmpPurchaseTime);
        int tmpPurchaseState = in.readInt();
        this.purchaseState = tmpPurchaseState == -1 ? null : PurchaseState.values()[tmpPurchaseState];
        this.developerPayload = in.readString();
        this.purchaseToken = in.readString();
        this.autoRenewing = in.readByte() != 0;
    }

    public static final Parcelable.Creator<PurchaseData> CREATOR = new Parcelable.Creator<PurchaseData>() {
        @Override
        public PurchaseData createFromParcel(Parcel source) {
            return new PurchaseData(source);
        }

        @Override
        public PurchaseData[] newArray(int size) {
            return new PurchaseData[size];
        }
    };
}

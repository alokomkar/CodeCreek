package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alok Omkar on 2017-03-08.
 */

public class OptionModel implements Parcelable {

    private int optionId;
    private String option;

    private boolean isSelected;

    public OptionModel(int optionId, String option) {
        this.optionId = optionId;
        this.option = option;
    }

    public OptionModel() {
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.optionId);
        dest.writeString(this.option);
    }

    protected OptionModel(Parcel in) {
        this.optionId = in.readInt();
        this.option = in.readString();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public static final Parcelable.Creator<OptionModel> CREATOR = new Parcelable.Creator<OptionModel>() {
        @Override
        public OptionModel createFromParcel(Parcel source) {
            return new OptionModel(source);
        }

        @Override
        public OptionModel[] newArray(int size) {
            return new OptionModel[size];
        }
    };
}

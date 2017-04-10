package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

/**
 * Created by Alok on 10/04/17.
 */

@RushTableAnnotation
public class SlideModel extends RushObject implements Parcelable {

    private String slidePushId;
    private String code;
    private String title;
    private String subTitle;
    private String slideImageUrl;

    public SlideModel(String slidePushId, String code, String title, String subTitle) {
        this.slidePushId = slidePushId;
        this.code = code;
        this.title = title;
        this.subTitle = subTitle;
    }

    public SlideModel(String slidePushId, String code, String title, String subTitle, String slideImageUrl) {
        this.slidePushId = slidePushId;
        this.code = code;
        this.title = title;
        this.subTitle = subTitle;
        this.slideImageUrl = slideImageUrl;
    }

    public SlideModel() {
    }

    public String getSlidePushId() {
        return slidePushId;
    }

    public void setSlidePushId(String slidePushId) {
        this.slidePushId = slidePushId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSlideImageUrl() {
        return slideImageUrl;
    }

    public void setSlideImageUrl(String slideImageUrl) {
        this.slideImageUrl = slideImageUrl;
    }

    @Override
    public String toString() {
        return "SlideModel{" +
                "slidePushId='" + slidePushId + '\'' +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SlideModel that = (SlideModel) o;

        if (slidePushId != null ? !slidePushId.equals(that.slidePushId) : that.slidePushId != null)
            return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (subTitle != null ? !subTitle.equals(that.subTitle) : that.subTitle != null)
            return false;
        return slideImageUrl != null ? slideImageUrl.equals(that.slideImageUrl) : that.slideImageUrl == null;

    }

    @Override
    public int hashCode() {
        int result = slidePushId != null ? slidePushId.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (subTitle != null ? subTitle.hashCode() : 0);
        result = 31 * result + (slideImageUrl != null ? slideImageUrl.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.slidePushId);
        dest.writeString(this.code);
        dest.writeString(this.title);
        dest.writeString(this.subTitle);
        dest.writeString(this.slideImageUrl);
    }

    protected SlideModel(Parcel in) {
        this.slidePushId = in.readString();
        this.code = in.readString();
        this.title = in.readString();
        this.subTitle = in.readString();
        this.slideImageUrl = in.readString();
    }

    public static final Creator<SlideModel> CREATOR = new Creator<SlideModel>() {
        @Override
        public SlideModel createFromParcel(Parcel source) {
            return new SlideModel(source);
        }

        @Override
        public SlideModel[] newArray(int size) {
            return new SlideModel[size];
        }
    };
}

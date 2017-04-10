package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

import co.uk.rushorm.core.RushObject;

/**
 * Created by Alok on 10/04/17.
 */

public class PresentationModel extends RushObject implements Parcelable {

    private String presentationPushId;
    private String presenterName;
    private String presentationImage;
    private HashMap<String, Integer> tagList;
    private ArrayList<SlideModel> slideModelArrayList;


    public PresentationModel(String presentationPushId, String presenterName, HashMap<String, Integer> tagList, ArrayList<SlideModel> slideModelArrayList) {
        this.presentationPushId = presentationPushId;
        this.presenterName = presenterName;
        this.tagList = tagList;
        this.slideModelArrayList = slideModelArrayList;
    }

    public PresentationModel(String presentationPushId, String presenterName, String presentationImage, HashMap<String, Integer> tagList, ArrayList<SlideModel> slideModelArrayList) {
        this.presentationPushId = presentationPushId;
        this.presenterName = presenterName;
        this.presentationImage = presentationImage;
        this.tagList = tagList;
        this.slideModelArrayList = slideModelArrayList;
    }

    public PresentationModel() {
    }

    public String getPresentationPushId() {
        return presentationPushId;
    }

    public void setPresentationPushId(String presentationPushId) {
        this.presentationPushId = presentationPushId;
    }

    public String getPresenterName() {
        return presenterName;
    }

    public void setPresenterName(String presenterName) {
        this.presenterName = presenterName;
    }

    public HashMap<String, Integer> getTagList() {
        return tagList;
    }

    public void setTagList(HashMap<String, Integer> tagList) {
        this.tagList = tagList;
    }

    public ArrayList<SlideModel> getSlideModelArrayList() {
        return slideModelArrayList;
    }

    public void setSlideModelArrayList(ArrayList<SlideModel> slideModelArrayList) {
        this.slideModelArrayList = slideModelArrayList;
    }

    public String getPresentationImage() {
        return presentationImage;
    }

    public void setPresentationImage(String presentationImage) {
        this.presentationImage = presentationImage;
    }

    @Override
    public String toString() {
        return "PresentationModel{" +
                "presentationPushId='" + presentationPushId + '\'' +
                ", presenterName='" + presenterName + '\'' +
                ", tagList=" + tagList +
                ", slideModelArrayList=" + slideModelArrayList +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PresentationModel that = (PresentationModel) o;

        if (presentationPushId != null ? !presentationPushId.equals(that.presentationPushId) : that.presentationPushId != null)
            return false;
        if (presenterName != null ? !presenterName.equals(that.presenterName) : that.presenterName != null)
            return false;
        if (tagList != null ? !tagList.equals(that.tagList) : that.tagList != null) return false;
        return slideModelArrayList != null ? slideModelArrayList.equals(that.slideModelArrayList) : that.slideModelArrayList == null;

    }

    @Override
    public int hashCode() {
        int result = presentationPushId != null ? presentationPushId.hashCode() : 0;
        result = 31 * result + (presenterName != null ? presenterName.hashCode() : 0);
        result = 31 * result + (tagList != null ? tagList.hashCode() : 0);
        result = 31 * result + (slideModelArrayList != null ? slideModelArrayList.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.presentationPushId);
        dest.writeString(this.presenterName);
        dest.writeString(this.presentationImage);
        dest.writeSerializable(this.tagList);
        dest.writeTypedList(this.slideModelArrayList);
    }

    protected PresentationModel(Parcel in) {
        this.presentationPushId = in.readString();
        this.presenterName = in.readString();
        this.presentationImage = in.readString();
        this.tagList = (HashMap<String, Integer>) in.readSerializable();
        this.slideModelArrayList = in.createTypedArrayList(SlideModel.CREATOR);
    }

    public static final Creator<PresentationModel> CREATOR = new Creator<PresentationModel>() {
        @Override
        public PresentationModel createFromParcel(Parcel source) {
            return new PresentationModel(source);
        }

        @Override
        public PresentationModel[] newArray(int size) {
            return new PresentationModel[size];
        }
    };
}

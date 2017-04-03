package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

import static com.sortedqueue.programmercreek.constants.AlgorithmConstants.CONTENT_CODE;

/**
 * Created by Alok Omkar on 2017-03-15.
 */

@RushTableAnnotation
public class Algorithm extends RushObject implements Parcelable {

    private AlgorithmsIndex algorithmsIndex;
    private ArrayList<AlgorithmContent> algorithmContentArrayList;

    public Algorithm(AlgorithmsIndex algorithmsIndex, ArrayList<AlgorithmContent> algorithmContentArrayList) {
        this.algorithmsIndex = algorithmsIndex;
        this.algorithmContentArrayList = algorithmContentArrayList;
    }

    public Algorithm() {
    }

    public AlgorithmsIndex getAlgorithmsIndex() {
        return algorithmsIndex;
    }

    public void setAlgorithmsIndex(AlgorithmsIndex algorithmsIndex) {
        this.algorithmsIndex = algorithmsIndex;
    }

    public ArrayList<AlgorithmContent> getAlgorithmContentArrayList() {
        return algorithmContentArrayList;
    }

    public void setAlgorithmContentArrayList(ArrayList<AlgorithmContent> algorithmContentArrayList) {
        this.algorithmContentArrayList = algorithmContentArrayList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Algorithm algorithm = (Algorithm) o;

        if (algorithmsIndex != null ? !algorithmsIndex.equals(algorithm.algorithmsIndex) : algorithm.algorithmsIndex != null)
            return false;
        return algorithmContentArrayList != null ? algorithmContentArrayList.equals(algorithm.algorithmContentArrayList) : algorithm.algorithmContentArrayList == null;

    }

    @Override
    public int hashCode() {
        int result = algorithmsIndex != null ? algorithmsIndex.hashCode() : 0;
        result = 31 * result + (algorithmContentArrayList != null ? algorithmContentArrayList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Algorithm{" +
                "algorithmsIndex=" + algorithmsIndex +
                ", algorithmContentArrayList=" + algorithmContentArrayList +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.algorithmsIndex, flags);
        dest.writeTypedList(this.algorithmContentArrayList);
    }

    protected Algorithm(Parcel in) {
        this.algorithmsIndex = in.readParcelable(AlgorithmsIndex.class.getClassLoader());
        this.algorithmContentArrayList = in.createTypedArrayList(AlgorithmContent.CREATOR);
    }

    public static final Parcelable.Creator<Algorithm> CREATOR = new Parcelable.Creator<Algorithm>() {
        @Override
        public Algorithm createFromParcel(Parcel source) {
            return new Algorithm(source);
        }

        @Override
        public Algorithm[] newArray(int size) {
            return new Algorithm[size];
        }
    };


    public String toAlgorithmString() {
        String algorithmString = "/**** Title : " + getAlgorithmsIndex().getProgramTitle() + "****/\n\n";
        for( AlgorithmContent algorithmContent : algorithmContentArrayList ) {
            if( algorithmContent.getContentType() == CONTENT_CODE ) {
                algorithmString += algorithmContent.getProgramCode() + "\n\n";
            }
        }
        return algorithmString;
    }
}

package com.sortedqueue.programmercreek.database;

import java.util.ArrayList;

/**
 * Created by Alok on 13/04/17.
 */

public class TagModel {

    private ArrayList<String> tagArrayList;

    public TagModel(ArrayList<String> tagArrayList) {
        this.tagArrayList = tagArrayList;
    }

    public TagModel() {
    }

    public ArrayList<String> getTagArrayList() {
        if( tagArrayList == null ) {
            tagArrayList = new ArrayList<>();
        }
        return tagArrayList;
    }

    public void setTagArrayList(ArrayList<String> tagArrayList) {
        this.tagArrayList = tagArrayList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagModel tagModel = (TagModel) o;

        return tagArrayList != null ? tagArrayList.equals(tagModel.tagArrayList) : tagModel.tagArrayList == null;

    }

    @Override
    public int hashCode() {
        return tagArrayList != null ? tagArrayList.hashCode() : 0;
    }
}

package com.sortedqueue.programmercreek.database;

/**
 * Created by Alok on 15/05/17.
 */

public class TutorialModel {

    private String stepDescription;
    private String stepImageUrl;

    public TutorialModel(String stepDescription, String stepImageUrl) {
        this.stepDescription = stepDescription;
        this.stepImageUrl = stepImageUrl;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public String getStepImageUrl() {
        return stepImageUrl;
    }

    public void setStepImageUrl(String stepImageUrl) {
        this.stepImageUrl = stepImageUrl;
    }

}

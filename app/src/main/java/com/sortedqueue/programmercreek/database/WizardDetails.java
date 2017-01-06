package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import co.uk.rushorm.core.RushObject;

/**
 * Created by Alok on 05/01/17.
 */

public class WizardDetails extends RushObject implements Parcelable {

    private int wizardIndex;
    private int wizardType;
    private String wizardUrl;
    private String syntaxId;
    private String programLanguage;
    private int programTestType; //match, test, quiz

    public static final int TYPE_WIKI = 1;
    public static final int TYPE_PROGRAM_INDEX = 2;
    public static final int TYPE_LANGUAGE_MODULE = 3;
    public static final int TYPE_SYNTAX_MODULE = 3;

    public WizardDetails() {
    }

    public WizardDetails( int wizardIndex, int wizardType, String wizardUrl, String programLanguage ) {
        this.wizardIndex = wizardIndex;
        this.wizardType = wizardType;
        this.wizardUrl = wizardUrl;
        this.programLanguage = programLanguage;
    }

    public String getSyntaxId() {
        return syntaxId;
    }

    public void setSyntaxId(String syntaxId) {
        this.syntaxId = syntaxId;
    }

    public int getWizardIndex() {
        return wizardIndex;
    }

    public void setWizardIndex(int wizardIndex) {
        this.wizardIndex = wizardIndex;
    }

    public int getWizardType() {
        return wizardType;
    }

    public void setWizardType(int wizardType) {
        this.wizardType = wizardType;
    }

    public String getWizardUrl() {
        return wizardUrl;
    }

    public void setWizardUrl(String wizardUrl) {
        this.wizardUrl = wizardUrl;
    }

    public String getProgramLanguage() {
        return programLanguage;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    public int getProgramTestType() {
        return programTestType;
    }

    public void setProgramTestType(int programTestType) {
        this.programTestType = programTestType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.wizardIndex);
        dest.writeInt(this.wizardType);
        dest.writeString(this.wizardUrl);
        dest.writeString(this.programLanguage);
    }

    protected WizardDetails(Parcel in) {
        this.wizardIndex = in.readInt();
        this.wizardType = in.readInt();
        this.wizardUrl = in.readString();
        this.programLanguage = in.readString();
    }

    public static final Parcelable.Creator<WizardDetails> CREATOR = new Parcelable.Creator<WizardDetails>() {
        @Override
        public WizardDetails createFromParcel(Parcel source) {
            return new WizardDetails(source);
        }

        @Override
        public WizardDetails[] newArray(int size) {
            return new WizardDetails[size];
        }
    };
}

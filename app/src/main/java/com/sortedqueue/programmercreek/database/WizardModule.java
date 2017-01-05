package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushList;

/**
 * Created by Alok on 05/01/17.
 */

public class WizardModule extends RushObject implements Parcelable {

    private String program_Language;
    private String wizardModuleId;
    private String wizardName;

    @RushList(classType = WizardDetails.class)
    private ArrayList<WizardDetails> wizardModules = new ArrayList<>();

    public WizardModule() {
    }

    public WizardModule(String program_Language, String wizardModuleId, String wizardName, ArrayList<WizardDetails> wizardModules) {
        this.program_Language = program_Language;
        this.wizardModuleId = wizardModuleId;
        this.wizardName = wizardName;
        this.wizardModules = wizardModules;
    }

    public String getProgram_Language() {
        return program_Language;
    }

    public void setProgram_Language(String program_Language) {
        this.program_Language = program_Language;
    }

    public String getWizardModuleId() {
        return wizardModuleId;
    }

    public void setWizardModuleId(String wizardModuleId) {
        this.wizardModuleId = wizardModuleId;
    }

    public String getWizardName() {
        return wizardName;
    }

    public void setWizardName(String wizardName) {
        this.wizardName = wizardName;
    }

    public ArrayList<WizardDetails> getWizardModules() {
        return wizardModules;
    }

    public void setWizardModules(ArrayList<WizardDetails> wizardModules) {
        this.wizardModules = wizardModules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WizardModule that = (WizardModule) o;

        if (program_Language != null ? !program_Language.equals(that.program_Language) : that.program_Language != null)
            return false;
        if (wizardModuleId != null ? !wizardModuleId.equals(that.wizardModuleId) : that.wizardModuleId != null)
            return false;
        if (wizardName != null ? !wizardName.equals(that.wizardName) : that.wizardName != null)
            return false;
        return wizardModules != null ? wizardModules.equals(that.wizardModules) : that.wizardModules == null;

    }

    @Override
    public int hashCode() {
        int result = program_Language != null ? program_Language.hashCode() : 0;
        result = 31 * result + (wizardModuleId != null ? wizardModuleId.hashCode() : 0);
        result = 31 * result + (wizardName != null ? wizardName.hashCode() : 0);
        result = 31 * result + (wizardModules != null ? wizardModules.hashCode() : 0);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.program_Language);
        dest.writeString(this.wizardModuleId);
        dest.writeString(this.wizardName);
        dest.writeTypedList(this.wizardModules);
    }

    protected WizardModule(Parcel in) {
        this.program_Language = in.readString();
        this.wizardModuleId = in.readString();
        this.wizardName = in.readString();
        this.wizardModules = in.createTypedArrayList(WizardDetails.CREATOR);
    }

    public static final Parcelable.Creator<WizardModule> CREATOR = new Parcelable.Creator<WizardModule>() {
        @Override
        public WizardModule createFromParcel(Parcel source) {
            return new WizardModule(source);
        }

        @Override
        public WizardModule[] newArray(int size) {
            return new WizardModule[size];
        }
    };
}

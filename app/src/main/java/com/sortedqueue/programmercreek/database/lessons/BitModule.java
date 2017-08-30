package com.sortedqueue.programmercreek.database.lessons;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alok on 28/08/17.
 */

public class BitModule implements Parcelable {

    private String moduleId;
    private String title;
    private String description;
    private String code;
    private String output;
    private String testMode;
    private String programLanguage;
    private String imageUrl;

    public BitModule(String moduleId, String programLanguage, String title, String description, String code, String testMode) {
        this.moduleId = moduleId;
        this.title = title;
        this.description = description;
        this.code = code;
        this.testMode = testMode;
        this.programLanguage = programLanguage;
    }

    public BitModule(String moduleId, String programLanguage, String title, String description, String code, String testMode, String output) {
        this.moduleId = moduleId;
        this.title = title;
        this.description = description;
        this.code = code.equals("") ? null : code;
        this.output = output.equals("") ? null : output;
        this.testMode = testMode.equals("") ? null : testMode;
        this.programLanguage = programLanguage;
    }

    public BitModule(String moduleId, String programLanguage, String title, String description, String code, String testMode, String output, String imageUrl) {
        this.moduleId = moduleId;
        this.title = title;
        this.description = description;
        this.code = code.equals("") ? null : code;
        this.output = output.equals("") ? null : output;
        this.testMode = testMode.equals("") ? null : testMode;
        this.programLanguage = programLanguage;
        this.imageUrl = imageUrl;
    }


    public BitModule(String moduleId, String programLanguage, String title, String description ) {
        this.moduleId = moduleId;
        this.title = title;
        this.description = description;
        this.programLanguage = programLanguage;
    }

    public BitModule(String moduleId, String programLanguage, String title, String description, String code) {
        this.moduleId = moduleId;
        this.title = title;
        this.description = description;
        this.code = code;
        this.programLanguage = programLanguage;
    }


    public BitModule() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTestMode() {
        return testMode;
    }

    public void setTestMode(String testMode) {
        this.testMode = testMode;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getProgramLanguage() {
        return programLanguage;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.moduleId);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.code);
        dest.writeString(this.output);
        dest.writeString(this.testMode);
        dest.writeString(this.programLanguage);
        dest.writeString(this.imageUrl);
    }

    protected BitModule(Parcel in) {
        this.moduleId = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.code = in.readString();
        this.output = in.readString();
        this.testMode = in.readString();
        this.programLanguage = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Creator<BitModule> CREATOR = new Creator<BitModule>() {
        @Override
        public BitModule createFromParcel(Parcel source) {
            return new BitModule(source);
        }

        @Override
        public BitModule[] newArray(int size) {
            return new BitModule[size];
        }
    };
}

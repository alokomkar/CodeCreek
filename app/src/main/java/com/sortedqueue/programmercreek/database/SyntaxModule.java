package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushList;

/**
 * Created by Alok Omkar on 2016-12-25.
 */

public class SyntaxModule extends RushObject implements Parcelable {

    private String syntaxModuleId;
    private String moduleId;
    private String syntaxLanguage;

    /**
     * Name
     * Description
     * Example Command / Program line
     * Output on execution
     * Question command with program / line
     * Display expected output
     * Predetermined inputs as choice
     * Solution for question
     * Extras about the command
     */
    private String syntaxName;
    private String syntaxDescription;
    private String syntaxCommand;
    private String syntaxCommandOutput;

    private String syntaxQuestion;
    private String syntaxQuestionOutput;
    private String syntaxSolution;
    @RushList(classType = ModuleOption.class)
    private ArrayList<ModuleOption> syntaxOptions = new ArrayList<>();

    public SyntaxModule(String syntaxModuleId,
                        String moduleId,
                        String syntaxLanguage,
                        String syntaxName,
                        String syntaxDescription,
                        String syntaxCommand,
                        String syntaxCommandOutput,
                        String syntaxQuestion,
                        String syntaxQuestionOutput,
                        String syntaxSolution,
                        ArrayList<ModuleOption> syntaxOptions) {
        this.syntaxModuleId = syntaxModuleId;
        this.moduleId = moduleId;
        this.syntaxLanguage = syntaxLanguage;
        this.syntaxName = syntaxName;
        this.syntaxDescription = syntaxDescription;
        this.syntaxCommand = syntaxCommand;
        this.syntaxCommandOutput = syntaxCommandOutput;
        this.syntaxQuestion = syntaxQuestion;
        this.syntaxQuestionOutput = syntaxQuestionOutput;
        this.syntaxSolution = syntaxSolution;
        this.syntaxOptions = syntaxOptions;
    }

    public SyntaxModule() {
    }

    public String getSyntaxModuleId() {
        return syntaxModuleId;
    }

    public void setSyntaxModuleId(String syntaxModuleId) {
        this.syntaxModuleId = syntaxModuleId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getSyntaxLanguage() {
        return syntaxLanguage;
    }

    public void setSyntaxLanguage(String syntaxLanguage) {
        this.syntaxLanguage = syntaxLanguage;
    }

    public String getSyntaxName() {
        return syntaxName;
    }

    public void setSyntaxName(String syntaxName) {
        this.syntaxName = syntaxName;
    }

    public String getSyntaxDescription() {
        return syntaxDescription;
    }

    public void setSyntaxDescription(String syntaxDescription) {
        this.syntaxDescription = syntaxDescription;
    }

    public String getSyntaxCommand() {
        return syntaxCommand;
    }

    public void setSyntaxCommand(String syntaxCommand) {
        this.syntaxCommand = syntaxCommand;
    }

    public String getSyntaxCommandOutput() {
        return syntaxCommandOutput;
    }

    public void setSyntaxCommandOutput(String syntaxCommandOutput) {
        this.syntaxCommandOutput = syntaxCommandOutput;
    }

    public String getSyntaxQuestion() {
        return syntaxQuestion;
    }

    public void setSyntaxQuestion(String syntaxQuestion) {
        this.syntaxQuestion = syntaxQuestion;
    }

    public String getSyntaxQuestionOutput() {
        return syntaxQuestionOutput;
    }

    public void setSyntaxQuestionOutput(String syntaxQuestionOutput) {
        this.syntaxQuestionOutput = syntaxQuestionOutput;
    }

    public String getSyntaxSolution() {
        return syntaxSolution;
    }

    public void setSyntaxSolution(String syntaxSolution) {
        this.syntaxSolution = syntaxSolution;
    }

    public ArrayList<ModuleOption> getSyntaxOptions() {
        return syntaxOptions;
    }

    public void setSyntaxOptions(ArrayList<ModuleOption> syntaxOptions) {
        this.syntaxOptions = syntaxOptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SyntaxModule that = (SyntaxModule) o;

        if (!syntaxModuleId.equals(that.syntaxModuleId)) return false;
        if (!moduleId.equals(that.moduleId)) return false;
        return syntaxLanguage.equals(that.syntaxLanguage);

    }

    @Override
    public int hashCode() {
        int result = syntaxModuleId.hashCode();
        result = 31 * result + moduleId.hashCode();
        result = 31 * result + syntaxLanguage.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SyntaxModule{" +
                "syntaxModuleId='" + syntaxModuleId + '\'' +
                ", moduleId='" + moduleId + '\'' +
                ", syntaxLanguage='" + syntaxLanguage + '\'' +
                ", syntaxName='" + syntaxName + '\'' +
                ", syntaxDescription='" + syntaxDescription + '\'' +
                ", syntaxCommand='" + syntaxCommand + '\'' +
                ", syntaxCommandOutput='" + syntaxCommandOutput + '\'' +
                ", syntaxQuestion='" + syntaxQuestion + '\'' +
                ", syntaxQuestionOutput='" + syntaxQuestionOutput + '\'' +
                ", syntaxSolution='" + syntaxSolution + '\'' +
                ", syntaxOptions=" + syntaxOptions +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.syntaxModuleId);
        dest.writeString(this.moduleId);
        dest.writeString(this.syntaxLanguage);
        dest.writeString(this.syntaxName);
        dest.writeString(this.syntaxDescription);
        dest.writeString(this.syntaxCommand);
        dest.writeString(this.syntaxCommandOutput);
        dest.writeString(this.syntaxQuestion);
        dest.writeString(this.syntaxQuestionOutput);
        dest.writeString(this.syntaxSolution);
        dest.writeTypedList(this.syntaxOptions);
    }

    protected SyntaxModule(Parcel in) {
        this.syntaxModuleId = in.readString();
        this.moduleId = in.readString();
        this.syntaxLanguage = in.readString();
        this.syntaxName = in.readString();
        this.syntaxDescription = in.readString();
        this.syntaxCommand = in.readString();
        this.syntaxCommandOutput = in.readString();
        this.syntaxQuestion = in.readString();
        this.syntaxQuestionOutput = in.readString();
        this.syntaxSolution = in.readString();
        this.syntaxOptions = in.createTypedArrayList(ModuleOption.CREATOR);
    }

    public static final Parcelable.Creator<SyntaxModule> CREATOR = new Parcelable.Creator<SyntaxModule>() {
        @Override
        public SyntaxModule createFromParcel(Parcel source) {
            return new SyntaxModule(source);
        }

        @Override
        public SyntaxModule[] newArray(int size) {
            return new SyntaxModule[size];
        }
    };
}

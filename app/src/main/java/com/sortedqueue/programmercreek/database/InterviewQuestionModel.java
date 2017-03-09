package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Alok Omkar on 2017-03-08.
 */

public class InterviewQuestionModel implements Parcelable {

    private int typeOfQuestion;
    private String programLanguage;

    private String question;
    private int correctOption;

    private ArrayList<Integer> correctOptions;
    private ArrayList<Integer> correctSequence;

    private ArrayList<OptionModel> optionModels;
    private String modelId;

    public InterviewQuestionModel(int typeOfQuestion,
                                  String programLanguage,
                                  String question,
                                  int correctOption,
                                  ArrayList<Integer> correctOptions,
                                  ArrayList<Integer> correctSequence,
                                  ArrayList<OptionModel> optionModels) {
        this.typeOfQuestion = typeOfQuestion;
        this.programLanguage = programLanguage;
        this.question = question;
        this.correctOption = correctOption;
        this.correctOptions = correctOptions;
        this.correctSequence = correctSequence;
        this.optionModels = optionModels;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public InterviewQuestionModel() {
    }

    public int getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(int typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }

    public String getProgramLanguage() {
        return programLanguage;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }

    public ArrayList<Integer> getCorrectOptions() {
        return correctOptions;
    }

    public void setCorrectOptions(ArrayList<Integer> correctOptions) {
        this.correctOptions = correctOptions;
    }

    public ArrayList<Integer> getCorrectSequence() {
        return correctSequence;
    }

    public void setCorrectSequence(ArrayList<Integer> correctSequence) {
        this.correctSequence = correctSequence;
    }

    public ArrayList<OptionModel> getOptionModels() {
        return optionModels;
    }

    public void setOptionModels(ArrayList<OptionModel> optionModels) {
        this.optionModels = optionModels;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.typeOfQuestion);
        dest.writeString(this.programLanguage);
        dest.writeString(this.question);
        dest.writeInt(this.correctOption);
        dest.writeList(this.correctOptions);
        dest.writeList(this.correctSequence);
        dest.writeTypedList(this.optionModels);
        dest.writeString(this.modelId);
    }

    protected InterviewQuestionModel(Parcel in) {
        this.typeOfQuestion = in.readInt();
        this.programLanguage = in.readString();
        this.question = in.readString();
        this.correctOption = in.readInt();
        this.correctOptions = new ArrayList<Integer>();
        in.readList(this.correctOptions, Integer.class.getClassLoader());
        this.correctSequence = new ArrayList<Integer>();
        in.readList(this.correctSequence, Integer.class.getClassLoader());
        this.optionModels = in.createTypedArrayList(OptionModel.CREATOR);
        this.modelId = in.readString();
    }

    public static final Creator<InterviewQuestionModel> CREATOR = new Creator<InterviewQuestionModel>() {
        @Override
        public InterviewQuestionModel createFromParcel(Parcel source) {
            return new InterviewQuestionModel(source);
        }

        @Override
        public InterviewQuestionModel[] newArray(int size) {
            return new InterviewQuestionModel[size];
        }
    };
}

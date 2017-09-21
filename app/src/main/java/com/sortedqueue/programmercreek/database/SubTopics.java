package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alok on 18/09/17.
 */

public class SubTopics implements Parcelable {

    private String subTopicId;
    private String programLanguage;
    private String title;
    private String description;
    private String code;
    private String output;
    private String imageUrl;
    private String testMode;
    private String question;
    private String options; // all separated by delimiter - |||
    private String answer;
    private String explanation;
    private String questionCode;

    public SubTopics() {
    }

    public SubTopics(String subTopicId, String programLanguage, String title, String description) {
        this.subTopicId = subTopicId;
        this.programLanguage = programLanguage;
        this.title = title;
        this.description = description;
    }

    public SubTopics(String subTopicId, String programLanguage, String title, String description, String code) {
        this.subTopicId = subTopicId;
        this.programLanguage = programLanguage;
        this.title = title;
        this.description = description;
        this.code = code;
    }

    public SubTopics(String subTopicId, String programLanguage, String title, String description, String code, String output) {
        this.subTopicId = subTopicId;
        this.programLanguage = programLanguage;
        this.title = title;
        this.description = description;
        this.code = code;
        this.output = output;
    }

    public SubTopics(String subTopicId, String programLanguage, String title, String description, String code, String output, String imageUrl) {
        this.subTopicId = subTopicId;
        this.programLanguage = programLanguage;
        this.title = title;
        this.description = description;
        this.code = code;
        this.output = output;
        this.imageUrl = imageUrl;
    }

    public SubTopics(String subTopicId, String programLanguage, String title, String description, String code, String output, String imageUrl, String testMode) {
        this.subTopicId = subTopicId;
        this.programLanguage = programLanguage;
        this.title = title;
        this.description = description;
        this.code = code;
        this.output = output;
        this.imageUrl = imageUrl;
        this.testMode = testMode;
    }

    public SubTopics(String subTopicId, String programLanguage, String title, String description, String code, String output,  String imageUrl, String testMode, String question, String options, String answer) {
        this.subTopicId = subTopicId;
        this.programLanguage = programLanguage;
        this.title = title;
        this.description = description;
        this.code = code;
        this.output = output;
        this.testMode = testMode;
        this.imageUrl = imageUrl;
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public SubTopics(String subTopicId, String programLanguage, String title, String description, String code, String output,  String imageUrl, String testMode, String question, String options, String answer, String explanation) {
        this.subTopicId = subTopicId;
        this.programLanguage = programLanguage;
        this.title = title;
        this.description = description;
        this.code = code;
        this.output = output;
        this.testMode = testMode;
        this.imageUrl = imageUrl;
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.explanation = explanation;
    }

    public SubTopics(String subTopicId, String programLanguage, String title, String description, String code, String output,  String imageUrl, String testMode, String question, String options, String answer, String explanation, String questionCode) {
        this.subTopicId = subTopicId;
        this.programLanguage = programLanguage;
        this.title = title;
        this.description = description;
        this.code = code;
        this.output = output;
        this.testMode = testMode;
        this.imageUrl = imageUrl;
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.explanation = explanation;
        this.questionCode = questionCode;
    }


    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getSubTopicId() {
        return subTopicId;
    }

    public void setSubTopicId(String subTopicId) {
        this.subTopicId = subTopicId;
    }

    public String getProgramLanguage() {
        return programLanguage;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
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
        if( code == null || code.equalsIgnoreCase("") ) return null;
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOutput() {
        if( output == null || output.equalsIgnoreCase("") ) return null;
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTestMode() {
        if( testMode != null && testMode.equalsIgnoreCase("") ) {
            return null;
        }
        return testMode;
    }

    public void setTestMode(String testMode) {
        this.testMode = testMode;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.subTopicId);
        dest.writeString(this.programLanguage);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.code);
        dest.writeString(this.output);
        dest.writeString(this.imageUrl);
        dest.writeString(this.testMode);
        dest.writeString(this.question);
        dest.writeString(this.options);
        dest.writeString(this.answer);
        dest.writeString(this.explanation);
        dest.writeString(this.questionCode);
    }

    protected SubTopics(Parcel in) {
        this.subTopicId = in.readString();
        this.programLanguage = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.code = in.readString();
        this.output = in.readString();
        this.imageUrl = in.readString();
        this.testMode = in.readString();
        this.question = in.readString();
        this.options = in.readString();
        this.answer = in.readString();
        this.explanation = in.readString();
        this.questionCode = in.readString();
    }

    public static final Creator<SubTopics> CREATOR = new Creator<SubTopics>() {
        @Override
        public SubTopics createFromParcel(Parcel source) {
            return new SubTopics(source);
        }

        @Override
        public SubTopics[] newArray(int size) {
            return new SubTopics[size];
        }
    };
}

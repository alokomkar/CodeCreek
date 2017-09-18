package com.sortedqueue.programmercreek.database;

import java.util.ArrayList;

/**
 * Created by Alok on 18/09/17.
 */

public class TopicDetails {

    private String topicId;
    private String topicLanguage;
    private String topicDescription;
    private ArrayList<SubTopics> subTopicsArrayList;

    public TopicDetails() {
    }

    public TopicDetails(String topicId, String topicLanguage, String topicDescription) {
        this.topicId = topicId;
        this.topicLanguage = topicLanguage;
        this.topicDescription = topicDescription;
    }

    public TopicDetails(String topicId, String topicLanguage, String topicDescription, ArrayList<SubTopics> subTopicsArrayList) {
        this.topicId = topicId;
        this.topicLanguage = topicLanguage;
        this.topicDescription = topicDescription;
        this.subTopicsArrayList = subTopicsArrayList;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicLanguage() {
        return topicLanguage;
    }

    public void setTopicLanguage(String topicLanguage) {
        this.topicLanguage = topicLanguage;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public ArrayList<SubTopics> getSubTopicsArrayList() {
        return subTopicsArrayList;
    }

    public void setSubTopicsArrayList(ArrayList<SubTopics> subTopicsArrayList) {
        this.subTopicsArrayList = subTopicsArrayList;
    }
}

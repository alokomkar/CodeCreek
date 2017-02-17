package com.sortedqueue.programmercreek.database;

import java.util.ArrayList;

/**
 * Created by Alok on 30/12/16.
 */

public class QuizModel {

    private int questionIndex;
    private String question;

    private ArrayList<String> optionsList = new ArrayList<>();
    private String selectedOption = "";
    private String correctOption = "correctOption";

    public QuizModel(int questionIndex, String question, ArrayList<String> optionsList) {
        this.questionIndex = questionIndex;
        this.question = question;
        this.optionsList = optionsList;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public boolean isCorrectAnswer() {
        return correctOption.equals(selectedOption);
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public QuizModel() {
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(ArrayList<String> optionsList) {
        this.optionsList = optionsList;
    }
}

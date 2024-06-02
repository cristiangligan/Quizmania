package model;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private int id;
    private String question;
    private List<Options> options;
    private int quizId;

    public Question(String question, int quizId) {
        this.question = question;
        this.options = new ArrayList<>();
        this.quizId = quizId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Options> getOptions() {
        return options;
    }
    public List<String> getOptionsText(){
        List<String> questions = new ArrayList<>();
        for(Options a: options){
            questions.add(a.getText());
        }
        return questions;
    }

    public void setOptions(List<Options> options) {
        this.options = options;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return question;
    }
}

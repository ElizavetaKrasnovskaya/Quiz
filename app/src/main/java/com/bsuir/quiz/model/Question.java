package com.bsuir.quiz.model;


import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    private String url;
    private List<Answer> answers;
    private Long time = Long.valueOf(30000);

    public Question() {
    }

    public Question(String url, List<Answer> answers) {
        this.url = url;
        this.answers = answers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}

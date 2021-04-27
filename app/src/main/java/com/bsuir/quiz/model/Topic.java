package com.bsuir.quiz.model;

import java.io.Serializable;
import java.util.List;

public class Topic implements Serializable {
    private String name;
    private String url;
    private List<Question> questions;

    public Topic() {
    }

    public Topic(String name, String url, List<Question> questions) {
        this.name = name;
        this.url = url;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

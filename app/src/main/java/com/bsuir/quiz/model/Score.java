package com.bsuir.quiz.model;

public class Score {

    private String name;
    private String value;
    private String time;

    public Score() {
    }

    public Score(String name, String value, String time) {
        this.name = name;
        this.value = value;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Email: " + name + ", ques amount: " + value + ", time: " + time;
    }
}

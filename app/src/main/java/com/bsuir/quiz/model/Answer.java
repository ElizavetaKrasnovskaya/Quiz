package com.bsuir.quiz.model;

import java.io.Serializable;

public class Answer implements Serializable {

    private String name;
    private boolean value;

    public Answer() {
    }

    public Answer(String name, boolean value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}

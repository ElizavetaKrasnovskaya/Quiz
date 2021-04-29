package com.bsuir.quiz.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bsuir.quiz.model.Question;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class QuestionAdapter extends FragmentPagerAdapter {

    private final List<Question> allQuestions;
    private final int amountOfQuestions;

    public QuestionAdapter(FragmentManager fm, List<Question> questions, int amountOfQuestions) {
        super(fm);
        this.allQuestions = questions;
        Collections.shuffle(questions);
        this.amountOfQuestions = amountOfQuestions;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        List<Question> questions = allQuestions.subList(0, amountOfQuestions);
        arguments.putSerializable(QuestionFragment.QUESTION, (Serializable) questions.get(position));
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setArguments(arguments);
        return questionFragment;
    }

    @Override
    public int getCount() {
        return amountOfQuestions;
    }

}

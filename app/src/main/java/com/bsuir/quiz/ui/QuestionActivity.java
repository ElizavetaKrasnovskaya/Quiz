package com.bsuir.quiz.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.bsuir.quiz.R;
import com.bsuir.quiz.adapter.QuestionAdapter;
import com.bsuir.quiz.adapter.QuestionFragment;
import com.bsuir.quiz.adapter.TopicAdapter;

public class QuestionActivity extends AppCompatActivity implements QuestionFragment.OnButtonClickListener {

    private static int amountOfQuestions;
    private ViewPager viewPager;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        QuestionFragment.setCorrectAnswer(0);
        QuestionFragment.setWrongAnswer(0);
        QuestionFragment.setTransitTime(0);
        QuestionFragment.setFinishTimeQuestion(0);
        QuestionFragment.setCounterOfFriendsAnswer(1);
        QuestionFragment.setCounterOfPrompt(3);

        final QuestionAdapter[] questionAdapter = {new QuestionAdapter(getSupportFragmentManager(),
                TopicAdapter.getSelectedTopic().getQuestions(), amountOfQuestions)};

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(questionAdapter[0]);
        viewPager.setPageTransformer(true, new CubeOutTransformer());
    }

    public static void setAmountOfQuestions(int amountOfQuestions) {
        QuestionActivity.amountOfQuestions = amountOfQuestions;
    }

    public static int getAmountOfQuestions() {
        return amountOfQuestions;
    }

    @Override
    public void onButtonClicked(View view) {
        int currPos = viewPager.getCurrentItem();
        mHandler.postDelayed(() -> viewPager.setCurrentItem(currPos + 1), 2000);
    }
}
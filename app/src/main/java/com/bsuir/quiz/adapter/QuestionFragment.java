package com.bsuir.quiz.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.anychart.charts.Pie;
import com.anychart.core.PiePoint;
import com.bsuir.quiz.R;
import com.bsuir.quiz.model.Answer;
import com.bsuir.quiz.model.Question;
import com.bsuir.quiz.ui.PieChartActivity;
import com.bsuir.quiz.ui.QuestionActivity;
import com.bsuir.quiz.ui.SplashActivity;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestionFragment extends Fragment implements View.OnClickListener {

    private long countdownInMillisS = (long) -1;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private TextView timer;
    private String userAnswer;

    private static int correctAnswer = 0;
    private static int wrongAnswer = 0;
    private static int unansweredQuestion = 0;

    static final String QUESTION = "QUESTION";

    private Question question;
    private String trueAnswer;
    private Button firstButton;
    private Button secondButton;
    private Button thirdButton;
    private Button fourthButton;


    public QuestionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            question = (Question) arguments.getSerializable(QUESTION);
            for (Answer answer : question.getAnswers()) {
                if (answer.getValue()) {
                    trueAnswer = answer.getName();
                }
            }
            timeLeftInMillis = question.getTime();
            displayValues(view, question.getUrl());
        }
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener((Toolbar.OnMenuItemClickListener) item -> {
            switch (item.getItemId()) {
                case R.id.statistics:
                    PieChartActivity.setAmountOfAnswers(new int[]{correctAnswer, wrongAnswer, unansweredQuestion});
                    Intent intent = new Intent(getContext(), PieChartActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.amount:
                    showAlertDialog();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        });
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onResume();
        } else {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            startCountDown();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Enter amount of questions:");
        String[] items = {"5", "10", "15"};
        int checkedItem = 1;
        final Intent[] intent = new Intent[1];
        alertDialog.setSingleChoiceItems(items, checkedItem, (dialog, which) -> {
            switch (which) {
                case 0:
                    QuestionActivity.setAmountOfQuestions(5);
                    intent[0] = new Intent(getActivity(), QuestionActivity.class);
                    intent[0].addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent[0]);
                    break;
                case 1:
                    QuestionActivity.setAmountOfQuestions(10);
                    intent[0] = new Intent(getActivity(), QuestionActivity.class);
                    intent[0].addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent[0]);
                    break;
                case 2:
                    QuestionActivity.setAmountOfQuestions(15);
                    intent[0] = new Intent(getActivity(), QuestionActivity.class);
                    intent[0].addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent[0]);
                    break;
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private void displayValues(View view, String url) {
        ImageView imageView = view.findViewById(R.id.image);
        firstButton = view.findViewById(R.id.button_1);
        secondButton = view.findViewById(R.id.button_2);
        thirdButton = view.findViewById(R.id.button_3);
        fourthButton = view.findViewById(R.id.button_4);
        timer = view.findViewById(R.id.tv);

        Collections.shuffle(question.getAnswers());

        firstButton.setText(question.getAnswers().get(0).getName());
        secondButton.setText(question.getAnswers().get(1).getName());
        thirdButton.setText(question.getAnswers().get(2).getName());
        fourthButton.setText(question.getAnswers().get(3).getName());

        if (userAnswer != null) {
            if (firstButton.getText().equals(userAnswer) && firstButton.getText().equals(trueAnswer)) {
                firstButton.setTextColor(Color.GREEN);
            } else if (firstButton.getText().equals(userAnswer) && !firstButton.getText().equals(trueAnswer)) {
                firstButton.setTextColor(Color.RED);
            }
            if (secondButton.getText().equals(userAnswer) && secondButton.getText().equals(trueAnswer)) {
                secondButton.setTextColor(Color.GREEN);
            } else if (secondButton.getText().equals(userAnswer) && !secondButton.getText().equals(trueAnswer)) {
                secondButton.setTextColor(Color.RED);
            }
            if (thirdButton.getText().equals(userAnswer) && thirdButton.getText().equals(trueAnswer)) {
                thirdButton.setTextColor(Color.GREEN);
            } else if (thirdButton.getText().equals(userAnswer) && !thirdButton.getText().equals(trueAnswer)) {
                thirdButton.setTextColor(Color.RED);
            }
            if (fourthButton.getText().equals(userAnswer) && fourthButton.getText().equals(trueAnswer)) {
                fourthButton.setTextColor(Color.GREEN);
            } else if (fourthButton.getText().equals(userAnswer) && !fourthButton.getText().equals(trueAnswer)) {
                fourthButton.setTextColor(Color.RED);
            }
            countDownTimer.cancel();
            int minutes = (int) (countdownInMillisS / 1000) / 60;
            int seconds = (int) (countdownInMillisS / 1000) % 60;
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            timer.setText(timeFormatted);
            if (timeLeftInMillis < 10000) {
                timer.setTextColor(Color.RED);
            }
            showTrueAnswer();
        }

        firstButton.setOnClickListener(this);
        secondButton.setOnClickListener(this);
        thirdButton.setOnClickListener(this);
        fourthButton.setOnClickListener(this);

        new QuestionFragment.LoadImage(imageView).execute(url);
    }

    private void startCountDown() {
        if (countdownInMillisS != -1) {
            timeLeftInMillis = countdownInMillisS;
        }
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                showTrueAnswer();
                unansweredQuestion++;
                firstButton.setClickable(false);
                secondButton.setClickable(false);
                thirdButton.setClickable(false);
                fourthButton.setClickable(false);
            }
        }.start();
        if (userAnswer != null) {
            countDownTimer.cancel();
        }
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timer.setText(timeFormatted);
        countdownInMillisS = timeLeftInMillis;
        if (timeLeftInMillis < 10000) {
            timer.setTextColor(Color.RED);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_1:
                if (firstButton.getText().equals(trueAnswer)) {
                    firstButton.setTextColor(Color.GREEN);
                    correctAnswer++;

                } else {
                    firstButton.setTextColor(Color.RED);
                    wrongAnswer++;
                }
                userAnswer = firstButton.getText().toString();
                firstButton.setClickable(false);
                secondButton.setClickable(false);
                thirdButton.setClickable(false);
                fourthButton.setClickable(false);
                countDownTimer.cancel();
                showTrueAnswer();
                break;
            case R.id.button_2:
                if (secondButton.getText().equals(trueAnswer)) {
                    secondButton.setTextColor(Color.GREEN);
                    correctAnswer++;
                } else {
                    secondButton.setTextColor(Color.RED);
                    wrongAnswer++;
                }
                userAnswer = secondButton.getText().toString();
                firstButton.setClickable(false);
                secondButton.setClickable(false);
                thirdButton.setClickable(false);
                fourthButton.setClickable(false);
                countDownTimer.cancel();
                showTrueAnswer();
                break;
            case R.id.button_3:
                if (thirdButton.getText().equals(trueAnswer)) {
                    thirdButton.setTextColor(Color.GREEN);
                    correctAnswer++;
                } else {
                    thirdButton.setTextColor(Color.RED);
                    wrongAnswer++;
                }
                userAnswer = thirdButton.getText().toString();
                firstButton.setClickable(false);
                secondButton.setClickable(false);
                thirdButton.setClickable(false);
                fourthButton.setClickable(false);
                countDownTimer.cancel();
                showTrueAnswer();
                break;
            case R.id.button_4:
                if (fourthButton.getText().equals(trueAnswer)) {
                    correctAnswer++;
                    fourthButton.setTextColor(Color.GREEN);
                } else {
                    fourthButton.setTextColor(Color.RED);
                    wrongAnswer++;
                }
                userAnswer = fourthButton.getText().toString();
                firstButton.setClickable(false);
                secondButton.setClickable(false);
                thirdButton.setClickable(false);
                fourthButton.setClickable(false);
                countDownTimer.cancel();
                showTrueAnswer();
                break;
        }
        if((correctAnswer + wrongAnswer + unansweredQuestion) == QuestionActivity.getAmountOfQuestions()){
            PieChartActivity.setAmountOfAnswers(new int[]{correctAnswer, wrongAnswer, unansweredQuestion});
            Intent intent = new Intent(getContext(), PieChartActivity.class);
            startActivity(intent);
        }
    }

    private void showTrueAnswer() {
        if (firstButton.getText().equals(trueAnswer)) {
            firstButton.setTextColor(Color.GREEN);
        }
        if (secondButton.getText().equals(trueAnswer)) {
            secondButton.setTextColor(Color.GREEN);
        }
        if (thirdButton.getText().equals(trueAnswer)) {
            thirdButton.setTextColor(Color.GREEN);
        }
        if (fourthButton.getText().equals(trueAnswer)) {
            fourthButton.setTextColor(Color.GREEN);
        }
    }

    public static int getCorrectAnswer() {
        return correctAnswer;
    }

    public static int getWrongAnswer() {
        return wrongAnswer;
    }

    public static int getUnansweredQuestion() {
        return unansweredQuestion;
    }

    static class LoadImage extends AsyncTask<String, Void, Bitmap> {

        private final WeakReference<ImageView> imageViewReference;

        public LoadImage(ImageView imageView) {
            imageViewReference = new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                return downloadBitmap(params[0]);
            } catch (Exception e) {
                Log.d("error", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        }

        private Bitmap downloadBitmap(String url) {
            HttpURLConnection urlConnection = null;
            try {
                java.net.URL uri = new URL(url);
                urlConnection = (HttpURLConnection) uri.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    return BitmapFactory.decodeStream(inputStream);
                }
            } catch (Exception e) {
                urlConnection.disconnect();
                Log.w("ImageDownloader", "Error downloading image from " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}
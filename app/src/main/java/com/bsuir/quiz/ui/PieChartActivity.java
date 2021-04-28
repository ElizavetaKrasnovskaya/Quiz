package com.bsuir.quiz.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.bsuir.quiz.R;
import com.bsuir.quiz.adapter.QuestionFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PieChartActivity extends AppCompatActivity {

    private AnyChartView anyChartView;
    private TextView textViewTime;
    private String[] answers = {"correct", "wrong", "unanswered"};
    private static int[] amountOfAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        anyChartView = findViewById(R.id.any_chart_view);
        textViewTime = findViewById(R.id.transit_time);
        long transitTime = QuestionFragment.getTransitTime();
        int minutes = (int) (transitTime / 1000) / 60;
        int seconds = (int) (transitTime / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewTime.setText("Transit time: " + timeFormatted);
        setupPieChart();
        QuestionFragment.setCorrectAnswer(amountOfAnswers[0]);
        QuestionFragment.setWrongAnswer(amountOfAnswers[1]);
        QuestionFragment.setTransitTime(transitTime);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setupPieChart() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        if (amountOfAnswers.length == 3) {
            for (int i = 0; i < answers.length; i++) {
                dataEntries.add(new ValueDataEntry(answers[i], amountOfAnswers[i]));
            }
        }

        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }

    public static int[] getAmountOfAnswers() {
        return amountOfAnswers;
    }

    public static void setAmountOfAnswers(int[] amountOfAnswers) {
        PieChartActivity.amountOfAnswers = amountOfAnswers;
    }
}
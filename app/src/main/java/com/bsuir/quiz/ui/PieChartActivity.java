package com.bsuir.quiz.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.bsuir.quiz.R;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity {

    private AnyChartView anyChartView;
    private String[] months = {"correct", "wrong", "unanswered"};
    private static int[] amountOfAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        anyChartView = findViewById(R.id.any_chart_view);

        setupPieChart();
    }

    public void  setupPieChart(){
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        if(amountOfAnswers.length==3) {
            for (int i = 0; i < months.length; i++) {
                dataEntries.add(new ValueDataEntry(months[i], amountOfAnswers[i]));
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
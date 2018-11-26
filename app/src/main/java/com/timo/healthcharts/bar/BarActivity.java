package com.timo.healthcharts.bar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.timo.healthcharts.R;
import com.timo.healthcharts.manager.ChartManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarActivity extends AppCompatActivity {

    @BindView(R.id.bar_chart)
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        ButterKnife.bind(this);

        initChart(barChart);
        initChartData();
    }

    private void initChartData() {
        List<IBarDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            List<BarEntry> entryList = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                BarEntry barEntry = new BarEntry(new Random().nextInt(20) + 10, new Random().nextInt(20) + 20);
                entryList.add(barEntry);
            }
            BarDataSet barDataSet = new BarDataSet(entryList, "");
            dataSets.add(barDataSet);
        }
        BarData barData = new BarData(dataSets);
        barData.setValueTextColor(Color.YELLOW);
        barChart.setData(barData);
    }

    private void initChart(BarChart chart) {
        ChartManager.setXAxis(chart);
        ChartManager.setYAxis(chart);
    }
}

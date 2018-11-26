package com.timo.healthcharts.year;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.timo.healthcharts.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description:
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 汤迪 on 2018/11/23
 */
public class PieActivity extends AppCompatActivity {

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        pieChart = findViewById(R.id.pie_chart);

        setPieData();
    }

    private void setPieData() {
        List<PieEntry> pieList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PieEntry pieEntry = new PieEntry(new Random().nextInt(20),"标签" + i);
            pieList.add(pieEntry);
        }
        IPieDataSet dataSet = new PieDataSet(pieList, "娃哈哈");

        PieData data = new PieData();
        data.setDataSet(dataSet);
        pieChart.setData(data);
    }
}

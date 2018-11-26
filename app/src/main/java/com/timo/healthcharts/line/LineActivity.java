package com.timo.healthcharts.line;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.timo.healthcharts.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LineActivity extends AppCompatActivity {

    @BindView(R.id.line_chart)
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        ButterKnife.bind(this);

        initLineChart();
        initLineData();
    }

    private void initLineChart() {
    }

    private void initLineData() {
        List<Entry> entryList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Entry entry = new Entry(i, new Random().nextInt(20) );
            entry.describeContents();
            entryList.add(entry);
        }
        List<ILineDataSet> lineSet = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            LineDataSet lineDataSet = new LineDataSet(entryList, "折线图" + 1);
            lineSet.add(lineDataSet);
        }
        LineData lineData = new LineData(lineSet);
        lineChart.setData(lineData);
    }
}

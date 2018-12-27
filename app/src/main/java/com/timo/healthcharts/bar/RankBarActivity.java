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

public class RankBarActivity extends AppCompatActivity {

    @BindView(R.id.bar_chart)
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_bar);
        ButterKnife.bind(this);

        initChart(barChart);
        initChartData(barChart);
    }

    private void initChart(BarChart chart) {

        ChartManager chartManager = ChartManager.getInstance();
        chartManager.setDesc(chart, "我是柱状图");
        chartManager.setTouchEvent(chart);
        chartManager.setXAxis(chart);
        chartManager.setYAxis(chart);
        chartManager.setLegend(chart);

        //控制缩放一次查看x轴上不超过10个值而无需滚动
        chart.setVisibleXRangeMaximum(5);

        //在每个条形后面绘制一个灰色区域，指示最大值,启用他的功能会使性能降低约40％
        chart.setDrawBarShadow(false);
        //值是否设置到柱状外,默认是
        chart.setDrawValueAboveBar(true);

    }

    private void initChartData(BarChart chart) {
        List<IBarDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<BarEntry> entryList = new ArrayList<>();
            for (int j = 0; j < 1; j++) {
                BarEntry barEntry = new BarEntry(i, new Random().nextInt(20) + 10);
                barEntry.setX(0.5f+i);
                entryList.add(barEntry);
            }
            BarDataSet barDataSet = new BarDataSet(entryList, "");
            //设置柱状图颜色
            barDataSet.setColors(new int[]{R.color.red4}, this);
            barDataSet.setBarBorderWidth(2);
            barDataSet.setFormLineWidth(5);
            dataSets.add(barDataSet);
        }

        BarData barData = new BarData(dataSets);
        //设置柱状值颜色和大小
        barData.setValueTextColor(Color.RED);
        barData.setValueTextSize(14f);

        int barAmount = dataSets.size(); //需要显示柱状图的类别 数量
        //设置组间距占比30% 每条柱状图宽度占比 70% /barAmount  柱状图间距占比 0%
        float groupSpace = 0.3f; //柱状图组之间的间距
        float barWidth = (1f - groupSpace) / barAmount;
        float barSpace = 0f;

        //占百分比 默认0.85f
        barData.setBarWidth(barWidth);

        //并列，不设置则是叠加 ,BarData needs to hold at least 2 BarDataSets to allow grouping.
//        barData.groupBars(0, groupSpace, barSpace);
        chart.setData(barData);
    }
}

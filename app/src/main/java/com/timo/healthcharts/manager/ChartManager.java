package com.timo.healthcharts.manager;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

/**
 * @Description:
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 汤迪 on 2018/11/26
 */
public class ChartManager {

    /**
     * 设置X轴，适用于所有图表
     */
    public static void setXAxis(Chart chart) {
        XAxis xAxis = chart.getXAxis();
        //边缘不剪切
        xAxis.setAvoidFirstLastClipping(true);
        //X轴位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //绘制x轴标签的倾斜角度（以度为单位）
        xAxis.setLabelRotationAngle(45);
        //X轴标签颜色和大小
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.RED);
        //绘制X轴
        xAxis.setDrawAxisLine(true);
        //是否绘制X轴网格线（即与Y轴平行的线）
        xAxis.setDrawGridLines(true);
    }


    /**
     * 设置Y轴，适用于BarLineChart
     */
    public static void setYAxis(BarLineChartBase chart) {
        //隐藏右边Y轴
        chart.getAxisRight().setEnabled(false);

        //获取左轴
        YAxis yAxis = chart.getAxisLeft();
        //设置间隔
        yAxis.setGranularity(1f);
        //强制几个标签
        yAxis.setLabelCount(6, true);
        //是否绘制Y轴
        yAxis.setDrawAxisLine(true);
        //设置最小值
        yAxis.setAxisMinimum(0);
        //是否绘制Y轴标签
        yAxis.setDrawLabels(true);
        //是否绘制网格线
        yAxis.setDrawGridLines(true);

        //设置零线
        yAxis.setZeroLineColor(Color.YELLOW);
        yAxis.setZeroLineWidth(2f);
        //指示是否应绘制零线，而不管其他网格线
        yAxis.setDrawZeroLine(true);
    }
}

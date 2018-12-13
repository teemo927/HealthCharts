package com.timo.healthcharts.manager;

import android.graphics.Color;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

/**
 * @Description:
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 汤迪 on 2018/11/26
 */
public class ChartManager {

    private static volatile ChartManager sInst = null;

    protected String[] mouth_value = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    protected String[] year_health_value = new String[]{
            "肝功能异常", "血脂异常", "高血压病", "超重", "冠心病5", "冠心病6", "冠心病7", "冠心病8", "冠心病9"
    };

    public static ChartManager getInstance() {
        ChartManager inst = sInst;
        if (inst == null) {
            synchronized (ChartManager.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new ChartManager();
                    sInst = inst;
                }
            }
        }
        return inst;
    }

    private ChartManager() {

    }

    public void setDesc(Chart chart, String text) {
        //设置表格描述label
        Description description = new Description();
        description.setText(text);
        description.setTextColor(Color.BLACK);
        chart.setDescription(description);
    }

    /**
     * 与图表的交互
     */
    public void setTouchEvent(final BarChart chart) {
        //是否允许缩放，禁止后后续放大和拖拽均失效
        chart.setScaleEnabled(true);
        //是否允许双击放大
        chart.setDoubleTapToZoomEnabled(false);
        //是否允许拖拽
        chart.setDragEnabled(true);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(chart.getContext(), e.getY() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {
                Toast.makeText(chart.getContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 设置X轴，适用于所有图表
     */
    public void setXAxis(Chart chart) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMaximum(5);
        xAxis.setAxisMinimum(-1f);
        //设置间隔
        xAxis.setGranularity(1f);
        //强制几个标签,导致轴上的数字不均匀
//        xAxis.setLabelCount(3, true);
        //边缘不剪切
        xAxis.setAvoidFirstLastClipping(true);
        //X轴位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //绘制x轴标签的倾斜角度（以度为单位）
        xAxis.setLabelRotationAngle(0);
        //X轴标签颜色和大小
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.RED);
        //绘制X轴
        xAxis.setDrawAxisLine(true);
        //是否绘制X轴网格线（即与Y轴平行的线）
        xAxis.setDrawGridLines(true);
        //设置网格颜色
        xAxis.setGridColor(Color.GREEN);
        //自定义X轴
        xAxis.setValueFormatter(new XValueFormatter(year_health_value));
    }

    /**
     * 设置Y轴，适用于BarLineChart
     */
    public void setYAxis(BarLineChartBase chart) {
        //隐藏右边Y轴
        chart.getAxisRight().setEnabled(false);

        //获取左轴
        YAxis yAxis = chart.getAxisLeft();
        //设置间隔
        yAxis.setGranularity(1f);
        //是否绘制Y轴
        yAxis.setDrawAxisLine(true);
        //设置最小值
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(30);
        //强制几个标签,强制可能会导致轴上的数字不均匀
        yAxis.setLabelCount(6, false);
        //是否绘制Y轴标签
        yAxis.setDrawLabels(true);
        //是否绘制网格线
        yAxis.setDrawGridLines(true);
        //设置网格颜色
        yAxis.setGridColor(Color.BLUE);

        //设置零线
        yAxis.setZeroLineColor(Color.YELLOW);
        yAxis.setZeroLineWidth(2f);
        //指示是否应绘制零线，而不管其他网格线
        yAxis.setDrawZeroLine(true);

        //设置图表中的最高值的顶部间距占最高值的值的百分比
//        yAxis.setSpaceTop(10f);

    }

    /**
     * 设置图例标签
     */
    public void setLegend(Chart chart) {
        Legend legend = chart.getLegend();
        legend.setTextColor(Color.GREEN);
        //这是在图例标签旁边绘制的形状，其中DataSet包含图例条目所代表的颜色
        legend.setForm(Legend.LegendForm.SQUARE);
        //图例标签为线时的宽度
        legend.setFormLineWidth(5);
        //为圆形时是半径，为正方形时是边长，为长方形才是宽度
        legend.setFormSize(16);
        //设置水平轴上图例标签之间的间距
        legend.setXEntrySpace(15);
        //设置垂直轴上图例标签之间的间距
        legend.setYEntrySpace(10);
        //设置 legend-form 和 legend-label 之间的空间
        legend.setFormToTextSpace(5);

        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //设置方位
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
    }

    /**
     * 设置限制线
     */
    public void addLimitLine(LineChart chart) {
        LimitLine xLimitLine = new LimitLine(2f, "xL 测试");
        XAxis xAxis = chart.getXAxis();
        xAxis.addLimitLine(xLimitLine);

        LimitLine yLimitLine = new LimitLine(10f, "yL 测试");
        YAxis yAxis = chart.getAxisLeft();
        yAxis.addLimitLine(yLimitLine);
    }
}

package com.timo.healthcharts.manager;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * @Description: 自定义X轴
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 汤迪 on 2018/11/28
 */
public class XValueFormatter implements IAxisValueFormatter {
    private String[] mValues;

    XValueFormatter(String[] values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (value < 0) return "";
        return mValues[(int) value % mValues.length];
    }
}

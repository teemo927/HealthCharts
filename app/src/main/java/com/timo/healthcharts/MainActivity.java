package com.timo.healthcharts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.timo.healthcharts.bar.BarActivity;
import com.timo.healthcharts.line.LineActivity;
import com.timo.healthcharts.pie.PieActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvChart = findViewById(R.id.rv_chart);
        rvChart.setLayoutManager(new LinearLayoutManager(this));
        List<String> mData = new ArrayList<>();
        mData.add("扇形图");
        mData.add("折线图");
        mData.add("柱状图");
        mData.add("并行柱状图");
        mData.add("水平叠加柱状图");
        mData.add("水平并行柱状图");
        rvChart.setAdapter(new CommonAdapter<String>(this, R.layout.item_chart, mData) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                holder.setText(R.id.tv, s);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToChart(position);
                    }
                });
            }
        });
    }

    private void goToChart(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, PieActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, LineActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, BarActivity.class));
                break;
        }
    }
}

package com.timo.healthcharts.drag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.timo.healthcharts.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipeDeleteActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_delete);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        List<String> mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add("数据"+i);
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new CommonAdapter<String>(this, R.layout.item_swipe_delete, mData) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_msg,s);
            }
        });
    }
}

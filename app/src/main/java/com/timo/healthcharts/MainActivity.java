package com.timo.healthcharts;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.timo.healthcharts.bar.BarActivity;
import com.timo.healthcharts.bar.RankBarActivity;
import com.timo.healthcharts.common.AlarmClockService;
import com.timo.healthcharts.common.AlarmReceiver;
import com.timo.healthcharts.common.CustomTestService;
import com.timo.healthcharts.common.NotificationUtil;
import com.timo.healthcharts.drag.DragActivity;
import com.timo.healthcharts.drag.SwipeDeleteActivity;
import com.timo.healthcharts.line.LineActivity;
import com.timo.healthcharts.pie.PieActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView rvChart;

    private AlarmManager alarmManager = null;
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        initView();

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    private void initView() {
        rvChart = findViewById(R.id.rv_chart);
        rvChart.setLayoutManager(new LinearLayoutManager(this));
        List<String> mData = new ArrayList<>();
        mData.add("扇形图");
        mData.add("折线图");
        mData.add("柱状图");
        mData.add("排行柱状图");
        mData.add("设置闹钟");
        mData.add("后台服务");
        mData.add("滑动删除布局");
        mData.add("拖拽布局");
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
            case 3:
                startActivity(new Intent(this, RankBarActivity.class));
                break;
            case 4:
                setClock();
                break;
            case 5:
                startService(new Intent(this, CustomTestService.class));
                break;
            case 6:
                startActivity(new Intent(this,SwipeDeleteActivity.class));
                break;
            case 7:
                startActivity(new Intent(this,DragActivity.class));
                break;
        }
    }

    public void setClock() {
        Dialog dialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();//获取日期对象
                        c.setTimeInMillis(System.currentTimeMillis());          //设置Calendar对象
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);                 //设置闹钟小时数
                        c.set(Calendar.MINUTE, minute);                         //设置闹钟的分钟数
                        c.set(Calendar.SECOND, 0);                              //设置闹钟的秒数
                        c.set(Calendar.MILLISECOND, 0);                         //设置闹钟的毫秒数

                        Intent intent = new Intent(context, AlarmReceiver.class);       //创建Intent对象
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "我是闹钟");
                        bundle.putString("content", "起床时间到");
                        intent.putExtras(bundle);
                        PendingIntent pi = PendingIntent.getBroadcast(context, 0,
                                intent, PendingIntent.FLAG_CANCEL_CURRENT);             //创建PendingIntent

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationUtil.sendNotification(context, "我是闹钟", "闹钟时间" + hourOfDay + ":" + minute, 100);
                            startService(new Intent(context, AlarmClockService.class));
                        }

                        //设置一次性闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟执行时间，第三个参数表示闹钟响应动作。
                        if (c.getTimeInMillis() < System.currentTimeMillis()) {
                            Log.i("clock", "设置时间要推迟24小时,不然立刻会响");
                            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + 24 * 60 * 60 * 1000, pi);
                        } else {
                            //设置闹钟，当前时间就唤醒
                            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                        }
                        Toast.makeText(context, "闹钟设置成功", Toast.LENGTH_LONG).show();
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false);
        dialog.show();
    }
}

package com.timo.healthcharts.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null) {
            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");

            AlarmActivity.launchActivity(context, title, content);
        } else {
            Toast.makeText(context, "参数不能为空", Toast.LENGTH_SHORT).show();
        }
    }

}

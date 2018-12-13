package com.timo.healthcharts.notice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.timo.healthcharts.R;

/**
 * @Description:
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 汤迪 on 2018/12/13
 */
public class NoticeUtil {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void sendNotification(Context context) {

        Class clazz = null;
        try {
            clazz = Class.forName("com.timo.healthcharts.MainActivity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Intent hangIntent = new Intent(context, clazz);
        PendingIntent hangPendingIntent = PendingIntent.getActivity(context, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        String id = "channel_0";
        String des = "111";
        NotificationChannel channel = new NotificationChannel(id, des, NotificationManager.IMPORTANCE_MIN);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
        Notification notification = new Notification.Builder(context, id)
                .setContentTitle("Base Notification View")
                .setContentText("您有一条新通知")
                .setContentIntent(hangPendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new Notification.MediaStyle())
                .setAutoCancel(false)
                .build();
        notificationManager.notify(1, notification);
    }
}

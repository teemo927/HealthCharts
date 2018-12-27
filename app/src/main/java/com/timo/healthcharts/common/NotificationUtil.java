package com.timo.healthcharts.common;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import com.timo.healthcharts.MainActivity;
import com.timo.healthcharts.R;

/**
 * @Description:
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 汤迪 on 2018/12/13
 */
public class NotificationUtil {

    private static NotificationManager notificationManager;

    public static Notification sendNotification(Context context, String title, String msg,int id) {
        notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);

        //设置点击跳转
        Intent hangIntent = new Intent(context, MainActivity.class);
        PendingIntent hangPendingIntent = PendingIntent.getActivity(context, 0,
                hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        String channelId = "channel_0" + SystemClock.currentThreadTimeMillis();
        String channelName = "channel_name_1";
        Notification notification;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            createNotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notification = new Notification.Builder(context, channelId)
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setContentIntent(hangPendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setStyle(new Notification.MediaStyle())
                    .setAutoCancel(false)
                    .build();
        } else {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId);
            notification = mBuilder
                    .setContentTitle(context.getResources().getString(R.string.app_name))
                    .setContentIntent(hangPendingIntent)
                    .setWhen(System.currentTimeMillis())                    //通知产生的时间，会在通知信息里显示
                    .setPriority(NotificationCompat.PRIORITY_HIGH)       //设置该通知优先级
                    .setAutoCancel(true)                                    //设置这个标志当用户单击面板就可以让通知将自动取消
                    .setOngoing(true)//true，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                    .setSound(null)
                    .setOnlyAlertOnce(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
        }
        notificationManager.notify(id, notification);
//        context.startForeground(1, notification);
        return notification;
    }

    /**
     * 为8.0 设置通知渠道
     *
     * @param channelId
     * @param channelName
     * @param importance
     */
    @TargetApi(Build.VERSION_CODES.O)
    private static void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

}

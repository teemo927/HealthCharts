package com.timo.healthcharts.common;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.timo.healthcharts.R;

import java.io.IOException;

public class AlarmClockService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private MediaPlayer mediaPlayer;
    private boolean isPlayerReleased;
    private int playCount;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                isPlayerReleased = true;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("AlarmClockService", "AlarmClockService启动");
        playMedia();
        Notification notification = NotificationUtil.sendNotification(this, "酷我音乐", "正在播放...", 10010);
        startForeground(2, notification);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Notification notification = NotificationUtil.sendNotification(AlarmClockService.this, "就是划不掉", "《活着》", 10020);
                startForeground(3, notification);
            }
        }).start();
    }

    private void playMedia() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mp.stop();
                mp.release();
                mediaPlayer = null;
                return true;
            }
        });
        mediaPlayer.setOnPreparedListener(this);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }
        try {
            setResource(getResources(), mediaPlayer, R.raw.in_call_alarm);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();

            mediaPlayer.start();
            mediaPlayer.setVolume(0.9f, 0.9f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessageDelayed(0, 100000);
    }

    private void setResource(Resources resources, MediaPlayer player, int res) {

        try {
            AssetFileDescriptor afd = resources.openRawResourceFd(res);
            if (afd != null) {
                player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
                        afd.getLength());
                afd.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playCount += 1;
        if (playCount < 5) {
            mediaPlayer.start();
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
            isPlayerReleased = true;
        }
    }

//    private void addClock() {
//        Intent intent = new Intent("alarm_receiver");
//        try {
//            Class clz = Class.forName("com.timo.healthcharts.common.AlarmReceiver");
//            intent.setClass(this, clz);
//        } catch (ClassNotFoundException e1) {
//            e1.printStackTrace();
//        }
//
//        Bundle bundle = new Bundle();
//        bundle.putString("title", "我是闹钟");
//        bundle.putString("content", "起床时间到");
//        intent.putExtras(bundle);
//
//        PendingIntent pi = PendingIntent.getBroadcast(this, 1
//                , intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
////        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
////        assert am != null;
////        if (Build.VERSION.SDK_INT > 19) {
////            am.setExact(AlarmManager.RTC_WAKEUP, e.c.getTimeInMillis(), pi);
////        } else {
////            am.set(AlarmManager.RTC_WAKEUP, e.c.getTimeInMillis(), pi);
////        }
//    }

}

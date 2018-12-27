package com.timo.healthcharts.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.timo.healthcharts.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmActivity extends Activity {
    public static final String P_TITLE = "title";
    public static final String P_CONTENT = "content";

    @BindView(R.id.alarm_time)
    TextView alarmTime;
    @BindView(R.id.alarm_hint)
    TextView alarmHint;
    private Vibrator vibrator;
    private Context context;

    public static void launchActivity(Context context, String title, String content) {
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.putExtra(P_TITLE, title);
        intent.putExtra(P_CONTENT, content);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        alarmTime.setText(intent.getStringExtra(P_TITLE));
        alarmHint.setText(intent.getStringExtra(P_CONTENT));

        alarmStart();
    }

    private void alarmStart() {
        Log.i("clock", "闹钟响了........");

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibratorControl(50000);
        }
//        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//        if (audioManager != null) {
//            audioManager.setStreamVolume(AudioManager.RINGER_MODE_NORMAL, 5, 0);
//        }
    }

    private void vibratorControl(int ringDuration) {
        int arrLength = ringDuration / 500;
        long[] vibratorPattern = new long[arrLength];
        for (int i = 0; i < arrLength; i++) {
            vibratorPattern[i] = 1000;
        }
        vibrator.vibrate(vibratorPattern, -1);
    }

    @OnClick({R.id.alarm_cancel_btn, R.id.alarm_ok_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alarm_cancel_btn:
                vibrator.cancel();
                break;
            case R.id.alarm_ok_btn:
                vibrator.cancel();
                break;
        }
    }
}



package com.example.musictestmentoring;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    // 뮤직 서비스 함수를 상수로
    private static final String TAG = MusicService.class.getSimpleName();

    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        // 뮤직 플레이
        mediaPlayer=MediaPlayer.create(this,R.raw.sample);
        mediaPlayer.setLooping(true); // 반복 재생
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        // 호출될 때 뮤직 재생
        mediaPlayer.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        mediaPlayer.stop();

        super.onDestroy();
    }
}

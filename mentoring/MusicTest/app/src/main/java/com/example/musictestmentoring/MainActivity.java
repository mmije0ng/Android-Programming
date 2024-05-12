package com.example.musictestmentoring;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    Button mBtnStart;
    Button mBtnStop;
    LottieAnimationView mLottieIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
     //   EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    /*    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }); */

        mLottieIcon= findViewById(R.id.lottie_icon);

        mBtnStart= findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusicService();
                // start 클릭 시 로티 애니메이션 동작
                mLottieIcon.playAnimation();
            }
        });

        mBtnStop= findViewById(R.id.btn_stop);
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusicService();
                mLottieIcon.pauseAnimation();
            }
        });
    }

    private void startMusicService(){
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        Log.d(TAG,"startMusicService");
    }

    private void stopMusicService(){
        Intent intent = new Intent(this, MusicService.class);
        stopService(intent);
        Log.d(TAG,"stopMusicService");
    }

}
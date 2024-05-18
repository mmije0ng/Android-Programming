package com.example.practice12

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private val channelID = "service_channel"

    // 채널 만들기
    override fun onCreate() {
        super.onCreate()

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelID, "service channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "notification channel for service."
            NotificationManagerCompat.from(this).createNotificationChannel(channel)
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, channelID)
        .setContentTitle("Foreground Service")
        .setContentText("Foreground Service running")
        .setSmallIcon(R.drawable.baseline_cloud_queue_24)
        .setOnlyAlertOnce(true)  // importance 에 따라 알림 소리가 날 때, 처음에만 소리나게 함
        .build()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 알림이 띄워지고 포그라운드 실행
        startForeground(10,createNotification())

        // 파일 저장
        val repo=MyRepository(this)

        val str=intent?.getStringExtra("data")
        repo.valueInternal=str?:""

        return super.onStartCommand(intent, flags, startId)
    }
}
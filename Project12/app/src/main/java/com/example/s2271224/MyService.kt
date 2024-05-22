package com.example.s2271224

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.io.File
import android.os.Environment
import com.example.practice12.MyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService(): Service() {
    private val binder = LocalBinder()
    private val notificationID = 10
    private val channelID = "service_channel"
    private lateinit var context: Context

    var initValue: Int = 0

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService() = this@MyService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    // 채널 만들기
    override fun onCreate() {
        super.onCreate()

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelID, "service channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "notification channel for service."
            NotificationManagerCompat.from(this).createNotificationChannel(channel)
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, channelID)
        .setContentTitle("Foreground Service")
        .setContentText("Foreground Service running")
        .setSmallIcon(R.drawable.baseline_notification_important_24)
        .setOnlyAlertOnce(true)  // importance 에 따라 알림 소리가 날 때, 처음에만 소리나게 함
        .build()


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("MyService:onStartCommand $startId")

        initValue = intent?.getIntExtra("init", 500) ?: 0 // 기본값은 100으로 설정
        startForeground(notificationID, createNotification())  // 포그라운드 실행
        val repo = MyRepository(this)

        CoroutineScope(Dispatchers.Default).apply {
            launch {
                repo.valueInternal = initValue.toString()
            }
        }

        println("서비스 txt 값: "+initValue.toString())

        stopSelf(startId)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }
}
package com.example.practice13

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.CallLog
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

// 1. 브로드캐스트(방송) 리시버 - 문자 수신
// 2. 컨텐트 리졸버 - 콜 로그 읽기
class MainActivity : AppCompatActivity() {
    private val broadcastReceiver = MyBroadCastReceiver()

    class MyBroadCastReceiver: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            println("onReceive ###### ${intent?.action}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 하나의 권한 요청
      //  requestSinglePermission(android.Manifest.permission.RECEIVE_SMS)

        // 문자 수신과 콜로그 권한 요청
        requestMultiplePermission(arrayOf(android.Manifest.permission.RECEIVE_SMS,
            android.Manifest.permission.READ_CALL_LOG))
    }

    // receiver 등록
    override fun onStart() {
        super.onStart()

        // IntentFilter에 원하는 방송 액션 추가
        ContextCompat.registerReceiver(this, broadcastReceiver, IntentFilter("android.provider.Telephony.SMS_RECEIVED"),
            ContextCompat.RECEIVER_EXPORTED)

        readCallLog()
    }

    // unregister
    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    private fun readCallLog(){
        // 권한이 없다면 return
        if(checkSelfPermission(android.Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED)
            return

        val projection = arrayOf(CallLog.Calls._ID, CallLog.Calls.NUMBER)
        val cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, projection, null, null, null)

        println("readCallLog 실행")

        cursor?.use {
            val idx = it.getColumnIndex(CallLog.Calls.NUMBER)
            // CallLog에 있는 번호를 출력
            while (it.moveToNext()){
                val number = it.getString(idx)
                println("number: $number")
            }
        }
    }

    // 동적 권한 받기
    private fun requestSinglePermission(permission: String) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
            return

        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it == false) { // permission is not granted!
                AlertDialog.Builder(this).apply {
                    setTitle("Warning")
                    setMessage("Warning")
                }.show()
            }
        }

        if (shouldShowRequestPermissionRationale(permission)) {
            // you should explain the reason why this app needs the permission.
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                setMessage("Reason")
                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(permission) }
                setNegativeButton("Deny") { _, _ -> }
            }.show()
        } else {
            // should be called in onCreate()
            requestPermLauncher.launch(permission)
        }
    }

    // 여러개의 권한 요청
    private fun requestMultiplePermission(perms: Array<String>) {
        val requestPerms = perms.filter { checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
        if (requestPerms.isEmpty())
            return

        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val noPerms = it.filter { item -> item.value == false }.keys.toMutableSet()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                if (it.contains(android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED) || it.contains(android.Manifest.permission.READ_MEDIA_IMAGES)) {
                    // READ_MEDIA_VISUAL_USER_SELECTED 와 READ_MEDIA_IMAGES 는 둘 중에 하나만 권한 부여받게 됨
                    // 따라서 둘 중에 하나만 있다면 noPerms에 다른 권한은 제거
                    noPerms.remove(android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
                    noPerms.remove(android.Manifest.permission.READ_MEDIA_IMAGES)
                }
            }
            if (noPerms.isNotEmpty()) { // there is a permission which is not granted!
                AlertDialog.Builder(this).apply {
                    setTitle("Warning")
                    setMessage("Warning")
                }.show()
            }
        }

        val showRationalePerms = requestPerms.filter {shouldShowRequestPermissionRationale(it)}
        if (showRationalePerms.isNotEmpty()) {
            // you should explain the reason why this app needs the permission.
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                setMessage("Reason")
                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(requestPerms.toTypedArray()) }
                setNegativeButton("Deny") { _, _ -> }
            }.show()
        } else {
            // should be called in onCreate()
            requestPermLauncher.launch(requestPerms.toTypedArray())
        }
    }
}
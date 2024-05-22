package com.example.s2271224

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import java.io.File
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private var myService: MyService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonGet = findViewById<Button>(R.id.buttonGet)
        val textView = findViewById<TextView>(R.id.textView)
        textView .text = "0"

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
            requestSinglePermission(Manifest.permission.POST_NOTIFICATIONS)
        }

        buttonGet.setOnClickListener {
            System.out.println("textView: "+myService?.initValue)
            textView.text = myService?.initValue.toString()
         //   Intent(this, MyService::class.java).putExtra("init",500)
        }

        Intent(this, MyService::class.java).also { intent ->
            intent.putExtra("init", 0) // Service 시작 시 init 값을 전달
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }

        // 2. WorkManager
        WorkManager.getInstance(this).getWorkInfosForUniqueWorkLiveData(MyWorker.name)
            .observe(this) { workInfo ->
                if (workInfo.isNotEmpty()) {
                    when (workInfo[0].state) {
                        WorkInfo.State.ENQUEUED -> println("Worker enqueued!")
                        WorkInfo.State.RUNNING -> println("Worker running!")
                        WorkInfo.State.SUCCEEDED -> println("Worker succeeded!")  // only for one time worker
                        WorkInfo.State.CANCELLED -> println("Worker cancelled!")
                        else -> println(workInfo[0].state)
                    }
                }
            }

        startWorker()
    }

    private fun startWorker() {
        val constraints = Constraints.Builder().apply {
     //       setRequiredNetworkType(NetworkType.UNMETERED) // un-metered network such as WiFi
            setRequiresBatteryNotLow(true)
        }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            MyWorker.name,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }

    private fun stopWorker() {
        // to stop the MyWorker
        WorkManager.getInstance(this).cancelUniqueWork(MyWorker.name)
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = (service as MyService.LocalBinder).getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
        }
    }

    // onStart에서 MyService를 bindService
    override fun onStart() {
        super.onStart()
        Intent(this, MyService::class.java).also {
            bindService(it, serviceConnection, BIND_AUTO_CREATE)
        }
    }

    // onStop에서 MyService를 unbind
    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
    }

    // 권한요청
    private fun requestSinglePermission(permission: String) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
            return

        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it == false) { // permission is not granted!
                AlertDialog.Builder(this).apply {
                    setTitle("Warning")
                    setMessage(getString(R.string.no_permission, permission))
                }.show()
            }
        }

        if (shouldShowRequestPermissionRationale(permission)) {
            // you should explain the reason why this app needs the permission.
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                setMessage(getString(R.string.req_permission_reason, permission))
                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(permission) }
                setNegativeButton("Deny") { _, _ -> }
            }.show()
        } else {
            // should be called in onCreate()
            requestPermLauncher.launch(permission)
        }
    }
}
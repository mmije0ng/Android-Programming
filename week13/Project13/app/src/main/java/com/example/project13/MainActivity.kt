package com.example.project13

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CallLog
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project13.MainActivity.MyBroadCastReceiver.Companion.ACTION_MY_BROADCAST

class MainActivity : AppCompatActivity() {
    private val broadcastReceiver = MyBroadCastReceiver()
    private lateinit var textViewBroadcast: TextView
    val viewModel: MyViewModel by viewModels()

    class MyBroadCastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            println("브로드캐스트 리시버 액션: ${intent?.action}")
            showBroadcast(context, ACTION_MY_BROADCAST)
        }

        private fun showBroadcast(context: Context?, msg: String) {
            context?.let {
                val broadcastIntent = Intent("UPDATE_UI")
                broadcastIntent.putExtra("message", msg)
                LocalBroadcastManager.getInstance(it).sendBroadcast(broadcastIntent)
                println("showBroadcast: $msg")
            }
        }

        companion object {
            const val ACTION_MY_BROADCAST = "ACTION_MY_BROADCAST"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewBroadcast = findViewById(R.id.textViewBroadcast)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
 //       val viewModel by viewModels<MyViewModel>()

        val adapter = CustomAdapter(viewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        readCallLog()

  //      viewModel.itemsListData.observe(this) {
       //     ItemEvent.ADD -> adapter.notifyItemInserted(viewModel.itemsEventPos)
         //   adapter.notifyItemInserted(viewModel.itemsEventPos)
    ///        adapter.notifyDataSetChanged()
    //    }

        registerForContextMenu(recyclerView)

        // Register local broadcast receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(
            uiUpdateReceiver,
            IntentFilter("UPDATE_UI")
        )

        // Request single permission
        requestSinglePermission(android.Manifest.permission.READ_CALL_LOG)
    }

    private fun startBroadcastReceiver() {
        IntentFilter().also {
            it.addAction("android.provider.Telephony.CALL_LOG")
            it.addAction(ACTION_MY_BROADCAST)
            ContextCompat.registerReceiver(this, broadcastReceiver, it, ContextCompat.RECEIVER_EXPORTED)
            // NOT_EXPORTED로 하면 앱(자신 포함)에서 보내는 방송은 못 받고 시스템이 보내는 것 만 받음
            // EXPORTED로 해야 시스템 뿐 아니라 다른 앱(문자, MY_BROADCAST)도 수신 가능
        }
    }

    override fun onStart() {
        super.onStart()
        startBroadcastReceiver()

        readCallLog()
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(uiUpdateReceiver)
    }

    private val uiUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.getStringExtra("message") ?: "NO_ACTION"
            textViewBroadcast.text = message
            println("uiUpdateReceiver: $message")
        }
    }

    private fun readCallLog() {
        if (checkSelfPermission(android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED)
            return

        val projection = arrayOf(CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.TYPE)
        val cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, projection, null, null, null)

        println("readCallLog 실행")

        cursor?.use { cursor ->
            val phoneNumberIdx = cursor.getColumnIndex(CallLog.Calls.NUMBER)
            val typeIdx = cursor.getColumnIndex(CallLog.Calls.TYPE)

            while (cursor.moveToNext()) {
                val phoneNumber = cursor.getString(phoneNumberIdx)
                val type = cursor.getInt(typeIdx)

                val typeString = when (type) {
                    CallLog.Calls.INCOMING_TYPE -> "INCOMING"
                    CallLog.Calls.OUTGOING_TYPE -> "OUTGOING"
                    CallLog.Calls.MISSED_TYPE -> "MISSED"
                    else -> "알 수 없음"
                }

                // 데이터 추가
                viewModel.addItem(Item(phoneNumber, typeString))

                println("전화 번호: $phoneNumber")
                println("통화 유형: $typeString")
            }
        }
    }


    private fun requestSinglePermission(permission: String) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
            return

        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it == false) {
                AlertDialog.Builder(this).apply {
                    setTitle("Warning")
                    setMessage("Warning")
                }.show()
            }
        }

        if (shouldShowRequestPermissionRationale(permission)) {
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                setMessage("Reason")
                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(permission) }
                setNegativeButton("Deny") { _, _ -> }
            }.show()
        } else {
            requestPermLauncher.launch(permission)
        }
    }
}

package com.example.project6

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("########## MainActivity - onCreate ##########")

        val editText =findViewById<EditText>(R.id.editText)

        // SecondActivity에서 보낸 데이터 받기
        // 콜백 함수
        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val msg = it.data?.getStringExtra("resultData")?:"0"
            editText.setText(msg)
            Snackbar.make(findViewById(R.id.button), msg, Snackbar.LENGTH_SHORT).show()
        }

        // 버튼 클릭시 SecondActivity로 이동, 데이터 전송
        findViewById<Button>(R.id.button)?.setOnClickListener {
            // SecondActivity 인텐트 지정
            val intent = Intent(this, SecondActivity::class.java)

            val inputData = editText.text.toString()
            Log.i("inputData", "editText 입력 데이터: "+inputData)

            // 인텐트에 데이터를 포함해 전송, key value
            intent.putExtra("inputData",inputData)

            // SecondActivity 시작
            activityResult.launch(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        println("########## MainActivity - onStart ##########")
    }

    override fun onResume() {
        super.onResume()
        println("########## MainActivity - onResume ##########")
    }

    override fun onPause() {
        super.onPause()
        println("########## MainActivity - onPause ##########")
    }

    override fun onStop() {
        super.onStop()
        println("########## MainActivity - onStop ##########")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("########## MainActivity - onDestroy ##########")
    }
}
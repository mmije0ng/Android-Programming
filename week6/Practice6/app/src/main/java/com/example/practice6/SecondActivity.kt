package com.example.practice6

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.addCallback

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        println("########## SecondActivity - onCreate ##########")

        // 데이터 받기
        val msg = intent?.getStringExtra("userdata")?:""
        findViewById<TextView>(R.id.textView).text = "${msg}"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 백 버튼 클릭, 콜백 함수 지정
        onBackPressedDispatcher.addCallback(this, true) {
            // MainActivity로 데이터 전송
            val resultIntent = Intent()
            resultIntent.putExtra("resultdata","result string")
            setResult(RESULT_OK, resultIntent)

            finish() // 액티비티 종료, MainActivity로 돌아감
        }
    }

    override fun onStart() {
        super.onStart()
        println("########## SecondActivity - onStart ##########")
    }

    override fun onResume() {
        super.onResume()
        println("########## SecondActivity - onResume ##########")
    }

    override fun onPause() {
        super.onPause()
        println("########## SecondActivity - onPause ##########")
    }

    override fun onStop() {
        super.onStop()
        println("########## SecondActivity - onStop ##########")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("########## SecondActivity - onDestroy ##########")
    }
}
package com.example.project6

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class SecondActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        println("########## SecondActivity - onCreate ##########")

        // 데이터 받기
        val msg = intent?.getStringExtra("inputData")?:"0"
        Log.i("inputData", "SecondActivity 받은 데이터: "+msg)
        val textView=findViewById<TextView>(R.id.textView)
        textView.text = "${msg}"

        // view 모델 객체 가져오기
        val viewModel = ViewModelProvider(this, MyViewModelFactory(Integer.parseInt(msg)))[MyViewModel::class.java]
        viewModel.countLiveData.observe(this){
            textView.text = "${it}"
        }

        // 데이터 증가
        findViewById<Button>(R.id.buttonInc)?.setOnClickListener {
            viewModel.increaseCount()
        }

        // 데이터 감소
        findViewById<Button>(R.id.buttonDec)?.setOnClickListener {
            viewModel.decreaseCount()
        }

        // 백 버튼 클릭, 콜백 함수 지정
        onBackPressedDispatcher.addCallback(this, true) {
            // MainActivity로 데이터 전송
            val resultIntent = Intent()
            resultIntent.putExtra("resultData",textView.text)
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
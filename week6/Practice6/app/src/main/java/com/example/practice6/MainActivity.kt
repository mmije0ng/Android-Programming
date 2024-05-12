package com.example.practice6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("########## MainActivity - onCreate ##########")

        // SecondActivity에서 보낸 데이터 받기
        // 콜백 함수
        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val msg = it.data?.getStringExtra("resultdata")?:""
            Snackbar.make(findViewById(R.id.button), msg, Snackbar.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.button)?.setOnClickListener {
            // SecondActivity 인텐트 지정
            val intent = Intent(this, SecondActivity::class.java)

            // 인텐트에 데이터를 포함해 전송, key value
            intent.putExtra("userdata","hello")

            // SecondActivity 시작
            activityResult.launch(intent)
        }

        // view 모델 객체 가져오기
        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        viewModel.countLiveData.observe(this){
            findViewById<TextView>(R.id.textView3).text = "${it}"
        }

        findViewById<Button>(R.id.button2)?.setOnClickListener {
            viewModel.increaseCount()
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
package com.example.practice10

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // recyclerView 가져오기
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // viewModel 생성
        val viewModel by viewModels<MyViewModel>()
        viewModel.addItem(Item("John", "Baker"))
        viewModel.addItem(Item("미정", "박"))
        viewModel.addItem(Item("안드로이드", "13"))

        // CustomAdapter 생성
        val adapter = CustomAdapter(viewModel)
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        // floatingAction 버튼
        findViewById<FloatingActionButton>(R.id.floatingActionButton)?.setOnClickListener{
            viewModel.addItem(Item("test", "test"))

            // 데이터 변경 사항 알리기
            // 가급적x
            adapter.notifyDataSetChanged()
        }

        //아이템 클릭시 다이얼로그 띄우기
        viewModel.clickItem.observe(this){
            val item = viewModel.items[it]
            ItemDialog(item).show(supportFragmentManager,"")
        }
    }
}
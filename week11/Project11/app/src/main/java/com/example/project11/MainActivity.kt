package com.example.project11

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project11.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // viewModel 생성
        val viewModel = ViewModelProvider(this, MyViewModelFactory(this))
            .get(MyViewModel::class.java)

        // recyclerView 가져오기
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // CustomAdapter 생성
        val adapter = CustomAdapter(viewModel)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        // floatingAction 버튼
        findViewById<FloatingActionButton>(R.id.floatingActionButton)?.setOnClickListener{
            ItemDialog().show(supportFragmentManager, "ItemDialog")
        }

        viewModel.myData.observe(this){
           when(viewModel.itemsEvent){
                ItemEvent.ADD -> adapter.notifyItemInserted(adapter.itemCount)
                ItemEvent.EDIT -> adapter.notifyItemChanged(adapter.getEditIndex())
                ItemEvent.DELETE -> adapter.notifyItemRemoved(adapter.itemCount-1)
            }

         //   recyclerView.adapter?.notifyDataSetChanged()
        }

        viewModel.itemClickEvent.observe(this) {
            ItemDialog(it).show(supportFragmentManager, "ItemDialog")
        }

        registerForContextMenu(recyclerView)
    }

    // 컨텍스트 메뉴 생성이 필요할 때
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.ctx_menu, menu)
    }

    // 컨텍스트 메뉴 항목 선택시 호출
    override fun onContextItemSelected(item: MenuItem): Boolean {
        // viewModel 생성
        val viewModel = ViewModelProvider(this, MyViewModelFactory(this))
            .get(MyViewModel::class.java)

        when(item.itemId){
            R.id.edit -> viewModel.itemClickEvent.value = viewModel.longClickItem
            R.id.delete -> viewModel.deleteItem(viewModel.longClickItem)
            else -> return false
        }
        return true
    }
}
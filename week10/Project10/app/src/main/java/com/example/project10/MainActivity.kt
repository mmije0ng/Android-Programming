package com.example.project10

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    // viewModel 생성
    private val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        viewModel.itemsListData.observe(this) {
            when(viewModel.itemsEvent){
                ItemEvent.ADD -> adapter.notifyItemInserted(viewModel.itemsEventPos)
                ItemEvent.EDIT -> adapter.notifyItemChanged(viewModel.itemsEventPos)
                ItemEvent.DELETE -> adapter.notifyItemRemoved(viewModel.itemsEventPos)
            }
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
        when(item.itemId){
            R.id.edit -> viewModel.itemClickEvent.value = viewModel.itemLongClick
            R.id.delete -> viewModel.deleteItem(viewModel.itemLongClick)
            else -> return false
        }
        return true
    }
}
package com.example.practice10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val viewModel: MyViewModel): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // ViewHolder 클래스
    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // item_layout 연결
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_layout, parent, false)
        // ViewHolder 생성
        val viewHolder = ViewHolder(view)

        // 클릭시 다이얼로그 띄우기
        view.setOnClickListener{
            viewModel.clickItem.value = viewHolder.adapterPosition
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val text1 = view.findViewById<TextView>(R.id.textView1)
        val text2 = view.findViewById<TextView>(R.id.textView2)

        text1.text = viewModel.items[position].firstname
        text2.text = viewModel.items[position].lastname
    }

    override fun getItemCount(): Int {
        return viewModel.items.size
    }
}
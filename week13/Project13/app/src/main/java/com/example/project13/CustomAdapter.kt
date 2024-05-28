package com.example.project13

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val viewModel: MyViewModel) :

    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    }

    // ViewHolder 생성, ViewHolder 는 View 를 담는 상자
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val view = layoutInflater.inflate(R.layout.item_layout, viewGroup, false)
        val viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // position: 항목 번호, 0에서 시작
        val textView = viewHolder.itemView.findViewById<TextView>(R.id.textView)
        val textView2 = viewHolder.itemView.findViewById<TextView>(R.id.textView2)

        val item = viewModel.items[position]
        textView.text = item.phoneNumber
        textView2.text = item.state
    }

    override fun getItemCount() = viewModel.itemsSize
}
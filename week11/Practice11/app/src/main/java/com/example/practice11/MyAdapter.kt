package com.example.practice11

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val viewModel:MyViewModel): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemView: View): RecyclerView.ViewHolder(itemView){
        fun setContent(pos: Int){
            val textView = itemView.findViewById<TextView>(R.id.textView)
            textView.text = viewModel.myData.value?.get(pos)?.name?:""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_layout, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContent(position)
    }

    override fun getItemCount(): Int {
        return viewModel.myData.value?.size?:0
    }
}
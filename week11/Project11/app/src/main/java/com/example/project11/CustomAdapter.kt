package com.example.project11

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val viewModel: MyViewModel): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // ViewHolder 클래스
    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun setContents(pos: Int){
            val textView = view.findViewById<TextView>(R.id.textView)
            val textView2 = view.findViewById<TextView>(R.id.textView2)

            with (viewModel.myData.value?.get(pos)) {
                textView.text = this?.name
                textView2.text = this?.address

                System.out.println( "setContents: "+textView.text )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // item_layout 연결
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_layout, parent, false)

        // ViewHolder 생성
        val viewHolder = ViewHolder(view)

        // 클릭시 다이얼로그 띄우기
        view.setOnClickListener{
            viewModel.itemClickEvent.value=viewHolder.adapterPosition
        }

        // 롱클릭시
        view.setOnLongClickListener {
            viewModel.longClickItem=viewHolder.adapterPosition
            false
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun getItemCount(): Int {
        return viewModel.myData.value?.size ?: 0
    }

    fun getEditIndex(): Int{
        return viewModel.editPos
    }
}
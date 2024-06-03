package com.example.project14


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val viewModel: MyViewModel) :

    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val view = layoutInflater.inflate(R.layout.item_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text= viewModel.items[position]
        System.out.println("viewModel.items[${position}]: ${viewModel.items[position]}")


        /*viewModel.response.observe(viewHolder.itemView.context as LifecycleOwner) { response ->
            viewHolder.textView.setText(response[position])
        }

         */
    }

    override fun getItemCount() = viewModel.itemsSize
}
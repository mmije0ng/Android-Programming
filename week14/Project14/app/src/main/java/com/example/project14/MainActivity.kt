package com.example.project14

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        val viewModel : MyViewModel by viewModels {
            MyViewModelFactory(this)
        }

        val adapter = MyAdapter(viewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        if (!isNetworkAvailable())
            System.out.println("인터넷 연결 안 됨")

        // 소켓 연결
        viewModel.refreshJavaSocket()

        val buttonQuery = findViewById<Button>(R.id.buttonQuery)
        buttonQuery.setOnClickListener {
            val editUsername = findViewById<EditText>(R.id.editUsername)
            System.out.println("button: ${editUsername.text.toString()}")
            viewModel.refreshRetrofit(editUsername.text.toString())
        }

        viewModel.response.observe(this) {
            adapter.notifyDataSetChanged()
        }

     /*   viewModel.response.observe(this) {
            val editUsername = findViewById<EditText>(R.id.editUsername)
            editUsername.setText(it.toString())
        }

      */
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        println("${actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)}, ${actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)}, " +
                "${actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)}")
        return actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }
}
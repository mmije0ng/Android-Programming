package com.example.practice5

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
     /*   ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        } */


        val btnOK = findViewById<Button>(R.id.btnOK)
        val txtViewTitle = findViewById<TextView>(R.id.txtViewTItle)
        val txtViewName = findViewById<TextView>(R.id.txtViewName)
        val editText = findViewById<EditText>(R.id.editText)

        btnOK.setOnClickListener{
            val str = editText.text.toString()
            txtViewTitle.text =  str
            txtViewName.text= resources.getString(R.string.ok)
        }

    }
}
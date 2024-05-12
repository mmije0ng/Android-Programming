package com.example.project9

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class Home: Fragment(R.layout.fragment_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     //   view.findViewById<TextView>(R.id.textView).text="Home"
    }
}

class Page1: Fragment(R.layout.fragment_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text="Page1Fragment"
        view.findViewById<TextView>(R.id.textView2).text="2271224"
    }
}

class Page2: Fragment(R.layout.fragment_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text="Page2Fragment"
        view.findViewById<TextView>(R.id.textView2).text="박미정"
    }
}

class Dialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable=false // ok 버튼 클릭시만 다이얼로그 종료

        return AlertDialog.Builder(requireActivity()).apply {
            setTitle("2271224")
            setMessage("박미정")
            setPositiveButton("OK") {dialog, id -> println("OK") }
        }.create()

        //return super.onCreateDialog(savedInstanceState)
    }
}
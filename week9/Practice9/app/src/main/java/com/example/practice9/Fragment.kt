package com.example.practice9

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class MyDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable=false // ok 버튼 클릭시만 다이얼로그 종료

        return AlertDialog.Builder(requireActivity()).apply {
            setMessage("Dialog Message")
            setPositiveButton("OK") {dialog, id -> println("OK") }
        }.create()

        //return super.onCreateDialog(savedInstanceState)
    }
}

class Home: Fragment(R.layout.fragment_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text="Home"
    }
}

class Frag1: Fragment(R.layout.fragment_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text="Frag1"
    }
}

class Frag2: Fragment(R.layout.fragment_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text="Frag2"
    }
}
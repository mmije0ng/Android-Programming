package com.example.practice7

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController

class MyViewModel: ViewModel(){
    val myValue = MutableLiveData<String>()
    // 초기 값
    init{
        myValue.value="hello"
    }

}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

  //      val viewModel: MyViewModel by viewModels()
    }
}

class Frag1Fragment: Fragment(R.layout.frag1_layout){
    // 반드시 인자로 View 넘겨 줘야함
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button).setOnClickListener {
            // 액티비티가 해당 뷰모델의 소유자
            val viewModel: MyViewModel by activityViewModels()
            viewModel.myValue.value="frag1_to_frag2"

            // fragment1 -> fragment2 로 이동
            findNavController().navigate(R.id.action_frag1Fragment_to_frag2Fragment)
        }

        val viewModel: MyViewModel by activityViewModels()
        // 프래그먼트의 뷰가 보여지고 있을 때
        // 프래그 2->1
        // 프래그 1->2 로 갈때도 출력
        // 뷰가 이동하기 전에 아직 살아있기 때문
        viewModel.myValue.observe(viewLifecycleOwner){
            println("Frag1: ############ {$it} ###########")
        }

    }
}

class Frag2Fragment: Fragment(R.layout.frag2_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button2).setOnClickListener {
            // 액티비티가 해당 뷰모델의 소유자
            val viewModel: MyViewModel by activityViewModels()
            viewModel.myValue.value="frag2_to_frag1"

            // fragment2 -> fragment1 로 이동
            findNavController().navigate(R.id.action_frag2Fragment_to_frag1Fragment)
        }

        val viewModel: MyViewModel by activityViewModels()
        // 프래그먼트의 뷰가 보여지고 있을 때
        viewModel.myValue.observe(viewLifecycleOwner){
            println("Frag2: ############ {$it} ###########")
        }
    }
}
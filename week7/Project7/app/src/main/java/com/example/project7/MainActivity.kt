package com.example.project7

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

// home 프래그먼트
class HomeFragment: Fragment(R.layout.home){
    // 반드시 인자로 View 넘겨 줘야함
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button).setOnClickListener {
            // 액티비티가 해당 뷰모델의 소유자
            val viewModel: MyViewModel by activityViewModels {
                MyViewModelFactory(0)
            }

            // 뷰 모델 livedata 값 증가시키기
            viewModel.increaseCount()

            // home -> fragment1 로 이동
            findNavController().navigate(R.id.action_homeFragment_to_frag1Fragment)
        }

        val viewModel: MyViewModel by activityViewModels {
            MyViewModelFactory(0)
        }

        viewModel.countLiveData.observe(viewLifecycleOwner){
            // textView의 text를 livedata 값으로 설정
            view.findViewById<TextView>(R.id.textView).text = viewModel.countLiveData.value.toString()
        }

    }
}

// fra1
class Frag1Fragment: Fragment(R.layout.nav1_fragment){
    // 반드시 인자로 View 넘겨 줘야함
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button).setOnClickListener {
            val viewModel: MyViewModel by activityViewModels()

            // 뷰 모델 livedata 값 증가시키기
            viewModel.increaseCount()

            // fragment1  -> fragment2 로 이동
            findNavController().navigate(R.id.action_frag1Fragment_to_frag2Fragment)
        }

        val viewModel: MyViewModel by activityViewModels()

        viewModel.countLiveData.observe(viewLifecycleOwner){
            // textView의 text를 livedata 값으로 설정
            view.findViewById<TextView>(R.id.textView).text = viewModel.countLiveData.value.toString()
        }

    }
}

// frag2
class Frag2Fragment: Fragment(R.layout.nav2_fragment){
    // 반드시 인자로 View 넘겨 줘야함
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button).setOnClickListener {
            // 액티비티가 해당 뷰모델의 소유자
            val viewModel: MyViewModel by activityViewModels()

            // 뷰 모델 livedata 값 증가시키기
            viewModel.increaseCount()

            // fragment1  -> fragment2 로 이동
            findNavController().navigate(R.id.action_frag2Fragment_to_homeFragment)
        }

        val viewModel: MyViewModel by activityViewModels()

        viewModel.countLiveData.observe(viewLifecycleOwner){
            // textView의 text를 livedata 값으로 설정
            view.findViewById<TextView>(R.id.textView).text = viewModel.countLiveData.value.toString()
        }
    }
}
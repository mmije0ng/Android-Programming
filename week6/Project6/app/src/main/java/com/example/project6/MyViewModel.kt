package com.example.project6

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModel(count: Int): ViewModel() {
    // MVVM LivaData
    val countLiveData = MutableLiveData<Int>()

    // ViewModel 생성자
    init{
        countLiveData.value=count
        Log.i("countLiveData", "countLiveData 초기값: "+countLiveData.value)
   }

    fun increaseCount() {
        countLiveData.value = (countLiveData.value ?: 0) + 1
        Log.i("buttonInc", "ViewModel increaseCount: "+countLiveData.value)
    }

    fun decreaseCount(){
        countLiveData.value = (countLiveData.value ?: 0) - 1
        Log.i("buttonInc", "ViewModel decreaseCount: "+countLiveData.value)
    }
}

// ViewModelProvider.Factory로 생성자 인자를 받을 수 있는
// 커스텀 ViewModelProvider 생성
class MyViewModelFactory(private val count: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MyViewModel(count) as T
    }
}
package com.example.practice6

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    // MVVM LivaData
    val countLiveData = MutableLiveData<Int>()

    init{
        countLiveData.value = 0
    }

    fun increaseCount(){
        countLiveData.value = (countLiveData.value?:0) +1
    }
}
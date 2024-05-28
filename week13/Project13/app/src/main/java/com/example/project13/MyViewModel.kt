package com.example.project13

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Item(val phoneNumber: String?, val state: String?)
enum class ItemEvent { ADD, UPDATE, DELETE }


class MyViewModel : ViewModel() {
    val itemsListData = MutableLiveData<ArrayList<Item>>()
    val items = ArrayList<Item>()

    val itemsSize
        get() = items.size

    fun addItem(item: Item) {
        items.add(item)
        itemsListData.value = ArrayList(items) // 데이터를 변경한 후에 LiveData를 업데이트
    }
}
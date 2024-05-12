package com.example.project10

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Item(val name: String, val address: String)

enum class ItemEvent { ADD, EDIT, DELETE }

class MyViewModel : ViewModel() {
    val itemsListData = MutableLiveData<ArrayList<Item>>()
    val items = ArrayList<Item>()
    var itemsEvent = ItemEvent.ADD // 변경 종류(추가/수정/삭제)를 기록하기 위한 변수
    var itemsEventPos = -1 // 변경된 데이터 위치를 기록하기 위한 변수
    val itemClickEvent = MutableLiveData<Int>()
    var itemLongClick = -1

    fun addItem(item: Item) {
        itemsEvent = ItemEvent.ADD // 데이터가 추가됨
        itemsEventPos = items.size // 추가될 때는 마지막에 추가되므로 현재 리스트 크기를 사용
        items.add(item)
        itemsListData.value = items
    }

    fun editItem(pos: Int, item: Item) {
        itemsEvent = ItemEvent.EDIT // 데이터가 수정됨
        itemsEventPos = pos // 수정된 데이터 위치
        items[pos] = item
        itemsListData.value = items
    }

    fun deleteItem(pos: Int) {
        itemsEvent = ItemEvent.DELETE // 데이터가 삭제됨
        itemsEventPos = pos // 삭제 직전의 위치
        items.removeAt(pos)
        itemsListData.value = items
    }
    fun getItem(pos: Int) =  items[pos]
}
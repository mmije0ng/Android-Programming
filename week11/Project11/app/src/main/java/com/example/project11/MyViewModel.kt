package com.example.project11

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

enum class ItemEvent { ADD, EDIT, DELETE }

class MyViewModel(private val repository: MyRepository) : ViewModel() {
    var longClickItem: Int = -1
    var editPos=0;

    val myData = repository.myDao.getAll()

    var itemsEvent = ItemEvent.ADD // 변경 종류(추가/수정/삭제)를 기록하기 위한 변수
    val itemClickEvent = MutableLiveData<Int>()

    fun addItem(name: String, address: String) {
        itemsEvent = ItemEvent.ADD // 데이터가 추가됨
        repository.addItem(name, address)
    }

    fun updateItem(pos: Int, name: String, address: String) {
        itemsEvent = ItemEvent.EDIT // 데이터가 수정됨
        editPos=pos; // 데이터 수정 인덱스
        myData.value?.let {
            repository.updateItem(Item(it[pos].id, name, address))
        }
    }

    fun deleteItem(pos: Int) {
        itemsEvent = ItemEvent.DELETE // 데이터가 삭제됨
        myData.value?.let {
            repository.deleteItem(it[pos].id)
        }
    }
}

class MyViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MyViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            MyViewModel(MyRepository(context)) as T
        else
            throw IllegalArgumentException()
    }
}
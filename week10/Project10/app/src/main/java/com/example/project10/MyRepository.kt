package com.example.storingdatalab

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyRepository(context: Context) {
    val myDao = MyDatabase.getDatabase(context).getMyDAO()

    fun addItem(name: String, address: String) {
        CoroutineScope(Dispatchers.IO).launch {
            myDao.insert(Item(0, name, address))
        }
    }

    fun updateItem(item: Item) {
        CoroutineScope(Dispatchers.IO).launch {
            myDao.update(item)
        }
    }

    fun deleteItem(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            myDao.delete(Item(id, "", ""))
        }
    }
}
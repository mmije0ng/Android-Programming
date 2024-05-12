package com.example.practice11

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

// 파일을 읽거나 쓰기
class MyRepository(val context: Context) {
    private val file = File(context.filesDir, "data.txt")
    private var data = mutableListOf<String>()

    val myDao = MyDatabase.getDatabase(context).getMyDAO()

    fun addData(value: String){
        CoroutineScope(Dispatchers.IO).launch {
            myDao.insert(Item(0,value, "address"))
        }
    }

  /*  fun readData(): List<String> {
        return if (file.exists()){
            val txt = file.readText(Charsets.UTF_8)
            data =  txt.split("/").toMutableList()
            data
        } else {
            listOf()
        }
    }

    fun addData(value: String){
        data.add(value)
        val str = data.joinToString ("/")
        file.writeText(str,Charsets.UTF_8)
    } */
}
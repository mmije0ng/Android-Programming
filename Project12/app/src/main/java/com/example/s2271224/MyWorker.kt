package com.example.s2271224

import android.content.Context
import android.os.Environment
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.practice12.MyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.TimeUnit

class MyWorker (private val context: Context, params:WorkerParameters): CoroutineWorker(context,params){
    private val notificationID = 5
    private val channelID = "worker foreground channel"

    override suspend fun doWork(): Result {
        val repo = MyRepository(context)

        println("worker 실행")

        val initValueFromFile = repo.valueInternal
        val toSave: Int = try {
            initValueFromFile.toInt() + 1
        } catch (e: Exception) {
            println("worker 예외 발생")
            0
        }

        repo.valueExternalWorker = toSave.toString()
        println("worker 실행 후 서비스 내부 파일: "+initValueFromFile.toString())
        println("worker 실행 후 worker.txt: "+repo.valueExternalWorker.toString())

        return Result.success()
    }

    companion object { // worker 식별자로 사용할 이름
        const val name = "com.example.s2271224.MyWorker"
    }
}
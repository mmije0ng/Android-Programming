package com.example.practice12

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class MyWorker(private val context:Context, params:WorkerParameters): CoroutineWorker(context,params) {

    //setForeground()

    override suspend fun doWork(): Result {
        val repo = MyRepository(context)
        repo.valueInternal="New value"

        return Result.success()
    }
}
package com.example.practice12

import android.content.Context
import java.io.File
import android.os.Environment

class MyRepository(context: Context) {
    private val fileInternal = File(context.filesDir, "serviceInternal.txt")
    private val fileExternal =
        if (isExternalStorageMounted)
            File(context.getExternalFilesDir(null), "serviceExternal.txt")
        else
            fileInternal

    private val fileExternalWorker =
        if (isExternalStorageMounted)
            File(context.getExternalFilesDir(null), "worker.txt")
        else
            fileInternal

    var valueInternal: String = readValue(fileInternal) // initialized by readValue
        set(v) {
            field = v
            writeValue(fileInternal, v) // write value whenever update the value
        }

    var valueExternal: String = readValue(fileExternal) // initialized by readValue
        set(v) {
            field = v
            writeValue(fileExternal, v)  // write value whenever update the value
        }

    var valueExternalWorker: String = readValue(fileExternalWorker) // initialized by readValue
        set(v) {
            field = v
            writeValue(fileExternalWorker, v)  // write value whenever update the value
        }

    private fun readValue(file: File) : String {
        return try {
            println("$file")
            // Internal Storage - /data/user/0/com.example.fileexample/files/appfile.txt
            // External Storage - /storage/emulated/0/Android/data/com.example.fileexample/files/appfile.txt
            file.readText(Charsets.UTF_8)
        } catch (e: Exception) {
            ""
        }
    }

    private fun writeValue(file: File, value: String) {
        file.writeText(value, Charsets.UTF_8)
    }

    private val isExternalStorageMounted: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return state == Environment.MEDIA_MOUNTED
        }
}
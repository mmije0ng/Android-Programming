package com.example.project14

import android.app.Application
import android.content.Context
import androidx.core.graphics.createBitmap
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.StringBuilder
import javax.net.SocketFactory

data class Owner(val login: String)
data class Repo(val name: String, val owner: Owner, val url: String)

interface RestApi {
    @GET("users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repo>
}

class MyViewModel(private val context: Context) : ViewModel() {
    private val baseURL = "https://api.github.com/"
    private val api: RestApi = with(Retrofit.Builder()) {
        baseUrl(baseURL)
        addConverterFactory(GsonConverterFactory.create())
        build()
    }.create(RestApi::class.java)

    val response = MutableLiveData<List<String>>()
    val items = ArrayList<String>()
    val itemsSize
        get() = items.size

    init {
        response.value = emptyList()  // 초기값 설정
    }

    fun refreshRetrofit(userName: String) {
        viewModelScope.launch {
            try {
                val repos = api.listRepos(userName)

                var repoNames =StringBuilder().apply {
                    repos.forEach {
                        items.add(it.name.toString())
                        System.out.println("items: ${it.name.toString() }")
                        response.value = ArrayList(items)
                    }
                }.toString()

                System.out.println("ViewModel repos: ${response.value }")

            } catch (e: Exception) {
                response.value = listOf("Failed to connect to the server ${e.message}")
            }
        }
    }

    fun refreshJavaSocket() {
        viewModelScope.launch {
            try {
                val sock = withContext(Dispatchers.IO) {
                    SocketFactory.getDefault().createSocket("google.com", 80)
                }
                val r = withContext(Dispatchers.IO) {
                    val istream = sock.getInputStream()
                    val ostream = sock.getOutputStream()
                    ostream.write("GET / \r\n".toByteArray())
                    ostream.flush()
                    istream.readBytes()
                }
                response.value = listOf(r.decodeToString())
                withContext(Dispatchers.IO) {
                    sock.close()
                }
            } catch (e: Exception) {
                response.value = listOf("Failed to connect to the server ${e.message}")
            }
        }
    }
}


class MyViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MyViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            MyViewModel(context.applicationContext) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.practice14

import android.content.Context
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import java.net.URL
import javax.net.ssl.HttpsURLConnection

data class Owner(val login: String)
data class Repo(val name: String, val owner: Owner, val url: String)

interface RestApi {
    @GET("users/{user}/repos")
    fun listReposCall(@Path("user") user: String): Call<List<Repo>>

}

interface RestApi2 {
    @GET("images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    suspend fun getImage(): ResponseBody
}


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 인터넷 접속 가능 여부 검사
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork

        // 현재 활성화된 네트워크
        val actNw = connectivityManager.getNetworkCapabilities(nw)

        val isConnected = actNw?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?:false
        if(!isConnected)
            Snackbar.make(findViewById(R.id.main), "인터넷 연결이 필요합니다.", Snackbar.LENGTH_SHORT).show()

        // 버튼 클릭시
        findViewById<Button>(R.id.button).setOnClickListener {
          //  downloadImage()
         //   refreshRetrofit("mmije0ng")
            retrofitGetImage()
        }
    }

    fun retrofitGetImage(){
        var baseURL = "https://www.google.com/"
        var api: RestApi2 = with(Retrofit.Builder()){
            baseUrl(baseURL)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }.create(RestApi2::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getImage()
            val bitmap = BitmapFactory.decodeStream(response.byteStream())
            withContext(Dispatchers.Main){
                findViewById<ImageView>(R.id.imageView).setImageBitmap(bitmap)
            }
        }
    }

    fun refreshRetrofit(userName: String){
        val baseURL = "https://api.github.com/"
        val api: RestApi = with(Retrofit.Builder()) {
            baseUrl(baseURL)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }.create(RestApi::class.java)

        // 콜백
        try {
            val repos = api.listReposCall(userName)
            repos.enqueue(object: Callback<List<Repo>>{
                override fun onResponse(p0: Call<List<Repo>>, p1: Response<List<Repo>>) {
                    if(p1.isSuccessful){
                        val repos2=p1.body()
                        val str = StringBuilder().apply {
                            repos2?.forEach {
                                append(it.name)
                                append(" - ")
                                append(it.owner.login)
                                append("\n")
                            }
                        }.toString()

                        System.out.println("str: ${str}")
                        findViewById<TextView>(R.id.textView).text=str
                    }

                    else
                        System.out.println("p1 fail")
                }

                override fun onFailure(p0: Call<List<Repo>>, p1: Throwable) {
                    System.out.println("onFailure")
                }
            }
            )

          /*  responseBy.value = "retrofit : ${repos.size} repositories"
            response.value = StringBuilder().apply {
                repos.forEach {
                    append(it.name)
                    append(" - ")
                    append(it.owner.login)
                    append("\n")
                }
            }.toString()
            */

        } catch (e: Exception) {

        }
    }



    // HTTPS URL Connection
    // Retrofit
    // 이미지 다운로드
    fun downloadImage() {
        val url = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"
        val imageView = findViewById<ImageView>(R.id.imageView)

        CoroutineScope(Dispatchers.IO).launch {
            val conn = URL(url).openConnection() as HttpsURLConnection

            val istream = conn.inputStream
            val rImg = BitmapFactory.decodeStream(istream)

            withContext(Dispatchers.Main){
                imageView.setImageBitmap(rImg)
            }

            conn.disconnect()
        }

        // 코루틴이 끝나기전 이미지가 리턴됨
        // 여기서 rImg를 사용해서는 안 됨
    }
}
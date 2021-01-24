package com.dengjian.annotationpractice.view

import android.annotation.SuppressLint
import org.jetbrains.anko.intentFor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import com.dengjian.annotationpractice.R
import com.dengjian.annotationpractice.dagger2.DaggerGlobalComponent
import com.dengjian.annotationpractice.dagger2.`object`.HttpObject
import com.dengjian.annotationpractice.dagger2.module.DatabaseModule
import com.dengjian.annotationpractice.dagger2.module.HttpModule
import com.dengjian.annotationpractice.myretrofit.MyRetrofit
import com.dengjian.annotationpractice.myretrofit.api.WeatherApi
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var httpObject: HttpObject
    private val mainHandler = Handler(Looper.getMainLooper())
    private lateinit var myRetrofit: MyRetrofit
    private lateinit var weatherApi: WeatherApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // test dagger2
//        DaggerGlobalComponent.create().inject(this)
        DaggerGlobalComponent.builder()
            .httpModule(HttpModule())
            .databaseModule(DatabaseModule())
            .build()
            .inject(this)
        Log.d(TAG, "httpObject = ${httpObject.hashCode()}")

        // test MyRetrofit
        myRetrofit = MyRetrofit.Builder().baseUrl("https://restapi.amap.com").build()
        weatherApi = myRetrofit.create(WeatherApi::class.java)
        try {
            getWeather(findViewById(R.id.tv_result))
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        // test bundle
//        val transferIntArray = listOf<Int>(0, 1, 2, 3, 4, 5)
//        startActivity(this.intentFor<SecondActivity>(
//                "mStringFromIntent" to "testStringValue",
//                "testInt" to 1,
//                "testLong" to 1000L,
//                "mIntArrayFromIntent" to transferIntArray
//        ))
    }

    private fun getWeather(tv: TextView) {
        val call: okhttp3.Call = weatherApi.getWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");
        call.enqueue(object: okhttp3.Callback {
            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Get weather failed: $e")
                mainHandler.post {
                    tv.text = "Get weather failed: $e"
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "onResponse get: ${response.body}")
                mainHandler.post {
                    tv.text = "Get weather success, response.body = ${response.body}"
                }
                response.close()
            }
        })
    }

    private fun postWeather(tv: TextView) {
        val call: okhttp3.Call = weatherApi.postWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");
        call.enqueue(object: okhttp3.Callback {
            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Post weather failed: $e")
                mainHandler.post {
                    tv.text = "Get weather failed, e = $e"
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "onResponse get: ${response.body}")
                mainHandler.post {
                    tv.text = "Get weather success, response.body = ${response.body}"
                }
                response.close()
            }
        })
    }
}
package com.dengjian.annotationpractice.javaruntime

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GsonTypeTest {
    class Response<T>(var data: T, var code: Int, var message: String) {
        override fun toString(): String {
            return "Response{data=$data, code=$code, message='$message'}"
        }
    }

    class Data(var result: String) {
        override fun toString(): String {
            return "Data{result=$result}"
        }
    }

    companion object {
        @JvmStatic
        fun main(vararg args: String) {
            val dataResponse: Response<Data>
                    = Response(Data("我是一个数据"), 1, "success")

            val gson = Gson()
            val json = gson.toJson(dataResponse)
            println(json)

            // 为什么TypeToken要定义为抽象类?
            val resp: Response<Data> =
                gson.fromJson(json, object : TypeToken<Response<Data>>(){}.type)
            println(resp.data.result)
        }
    }
}
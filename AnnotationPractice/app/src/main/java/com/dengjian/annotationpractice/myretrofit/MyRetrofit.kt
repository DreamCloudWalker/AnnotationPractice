package com.dengjian.annotationpractice.myretrofit

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

@Suppress("UNCHECKED_CAST")
class MyRetrofit(val callFactory: Call.Factory, val baseUrl: HttpUrl) {
    private val serviceMethodCache: ConcurrentHashMap<Method, ServiceMethod> = ConcurrentHashMap()

    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(service.classLoader, arrayOf<Class<*>>(service)
        ) { proxy, method, args ->
            // 解析这个method 上所有的注解信息
            val serviceMethod = loadServiceMethod(method)
            serviceMethod.invoke(args)
        } as T
    }

    private fun loadServiceMethod(method: Method): ServiceMethod {
        // 双重校验
        var result = serviceMethodCache[method]
        if (null != result) {
            return result
        }

        synchronized(serviceMethodCache) {
            result = serviceMethodCache[method]
            if (null == result) {
                result = ServiceMethod.Builder(this, method).build()
                serviceMethodCache[method] = result!!
            }
        }
        return result!!
    }

    class Builder {
        private var baseUrl: HttpUrl? = null
        //Okhttp->OkhttpClient
        private var callFactory : okhttp3.Call.Factory? = null

        fun callFactory(factory: okhttp3.Call.Factory): Builder {
            callFactory = factory
            return this
        }

        fun baseUrl(url: String): Builder {
            baseUrl = url.toHttpUrl()
            return this
        }

        fun build(): MyRetrofit {
            checkNotNull(baseUrl) { "Base URL required." }
            if (null == callFactory) {
                callFactory = OkHttpClient()
            }

            return MyRetrofit(callFactory!!, baseUrl!!)
        }
    }
}
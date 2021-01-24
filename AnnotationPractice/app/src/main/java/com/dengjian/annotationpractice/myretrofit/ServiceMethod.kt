package com.dengjian.annotationpractice.myretrofit

import com.dengjian.annotationpractice.myretrofit.annotation.Field
import com.dengjian.annotationpractice.myretrofit.annotation.GET
import com.dengjian.annotationpractice.myretrofit.annotation.POST
import com.dengjian.annotationpractice.myretrofit.annotation.Query
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Request
import java.lang.reflect.Method

class ServiceMethod(builder: Builder) {
    internal var baseUrl: HttpUrl? = null
    internal var callFactory: Call.Factory? = null
    internal var httpMethod: String? = builder.httpMethod
    internal var urlBuilder: HttpUrl.Builder? = null
    private var formBuild: FormBody.Builder? = null
    private val relativeUrl: String? = builder.relativeUrl
    private val hasBody: Boolean = builder.hasBody
    private val parameterHandler = builder.parameterHandler

    init {
        baseUrl = builder.myRetrofit.baseUrl
        callFactory = builder.myRetrofit.callFactory

        if (hasBody) {
            formBuild = FormBody.Builder();
        }
    }

    operator fun invoke(args: Array<Any>): Any? {
        checkNotNull(baseUrl) { "baseUrl required!" }
        checkNotNull(relativeUrl) { "URL params required!" }
        /**
         * 1  处理请求的地址与参数
         */
        var index = 0
        parameterHandler.forEach { handler ->
            handler.apply(this@ServiceMethod, args[index].toString())
            index++
        }

        // 获取最终请求地址
        if (null == urlBuilder) {
            urlBuilder = baseUrl!!.newBuilder(relativeUrl)
        }
        checkNotNull(urlBuilder) { "UrlBuilder failed!" }
        checkNotNull(httpMethod) { "httpMethod can not be null!" }
        val url = urlBuilder!!.build()

        // 请求体
        val formBody = formBuild?.build()

        val request = Request.Builder().url(url).method(httpMethod!!, formBody).build()
        return callFactory?.newCall(request)
    }

    // get请求,  把 k-v 拼到url里面
    fun addQueryParameter(key: String, value: String?) {
        checkNotNull(baseUrl) { "baseUrl required!" }
        checkNotNull(relativeUrl) { "URL params required!" }
        if (urlBuilder == null) {
            urlBuilder = baseUrl!!.newBuilder(relativeUrl)
        }
        urlBuilder!!.addQueryParameter(key, value)
    }

    // Post   把k-v 放到 请求体中
    fun addFiledParameter(key: String, value: String) {
        checkNotNull(formBuild) { "post must has formBuild!" }
        formBuild!!.add(key, value)
    }

    class Builder(val myRetrofit: MyRetrofit, private val method: Method) {
        internal var parameterHandler = ArrayList<ParameterHandler>()
        internal var httpMethod: String? = null
        internal var relativeUrl: String? = null
        internal var hasBody: Boolean = false
        // 获取方法上的所有的注解
        private val methodAnnotations: Array<Annotation> = method.annotations
        // 获得方法参数的所有的注解 (一个参数可以有多个注解,一个方法又会有多个参数)
        private val parameterAnnotations: Array<Array<Annotation>> = method.parameterAnnotations

        fun build(): ServiceMethod {
            /**
             * 1 解析方法上的注解, 只处理POST与GET
             */
            methodAnnotations.forEach { methodAnnotation ->
                if (methodAnnotation is POST) {
                    // 记录当前请求方式
                    httpMethod = "POST"
                    // 记录请求url的path
                    relativeUrl = methodAnnotation.value
                    hasBody = true

                } else if (methodAnnotation is GET) {
                    // 记录当前请求方式
                    httpMethod = "GET"
                    // 记录请求url的path
                    relativeUrl = methodAnnotation.value
                    hasBody = false
                }
            }

            /**
             * 2 解析方法参数的注解
             */
            val len = parameterAnnotations.size
            for (i in 0.until(len)) {
                // 一个参数上的所有的注解
                val annotations = parameterAnnotations[i]
                // 处理参数上的每一个注解
                annotations.forEach { annotation->
                    //todo 可以加一个判断:如果httpMethod是get请求,现在又解析到Filed注解,可以提示使用者使用Query注解
                    if (annotation is Field) {
                        // 得到注解上的value: 请求参数的key
                        val anValue = annotation.value
                        parameterHandler.add(ParameterHandler.FiledParameterHandler(anValue))
                    } else if (annotation is Query) {
                        val anValue = annotation.value
                        parameterHandler.add(ParameterHandler.QueryParameterHandler(anValue))
                    }
                }
            }

            return ServiceMethod(this)
        }
    }
}
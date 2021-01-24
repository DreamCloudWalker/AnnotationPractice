package com.dengjian.annotationpractice.myretrofit

abstract class ParameterHandler {
    abstract fun apply(serviceMethod: ServiceMethod, value: String)

    internal class QueryParameterHandler(var key: String) : ParameterHandler() {
        // serviceMethod: 回调
        override fun apply(
            serviceMethod: ServiceMethod,
            value: String
        ) {
            serviceMethod.addQueryParameter(key, value)
        }
    }

    internal class FiledParameterHandler(var key: String) : ParameterHandler() {
        override fun apply(
            serviceMethod: ServiceMethod,
            value: String
        ) {
            serviceMethod.addFiledParameter(key, value)
        }
    }
}
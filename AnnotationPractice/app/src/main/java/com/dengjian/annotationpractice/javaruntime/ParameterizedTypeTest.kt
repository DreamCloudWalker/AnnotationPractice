package com.dengjian.annotationpractice.javaruntime

import java.lang.reflect.ParameterizedType

/**
 * 具体的泛型类型，可以获得元数据中泛型签名类型(泛型真实类型)
 */
class ParameterizedTypeTest {
    val map: Map<String, String> = HashMap()

    companion object {

        @JvmStatic
        fun main(vararg args: String) {
            val field = ParameterizedTypeTest::class.java.getDeclaredField("map")
            println(field.genericType)
            val parameterizedType = field.genericType as ParameterizedType
            println(parameterizedType.rawType)
            parameterizedType.actualTypeArguments.forEach { type ->
                println(type)
            }
        }
    }
}
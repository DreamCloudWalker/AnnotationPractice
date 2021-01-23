package com.dengjian.annotationpractice.javaruntime

import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.WildcardType
import java.util.*
import kotlin.collections.HashSet

/**
 * 通配符泛型，获得上下限信息;
 */
class WildcardTypeTest {
    private val a: HashSet<out Number>? = null  // ? extends Number
    private val b: HashSet<in String>? = null   // ? super String

    companion object {

        @JvmStatic
        fun main(vararg args: String) {
            val fieldA = WildcardTypeTest::class.java.getDeclaredField("a")
            val fieldB = WildcardTypeTest::class.java.getDeclaredField("b")
            // 先拿到范型类型
            val parameterizedTypeA = fieldA.genericType as ParameterizedType
            val parameterizedTypeB = fieldB.genericType as ParameterizedType
            // 再从范型里拿到通配符类型
            val wildcardTypeA = (parameterizedTypeA.actualTypeArguments[0] as WildcardType)
            val wildcardTypeB = (parameterizedTypeB.actualTypeArguments[0] as WildcardType)
            // 方法测试
            println(wildcardTypeA.upperBounds[0])
            println(wildcardTypeB.lowerBounds[0])
            println(wildcardTypeA)
            println(wildcardTypeB)
        }
    }
}
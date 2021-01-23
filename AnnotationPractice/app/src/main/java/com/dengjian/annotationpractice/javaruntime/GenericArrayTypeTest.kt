package com.dengjian.annotationpractice.javaruntime

import java.lang.reflect.GenericArrayType

/**
 * 当需要描述的类型是泛型类的数组时，比如List[],Map[]，此接口会作为Type的实现。
 */
class GenericArrayTypeTest {
    var lists: Array<List<String>>? = null

    companion object {

        @JvmStatic
        fun main(vararg args: String) {
            val field = GenericArrayTypeTest::class.java.getDeclaredField("lists")
            val genericType = field.genericType as GenericArrayType
            println(genericType.genericComponentType)
        }
    }
}
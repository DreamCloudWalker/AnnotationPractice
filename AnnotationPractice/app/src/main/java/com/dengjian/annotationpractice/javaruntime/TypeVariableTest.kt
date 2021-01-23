package com.dengjian.annotationpractice.javaruntime

import java.io.Serializable
import java.lang.reflect.Field
import java.lang.reflect.TypeVariable

/**
 * 泛型类型变量。可以获取泛型上下限等信息
 */
class TypeVariableTest<K, V> where K : Comparable<*>, K : Serializable {
    var key: K? = null
    var value: V? = null

    companion object {

        @JvmStatic
        fun main(vararg args: String) {
            // 获取字段的类型
            val fk: Field = TypeVariableTest::class.java.getDeclaredField("key")
            val fv: Field = TypeVariableTest::class.java.getDeclaredField("value")
            val keyType: TypeVariable<*> = fk.genericType as TypeVariable<*>
            val valueType: TypeVariable<*> = fv.genericType as TypeVariable<*>
            // getName 方法
            println(keyType.name)
            println(valueType.name)
            // getGenericDeclaration 方法
            println(keyType.genericDeclaration)
            println(valueType.genericDeclaration)
            // getBounds 方法
            println("K的上界：")
            keyType.bounds.forEach { type ->
                println(type)
            }
            println("V的上界：")
            valueType.bounds.forEach { type ->
                println(type)
            }
        }
    }
}
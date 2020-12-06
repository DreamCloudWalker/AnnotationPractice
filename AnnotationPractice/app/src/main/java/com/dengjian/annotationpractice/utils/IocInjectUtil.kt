package com.dengjian.annotationpractice.utils

import android.app.Activity
import android.os.Parcelable
import android.text.TextUtils
import com.dengjian.annotationpractice.annotations.AutoWired
import java.util.*

class IocInjectUtil {
    companion object {
        fun inject(activity: Activity) {
            requireNotNull(activity) { "activity is null" }

            injectAutoWired(activity)
        }

        private fun injectAutoWired(activity: Activity) {
            val intent = activity.intent
            val bundle = intent.extras ?: return

            val clazz: Class<out Activity> = activity.javaClass // 对应getClass
            val fields = clazz.declaredFields
            fields.forEach { field ->
                val autoWired = field.getAnnotation(AutoWired::class.java)
                autoWired?.let {
                    val key = if (TextUtils.isEmpty(it.value)) field.name else it.value
                    if (bundle.containsKey(key)) {
                        var extraValue = bundle.get(key)
                        // Parcelable数组类型不能直接设置，其他的都可以
                        val componentType = field.type.componentType
                        // 当前属性是数组并且是 Parcelable（子类）数组
                        if (field.type.isArray && Parcelable::class.java.isAssignableFrom(componentType)) {
                            val objs = extraValue as Array<out Any>
                            // 创建对应类型的数组并由objects拷贝
                            extraValue = Arrays.copyOf<Any, Any>(objs, objs.size, field.type as Class<out Array<Any>>)
                        }

                        try {
                            field.isAccessible = true
                            field.set(activity, extraValue)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }
}
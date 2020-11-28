package com.dengjian.annotationpractice

import androidx.annotation.IntDef
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    companion object {
        private const val SUNDAY = 0
        private const val MONDAY = 1
        private const val TUESDAY = 2
        private const val WEDNESDAY = 3
        private const val THURSDAY = 4
        private const val FRIDAY = 5
        private const val SATURDAY = 6
    }
    private var currentDay = 0

    @IntDef(SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY)
    @Retention(AnnotationRetention.SOURCE)
    @Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
    annotation class WeekDay {

    }

    @Test
    fun test_annotation() {
        println("HelloAnnotation")
        setCurrentDay(222)
    }

    private fun setCurrentDay(@WeekDay day: Int) {
        currentDay = day
    }
}
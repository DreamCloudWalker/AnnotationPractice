package com.dengjian.annotationpractice.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dengjian.annotationpractice.annotations.AutoWired

class SecondActivity : BaseActivity() {
    @AutoWired
    private var mStringFromIntent: String = ""
    @AutoWired("testInt")
    private var mIntFromIntent: Int = 0
    @AutoWired("testLong")
    private var mLongFromIntent: Long = 0L
    @AutoWired
    private var mIntArrayFromIntent = listOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "mStringFromIntent = $mStringFromIntent, mIntFromIntent = $mIntFromIntent" +
                ", mLongFromIntent = $mLongFromIntent, mIntArrayFromIntent = $mIntArrayFromIntent")
    }
}
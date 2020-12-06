package com.dengjian.annotationpractice.view

import org.jetbrains.anko.intentFor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dengjian.annotationpractice.R

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transferIntArray = listOf<Int>(0, 1, 2, 3, 4, 5)
        startActivity(this.intentFor<SecondActivity>(
                "mStringFromIntent" to "testStringValue",
                "testInt" to 1,
                "testLong" to 1000L,
                "mIntArrayFromIntent" to transferIntArray
        ))
    }
}
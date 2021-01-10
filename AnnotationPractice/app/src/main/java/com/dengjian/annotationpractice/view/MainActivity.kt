package com.dengjian.annotationpractice.view

import org.jetbrains.anko.intentFor
import android.os.Bundle
import android.util.Log
import com.dengjian.annotationpractice.R
import com.dengjian.annotationpractice.dagger2.DaggerGlobalComponent
import com.dengjian.annotationpractice.dagger2.`object`.HttpObject
import com.dengjian.annotationpractice.dagger2.module.DatabaseModule
import com.dengjian.annotationpractice.dagger2.module.HttpModule
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var httpObject: HttpObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        DaggerGlobalComponent.create().inject(this)
        DaggerGlobalComponent.builder()
            .httpModule(HttpModule())
            .databaseModule(DatabaseModule())
            .build()
            .inject(this)
        Log.d(TAG, "httpObject = ${httpObject.hashCode()}")

        val transferIntArray = listOf<Int>(0, 1, 2, 3, 4, 5)
        startActivity(this.intentFor<SecondActivity>(
                "mStringFromIntent" to "testStringValue",
                "testInt" to 1,
                "testLong" to 1000L,
                "mIntArrayFromIntent" to transferIntArray
        ))
    }
}
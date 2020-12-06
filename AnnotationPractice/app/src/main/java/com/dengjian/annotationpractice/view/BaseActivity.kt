package com.dengjian.annotationpractice.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dengjian.annotationpractice.utils.IocInjectUtil

open class BaseActivity : AppCompatActivity() {
    @Suppress("HasPlatformType", "PropertyName")
    protected val TAG = javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IocInjectUtil.inject(this)
    }
}
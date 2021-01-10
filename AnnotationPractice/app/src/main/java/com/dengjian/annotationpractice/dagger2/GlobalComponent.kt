package com.dengjian.annotationpractice.dagger2

import com.dengjian.annotationpractice.dagger2.module.DatabaseModule
import com.dengjian.annotationpractice.dagger2.module.HttpModule
import com.dengjian.annotationpractice.view.MainActivity
import dagger.Component

@Component(modules = [HttpModule::class, DatabaseModule::class])
interface GlobalComponent {
    fun inject(activity: MainActivity)
}
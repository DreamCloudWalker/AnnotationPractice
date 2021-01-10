package com.dengjian.annotationpractice.dagger2.module

import com.dengjian.annotationpractice.dagger2.`object`.HttpObject
import dagger.Module
import dagger.Provides

@Module
class HttpModule {
    @Provides
    fun providerHttpObject() = HttpObject()
}
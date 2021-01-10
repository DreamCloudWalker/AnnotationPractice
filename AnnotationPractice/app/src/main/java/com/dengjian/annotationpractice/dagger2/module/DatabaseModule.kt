package com.dengjian.annotationpractice.dagger2.module

import com.dengjian.annotationpractice.dagger2.`object`.DatabaseObject
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun providerHttpObject(): DatabaseObject {
        return DatabaseObject()
    }
}
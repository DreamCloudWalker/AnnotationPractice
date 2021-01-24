package com.dengjian.annotationpractice.myretrofit.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class GET(val value: String = "")
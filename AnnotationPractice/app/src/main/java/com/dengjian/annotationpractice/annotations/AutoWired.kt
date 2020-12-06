package com.dengjian.annotationpractice.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class AutoWired(val value: String = "")
package com.dengjian.annotationpractice.myretrofit.api

import com.dengjian.annotationpractice.myretrofit.annotation.Field
import com.dengjian.annotationpractice.myretrofit.annotation.GET
import com.dengjian.annotationpractice.myretrofit.annotation.POST
import com.dengjian.annotationpractice.myretrofit.annotation.Query
import okhttp3.Call

interface WeatherApi {
    @POST("/v3/weather/weatherInfo")
    fun postWeather(
        @Field("city") city: String,
        @Field("key") key: String
    ): Call


    @GET("/v3/weather/weatherInfo")
    fun getWeather(
        @Query("city") city: String,
        @Query("key") key: String
    ): Call
}
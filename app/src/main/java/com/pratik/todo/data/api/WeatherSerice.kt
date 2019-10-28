package com.pratik.todo.data.api

import com.pratik.todo.data.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherSerice {
    @GET("data/2.5/weather")
    fun getWeather(@Query("q") name: String, @Query("APPID")  AppId: String ): Single<WeatherModel>
}
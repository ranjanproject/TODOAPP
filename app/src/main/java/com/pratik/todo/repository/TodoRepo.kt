package com.pratik.todo.repository

import com.pratik.todo.data.api.WeatherSerice
import com.pratik.todo.data.model.WeatherModel
import io.reactivex.Single

class TodoRepo: BaseRepo(){
   protected var service = retrofit.create(WeatherSerice::class.java)

   fun weatherData(name: String): Single<WeatherModel> = service.getWeather(name,
      "893fbf04b665cfba5b01ecab7ac869d4")

}
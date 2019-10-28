package com.pratik.todo.repository

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object RetrofitInstance {
    private lateinit var instance: Retrofit
    private lateinit var okHttpClient: OkHttpClient
    private fun createInstance(baseUrl: String): Retrofit {

        if (! ::okHttpClient.isInitialized) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization" , UUID.randomUUID().toString())
                        .build()
                    chain.proceed(newRequest)
                }
                .addInterceptor(interceptor)
                .build()
        }

        return Retrofit.Builder()
            .baseUrl("$baseUrl/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Synchronized
    private fun createInstance() {
        instance = createInstance("https://api.openweathermap.org")
    }

    fun getInstance(): Retrofit {
        if (! ::instance.isInitialized) createInstance()
        return instance
    }
}

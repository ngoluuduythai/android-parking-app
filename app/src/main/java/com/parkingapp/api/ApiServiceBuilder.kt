package com.parkingapp.api

import com.google.gson.Gson
import com.parkingapp.BuildConfig
import com.parkingapp.Debugger
import com.parkingapp.core.config.AppConfig.SERVER_TIMEOUT_READ
import com.parkingapp.core.config.AppConfig.SERVER_TIMEOUT_WRITE
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceBuilder(
        private val apiUri: String,
        private val gson: Gson) {

    fun build(): Retrofit {
        val httpClient = OkHttpClient.Builder()

        httpClient.readTimeout(SERVER_TIMEOUT_READ, TimeUnit.SECONDS)
        httpClient.writeTimeout(SERVER_TIMEOUT_WRITE, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) Debugger.attachToNetwork(httpClient)

        return Retrofit.Builder().baseUrl(apiUri)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .validateEagerly(true)
                .client(httpClient.build())
                .build()
    }
}

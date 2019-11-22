package com.shinhan.stockreward.api

import android.text.TextUtils
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiManager {
    companion object {
        private val BASE_URL = "http://10.3.17.150:3000"
        private val GSON_BUILDER = GsonBuilder()
        private val GSON = GSON_BUILDER.create()
        private val GSON_CONVERTER_FACTORY = GsonConverterFactory.create(GSON)


        private val HTTP_CLIENT = OkHttpClient.Builder()
            .addNetworkInterceptor( HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        private val RETROFIT = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(HTTP_CLIENT)
            .addConverterFactory(GSON_CONVERTER_FACTORY).build()

        val apiService = RETROFIT.create(ApiService::class.java)
    }
}

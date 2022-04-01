package com.example.smartkitchenandroid.api

import com.example.smartkitchenandroid.api.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val api: SmartKitchenApi by lazy {
        retrofit.create(SmartKitchenApi::class.java)
    }
}
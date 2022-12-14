package com.kaftanchikova.entrantandroid.api

import com.kaftanchikova.entrantandroid.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: EntrantApi by lazy {
        retrofit.create(EntrantApi::class.java)
    }

}
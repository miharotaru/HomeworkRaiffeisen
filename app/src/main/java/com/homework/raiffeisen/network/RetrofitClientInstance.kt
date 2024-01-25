package com.homework.raiffeisen.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance {
    companion object {
        private var retrofit: Retrofit?=null
    }

    private val BASE_URL="https://randomuser.me/"

    fun getRetrofitInstance(): Retrofit?{
        if(retrofit==null){
            retrofit= Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}
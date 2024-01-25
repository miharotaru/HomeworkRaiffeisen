package com.homework.raiffeisen.network

import retrofit2.Call
import retrofit2.http.GET
import com.homework.raiffeisen.classes.ResultFromAPI

interface ApiCallInstance {
    @GET("api/?page=0&results=20&seed=abc")
    fun getAllArticle(): Call<ResultFromAPI>
}
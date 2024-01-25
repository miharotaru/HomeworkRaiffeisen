package com.homework.raiffeisen.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.homework.raiffeisen.R
import com.homework.raiffeisen.classes.ResultFromAPI
import com.homework.raiffeisen.classes.User
import com.homework.raiffeisen.network.ApiCallInstance
import com.homework.raiffeisen.network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var userList= mutableListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getInformationAPI()
    }

    private fun getInformationAPI() {
        val api =
            RetrofitClientInstance().getRetrofitInstance()?.create(ApiCallInstance::class.java)

        if (api != null) {
            api.getAllArticle().enqueue(object : Callback<ResultFromAPI> {
                override fun onResponse(call: Call<ResultFromAPI>, response: Response<ResultFromAPI>) {
                    getListOfUser(response)
                }
                override fun onFailure(call: Call<ResultFromAPI>, t: Throwable) {
                    Log.d("API call", t.toString())
                }
            })
        } else {
            Log.d("API call", "API instance is null")
        }
    }

    private fun getListOfUser(response: Response<ResultFromAPI>) {
        if (response.code() == 200) {
            val body = response.body()
            if (body != null) {
                userList = body.results
                Log.d("List size from api", userList.size.toString())

            } else {
                Log.d("API response", "Response body is null")
            }
        } else {
            Log.d("API response", "API response not successful: ${response.code()}")
        }

    }

}
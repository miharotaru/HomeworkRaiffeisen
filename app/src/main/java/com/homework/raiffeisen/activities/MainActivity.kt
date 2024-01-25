package com.homework.raiffeisen.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.homework.raiffeisen.adapter.UserAdapter
import com.homework.raiffeisen.classes.ResultFromAPI
import com.homework.raiffeisen.classes.User
import com.homework.raiffeisen.databinding.ActivityMainBinding
import com.homework.raiffeisen.network.ApiCallInstance
import com.homework.raiffeisen.network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var userList = mutableListOf<User>()
    private var isLoading = false
    private var loadedPages = 0
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycleview()
        loadNextPage()
        onScrollListener()
    }

    private fun onScrollListener() {
        binding.recycleviewUserItems.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (!isLoading && loadedPages < 3
                    && (totalItemCount - visibleItemCount - firstVisibleItemPosition) < 3
                ) {
                    loadNextPage()
                }
            }
        })
    }

    private fun loadNextPage() {
        isLoading = true
        val api =
            RetrofitClientInstance().getRetrofitInstance()?.create(ApiCallInstance::class.java)
        if (api != null) {
            api.getAllArticle().enqueue(object : Callback<ResultFromAPI> {
                override fun onResponse(
                    call: Call<ResultFromAPI>,
                    response: Response<ResultFromAPI>
                ) {
                    if (response.code() == 200) {
                        getListOfUser(response)
                        isLoading = false
                    } else {
                        Log.d("API response", "API response not successful: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ResultFromAPI>, t: Throwable) {
                    Log.d("API call", t.toString())
                    isLoading = false
                }
            })
        } else {
            Log.d("API call", "API instance is null")
            isLoading = false
        }
    }

    private fun getListOfUser(response: Response<ResultFromAPI>) {
        val body = response.body()
        if (body != null) {
            userList.addAll(body.results)
            adapter.notifyDataSetChanged()
            loadedPages++
            Log.d("List size from API", userList.size.toString())
        } else {
            Log.d("API response", "Response body is null")
        }
    }

    private fun initRecycleview() {
        layoutManager = LinearLayoutManager(this)
        binding.recycleviewUserItems.layoutManager = layoutManager
        adapter = UserAdapter(userList as ArrayList<User>)
        binding.recycleviewUserItems.adapter = adapter
    }
}
package com.taskk.demo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taskk.demo.interfaces.Api
import com.taskk.demo.model.TopRatedModel
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TopRatedViewModel : ViewModel() {

    //this is the data that we will fetch asynchronously
    private var topList: MutableLiveData<List<TopRatedModel.Result>>? = null

    //we will call this method to get the data
    fun getUpcomingMovies(): LiveData<List<TopRatedModel.Result>> {
        //if the list is null
        if (topList == null) {
            topList = MutableLiveData<List<TopRatedModel.Result>>()
            //we will load it asynchronously from server in this method
            loadTopRatedMovies()
        }

        //finally we will return the list
        return topList as MutableLiveData<List<TopRatedModel.Result>>
    }


    //This method is using Retrofit to get the JSON data from URL
    private fun loadTopRatedMovies() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(Api::class.java!!)
        val call = api.getTopRated(Api.apiKey, 1)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) {
                        val type = object : TypeToken<TopRatedModel>() {

                        }.type
                        val gson = GsonBuilder().setLenient().create()
                        val data = gson.fromJson<TopRatedModel>(response.body()!!.string(), type)
                        topList!!.setValue(data.getResults())
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })
    }
}
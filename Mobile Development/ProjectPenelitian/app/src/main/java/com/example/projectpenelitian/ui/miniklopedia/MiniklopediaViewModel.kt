package com.example.projectpenelitian.ui.miniklopedia

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectpenelitian.api.response.DataItem
import com.example.projectpenelitian.api.response.MiniklopediaResponse
import com.example.projectpenelitian.utils.Event
import com.wensolution.storyapp.apiservice.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MiniklopediaViewModel : ViewModel() {

    private var parameter: String = ""

    fun setParameter(value: String) {
        parameter = value

        getListMiniklopedia(parameter)
    }

    private val _miniklopedia = MutableLiveData<MiniklopediaResponse>()
    val miniklopedia: LiveData<MiniklopediaResponse> = _miniklopedia

    private val _listMiniklopedia = MutableLiveData<List<DataItem>>()
    val listMiniklopedia: LiveData<List<DataItem>> = _listMiniklopedia

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()

    private fun getListMiniklopedia(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getMiniklopedia("Bearer $token")
        client.enqueue(object : Callback<MiniklopediaResponse> {
            override fun onResponse(
                call: Call<MiniklopediaResponse>,
                response: Response<MiniklopediaResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _miniklopedia.value = response.body()
                    _listMiniklopedia.value = response.body()?.data

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _snackbarText.value = Event("Data gagal dimuat")
                }
            }
            override fun onFailure(call: Call<MiniklopediaResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _snackbarText.value = Event("Data gagal dimuat")
            }
        })
    }

    companion object{
        private const val TAG = "MiniklopediaViewModel"
    }
}
package com.example.projectpenelitian.ui.miniklopedia

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectpenelitian.api.response.BookmarkDataItem
import com.example.projectpenelitian.api.response.BookmarkResponse
import com.example.projectpenelitian.utils.Event
import com.wensolution.storyapp.apiservice.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkViewModel : ViewModel() {

    private var parameter: String = ""

    fun setParameter(value: String) {
        parameter = value

//        getListBookmark(parameter)
    }

    var status = "true"

    private val _bookmark = MutableLiveData<BookmarkResponse>()
    val bookmark: LiveData<BookmarkResponse> = _bookmark

    private val _listBookmark = MutableLiveData<List<BookmarkDataItem>>()
    val listBookmark: LiveData<List<BookmarkDataItem>> = _listBookmark

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()

//    private fun getListBookmark(token: String) {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getBookmark("Bearer $token")
//        client.enqueue(object : Callback<BookmarkResponse> {
//            override fun onResponse(
//                call: Call<BookmarkResponse>,
//                response: Response<BookmarkResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    status = "true"
//                    _bookmark.value = response.body()
//                    _listBookmark.value = response.body()?.data
//
//                } else {
//                    status = "false"
//                    Log.e(TAG, "onFailure1: ${response.message()}")
//                    _snackbarText.value = Event("Data gagal dimuat")
//                }
//            }
//            override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure2: ${t.message.toString()}")
//                _snackbarText.value = Event("Data gagal dimuat")
//            }
//        })
//    }

    companion object{
        private const val TAG = "BookmarkViewModel"
    }
}
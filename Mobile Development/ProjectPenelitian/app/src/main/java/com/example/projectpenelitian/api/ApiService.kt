package com.wensolution.storyapp.apiservice

import com.example.projectpenelitian.api.response.AddBookmarkResponse
import com.example.projectpenelitian.api.response.AddMiniklopediaResponse
import com.example.projectpenelitian.api.response.BookmarkResponse
import com.example.projectpenelitian.api.response.DetailPlantResponse
import com.example.projectpenelitian.api.response.LoginResponse
import com.example.projectpenelitian.api.response.MessageResponse
import com.example.projectpenelitian.api.response.WeatherResponse
import com.example.projectpenelitian.data.dataClass.UserEditData
import com.example.projectpenelitian.data.dataClass.UserLoginData
import com.example.projectpenelitian.data.dataClass.UserRegisterData
import com.example.projectpenelitian.api.response.MiniklopediaResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("current")
    suspend fun weather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("key") key: String = "bad624e8960c4590a8f580bb7438f83d"
    ): Response<WeatherResponse>

    @POST("auth/login")
    suspend fun login(
        @Body requestBody: UserLoginData
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body requestBody: UserRegisterData
    ): MessageResponse

    @PUT("auth/user")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body requestBody: UserEditData
    ): MessageResponse

    @GET("plant")
    suspend fun detailPlant(
        @Header("Authorization") token: String,
        @Query("nama_tanaman") namaTanaman: String,
        @Query("jenis_penyakit") jenisPenyakit: String
    ): DetailPlantResponse

    @Multipart
    @POST("status")
    suspend fun addMiniklopedia(
        @Part("nama_tanaman") namaTanaman: String,
        @Part("jenis_penyakit") jenisPenyakit: String,
        @Part image: MultipartBody.Part,
        @Header("Authorization") token: String
    ): AddMiniklopediaResponse

    @Multipart
    @POST("bookmark")
    suspend fun addBookmark(
        @Part("nama_tanaman") namaTanaman: String,
        @Part("jenis_penyakit") jenisPenyakit: String,
        @Part image: MultipartBody.Part,
        @Header("Authorization") token: String
    ): AddBookmarkResponse

    @DELETE("bookmark/delete/{id}")
    suspend fun deleteBookmark(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): MessageResponse

    @GET("status")
    fun getMiniklopedia(
        @Header("Authorization") token: String,
    ): Call<MiniklopediaResponse>

//    @GET("bookmark")
//    fun getBookmark(
//        @Header("Authorization") token: String,
//    ): Call<BookmarkResponse>
    @GET("bookmark")
    suspend fun getBookmark(
        @Header("Authorization") token: String,
    ): BookmarkResponse
}
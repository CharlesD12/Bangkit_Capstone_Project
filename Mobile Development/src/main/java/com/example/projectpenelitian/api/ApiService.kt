package com.wensolution.storyapp.apiservice

import com.example.projectpenelitian.api.response.LoginResponse
import com.example.projectpenelitian.api.response.RegisterResponse
import com.example.projectpenelitian.api.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("current")
    suspend fun weather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("key") key: String = "bad624e8960c4590a8f580bb7438f83d"
    ): Response<WeatherResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse
}
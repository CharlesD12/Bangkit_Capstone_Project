package com.example.projectpenelitian.api.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse (
    val count: Long,
    val data: List<Datum>
)
data class Datum (
    @SerializedName("app_temp")
    val appTemp: Double,

    val aqi: Long,

    @SerializedName("city_name")
    val cityName: String,

    val clouds: Long,

    @SerializedName("country_code")
    val countryCode: String,

    val datetime: String,
    val dewpt: Double,
    val dhi: Double,
    val dni: Double,

    @SerializedName("elev_angle")
    val elevAngle: Double,

    val ghi: Double,
    val gust: Double,

    @SerializedName("h_angle")
    val hAngle: Long,

    val lat: Double,
    val lon: Double,

    @SerializedName("ob_time")
    val obTime: String,

    val pod: String,
    val precip: Long,
    val pres: Double,
    val rh: Long,
    val slp: Double,
    val snow: Long,

    @SerializedName("solar_rad")
    val solarRAD: Double,

    val sources: List<String>,

    @SerializedName("state_code")
    val stateCode: String,

    val station: String,
    val sunrise: String,
    val sunset: String,
    val temp: Double,
    val timezone: String,
    val ts: Long,
    val uv: Double,
    val vis: Long,
    val weather: Weather,

    @SerializedName("wind_cdir")
    val windCdir: String,

    @SerializedName("wind_cdir_full")
    val windCdirFull: String,

    @SerializedName("wind_dir")
    val windDir: Long,

    @SerializedName("wind_spd")
    val windSpd: Double
)

data class Weather (
    val icon: String,
    val description: String,
    val code: Long
)
package uz.yayra.otabek.common.weather

import com.google.gson.annotations.SerializedName

data class Coordinates4Response(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: Day,
    val city: City
)

data class Day(
    val dt: Long,
    val main: Main,
    val weather: Weather,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val rain: Rain,
    val sys: Sys2,
    @SerializedName("dt_txt")
    val dtTxt: String
)

data class Sys2(
    val pod: String
)

data class City(
    val id: Int,
    val name: String,
    val coordinates: Coordinates,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)
package uz.yayra.otabek.common.weather

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class CoordinatesResponse(
    @SerialName("coord")
    val coordinates: Coordinates? = null,
    @SerialName("weather")
    val weather: Weather? = null,
    @SerialName("base")
    val base: String,
    @SerialName("main")
    val main: Main? = null,
    @SerialName("visibility")
    val visibility: Int,
    @SerialName("wind")
    val wind: Wind? = null,
    @SerialName("rain")
    val rain: Rain? = null,
    @SerialName("snow")
    val snow: Snow? = null,
    @SerialName("clouds")
    val clouds: Clouds? = null,
    @SerialName("dt")
    val dt: Long,
    @SerialName("sys")
    val sys: Sys? = null,
    @SerialName("timezone")
    val timezone: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("cod")
    val cod: Int
)

data class Coordinates(
    @SerialName("lon")
    val lon: Double,
    @SerialName("lat")
    val lat: Double
)

data class Weather(
    val list: List<WeatherBody>
)

data class Main(
    @SerialName("temp")
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("temp_min")
    val tempMin: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    @SerialName("sea_level")
    val seaLevel: Int,
    @SerialName("grnd_level")
    val groundLevel: Int,
    @SerialName("temp_kf")
    val tempKf: Double?
)

data class Wind(
    @SerialName("speed")
    val speed: Double,
    @SerialName("deg")
    val deg: Double,
    @SerialName("gust")
    val gust: Double?
)

data class Rain(
    @SerialName("1h")
    val oneHour: Double
)

data class Clouds(
    @SerialName("all")
    val all: Int
)

data class Snow(
    @SerialName("1h")
    val oneHour: Double
)

data class Sys(
    @SerialName("type")
    val type: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("message")
    val message: String,
    @SerialName("country")
    val country: String,
    @SerialName("sunrise")
    val sunrise: Long,
    @SerialName("sunset")
    val sunset: Long
)

data class WeatherBody(
    @SerialName("id")
    val id: Int,
    @SerialName("main")
    val main: String,
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String
)
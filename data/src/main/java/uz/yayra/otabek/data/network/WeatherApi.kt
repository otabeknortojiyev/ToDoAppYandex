package uz.yayra.otabek.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.yayra.otabek.common.weather.Coordinates4Response
import uz.yayra.otabek.common.weather.CoordinatesResponse

interface WeatherApi {
  @GET("data/2.5/weather")
  suspend fun current(
    @Query("lat") lat: String, @Query("lon") lon: String, @Query("appid") appid: String
  ): Response<CoordinatesResponse>

  @GET("data/2.5/forecast")
  suspend fun forecast(
    @Query("lat") lat: String, @Query("lon") lon: String, @Query("appid") appid: String
  ): Response<Coordinates4Response>
}